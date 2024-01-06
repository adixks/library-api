package pl.szlify.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "SUBSCRIPTION")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clientEntity_id")
    private ClientEntity clientEntity;

    private String category;
    private String author;
}
