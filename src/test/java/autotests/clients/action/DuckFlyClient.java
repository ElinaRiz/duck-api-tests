package autotests.clients.action;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckFlyClient extends BaseTest {

    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                        .send()
                        .get("/api/duck/action/fly")
                        .queryParam("id", id));
    }
}