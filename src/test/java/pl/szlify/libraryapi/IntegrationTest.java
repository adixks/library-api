package pl.szlify.libraryapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.szlify.libraryapi.model.BookEntity;
import pl.szlify.libraryapi.model.ClientEntity;
import pl.szlify.libraryapi.model.SubscriptionEntity;
import pl.szlify.libraryapi.model.VerificationToken;
import pl.szlify.libraryapi.model.dto.*;
import pl.szlify.libraryapi.repository.BookRepository;
import pl.szlify.libraryapi.repository.ClientRepository;
import pl.szlify.libraryapi.repository.SubscriptionRepository;
import pl.szlify.libraryapi.repository.VerificationTokenRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @BeforeEach
    public void setupDatabase() {
        BookEntity bookEntity = new BookEntity()
                .setTitle("book")
                .setAuthor("author")
                .setCategory("category")
                .setAddedDate(new Date());
        entityManager.persist(bookEntity);

        ClientEntity clientEntity = new ClientEntity()
                .setFirstName("Adrian")
                .setLastName("Abc")
                .setEmail("adrian.abc@example.com")
                .setEnabled(true);
        entityManager.persist(clientEntity);

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity()
                .setClientEntity(clientEntity)
                .setCategory("category")
                .setAuthor("author");
        entityManager.persist(subscriptionEntity);

        VerificationToken verificationToken = new VerificationToken("sampleToken", clientEntity);
        entityManager.persist(verificationToken);
    }

    @AfterEach
    public void cleanupDatabase() {
        entityManager.createNativeQuery("DELETE FROM SUBSCRIPTION").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE SUBSCRIPTION AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM BOOK").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE BOOK AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM VERIFICATION_TOKEN").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE VERIFICATION_TOKEN AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM CLIENT").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE CLIENT AUTO_INCREMENT = 1").executeUpdate();
    }

    @Test
    public void addClient_ReturnsNewClient() throws Exception {
        ClientCreateDto clientCreateDto = new ClientCreateDto()
                .setFirstName("Alicja")
                .setLastName("Kowalska")
                .setEmail("alicja.kowalska@example.com");

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alicja"))
                .andExpect(jsonPath("$.lastName").value("Kowalska"))
                .andExpect(jsonPath("$.email").value("alicja.kowalska@example.com"));
        List<ClientEntity> clientEntities = clientRepository.findAll();
        assertEquals(2, clientEntities.size());
    }

    @Test
    public void confirmRegistration_ReturnsConfirmationMessage() throws Exception {
        String token = "sampleToken";

        mockMvc.perform(get("/api/v1/client/registrationConfirm").param("token", token))
                .andExpect(status().isOk())
                .andExpect(content().string("Registration confirmed"));
    }

    @Test
    public void confirmRegistration_ReturnsInvalidTokenMessage() throws Exception {
        String invalidToken = "invalidToken";

        mockMvc.perform(get("/api/v1/client/registrationConfirm").param("token", invalidToken))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid token"));
    }

    @Test
    public void addBook_ReturnsNewBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto().setTitle("title").setAuthor("author").setCategory("category");

        mockMvc.perform(post("/api/v1/books").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.author").value("author"))
                .andExpect(jsonPath("$.category").value("category"));
        List<BookEntity> bookEntities = bookRepository.findAll();
        assertEquals(2, bookEntities.size());
    }

    @Test
    public void addSubscription_ReturnsNewSubscription() throws Exception {
        SubscriptionCreateDto subscriptionCreateDto = new SubscriptionCreateDto().setClientId(1L).setCategory("category").setAuthor("author");

        mockMvc.perform(post("/api/v1/subscriptions").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(subscriptionCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value("1"))
                .andExpect(jsonPath("$.category").value("category"))
                .andExpect(jsonPath("$.author").value("author"));
        List<SubscriptionEntity> subscriptionEntities = subscriptionRepository.findAll();
        assertEquals(2, subscriptionEntities.size());
    }

    @Test
    public void deleteSubscription_ReturnsNoContent() throws Exception {
        SubscriptionCreateDto subscriptionCreateDto = new SubscriptionCreateDto().setClientId(1L).setCategory("category").setAuthor("author");
        MvcResult result = mockMvc.perform(post("/api/v1/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscriptionCreateDto)))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        SubscriptionDto returnedSubscription = objectMapper.readValue(response, SubscriptionDto.class);

        mockMvc.perform(delete("/api/v1/subscriptions/" + returnedSubscription.getId()))
                .andExpect(status().isNoContent());

        List<SubscriptionEntity> subscriptionEntities = subscriptionRepository.findAll();
        assertEquals(1, subscriptionEntities.size());
    }
}
