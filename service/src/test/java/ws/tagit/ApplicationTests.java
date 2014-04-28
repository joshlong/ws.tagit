package ws.tagit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {

    @Test
    public void contextLoads() {

        List<UserTag> userTagList = tagTemplate.tags("cows,dogs,#overused,'i love the belgian jug', cow")
                .stream()
                .map(t -> tagRepository.save(new UserTag("joshlong",
                        Integer.toString(t.getCleanTag().hashCode()), t.getCleanTag(), t.getTag())))
                .collect(Collectors.toList());

        userTagList.stream().forEach(System.err::println);

        Assert.assertEquals( 5,tagRepository.findByUsername("joshlong").size());

        tagRepository.findByUsernameAndContentId("joshlong" , "99644").forEach(System.err::println);

    }

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TagTemplate tagTemplate;

}
