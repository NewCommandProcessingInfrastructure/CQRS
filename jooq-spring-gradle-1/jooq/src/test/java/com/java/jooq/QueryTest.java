package com.java.jooq;

import com.java.jooq.data.AthleteDTO;
import com.java.jooq.generated.tables.records.AthleteRecord;
import com.java.jooq.generated.tables.records.CompetitionRecord;

import java.sql.Date;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

import static com.java.jooq.generated.Tables.ATHLETE;
import static com.java.jooq.generated.tables.Club.CLUB;
import static com.java.jooq.generated.tables.Competition.COMPETITION;
import static com.java.jooq.generated.tables.Organization.ORGANIZATION;
import static com.java.jooq.generated.tables.Series.SERIES;
import static org.assertj.core.api.Assertions.assertThat;

@JooqTest
public class QueryTest {

  @Autowired
  private DSLContext dsl;

  @BeforeEach
  void setup() {
    // Delete dependent tables first to avoid FK violations
    dsl.deleteFrom(ATHLETE).execute();
    dsl.deleteFrom(CLUB).execute();
    dsl.deleteFrom(COMPETITION).execute();
    dsl.deleteFrom(SERIES).execute();
    dsl.deleteFrom(ORGANIZATION).execute();

    // Insert organization
    dsl.insertInto(ORGANIZATION)
            .columns(ORGANIZATION.ID, ORGANIZATION.ORGANIZATION_KEY, ORGANIZATION.NAME, ORGANIZATION.OWNER)
            .values(1L, "DL", "Diamond League", "simon@martinelli.ch")
            .execute();

    // Insert series
    dsl.insertInto(SERIES)
            .columns(SERIES.ID, SERIES.NAME, SERIES.LOGO, SERIES.HIDDEN, SERIES.LOCKED, SERIES.ORGANIZATION_ID)
            .values(1L, "Diamond League 2022", null, false, false, 1L)
            .execute();

    // Insert competition
    dsl.insertInto(COMPETITION)
            .columns(COMPETITION.ID, COMPETITION.NAME, COMPETITION.COMPETITION_DATE,
                    COMPETITION.ALWAYS_FIRST_THREE_MEDALS, COMPETITION.MEDAL_PERCENTAGE,
                    COMPETITION.LOCKED, COMPETITION.SERIES_ID)
            .values(1L, "Athletissima", Date.valueOf("2022-08-26").toLocalDate(), true, 0, false, 1L)
            .execute();

    // Insert clubs
    dsl.insertInto(CLUB)
            .columns(CLUB.ID, CLUB.ABBREVIATION, CLUB.NAME, CLUB.ORGANIZATION_ID)
            .values(1L, "STB", "Stadtturnverein Bern", 1L)
            .execute();

    dsl.insertInto(CLUB)
            .columns(CLUB.ID, CLUB.ABBREVIATION, CLUB.NAME, CLUB.ORGANIZATION_ID)
            .values(2L, "LSU", "Louisiana State University", 1L)
            .execute();

    // Insert athlete
    dsl.insertInto(ATHLETE)
            .columns(ATHLETE.ID, ATHLETE.FIRST_NAME, ATHLETE.LAST_NAME, ATHLETE.GENDER,
                    ATHLETE.YEAR_OF_BIRTH, ATHLETE.CLUB_ID, ATHLETE.ORGANIZATION_ID)
            .values(1000L, "Armand", "Duplantis", "m", 1999, 2L, 1L)
            .execute();
  }

  @Test
  void find_competitions() {
    Result<CompetitionRecord> competitions = dsl
            .selectFrom(COMPETITION)
            .fetch();

    assertThat(competitions).hasSize(1);
  }

  @Test
  void insert_athlete() {
    Long id = dsl.insertInto(ATHLETE)
            .columns(ATHLETE.FIRST_NAME, ATHLETE.LAST_NAME, ATHLETE.GENDER, ATHLETE.YEAR_OF_BIRTH, ATHLETE.CLUB_ID, ATHLETE.ORGANIZATION_ID)
            .values("Mujinga", "Kambundji", "f", 1992, 1L, 1L)
            .returningResult(ATHLETE.ID)
            .fetchOneInto(Long.class);

    assertThat(id).isEqualTo(1);
  }

  @Test
  void updatable_record() {
    AthleteRecord athlete = dsl.newRecord(ATHLETE);
    athlete.setFirstName("Mujinga");
    athlete.setLastName("Kambundji");
    athlete.setGender("f");
    athlete.setYearOfBirth(1992);
    athlete.setClubId(1L);
    athlete.setOrganizationId(1L);

    athlete.store();

    assertThat(athlete.getId()).isNotNull();
  }

  @Test
  void projection() {
    Result<Record3<String, String, String>> athletes = dsl
            .select(ATHLETE.FIRST_NAME, ATHLETE.LAST_NAME, CLUB.NAME)
            .from(ATHLETE)
            .join(CLUB).on(CLUB.ID.eq(ATHLETE.CLUB_ID))
            .fetch();

    assertThat(athletes)
            .hasSize(1)
            .first()
            .satisfies(athlete -> {
              assertThat(athlete.get(ATHLETE.FIRST_NAME)).isEqualTo("Armand");
              assertThat(athlete.get(ATHLETE.LAST_NAME)).isEqualTo("Duplantis");
              assertThat(athlete.get(CLUB.NAME)).isEqualTo("Louisiana State University");
            });
  }

  @Test
  void projection_using_java_record() {
    List<AthleteDTO> athletes = dsl
            .select(ATHLETE.FIRST_NAME, ATHLETE.LAST_NAME, CLUB.NAME)
            .from(ATHLETE)
            .join(CLUB).on(CLUB.ID.eq(ATHLETE.CLUB_ID))
            .fetchInto(AthleteDTO.class);

    assertThat(athletes)
            .hasSize(1)
            .first()
            .satisfies(athlete -> {
              assertThat(athlete.firstName()).isEqualTo("Armand");
              assertThat(athlete.lastName()).isEqualTo("Duplantis");
              assertThat(athlete.clubName()).isEqualTo("Louisiana State University");
            });
  }

//  @Test
//  void implicit_join() {
//    List<AthleteDTO> athletes = dsl
//            .select(ATHLETE.FIRST_NAME, ATHLETE.LAST_NAME, ATHLETE.club().NAME)
//            .from(ATHLETE)
//            .fetchInto(AthleteDTO.class);
//
//    assertThat(athletes)
//            .hasSize(1)
//            .first()
//            .satisfies(athlete -> {
//              assertThat(athlete.firstName()).isEqualTo("Armand");
//              assertThat(athlete.lastName()).isEqualTo("Duplantis");
//              assertThat(athlete.clubName()).isEqualTo("Louisiana State University");
//            });
//  }


//  @Test
//  void nested() {
//    List<AthleteWithClubDTO> athletes = dsl
//            .select(ATHLETE.FIRST_NAME, ATHLETE.LAST_NAME,
//                    row(ATHLETE.club().ABBREVIATION, ATHLETE.club().NAME).mapping(ClubDTO::new))
//            .from(ATHLETE)
//            .fetch(mapping(AthleteWithClubDTO::new));
//
//    assertThat(athletes)
//            .hasSize(1)
//            .first()
//            .satisfies(athlete -> {
//              assertThat(athlete.firstName()).isEqualTo("Armand");
//              assertThat(athlete.lastName()).isEqualTo("Duplantis");
//              assertThat(athlete.club().name()).isEqualTo("Louisiana State University");
//            });
//  }

  @Test
  void delete() {
    int deletedRows = dsl
            .deleteFrom(ATHLETE)
            .where(ATHLETE.ID.eq(1000L))
            .execute();

    assertThat(deletedRows).isEqualTo(1);
  }
}
