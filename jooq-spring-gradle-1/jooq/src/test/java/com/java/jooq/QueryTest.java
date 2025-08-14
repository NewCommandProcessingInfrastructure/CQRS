package com.java.jooq;

import com.java.jooq.generated.tables.records.CompetitionRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

import static com.java.jooq.generated.tables.Competition.COMPETITION;
import static org.assertj.core.api.Assertions.assertThat;

@JooqTest
public class QueryTest {

  @Autowired
  private DSLContext dsl;

  @Test
  void find_competitions() {
    Result<CompetitionRecord> competitions = dsl
            .selectFrom(COMPETITION)
            .fetch();

    assertThat(competitions).hasSize(1);
  }
}
