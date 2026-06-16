package autotests.clients.actionClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckQuackClient extends DuckClient {

    @Step("Отправка команды 'Крякать' уточке")
    public void duckQuack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        String path = String.format("/api/duck/action/quack?id=%1$s&repetitionCount=%2$s&soundCount=%3$s",
                id, repetitionCount, soundCount);
        sendGetMethod(runner, path);
    }
}
