package autotests.clients.duckClients;

import autotests.clients.DuckBaseClient;
import com.consol.citrus.TestCaseRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckCreateClient extends DuckBaseClient {

    public String validateOkResponseAndGetDuckId(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage)
                .extract(fromBody().expression("$.id", "duckId")));

        return "${duckId}";
    }

    public String getDuckBodyWithIdMatcher(String color, double height, String material, String sound, String wingsState) {
        return "{\n" +
                "\"id\": \"@isNumber()@\",\n" +
                "\"color\": \"" + color + "\",\n" +
                "\"height\": " + height + ",\n" +
                "\"material\": \"" + material + "\",\n" +
                "\"sound\": \"" + sound + "\",\n" +
                "\"wingsState\": \"" + wingsState + "\"\n" +
                "}";
    }
}
