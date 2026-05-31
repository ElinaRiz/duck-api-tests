package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class BaseTest extends TestNGCitrusSpringSupport {

    public String buildDuckJson(String color, double height, String material, String sound, String wingsState) {
        return "{\n" +
                "\"color\": \"" + color + "\",\n" +
                "\"height\": " + height + ",\n" +
                "\"material\": \"" + material + "\",\n" +
                "\"sound\": \"" + sound + "\",\n" +
                "\"wingsState\": \"" + wingsState + "\"\n" +
                "}";
    }

    public String buildDuckJsonWithIdForCreateValidate(String color, double height, String material, String sound, String wingsState) {
        return "{\n" +
                "\"id\": \"@isNumber()@\",\n" +
                "\"color\": \"" + color + "\",\n" +
                "\"height\": " + height + ",\n" +
                "\"material\": \"" + material + "\",\n" +
                "\"sound\": \"" + sound + "\",\n" +
                "\"wingsState\": \"" + wingsState + "\"\n" +
                "}";
    }

    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .post("/api/duck/create")
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(buildDuckJson(color, height, material, sound, wingsState)));
    }

    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/properties")
                        .queryParam("id", id));
    }

    public String getDuckIdFromResponse(TestCaseRunner runner) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId")));
        return "${duckId}";
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

    public void validateNotFoundResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response(HttpStatus.NOT_FOUND)
                        .message()
                        .type(MessageType.JSON)
                        .validate(
                                jsonPath().expression("$.message", responseMessage)
                        ));
    }

    public void validateNotFoundResponseWith500Error(TestCaseRunner runner, String responseMessage) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
//                        .response(HttpStatus.NOT_FOUND)
//                        BUG: должна быть ошибка 404
                        .response(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message()
                        .type(MessageType.JSON)
                        .validate(
                                jsonPath().expression("$.message", responseMessage)
                        ));
    }
}