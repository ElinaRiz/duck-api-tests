package autotests.clients.actionClients;

import autotests.clients.DuckBaseClient;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckPropertiesClient extends DuckBaseClient {

    public void duckProperties(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/properties";
        runner.$(http().client(duckService)
                .send()
                .get(path)
                .queryParam("id", id));
    }
}
