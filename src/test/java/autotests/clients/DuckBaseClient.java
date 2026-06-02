package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static autotests.payloads.DuckPayload.getDuckBody;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckBaseClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        String path = "/api/duck/create";
        runner.$(http().client(duckService)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(getDuckBody(color, height, material, sound, wingsState)));
    }

    public void duckDelete(TestCaseRunner runner, String id) {
        String path = "/api/duck/delete";
        runner.$(http().client(duckService)
                .send()
                .delete(path)
                .queryParam("id", id));
    }

    public void duckProperties(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/properties";
        runner.$(http().client(duckService)
                .send()
                .get(path)
                .queryParam("id", id));
    }

    public String getDuckIdFromResponse(TestCaseRunner runner) {
        runner.$(http().client(duckService)
                .receive()
                .response()
                .message()
                .extract(fromBody().expression("$.id", "duckId")));

        return "${duckId}";
    }

    public void validateOkResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }
}
