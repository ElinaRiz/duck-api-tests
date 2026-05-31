package autotests.clients.action;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckQuackClient extends BaseTest {

    public void duckQuack(TestCaseRunner runner, String id, String repetition, String soundCount) {
        runner.$(http().client(duckService)
                        .send()
                        .get("/api/duck/action/quack")
                        .queryParam("id", id)
                        .queryParam("repetitionCount", repetition)
                        .queryParam("soundCount", soundCount));
    }
}