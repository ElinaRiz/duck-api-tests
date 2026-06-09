package autotests.clients.duckClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

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
}
