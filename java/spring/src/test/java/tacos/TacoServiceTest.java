package tacos;

import models.tables.records.PostsRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TacoServiceTest {
    @Autowired)
    private TacoService tacoService;

    @Test public void testGetAll() throws Exception {
        tacoService.getDsl().execute("truncate table oss.POSTS;");
        PostsRecord record = tacoService.getOne();
        assertThat(record, is(nullValue()));
    }
}