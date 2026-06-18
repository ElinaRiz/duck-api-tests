package autotests.clients.duckClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckDeleteClient extends DuckClient {

    @Step("Отправка запроса для удаления уточки")
    public void deleteDuck(TestCaseRunner runner, String id) {
        String path = String.format("/api/duck/delete?id=%s", id);
        sendDeleteMethod(runner, path);
    }

    @Step("Проверка удаления уточки из базы данных")
    public void validateDuckDeletedInDatabase(TestCaseRunner runner, String id) {
        String query = String.format("SELECT COUNT(*) AS counter FROM duck WHERE id=%s", id);
        validateVariableInDatabase(runner, query, "counter", "0");
    }
}
