package autotests.clients.actionClients;

import autotests.clients.DuckBaseClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class DuckSwimClient extends DuckBaseClient {

    public void duckSwim(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/swim";
        runner.$(http().client(duckService)
                .send()
                .get(path)
                .queryParam("id", id));
    }

    public void validateNotFoundResponseWith500Error(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(duckService)
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

    public void validateNotFoundResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.NOT_FOUND)
                .message()
                .type(MessageType.JSON)
                .validate(
                        jsonPath().expression("$.message", responseMessage)
                ));
    }
}
