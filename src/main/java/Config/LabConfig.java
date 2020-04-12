package Config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"repository", "Service", "UI","Model.validators"})
public class LabConfig {


}
