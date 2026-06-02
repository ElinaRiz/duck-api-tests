package autotests.tests.duckTests;

import autotests.clients.duckClients.DuckUpdateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckUpdateTest extends DuckUpdateClient {

    @Test(description = "Проверка изменения цвета и высоты уточки")
    @CitrusTest
    public void successfulUpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.05, "rubber", "quack", "ACTIVE");
        String duckId = getDuckIdFromResponse(runner);

        duckUpdate(runner, duckId, "green", 0.15, "rubber", "quack", "ACTIVE");
        validateOkResponse(runner, "{\n" +
                "\"message\":\"Duck with id = " + duckId + " is updated\"\n" +
                "}");

        duckDelete(runner, duckId);
    }

    @Test(description = "Проверка изменения цвета и звука уточки")
    @CitrusTest
    public void successfulUpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.05, "rubber", "quack", "ACTIVE");
        String duckId = getDuckIdFromResponse(runner);

        duckUpdate(runner, duckId, "white", 0.05, "rubber", "moo", "ACTIVE");
        validateOkResponse(runner, "{\n" +
                "\"message\":\"Duck with id = " + duckId + " is updated\"\n" +
                "}");

        duckDelete(runner, duckId);
    }
}
