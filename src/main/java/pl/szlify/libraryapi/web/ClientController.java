package pl.szlify.libraryapi.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.szlify.libraryapi.model.dto.ClientCreateDto;
import pl.szlify.libraryapi.model.dto.ClientDto;
import pl.szlify.libraryapi.service.ClientService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ClientDto addClient(@Valid @RequestBody ClientCreateDto clientCreateDto) {
        return clientService.addClient(clientCreateDto);
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        clientService.confirmRegistration(token);
        return "Registration confirmed";
    }
}
