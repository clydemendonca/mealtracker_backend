package com.calorie.mealtracker.respository;

import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.repository.MealtrackerUserJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MealtrackerUserJpaRespositoryTests {

    @Autowired
    private MealtrackerUserJpaRepository mealtrackerUserJpaRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        MealtrackerUser givenUser = new MealtrackerUser("admin", "asd", "mytest", MealtrackerUser.Role.ADMIN);

        // when
        MealtrackerUser found = mealtrackerUserJpaRepository.findByUsername(givenUser.getUsername());

        // then
        assertThat(found.getUsername())
                .isEqualTo(givenUser.getUsername());
    }

}
