package autotests.tests.duckTests;

import autotests.clients.duckClients.DuckCreateClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Создание уточки")
public class DuckCreateTest extends DuckCreateClient {

    @Test(description = "Проверка создания уточки с material rubber")
    @CitrusTest
    public void successfulCreateWithRubberMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duck = new DuckProperties()
                .color("red")
                .height(0.05)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");

        createDuck(runner, duck);
//        validateOkResponse(runner,
//                buildDuckJson("red", 0.05, "rubber", "quack", "ACTIVE"));
//         BUG: сервис возвращает id в ответе
        String duckId = validateResponseByResourceAndGetDuckId(runner, HttpStatus.OK, "createTest/createRubberDuck.json");

        deleteDuckInDatabase(runner, duckId);
    }

    @Test(description = "Проверка создания уточки с material wood")
    @CitrusTest
    public void successfulCreateWithWoodMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duck = new DuckProperties()
                .color("brown")
                .height(0.15)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");

        createDuck(runner, duck);
//        validateOkResponse(runner,
//                buildDuckJson("brown", 0.15, "wood", "quack", "ACTIVE"));
//        BUG: сервис возвращает id в ответе
        String duckId = validateResponseByResourceAndGetDuckId(runner, HttpStatus.OK, "createTest/createWoodDuck.json");

        deleteDuckInDatabase(runner, duckId);
    }
}
