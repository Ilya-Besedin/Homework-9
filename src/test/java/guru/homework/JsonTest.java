package guru.homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.homework.domain.JsonData;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JsonTest {

    @Test
    void jsonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonData jsondata = mapper.readValue(Paths.get("src/test/resources/clock.json").toFile(), JsonData.class);
        assertThat(jsondata.code).isEqualTo(200);
        assertThat(jsondata.item.russianName).contains("Лучшие часы");
        assertThat(jsondata.item.available).isEqualTo(true);
    }
}


