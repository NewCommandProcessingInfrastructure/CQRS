package jooq.data;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

@JooqTest
class JOOQ_Test {

  @Autowired
  private DSLContext dsl;

  @Test
  void appHasAGreeting() {
//    Result<CompetitionRecord> competitions =
//            dsl
//                    .selectFrom(COMPETITION)
//                    .fetch();
  }
}