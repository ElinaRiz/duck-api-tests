package autotests.clients.duck;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckDeleteClient extends BaseTest {

    public void duckDelete(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                        .send()
                        .delete("/api/duck/delete")
                        .queryParam("id", id));
    }
}
