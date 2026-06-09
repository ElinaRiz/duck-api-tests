package autotests.clients.actionClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckSwimClient extends DuckClient {

    @Step("Отправка команды 'Плыть' уточке")
    public void duckSwim(TestCaseRunner runner, String id) {
        String path = String.format("/api/duck/action/swim?id=%s", id);
        sendGetRequest(runner, path);
    }
}
