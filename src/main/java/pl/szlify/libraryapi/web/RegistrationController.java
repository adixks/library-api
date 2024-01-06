package pl.szlify.libraryapi.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.szlify.libraryapi.model.ClientEntity;
import pl.szlify.libraryapi.model.VerificationToken;
import pl.szlify.libraryapi.repository.ClientRepository;
import pl.szlify.libraryapi.repository.VerificationTokenRepository;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class RegistrationController {

    private final ClientRepository clientRepository;
    private final VerificationTokenRepository tokenRepository;

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "Invalid token";
        }

        ClientEntity client = verificationToken.getClient();
        client.setEnabled(true);
        clientRepository.save(client);
        return "Registration confirmed";
    }
}
