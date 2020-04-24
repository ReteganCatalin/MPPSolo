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
public class Rental extends BaseEntity<Long> {

    private Long ClientID;
    private Long MovieID;
    private int year;
    private int day;
    private int month;

}
