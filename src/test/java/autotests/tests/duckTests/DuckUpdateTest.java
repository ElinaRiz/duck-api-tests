package autotests.tests.duckTests;

import autotests.clients.duckClients.DuckUpdateClient;
import autotests.payloads.DuckProperties;
import autotests.payloads.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckUpdateTest extends DuckUpdateClient {

    @Test(description = "Проверка изменения цвета и высоты уточки")
    @CitrusTest
    public void successfulUpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duck = new DuckProperties()
                .color("yellow")
                .height(0.05)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, duck);
        String duckId = getDuckIdFromResponse(runner);

        DuckProperties updateDuck = new DuckProperties()
                .color("green")
                .height(0.15)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");
        updateDuck(runner, duckId, updateDuck);
        validateOkResponse(runner, "{\n" +
                "\"message\":\"Duck with id = " + duckId + " is updated\"\n" +
                "}");

        deleteDuck(runner, duckId);
    }

    @Test(description = "Проверка изменения цвета и звука уточки")
    @CitrusTest
    public void successfulUpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duck = new DuckProperties()
                .color("yellow")
                .height(0.05)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, duck);
        String duckId = getDuckIdFromResponse(runner);

        DuckProperties updateDuck = new DuckProperties()
                .color("white")
                .height(0.15)
                .material("rubber")
                .sound("moo")
                .wingsState("ACTIVE");
        updateDuck(runner, duckId, updateDuck);
        MessageResponse message = new MessageResponse()
                .message("Duck with id = " + duckId + " is updated");
        validateOkResponse(runner, message);

        deleteDuck(runner, duckId);
    }
}
