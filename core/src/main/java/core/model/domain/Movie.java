package core.model.domain;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@Proxy(lazy=false)
public class Movie extends BaseEntity<Long> {
    private String title;
    private  String genre;
    private int yearOfRelease;
    private String director;
    private String mainStar;

}
