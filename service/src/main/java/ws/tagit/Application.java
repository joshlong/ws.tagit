package ws.tagit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    TagTemplate tagTemplate() {
        return new TagTemplate();
    }
}

@RestController("/tags")
class TagRestController {

    private final TagTemplate tagTemplate;

    @Autowired
    TagRestController(TagTemplate tagTemplate) {
        this.tagTemplate = tagTemplate;
    }

    @RequestMapping(method = RequestMethod.POST)
    Collection<Tag> tags(@RequestParam String tags) {
        return this.tagTemplate.tags(tags);
    }

    // todo persist tags and link tags to a user identity so that i can ask for all tags for a given user matching correlation ID $X

}