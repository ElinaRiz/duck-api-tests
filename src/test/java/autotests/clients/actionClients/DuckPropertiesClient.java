package autotests.clients.actionClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckPropertiesClient extends DuckClient {

    @Step("Отправка запроса для получение характеристик уточки")
    public void getDuckProperties(TestCaseRunner runner, String id) {
        String path = String.format("/api/duck/action/properties?id=%s", id);
        sendGetRequest(runner, path);
    }
}
