package autotests.clients.actionClients;

import autotests.clients.DuckBaseClient;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckFlyClient extends DuckBaseClient {

    public void duckFly(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/fly";
        runner.$(http().client(duckService)
                        .send()
                        .get(path)
                        .queryParam("id", id));
    }
}
