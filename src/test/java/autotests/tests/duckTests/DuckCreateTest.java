package autotests.tests.duckTests;

import autotests.clients.duckClients.DuckCreateClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Создание уточки")
public class DuckCreateTest extends DuckCreateClient {

    DuckProperties duckProperties1 = new DuckProperties()
            .color("red")
            .height(0.05)
            .material("rubber")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckProperties duckProperties2 = new DuckProperties()
            .color("brown")
            .height(0.15)
            .material("wood")
            .sound("quack")
            .wingsState("ACTIVE");

    @Test(description = "Проверка создания уточки", dataProvider = "duckList")
    @CitrusTest
    @CitrusParameters({"payload", "resourcePath", "runner"})
    public void successfulCreate(DuckProperties payload, String resourcePath, @Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, payload);
//        validateOkResponse(runner,
//                buildDuckJson("brown", 0.15, "wood", "quack", "ACTIVE"));
//        BUG: сервис возвращает id в ответе
        String duckId = validateResponseByResourceAndExtractVariable(runner, HttpStatus.OK, resourcePath, "$.id", "duckId");
        executeAfterTest(runner, () -> deleteDuckFromDatabase(runner, duckId));

        validateDuckInDatabase(runner, duckId, payload);
    }

    @DataProvider(name = "duckList")
    public Object[][] duckProvider() {
        return new Object[][]{
                {duckProperties1, "createTest/createRubberDuck.json", null},
                {duckProperties2, "createTest/createWoodDuck.json", null}
        };
    }
}
