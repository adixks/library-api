package pl.szlify.libraryapi.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
