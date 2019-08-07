package tacos;

import models.tables.records.PostsRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static models.Tables.POSTS;

@Service
@Transactional
public class TacoService {
    private final DSLContext dsl;

    @Autowired
    public TacoService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public DSLContext getDsl() {
        return dsl;
    }

    public PostsRecord getOne() {
        return dsl.select().from(POSTS).fetchOneInto(POSTS);
    }
}
