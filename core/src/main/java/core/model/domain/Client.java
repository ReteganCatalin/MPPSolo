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

    public static ClientBuilder builder() {
        return new ClientBuilder();
    }

    public static class ClientBuilder {
        private String firstName;
        private String lastName;
        private int age;
        private List<Rental> rentals;

        ClientBuilder() {
        }

        public ClientBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ClientBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ClientBuilder age(int age) {
            this.age = age;
            return this;
        }

        public ClientBuilder rentals(List<Rental> rentals) {
            this.rentals = rentals;
            return this;
        }

        public Client build() {
            return new Client(firstName, lastName, age, rentals);
        }

        public String toString() {
            return "Client.ClientBuilder(firstName=" + this.firstName + ", lastName=" + this.lastName + ", age=" + this.age + ", rentals=" + this.rentals + ")";
        }
    }
}
