package autotests.clients.actionClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckSwimClient extends DuckClient {

    public void duckSwim(TestCaseRunner runner, String id) {
        String path = String.format("/api/duck/action/swim?id=%s", id);
        sendGetMethod(runner, path);
    }

    public void validateNotFoundResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.NOT_FOUND)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }

    public void validateNotFoundResponseByResource(TestCaseRunner runner, String resourcePath) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.NOT_FOUND)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(resourcePath)));
    }

    public void validateNotFoundResponse(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.NOT_FOUND)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }
}
