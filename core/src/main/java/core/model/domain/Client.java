package core.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="client")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true,exclude = {"rentals"})
@ToString(callSuper = true,exclude = {"rentals"})
@Builder
@NamedEntityGraphs({
        @NamedEntityGraph(name = "clientWithRentals",
        attributeNodes = @NamedAttributeNode(value = "rentals")),
        @NamedEntityGraph(name = "clientWithRentalsAndMovie",
                attributeNodes = @NamedAttributeNode(value = "rentals",
                        subgraph = "clientWithMovie"),
                subgraphs = @NamedSubgraph(name = "clientWithMovie",
                        attributeNodes = @NamedAttributeNode(value =
                                "movie")))
})
public class Client extends BaseEntity<Long> {

    @Column(name = "firstname", nullable = false, length = 40)
    private String firstName;
    @Column(name = "lastname", nullable = false, length = 40)
    private String lastName;
    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rental> rentals;

}
