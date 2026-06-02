package autotests.clients.duckClients;

import autotests.clients.DuckBaseClient;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckUpdateClient extends DuckBaseClient {

    public void duckUpdate(TestCaseRunner runner, String id, String color , double height , String material , String sound , String wingsState) {
        String path = "/api/duck/update";
        runner.$(http().client(duckService)
                        .send()
                        .put(path)
                        .queryParam("id", id)
                        .queryParam("color", color)
                        .queryParam("height", String.valueOf(height))
                        .queryParam("material", material)
                        .queryParam("sound", sound)
                        .queryParam("wingsState", wingsState));
    }
}
