package documentation.single.swagger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class SwaggerController {
    @GetMapping
    public String hello(){
        return "Redirect to /swagger-ui.html to get centralized swagger";
    }
}
