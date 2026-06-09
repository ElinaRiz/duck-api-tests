package autotests.clients.duckClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckCreateClient extends DuckClient {

    @Step("Отправка запроса для создания уточки")
    public void createDuck(TestCaseRunner runner, Object duckProperties) {
        String path = "/api/duck/create";
        sendPostRequest(runner, path, duckProperties);
    }
}
