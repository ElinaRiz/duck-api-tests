package autotests.clients.duckClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckCreateClient extends DuckClient {

    @Step("Отправка запроса для создания уточки")
    public void createDuck(TestCaseRunner runner, Object duckProperties) {
        String path = "/api/duck/create";
        sendPostMethod(runner, path, duckProperties);
    }

    @Step("Отправка POST запроса")
    public void sendPostMethod(TestCaseRunner runner, String path, Object duckProperties) {
        runner.$(http().client(duckService)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(duckProperties, new ObjectMapper())));
    }

    @Step("Валидация ответа на запрос с помощью передачи строки и получение id уточки")
    public String validateResponseAndGetDuckId(TestCaseRunner runner, HttpStatus httpStatus, String responseMessage) {
        runner.$(http().client(duckService)
                .receive()
                .response(httpStatus)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage)
                .extract(fromBody().expression("$.id", "duckId")));

        return "${duckId}";
    }

    @Step("Валидация ответа на запрос с помощью json из папки Resources и получение id уточки")
    public String validateResponseByResourceAndGetDuckId(TestCaseRunner runner, HttpStatus httpStatus, String resourcePath) {
        runner.$(http().client(duckService)
                .receive()
                .response(httpStatus)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(resourcePath))
                .extract(fromBody().expression("$.id", "duckId")));

        return "${duckId}";
    }

    @Step("Валидация ответа на запрос с помощью модели данных и получение id уточки")
    public String validateResponseAndGetDuckId(TestCaseRunner runner, HttpStatus httpStatus, Object expectedPayload) {
        runner.$(http().client(duckService)
                .receive()
                .response(httpStatus)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper()))
                .extract(fromBody().expression("$.id", "duckId")));

        return "${duckId}";
    }
}
