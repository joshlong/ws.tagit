package ws.tagit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Request OAuth authorization:
 * <code>
 * curl -X POST -vu android-tags:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=password&username=joshlong&grant_type=password&scope=write&client_secret=123456&client_id=android-tags"
 * </code>
 * <p/>
 * <p/>
 * Use the access_token returned in the previous request to make the authorized request to the protected endpoint:
 * <code>curl -v POST http://127.0.0.1:8080/tags --data "tags=cows,dogs"  -H "Authorization: Bearer 66953496-fc5b-44d0-9210-b0521863ffcb"</code>
 * <code>curl http://localhost:8080/tags  -H "Authorization: Bearer 66953496-fc5b-44d0-9210-b0521863ffcb"</code>
 *
 * @author Josh Long
 */

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

    @Configuration
    @EnableWebSecurity
    static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Override
        protected UserDetailsService userDetailsService() {
            return (username) -> {
                boolean valid = true;
                return new org.springframework.security.core.userdetails.User(
                        username, "password", valid, valid, valid, valid, AuthorityUtils.createAuthorityList("USER", "write"));
            };
        }

        @Bean
        @Override
        public UserDetailsService userDetailsServiceBean() throws Exception {
            return super.userDetailsServiceBean();
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.requestMatchers()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated();
        }
    }


    @Configuration
    @EnableResourceServer
    @EnableAuthorizationServer
    static class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

        private String applicationName = "tags";

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(this.authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("android-tags")
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                    .authorities("ROLE_USER")
                    .scopes("write")
                    .resourceIds(applicationName)
                    .secret("123456");
        }
    }


}

@RestController
class HelloController {

    @RequestMapping("/hi")
    String hello() {
        return "Hi";
    }
}

@RestController("/tags")
class TagRestController {

    @Autowired
    TagTemplate tagTemplate;

    @Autowired
    TagRepository tagRepository;

    @RequestMapping(method = RequestMethod.POST)
    Collection<UserTag> tags(Principal principal,
                             @RequestParam(required = false) String contentId,
                             @RequestParam String tags) {

        return this.tagTemplate.tags(tags)
                .stream()
                .map(t -> tagRepository.save(
                        new UserTag(principal.getName(), contentId, t.getCleanTag(), t.getTag())))
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<UserTag> userTags(Principal principal) {
        return this.tagRepository.findByUsername(principal.getName());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{tagId}")
    Collection<UserTag> userAndContentTags(Principal principal, @PathVariable String tagId) {
        return this.tagRepository.findByUsernameAndContentId(principal.getName(), tagId);
    }

}

interface TagRepository extends JpaRepository<UserTag, Long> {

    List<UserTag> findByUsernameAndContentId(String u, String c);

    List<UserTag> findByUsername(String u);
}


@javax.persistence.Entity
class UserTag {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String canonicalTag;

    private String sourceTag;
    private String contentId;

    @Override
    public String toString() {
        return "UserTag{" +
                "username='" + username + '\'' +
                ", canonicalTag='" + canonicalTag + '\'' +
                ", sourceTag='" + sourceTag + '\'' +
                ", contentId='" + contentId + '\'' +
                ", id=" + id +
                '}';
    }

    @Id
    @GeneratedValue
    private Long id;

    UserTag() {
    }

    UserTag(String username, String contentId, String canonicalTag, String sourceTag) {
        this.username = username;
        this.canonicalTag = canonicalTag;
        this.sourceTag = sourceTag;
        this.contentId = contentId;
    }

    public String getUsername() {
        return username;
    }

    public String getCanonicalTag() {
        return canonicalTag;
    }

    public String getSourceTag() {
        return sourceTag;
    }

    public String getContentId() {
        return contentId;
    }

    public Long getId() {
        return id;
    }
}
/*

@Component
class SimpleCORSFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}*/
