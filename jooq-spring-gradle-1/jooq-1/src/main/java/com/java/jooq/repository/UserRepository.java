package com.java.jooq.repository;

import static com.java.jooq.generated.tables.UserTable.USER_TABLE;

import com.java.jooq.entity.UserData;
import com.java.jooq.generated.tables.records.UserTableRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

  private final DSLContext dsl;

  public Long saveNewUser(UserData newUserData) {
    UserTableRecord record = dsl.insertInto(USER_TABLE,
                    USER_TABLE.USERNAME,
                    USER_TABLE.EMAIL,
                    USER_TABLE.FIRSTNAME,
                    USER_TABLE.LASTNAME,
                    USER_TABLE.AGE)
            .values(newUserData.getUsername(),
                    newUserData.getEmail(),
                    newUserData.getFirstname(),
                    newUserData.getLastname(),
                    newUserData.getAge())
            .returning()
            .fetchOne();

    return record != null ? record.getId() : 0L;
  }
}
