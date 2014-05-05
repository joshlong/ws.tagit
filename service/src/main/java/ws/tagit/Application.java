package ws.tagit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ws.tagit.parser.TagTemplate;

import javax.persistence.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Request OAuth authorization:
 * <code>
 * curl -X POST -vu android-tags:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=password&username=joshl&grant_type=password&scope=write&client_secret=123456&client_id=android-tags"
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
    CommandLineRunner commandLineRunner(TagService tagService) {
        return (args) -> {
            tagService.createTags("joshl", "1", "coffee,code,'spring framework', AOP, JDBC, frameworks, 'open-source'");
            tagService.createTags("rodj", "2", "'spring framework',jdbc,aop,'dependency injection', #bigdata");

        };
    }

    @Bean
    FilterRegistrationBean corsFilter(@Value("${tagit.origin:}") String origin) {
        return new FilterRegistrationBean(new Filter() {
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                    throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;
                String method = request.getMethod();
                response.setHeader("Access-Control-Allow-Origin", "http://localhost:9000");
                response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
                response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
                if ("OPTIONS".equals(method)) {
                    response.setStatus(HttpStatus.OK.value());
                } else {
                    chain.doFilter(req, res);
                }
            }

            public void init(FilterConfig filterConfig) {
            }

            public void destroy() {
            }
        });
    }

    @Bean
    TagTemplate tagTemplate() {
        return new TagTemplate();
    }

    @Configuration
    static class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        AccountRepository accountRepository;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            UserDetailsService userDetailsService =
                    (username) -> accountRepository.findByUsername(username)
                            .map(a -> new User(a.username, a.password, true, true, true, true, AuthorityUtils.createAuthorityList("USER", "write")))
                            .orElseThrow(() -> new UsernameNotFoundException("couldn't find the user " + username));
            auth.userDetailsService(userDetailsService);
        }
    }


    @Configuration
    @EnableResourceServer
    @EnableAuthorizationServer
    static class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

        String applicationName = "tags";

        // This is required for password grants, which we specify below as one of the  {@literal authorizedGrantTypes()}.
        @Autowired
        AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("android-" + applicationName)
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                    .authorities("ROLE_USER")
                    .scopes("write")
                    .resourceIds(applicationName)
                    .secret("123456");
        }
    }

}

@Service
@Transactional
class TagService {

    Collection<Tag> readTags(String principalName) {
        return accountRepository.findByUsername(principalName).get().tags;
    }

    Collection<Tag> createTags(String principalName, String contentId, String tags) {
        Account account = accountRepository.findByUsername(principalName).get();

        return tagTemplate.tags(tags).stream()
                .map(t -> tagRepository.save(new ws.tagit.Tag(account, t.getCleanTag(), t.getTag(), contentId)))
                .collect(Collectors.toList());
    }

    @Autowired
    TagTemplate tagTemplate;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    AccountRepository accountRepository;
}

@RestController
@RequestMapping("/tags")
class TagRestController {

    @Autowired
    TagService tagService;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Collection<Tag>> post(
            Principal principal,
            @RequestParam(required = false) String contentId,
            @RequestParam String tags) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
                ServletUriComponentsBuilder.fromCurrentRequest().build().toUri());

        return new ResponseEntity<>(
                this.tagService.createTags(principal.getName(), contentId, tags), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<Collection<Tag>> get(Principal principal) {
        return new ResponseEntity<>(
                tagService.readTags(principal.getName()), HttpStatus.OK);
    }
}

interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String n);
}

interface TagRepository extends JpaRepository<ws.tagit.Tag, Long> {
    Optional<ws.tagit.Tag> findByAccountUsernameAndContentId(String u, String c);

    Optional<ws.tagit.Tag> findByAccountUsername(String u);
}

@Entity
class Account {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    public Set<Tag> tags = new HashSet<>();

    @Id
    @GeneratedValue
    public Long id;


    public String username;

    Account(String name, String password) {
        this.username = name;
        this.password = password;
    }

    Account() {
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @JsonIgnore
    public String password;


}

@Entity
class Tag {
    @Column(nullable = false)
    public String canonicalTag;
    public String contentId;
    public String tag;

    @Id
    @GeneratedValue
    public Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    public Account account;

    Tag() {
    }

    @Override
    public String toString() {
        return "Tag{" +
                "canonicalTag='" + canonicalTag + '\'' +
                ", contentId='" + contentId + '\'' +
                ", tag='" + tag + '\'' +
                ", id=" + id +
                ", account=" + account +
                '}';
    }

    Tag(Account a, String normalizedTag, String tag, String contentId) {
        this.account = a;
        this.canonicalTag = normalizedTag;
        this.contentId = contentId;
        this.tag = tag;
    }
}
