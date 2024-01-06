package pl.szlify.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "VERIFICATION_TOKEN")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = ClientEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "client_id")
    private ClientEntity client;

    public VerificationToken(String token, ClientEntity client) {
        this.token = token;
        this.client = client;
    }
}
