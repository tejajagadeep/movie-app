package documentation.single.swagger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@ApiIgnore
public class SwaggerController {
    @GetMapping
    public void custom(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
}
