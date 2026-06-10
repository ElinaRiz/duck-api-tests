package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;

    @Step("Обновление данных в базе данных")
    protected void updateDatabase(TestCaseRunner runner, String query) {
        runner.$(sql(testDb)
                .statement(query));
    }

    @Step("Извлечь переменную из базы данных")
    protected String extractVariableFromDatabase(TestCaseRunner runner, String query, String columnName, String variableName) {
        runner.$(query(testDb)
                .statement(query)
                .extract(columnName, variableName));

        return String.format("${%s}", variableName);
    }

    @Step("Валидация переменной в базе данных")
    protected void validateVariableInDatabase(TestCaseRunner runner, String query, String columnName, String value) {
        runner.$(query(testDb)
                .statement(query)
                .validate(columnName, value));
    }

    @Step("Восстановление исходного состояния")
    protected void executeAfterTest(TestCaseRunner runner, Runnable action) {
        runner.$(doFinally()
                .actions(testContext -> action.run()));
    }


    @Step("Отправка GET запроса")
    protected void sendGetRequest(TestCaseRunner runner, String path) {
        runner.$(http().client(duckService)
                .send()
                .get(path));
    }

    @Step("Отправка POST запроса")
    protected void sendPostRequest(TestCaseRunner runner, String path, Object payload) {
        runner.$(http().client(duckService)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(payload, new ObjectMapper())));
    }

    @Step("Отправка PUT запроса")
    protected void sendPutRequest(TestCaseRunner runner, String path) {
        runner.$(http().client(duckService)
                .send()
                .put(path));
    }

    @Step("Отправка DELETE запроса")
    protected void sendDeleteMethod(TestCaseRunner runner, String path) {
        runner.$(http().client(duckService)
                .send()
                .delete(path));
    }


    @Step("Валидация ответа на запрос с помощью передачи строки")
    protected void validateResponseByString(TestCaseRunner runner, HttpStatus httpStatus, String expectedResponse) {
        runner.$(http().client(duckService)
                .receive()
                .response(httpStatus)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(expectedResponse));
    }

    @Step("Валидация ответа на запрос с помощью json из папки Resources")
    protected void validateResponseByResource(TestCaseRunner runner, HttpStatus httpStatus, String resourcePath) {
        runner.$(http().client(duckService)
                .receive()
                .response(httpStatus)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(resourcePath)));
    }

    @Step("Валидация ответа на запрос с помощью json из папки Resources и получение значения параметра")
    protected String validateResponseByResourceAndExtractVariable(TestCaseRunner runner, HttpStatus httpStatus, String resourcePath, String jsonPath, String variableName) {
        runner.$(http().client(duckService)
                .receive()
                .response(httpStatus)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(resourcePath))
                .extract(fromBody().expression(jsonPath, variableName)));

        return String.format("${%s}", variableName);
    }

    @Step("Валидация ответа на запрос с помощью модели данных")
    protected void validateResponseByPayload(TestCaseRunner runner, HttpStatus httpStatus, Object expectedPayload) {
        runner.$(http().client(duckService)
                .receive()
                .response(httpStatus)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }
}
