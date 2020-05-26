package core.model.domain;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Movie.class)
public abstract class Movie_ extends core.model.domain.BaseEntity_ {

	public static volatile SingularAttribute<Movie, Integer> yearOfRelease;
	public static volatile SingularAttribute<Movie, String> mainStar;
	public static volatile SingularAttribute<Movie, String> director;
	public static volatile SingularAttribute<Movie, String> genre;
	public static volatile ListAttribute<Movie, Rental> rentals;
	public static volatile SingularAttribute<Movie, String> title;

	public static final String YEAR_OF_RELEASE = "yearOfRelease";
	public static final String MAIN_STAR = "mainStar";
	public static final String DIRECTOR = "director";
	public static final String GENRE = "genre";
	public static final String RENTALS = "rentals";
	public static final String TITLE = "title";

}

