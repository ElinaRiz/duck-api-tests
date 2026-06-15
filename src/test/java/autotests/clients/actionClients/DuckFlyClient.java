package autotests.clients.actionClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;

public class DuckFlyClient extends DuckClient {

    public void duckFly(TestCaseRunner runner, String id) {
        String path = String.format("/api/duck/action/fly?id=%s", id);
        sendGetMethod(runner, path);
    }
}
