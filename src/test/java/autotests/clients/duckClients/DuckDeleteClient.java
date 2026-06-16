package autotests.clients.duckClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckDeleteClient extends DuckClient {

    @Step("Отправка запроса для удаления уточки")
    public void deleteDuck(TestCaseRunner runner, String id) {
        String path = String.format("/api/duck/delete?id=%s", id);
        sendDeleteMethod(runner, path);
    }

    @Step("Отправка DELETE запроса")
    public void sendDeleteMethod(TestCaseRunner runner, String path) {
        runner.$(http().client(duckService)
                .send()
                .delete(path));
    }

    @Step("Проверка удаления уточки из базы данных")
    public void validateDuckDeletedInDatabase(TestCaseRunner runner, String id) {
        String query = String.format("SELECT COUNT(*) AS counter FROM duck WHERE id=%s", id);
        validateVariableInDatabase(runner, query, "counter", "0");
    }

    @Step("Валидация переменной в базе данных")
    public void validateVariableInDatabase(TestCaseRunner runner, String query, String columnName, String value) {
        runner.$(query(testDb)
                .statement(query)
                .validate(columnName, value));
    }
}
