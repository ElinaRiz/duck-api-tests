package autotests.clients.duckClients;

import autotests.clients.DuckClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckUpdateClient extends DuckClient {

    public void updateDuck(TestCaseRunner runner, String id, DuckProperties duckProperties) {
        String path = "/api/duck/update";
        sendPutMethod(runner, path, id, duckProperties);
    }

    public void sendPutMethod(TestCaseRunner runner, String path, String id, DuckProperties duck) {
        runner.$(http().client(duckService)
                .send()
                .put(path)
                .queryParam("id", id)
                .queryParam("color", duck.color())
                .queryParam("height", String.valueOf(duck.height()))
                .queryParam("material", duck.material())
                .queryParam("sound", duck.sound())
                .queryParam("wingsState", duck.wingsState()));
    }
}
