import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import UI.Console;

public class main {
    public static void main(String[] args) {
        System.out.println("hello");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "Config"
                );

        context.getBean(Console.class).run();

        System.out.println("bye");
    }
}
