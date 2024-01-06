package pl.szlify.libraryapi.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.szlify.libraryapi.model.dto.SubscriptionCreateDto;
import pl.szlify.libraryapi.model.dto.SubscriptionDto;
import pl.szlify.libraryapi.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public SubscriptionDto addSubscription(@Valid @RequestBody SubscriptionCreateDto subscriptionCreateDto) {
        return subscriptionService.addSubscription(subscriptionCreateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
