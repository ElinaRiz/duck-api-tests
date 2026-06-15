package autotests.clients.duckClients;

import autotests.clients.DuckClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckUpdateClient extends DuckClient {

    @Step("Отправка запроса для обновления характеристик уточки")
    public void updateDuck(TestCaseRunner runner, String id, DuckProperties duck) {
        String path = String.format("/api/duck/update?id=%1$s&color=%2$s&height=%3$s&material=%4$s&sound=%5$s&wingsState=%6$s",
                id, duck.color(), duck.height(), duck.material(), duck.sound(), duck.wingsState());
        sendPutMethod(runner, path, id, duck);
    }

    @Step("Отправка PUT запроса")
    public void sendPutMethod(TestCaseRunner runner, String path, String id, DuckProperties duck) {
        runner.$(http().client(duckService)
                .send()
                .put(path));
    }
}
