package autotests.clients;

import autotests.EndpointConfig;
import autotests.payloads.DuckProperties;
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
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;

    @Step("Создание уточки в базе данных")
    public String createDuckInDatabase(TestCaseRunner runner, DuckProperties duck) {
        String id = extractDuckIdFromDatabase(runner, "SELECT MAX(id) + 1 AS new_duck_id FROM duck", "new_duck_id");

        String query = String.format("INSERT INTO duck (id, color, height, material, sound, wings_state)\n " +
                "VALUES (%1$s, '%2$s', %3$s, '%4$s', '%5$s', '%6$s')", id, duck.color(), duck.height(), duck.material(), duck.sound(), duck.wingsState());
        updateDatabase(runner, query);

        return id;
    }

    @Step("Удаление уточки из базы данных")
    public void deleteDuckFromDatabase(TestCaseRunner runner, String id) {
        String query = String.format("DELETE FROM duck WHERE id=%s", id);
        updateDatabase(runner, query);
    }

    @Step("Обновление данных в базе данных")
    public void updateDatabase(TestCaseRunner runner, String query) {
        runner.$(sql(testDb)
                .statement(query));
    }

    @Step("Полуение id уточки из базы данных")
    public String extractDuckIdFromDatabase(TestCaseRunner runner, String query, String extractField) {
        runner.$(query(testDb)
                .statement(query)
                .extract(extractField, "duckId"));

        return "${duckId}";
    }

    @Step("Отправка GET запроса")
    public void sendGetMethod(TestCaseRunner runner, String path) {
        runner.$(http().client(duckService)
                .send()
                .get(path));
    }

    @Step("Валидация ответа на запрос с помощью передачи строки")
    public void validateResponse(TestCaseRunner runner, HttpStatus httpStatus, String responseMessage) {
        runner.$(http().client(duckService)
                .receive()
                .response(httpStatus)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }

    @Step("Валидация ответа на запрос с помощью json из папки Resources")
    public void validateResponseByResource(TestCaseRunner runner, HttpStatus httpStatus, String resourcePath) {
        runner.$(http().client(duckService)
                .receive()
                .response(httpStatus)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(resourcePath)));
    }

    @Step("Валидация ответа на запрос с помощью модели данных")
    public void validateResponse(TestCaseRunner runner, HttpStatus httpStatus, Object expectedPayload) {
        runner.$(http().client(duckService)
                .receive()
                .response(httpStatus)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }
}
