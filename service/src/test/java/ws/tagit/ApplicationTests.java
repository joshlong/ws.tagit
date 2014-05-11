package ws.tagit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ws.tagit.parser.TagTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class ApplicationTests {

    @Autowired
    TagTemplate tagTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TagRepository tagRepository;

    @Test
    public void testAccountsLoaded() throws Exception {
        assert accountRepository.findByUsername("rodj").get() != null;
    }

    @Test
    public void testSavingTags() throws Exception {

        String username = "joshl";
        accountRepository.findByUsername(username).ifPresent(a -> {
            a.tags.forEach(tagRepository::delete);
            a.tags.forEach(System.out::println);
            tagTemplate.tags("spring,cows,dogs,#twitterstyle,'complex tags'")
                    .stream()
                    .map(t -> tagRepository.save(new Tag(a, t.getCleanTag(), t.getTag(), Integer.toString(t.hashCode()))))
                    .forEach(System.err::println);
            accountRepository.findByUsername( username).ifPresent( aa -> {
                assert aa.tags.size() >= 5;
            });


        });

        Account account = accountRepository.findByUsername(username).get();


    }


}
