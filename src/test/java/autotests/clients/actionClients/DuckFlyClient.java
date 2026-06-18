package autotests.clients.actionClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckFlyClient extends DuckClient {

    @Step("Отправка команды 'Лететь' уточке")
    public void duckFly(TestCaseRunner runner, String id) {
        String path = String.format("/api/duck/action/fly?id=%s", id);
        sendGetRequest(runner, path);
    }
}
