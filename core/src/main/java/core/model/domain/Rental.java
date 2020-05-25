package core.model.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name="rental")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true,exclude = {"client", "movie"})
@ToString(callSuper=true,exclude = {"client", "movie"})
@Builder
public class Rental extends BaseEntity<Long> {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "clientid")
    private Client client;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movieid")
    private  Movie movie;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "day", nullable = false)
    private int day;
    @Column(name = "month", nullable = false)
    private int month;

}
