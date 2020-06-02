package core.model.domain;

import org.hibernate.annotations.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.awt.print.Book;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rental.class)
public abstract class Rental_ extends core.model.domain.BaseEntity_ {

    public static volatile SingularAttribute<Rental, Client> client;
    public static volatile SingularAttribute<Rental, Movie> movie;
    public static volatile SingularAttribute<Rental, Integer> day;
    public static volatile SingularAttribute<Rental, Integer> month;
    public static volatile SingularAttribute<Rental, Integer> year;

}
