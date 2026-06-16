package autotests.clients;

import autotests.BaseTest;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

public class DuckClient extends BaseTest {

    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource duckDb;

    protected String createDuckInDatabase(TestCaseRunner runner, DuckProperties duck) {
        String id = extractVariableFromDatabase(runner, "SELECT COALESCE(MAX(id), 0) + 1 AS new_duck_id FROM duck", "new_duck_id", "duckId");
        return createDuckInDatabase(runner, duck, id);
    }

    protected String createDuckInDatabase(TestCaseRunner runner, DuckProperties duck, boolean isEvenId) {
        String query = String.format("SELECT COALESCE(MAX(id), 0) + CASE WHEN MOD(COALESCE(MAX(id), 0), 2) = %s " +
                        "THEN 2 ELSE 1 END AS new_duck_id FROM duck",
                isEvenId ? 0 : 1);

        String id = extractVariableFromDatabase(runner, query, "new_duck_id", "duckId");
        return createDuckInDatabase(runner, duck, String.valueOf(id));
    }

    @Step("Создание уточки в базе данных")
    protected String createDuckInDatabase(TestCaseRunner runner, DuckProperties duck, String id) {
        String query = String.format("INSERT INTO duck (id, color, height, material, sound, wings_state)\n " +
                "VALUES (%1$s, '%2$s', %3$s, '%4$s', '%5$s', '%6$s')", id, duck.color(), duck.height(), duck.material(), duck.sound(), duck.wingsState());
        updateDatabase(runner, query);

        return id;
    }

    @Step("Удаление уточки из базы данных")
    protected void deleteDuckFromDatabase(TestCaseRunner runner, String id) {
        String query = String.format("DELETE FROM duck WHERE id=%s", id);
        updateDatabase(runner, query);
    }

    protected void updateDatabase(TestCaseRunner runner, String query) {
        updateDatabase(runner, duckDb, query);
    }

    protected String extractVariableFromDatabase(TestCaseRunner runner, String query, String columnName, String variableName) {
        return extractVariableFromDatabase(runner, duckDb, query, columnName, variableName);
    }

    protected void validateVariableInDatabase(TestCaseRunner runner, String query, String columnName, String value) {
        validateVariableInDatabase(runner, duckDb, query, columnName, value);
    }


    protected void sendGetRequest(TestCaseRunner runner, String path) {
        sendGetRequest(runner, duckService, path);
    }

    protected void sendPostRequest(TestCaseRunner runner, String path, Object payload) {
        sendPostRequest(runner, duckService, path, payload);
    }

    protected void sendPutRequest(TestCaseRunner runner, String path) {
        sendPutRequest(runner, duckService, path);
    }

    protected void sendDeleteMethod(TestCaseRunner runner, String path) {
        sendDeleteMethod(runner, duckService, path);
    }


    protected void validateResponseByString(TestCaseRunner runner, HttpStatus httpStatus, String expectedResponse) {
        validateResponseByString(runner, duckService, httpStatus, expectedResponse);
    }

    protected void validateResponseByResource(TestCaseRunner runner, HttpStatus httpStatus, String resourcePath) {
        validateResponseByResource(runner, duckService, httpStatus, resourcePath);
    }

    protected String validateResponseByResourceAndExtractVariable(TestCaseRunner runner, HttpStatus httpStatus, String resourcePath, String jsonPath, String variableName) {
        return validateResponseByResourceAndExtractVariable(runner, duckService, httpStatus, resourcePath, jsonPath, variableName);
    }

    protected void validateResponseByPayload(TestCaseRunner runner, HttpStatus httpStatus, Object expectedPayload) {
        validateResponseByPayload(runner, duckService, httpStatus, expectedPayload);
    }

    @Step("Проверка характеристик уточки в базе данных")
    protected void validateDuckInDatabase(TestCaseRunner runner, String id, DuckProperties duckProperties) {
        String query = String.format("SELECT * FROM duck WHERE id=%s", id);
        runner.$(query(duckDb)
                .statement(query)
                .validate("color", duckProperties.color())
                .validate("height", String.valueOf(duckProperties.height()))
                .validate("material", duckProperties.material())
                .validate("sound", duckProperties.sound())
                .validate("wings_state", duckProperties.wingsState()));
    }
}
