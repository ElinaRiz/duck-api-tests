package autotests.clients.actionClients;

import autotests.clients.DuckBaseClient;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckQuackClient extends DuckBaseClient {

    public void duckQuack(TestCaseRunner runner, String id, String repetition, String soundCount) {
        String path = "/api/duck/action/quack";
        runner.$(http().client(duckService)
                        .send()
                        .get(path)
                        .queryParam("id", id)
                        .queryParam("repetitionCount", repetition)
                        .queryParam("soundCount", soundCount));
    }
}
