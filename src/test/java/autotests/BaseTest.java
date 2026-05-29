package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class BaseTest extends TestNGCitrusSpringSupport {

    public String buildDuckJson(String color , double height , String material , String sound , String wingsState) {
        return "{\n" +
                "\"color\": \"" + color + "\",\n" +
                "\"height\": \"" + height + "\",\n" +
                "\"material\": \"" + material + "\",\n" +
                "\"sound\": \"" + sound + "\",\n" +
                "\"wingsState\": \"" + wingsState + "\"\n" +
                "}";
    }

    public void createDuck(TestCaseRunner runner, String color , double height , String material , String sound , String wingsState) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .post("/api/duck/create")
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(buildDuckJson(color, height, material, sound, wingsState)));
    }

    public void saveDuckIdFromResponse(TestCaseRunner runner) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId")));
    }

    public void validateOkResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(responseMessage));
    }
}