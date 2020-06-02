package core.model.domain;


import org.hibernate.annotations.Generated;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public abstract class Client_ extends BaseEntity_ {

    public static volatile SetAttribute<Client, Rental> rentals;
    public static volatile SingularAttribute<Client, String> firstName;
    public static volatile SingularAttribute<Client, String> lastName;
    public static volatile SingularAttribute<Client, Integer> age;

}
