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
public class Client extends BaseEntity<Long> {

    private String firstName;
    private String lastName;
    private int age;

}
