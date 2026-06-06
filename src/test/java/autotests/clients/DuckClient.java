package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    public void createDuck(TestCaseRunner runner, Object duckProperties) {
        String path = "/api/duck/create";
        sendPostMethod(runner, path, duckProperties);
    }

    public void deleteDuck(TestCaseRunner runner, String id) {
        String path = String.format("/api/duck/delete?id=%s", id);
        sendDeleteMethod(runner, path);
    }

    public void getDuckProperties(TestCaseRunner runner, String id) {
        String path = String.format("/api/duck/action/properties?id=%s", id);
        sendGetMethod(runner, path);
    }

    public void sendPostMethod(TestCaseRunner runner, String path, Object duckProperties) {
        runner.$(http().client(duckService)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(duckProperties, new ObjectMapper())));
    }

    public void sendDeleteMethod(TestCaseRunner runner, String path) {
        runner.$(http().client(duckService)
                .send()
                .delete(path));
    }

    public void sendGetMethod(TestCaseRunner runner, String path) {
        runner.$(http().client(duckService)
                .send()
                .get(path));
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

    public void validateOkResponseByResource(TestCaseRunner runner, String resourcePath) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(resourcePath)));
    }

    public void validateOkResponse(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }
}
