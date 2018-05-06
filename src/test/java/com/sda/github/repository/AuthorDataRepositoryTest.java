package com.sda.github.repository;

import com.sda.github.domain.AuthorData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorDataRepositoryTest {

    @Autowired
    private AuthorDataRepository authorDataRepository;

    @Test
    public void shouldAddDataToRepository() {
        // given
        AuthorData authorData = new AuthorData();
        authorData.setDate(LocalDateTime.now());
        authorData.setEmail("email@email.com");
        authorData.setName("name");

        // when
        authorDataRepository.save(authorData);

        // then
        List<AuthorData> underTest = authorDataRepository.findAll();
        System.out.println(underTest);
        AuthorData author = underTest.get(0);
        assertThat(underTest.size()).isEqualTo(1);
        assertThat(author.getDate()).isEqualTo(authorData.getDate());
        assertThat(author.getId()).isNotNull();
    }
}
