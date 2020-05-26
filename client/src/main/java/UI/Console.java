//package UI;
//
//import core.model.domain.Client;
//import core.model.domain.Movie;
//import core.model.domain.Rental;
//import core.model.exceptions.DataTypeException;
//import core.model.exceptions.MyException;
//import UI.options.ClientOptions;
//import UI.options.MovieOptions;
//import UI.options.RentalOptions;
//import UI.utils.Commands;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import web.converter.*;
//import web.dto.*;
//import web.dto.StatMoviesDto;
//
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Component
//public class Console {
//
//    private final RestTemplate restTemplate;
//
//    private Map<String, Runnable> fctLinks=new HashMap<>();
//    private Commands commands=new Commands();
//    private final String baseURL = "http://localhost:8082/api";
//    private ClientConverter clientConverter=new ClientConverter();
//    private MovieConverter movieConverter=new MovieConverter();
//    private RentalConverter rentalConverter=new RentalConverter();
//    public Console()
//    {
//        this.initFunctionLinks();
//        AnnotationConfigApplicationContext context =
//                new AnnotationConfigApplicationContext(
//                        "config"
//                );
//
//        restTemplate = context.getBean(RestTemplate.class);
//
//    }
//
//    private void initFunctionLinks() {
//        fctLinks.put(ClientOptions.ADD.getCmdMessage(), this::uiAddClient);
//        fctLinks.put(ClientOptions.PRINT.getCmdMessage(), this::uiPrintAllClients);
//        fctLinks.put(ClientOptions.SORT.getCmdMessage(), this::uiPrintAllClientsSorted);
//        fctLinks.put(ClientOptions.FILTER.getCmdMessage(), this::uiFilterClientsByName);
//        fctLinks.put(ClientOptions.DELETE.getCmdMessage(), this::uiDeleteClient);
//        fctLinks.put(ClientOptions.UPDATE.getCmdMessage(), this::uiUpdateClient);
//        fctLinks.put(ClientOptions.STAT.getCmdMessage(), this::uiStatOldestClients);
//        fctLinks.put(MovieOptions.ADD.getCmdMessage(), this::uiAddMovie);
//        fctLinks.put(MovieOptions.PRINT.getCmdMessage(), this::uiPrintAllMovie);
//        fctLinks.put(MovieOptions.SORT.getCmdMessage(), this::uiPrintAllMoviesSorted);
//        fctLinks.put(MovieOptions.FILTER.getCmdMessage(), this::uiFilterMovieByTitle);
//        fctLinks.put(MovieOptions.DELETE.getCmdMessage(), this::uiDeleteMovie);
//        fctLinks.put(MovieOptions.UPDATE.getCmdMessage(), this::uiUpdateMovie);
//        fctLinks.put(MovieOptions.STAT.getCmdMessage(), this::uiStatMostRichYearsInMovies);
//        fctLinks.put(RentalOptions.ADD.getCmdMessage(), this::uiAddRental);
//        fctLinks.put(RentalOptions.PRINT.getCmdMessage(), this::uiPrintAllRentals);
//        fctLinks.put(RentalOptions.SORT.getCmdMessage(), this::uiPrintAllRentalsSorted);
//        fctLinks.put(RentalOptions.FILTER.getCmdMessage(), this::uiFilterRentalsByYear);
//        fctLinks.put(RentalOptions.DELETE.getCmdMessage(), this::uiDeleteRental);
//        fctLinks.put(RentalOptions.UPDATE.getCmdMessage(), this::uiUpdateRental);
//        fctLinks.put(RentalOptions.STAT.getCmdMessage(), this::uiStatMonthsOfMostRentedMovie);
//    }
//
//
//
//    private void printMenu() {
//        for (Map.Entry<Integer, String> com : commands.getCommands().entrySet()) {
//            String line = String.format("%4s. %s", com.getKey(), com.getValue());
//            System.out.println(line);
//        }
//
//    }
//
//    private void uiFilterClientsByName() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Input Client Name: ");
//        String name = scanner.nextLine();
//        try {
//            ClientsDto clients=restTemplate.getForObject(baseURL+"/filterClients/{name}",ClientsDto.class,name);
//            clients.getClients().forEach(client->{Client real_client= clientConverter.convertDtoToModel(client);
//                System.out.println(real_client);});
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void uiDeleteClient() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Input Client Id: ");
//        String input = scanner.nextLine();
//
//        long id;
//        try {
//            id = Long.parseLong(input);
//        } catch (NumberFormatException E) {
//            throw new DataTypeException();
//        }
//        try {
//            restTemplate.delete(baseURL+"/clients/{id}",id);
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void uiUpdateClient() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Input Old Client Id: ");
//        String input = scanner.nextLine();
//
//
//        System.out.println("Input New Client First Name: ");
//        String fName = scanner.nextLine();
//
//        System.out.println("Input New Client Last Name: ");
//        String lName = scanner.nextLine();
//
//        System.out.println("Input New Client Age: ");
//        String ageStr = scanner.nextLine();
//
//        int age;
//        long id;
//
//        try {
//            age = Integer.parseInt(ageStr);
//            id = Long.parseLong(input);
//        } catch (NumberFormatException e) {
//            throw new DataTypeException();
//        }
//
//        Client client = Client.builder().age(age).firstName(fName).lastName(lName).build();
//        client.setId(id);
//        try {
//            restTemplate.put(baseURL+"/clients",client);
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void uiDeleteRental() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Input Rental Id: ");
//        String input = scanner.nextLine();
//
//        long id;
//        try {
//            id = Long.parseLong(input);
//        } catch (NumberFormatException E) {
//            throw new DataTypeException();
//        }
//        try {
//            //rentalService.deleteRental(id);
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void uiFilterRentalsByYear() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Input renting year: ");
//        String yearString = scanner.nextLine();
//
//        int year;
//        try {
//            year = Integer.parseInt(yearString);
//        } catch (NumberFormatException E) {
//            throw new DataTypeException();
//        }
//        try {
//            RentalsDto rentals=restTemplate.getForObject(baseURL+"/filterRentals/{year}",RentalsDto.class,year);
//            rentals.getClients().forEach(rental->{Rental real_rental= rentalConverter.convertDtoToModel(rental);
//                System.out.println(real_rental);});
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void uiUpdateRental() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Input Rental ID:");
//        String rentalID = scanner.nextLine();
//
//        System.out.println("Input new Rental Year: ");
//        String yearString = scanner.nextLine();
//
//        System.out.println("Input new Rental Month: ");
//        String monthString = scanner.nextLine();
//
//        System.out.println("Input new Rental Day: ");
//        String dayString = scanner.nextLine();
//
//        int day;
//        int month;
//        int year;
//        long movieId = 0L;
//        long clientId = 0L;
//        long id;
//        try {
//            day = Integer.parseInt(dayString);
//            month = Integer.parseInt(monthString);
//            year = Integer.parseInt(yearString);
//            id = Long.parseLong(rentalID);
//        } catch (NumberFormatException e) {
//            throw new DataTypeException();
//        }
//
//        Rental rental = Rental.builder().ClientID(clientId).MovieID(movieId).day(day).month(month).year(year).build();
//        rental.setId(id);
//        try {
//            restTemplate.put(baseURL+"/rentals",rental);
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void uiAddRental() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Input Client ID: ");
//        String clientID = scanner.nextLine();
//
//        System.out.println("Input Movie ID: ");
//        String MovieID = scanner.nextLine();
//
//        System.out.println("Input Rental Year: ");
//        String yearString = scanner.nextLine();
//
//        System.out.println("Input Rental Month: ");
//        String monthString = scanner.nextLine();
//
//        System.out.println("Input Rental Day: ");
//        String dayString = scanner.nextLine();
//
//        int day;
//        int month;
//        int year;
//        long movieId;
//        long clientId;
//        long id=0L;
//        try {
//            day = Integer.parseInt(dayString);
//            month = Integer.parseInt(monthString);
//            year = Integer.parseInt(yearString);
//            clientId = Long.parseLong(clientID);
//            movieId = Long.parseLong(MovieID);
//        } catch (NumberFormatException e) {
//            throw new DataTypeException();
//        }
//
//        Rental rental = Rental.builder().ClientID(clientId).MovieID(movieId).day(day).month(month).year(year).build();
//        rental.setId(id);
//        try {
//            restTemplate.postForObject(baseURL+"/rentals",rental,RentalDto.class);
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void uiPrintAllRentals() {
//        RentalsDto rentals=restTemplate.getForObject(
//                baseURL+"/rentals"
//                , RentalsDto.class);
//        rentals.getClients().forEach(rental->{Rental real_rental= rentalConverter.convertDtoToModel(rental);
//            System.out.println(real_rental);});
//    }
//
//
//
//    private void uiStatOldestClients() {
//        System.out.println("Top 5 oldest Clients: ");
//        ClientsDto clients=restTemplate.getForObject(
//                baseURL+"/statClients"
//                , ClientsDto.class);
//        IntStream.range(0, 5)
//                .mapToObj(index ->clients.getClients().get(index) )
//                .forEach(client -> System.out.println("Age: " + client.getAge() +
//                        "\nName: " + client.getFirstName() + " " + client.getLastName() + "\n"
//                ));
//    }
//
//    private void uiStatMostRichYearsInMovies() {
//        System.out.println("The most rich years in movies are: ");
//        StatMoviesDto stats=restTemplate.getForObject(
//                baseURL+"/statMovies"
//                , StatMoviesDto.class);
//        stats.getMovies().forEach(movies->{;
//            System.out.println("Year "+movies.getYear()+"\nMovies"+movies.getTitles()+"\n");});
//    }
//
//    private void uiStatMonthsOfMostRentedMovie() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Input Release Year: ");
//        String yearString = scanner.nextLine();
//        System.out.println("Input Client Least Age: ");
//        String ageString = scanner.nextLine();
//        int age;
//        int release_year;
//        try {
//            release_year = Integer.parseInt(yearString);
//            age = Integer.parseInt(ageString);
//        } catch (NumberFormatException e) {
//            throw new DataTypeException();
//        }
//        StatRentalDto properties=StatRentalDto.builder().ClientLeastAge(age).YearOfRelease(release_year).build();
//        RentalsDto rentals=restTemplate.postForObject(baseURL+"/statRentals",properties,RentalsDto.class);
//        MoviesDto movies=restTemplate.getForObject(
//                baseURL+"/movies"
//                , MoviesDto.class);
//        List<MovieDto> movieDto =movies.getClients();
//        String title="";
//        for(int index=0;index<movieDto.size();index++)
//        {
//            if(movieDto.get(index).getId().equals(rentals.getClients().get(0).getMovieID()))
//            {
//                title=movieDto.get(index).getTitle();
//            }
//
//        }
//
//        System.out.println("Most rented Movie of the year " + yearString + " " + title);
//        System.out.println("The rental months of the most rented movie by clients older than:" + ageString + " years");
//        rentals.getClients().stream()
//                .map(rental -> rental.getMonth())
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
//                .entrySet()
//                .stream()
//                .sorted(((o1, o2) -> -1 * o1.getValue().compareTo(o2.getValue())))
//                .forEach(entity -> System.out.println(entity.getKey()));
//    }
//
//    private void uiUpdateMovie() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Input Old Movie Id: ");
//        String input = scanner.nextLine();
//
//        System.out.println("Input Movie Title: ");
//        String title = scanner.nextLine();
//
//        System.out.println("Input Movie Year: ");
//        String yearStr = scanner.nextLine();
//
//        System.out.println("Input Movie Main Star: ");
//        String mainStar = scanner.nextLine();
//
//        System.out.println("Input Movie Director: ");
//        String director = scanner.nextLine();
//
//        System.out.println("Input Movie Genre: ");
//        String genre = scanner.nextLine();
//
//        int year;
//        long id;
//
//        try {
//            year = Integer.parseInt(yearStr);
//            id = Long.parseLong(input);
//        } catch (NumberFormatException e) {
//            throw new DataTypeException();
//        }
//
//        Movie movie = Movie.builder().director(director).genre(genre).title(title).mainStar(mainStar).yearOfRelease(year).build();
//        movie.setId(id);
//        try {
//           restTemplate.put(baseURL+"/movies",movie);
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void uiAddMovie() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Input Movie Title: ");
//        String title = scanner.nextLine();
//
//        System.out.println("Input Movie Year: ");
//        String yearStr = scanner.nextLine();
//
//        System.out.println("Input Movie Main Star: ");
//        String mainStar = scanner.nextLine();
//
//        System.out.println("Input Movie Director: ");
//        String director = scanner.nextLine();
//
//        System.out.println("Input Movie Genre: ");
//        String genre = scanner.nextLine();
//
//        int year;
//        long id=0L;
//        try {
//            year = Integer.parseInt(yearStr);
//        } catch (NumberFormatException e) {
//            throw new DataTypeException();
//        }
//
//        Movie movie = Movie.builder().director(director).genre(genre).title(title).mainStar(mainStar).yearOfRelease(year).build();
//        movie.setId(id);
//        try {
//            restTemplate.postForObject(
//                    baseURL+"/movies"
//                    ,movie
//                    ,MovieDto.class);
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void uiFilterMovieByTitle() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Input Movie Title: ");
//        String title = scanner.nextLine();
//        try {
//            MoviesDto movies=restTemplate.getForObject(baseURL+"/filterMovies/{title}",MoviesDto.class,title);
//            movies.getClients().forEach(movie->{Movie real_movie= movieConverter.convertDtoToModel(movie);
//                System.out.println(real_movie);});
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private void uiPrintAllMovie() {
//        MoviesDto movies=restTemplate.getForObject(
//                baseURL+"/movies"
//                , MoviesDto.class);
//        movies.getClients().forEach(movie->{Movie real_movie= movieConverter.convertDtoToModel(movie);
//            System.out.println(real_movie);});
//    }
//
//    private void uiPrintAllClientsSorted() {
//        //Sort sort = Sort.by("FirstName","LastName").ascending().and(Sort.by("Age").descending());
//        List<String> directions=new ArrayList<>();
//        directions.add("Asc");
//        directions.add("Asc");
//        directions.add("Desc");
//        List<String> columns=new ArrayList<>();
//        columns.add("FirstName");
//        columns.add("LastName");
//        columns.add("Age");
//        SortDto sortDto=SortDto.builder().Directions(directions).Columns(columns).build();
//        ClientsDto clients=restTemplate.postForObject(
//                baseURL+"/sortClients",
//                sortDto,
//                ClientsDto.class);
//        clients.getClients().forEach(client->{Client real_client= clientConverter.convertDtoToModel(client);
//            System.out.println(real_client);});
//    }
//
//
//    private void uiPrintAllMoviesSorted() {
//        List<String> criterias = Arrays.asList("Id", "MainStar", "Title", "Genre", "YearOfRelease", "Director");
//        Scanner scanner = new Scanner(System.in);
//        List<String> directions=new ArrayList<>();
//        List<String> columns=new ArrayList<>();
//        try {
//            while (true) {
//
//                System.out.println("Pick column be careful it should be one of(Id,MainStar,Title,Director,Genre,YearOfRelease):");
//                String columnName = scanner.nextLine();
//                if (criterias.stream().filter(criteria -> criteria == columnName).collect(Collectors.toList()).size() == 0) {
//                    System.out.println("Wrong column next time be more careful !");
//                    break;
//                }
//                System.out.println("Pick order Desc or Asc: ");
//                String order = scanner.nextLine();
//                if (order.equals("done")){ break;}
//                else if(order.equals("Asc") || order.equals("Desc"))
//                {
//                    directions.add(order);
//                    columns.add(columnName);
//                } else {
//                    System.out.println("Wrong input!");
//                    break;
//                }
//
//
//            }
//            SortDto sort=SortDto.builder().Columns(columns).Directions(directions).build();
//            MoviesDto movies=restTemplate.postForObject(baseURL+"/sortedMovies",sort,MoviesDto.class);
//            movies.getClients().forEach(movie->{Movie real_movie= movieConverter.convertDtoToModel(movie);
//                System.out.println(real_movie);});
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//        private void uiPrintAllRentalsSorted () {
//            //Sort sort = Sort.by( "Day").ascending().and(Sort.by("Month").descending());
//            List<String> directions=new ArrayList<>();
//            directions.add("Asc");
//            directions.add("Desc");
//            List<String> columns=new ArrayList<>();
//            columns.add("Day");
//            columns.add("Month");
//            SortDto sortDto=SortDto.builder().Directions(directions).Columns(columns).build();
//            RentalsDto rentals=restTemplate.postForObject(
//                    baseURL+"/sortRentals",
//                    sortDto,
//                    RentalsDto.class);
//            rentals.getClients().forEach(rental->{Rental real_rental= rentalConverter.convertDtoToModel(rental);
//                System.out.println(real_rental);});
//        }
//
//        private void uiDeleteMovie () {
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("Input Movie Id: ");
//            String input = scanner.nextLine();
//
//            long id;
//            try {
//                id = Long.parseLong(input);
//            } catch (NumberFormatException E) {
//                throw new DataTypeException();
//            }
//            try {
//                restTemplate.delete(baseURL+"/movies/{id}", id);
//            } catch (MyException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//
//        private void uiPrintAllClients () {
//            ClientsDto clients=restTemplate.getForObject(
//                    baseURL+"/clients"
//                    , ClientsDto.class);
//            clients.getClients().forEach(client->{Client real_client= clientConverter.convertDtoToModel(client);
//                    System.out.println(real_client);});
//        }
//
//        private void uiAddClient () {
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("Input Client First Name: ");
//            String fName = scanner.nextLine();
//
//            System.out.println("Input Client Last Name: ");
//            String lName = scanner.nextLine();
//
//            System.out.println("Input Client Age: ");
//            String ageStr = scanner.nextLine();
//
//            int age;
//            long id=0L;
//            try {
//                age = Integer.parseInt(ageStr);
//            } catch (NumberFormatException e) {
//                throw new DataTypeException();
//            }
//
//            ClientDto client = ClientDto.builder().firstName(fName).lastName(lName).age(age).build();
//            client.setId(id);
//            try {
//                restTemplate.postForObject(
//                        baseURL+"/clients"
//                        ,client
//                        ,ClientDto.class);
//            } catch (MyException e) {
//                System.out.println(e.getMessage());
//            }
//
//        }
//
//        public void run () {
//
//            Scanner scanner = new Scanner(System.in);
//
//            while (true) {
//
//                printMenu();
//
//                System.out.println("Enter input");
//                String input = scanner.nextLine();
//                int key;
//
//                try {
//                    key = Integer.parseInt(input);
//                } catch (NumberFormatException e) {
//                    System.out.println("Command needs to be a number");
//                    continue;
//                }
//
//                if (!commands.containsCommand(key)) {
//                    System.out.println("Invalid Option");
//                }
//
//                String cmd = commands.getCommandValue(key);
//
//                if (cmd.equals("Exit")) {
//                    return;
//                }
//
//                if (!fctLinks.containsKey(cmd)) {
//                    System.out.println("Functionality not yet implemented!");
//                    continue;
//                }
//
//                try {
//                    fctLinks.get(cmd).run();
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }
//
//    }
//
//
