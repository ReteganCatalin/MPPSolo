package core.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="movie")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true,exclude = {"rentals"})
@ToString(callSuper = true,exclude = {"rentals"})
@Builder
@NamedEntityGraphs({
        @NamedEntityGraph(name = "movieWithRentals",
                attributeNodes = @NamedAttributeNode(value = "rentals")),
        @NamedEntityGraph(name = "movieWithRentalsAndClient",
                attributeNodes = @NamedAttributeNode(value = "rentals",
                        subgraph = "movieWithClient"),
                subgraphs = @NamedSubgraph(name = "movieWithClient",
                        attributeNodes = @NamedAttributeNode(value =
                                "client")))
})
public class Movie extends BaseEntity<Long> {
    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Column(name = "genre", nullable = false, length = 40)
    private  String genre;

    @Column(name = "yearofrelease", nullable = false, length = 40)
    private int yearOfRelease;
    @Column(name = "director", nullable = false, length = 40)
    private String director;
    @Column(name = "mainstar", nullable = false, length = 40)
    private String mainStar;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rental> rentals;



}
