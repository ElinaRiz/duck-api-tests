package autotests.tests.duckTests;

import autotests.clients.duckClients.DuckUpdateClient;
import autotests.payloads.DuckProperties;
import autotests.payloads.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Обновление уточки")
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
        String duckId = createDuckInDatabase(runner, duck);

        DuckProperties updateDuck = new DuckProperties()
                .color("green")
                .height(0.15)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");
        updateDuck(runner, duckId, updateDuck);
        validateResponse(runner, HttpStatus.OK, "{\n" +
                "\"message\":\"Duck with id = " + duckId + " is updated\"\n" +
                "}");
        validateDuckInDatabase(runner, duckId, updateDuck);

        deleteDuckFromDatabase(runner, duckId);
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
        String duckId = createDuckInDatabase(runner, duck);

        DuckProperties updateDuck = new DuckProperties()
                .color("white")
                .height(0.15)
                .material("rubber")
                .sound("moo")
                .wingsState("ACTIVE");
        updateDuck(runner, duckId, updateDuck);
        MessageResponse message = new MessageResponse()
                .message("Duck with id = " + duckId + " is updated");
        validateResponse(runner, HttpStatus.OK, message);
        validateDuckInDatabase(runner, duckId, updateDuck);

        deleteDuckFromDatabase(runner, duckId);
    }
}
