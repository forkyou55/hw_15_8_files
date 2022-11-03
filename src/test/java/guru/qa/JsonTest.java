package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.model.RaceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class JsonTest {
    ClassLoader classLoader = JsonTest.class.getClassLoader();

    @Test
    @DisplayName("Parsing json file by Jackson library")
    void jsonTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("race.json");
        ObjectMapper objectMapper = new ObjectMapper();
        String[] name = new String[]{"Bilbo Baggins", "Aragorn"};
        String[] rac = new String[]{"Hobbits", "Men"};
        Integer[] age = new Integer[]{131, 210};
        String[] wri = new String[]{"John Ronald Reuel Tolkien"};
        RaceTest race = objectMapper.readValue(is, RaceTest.class);
        assertThat(race.getNames()).isEqualTo(name);
        assertThat(race.getRace()).isEqualTo(rac);
        assertThat(race.getAges()).isEqualTo(age);
        assertThat(race.getWriter()).isEqualTo(wri);
        assertThat(race.getIsGoodCharacter()).isEqualTo(true);
    }
}
