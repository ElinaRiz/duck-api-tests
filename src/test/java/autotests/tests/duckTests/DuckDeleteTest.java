package autotests.tests.duckTests;

import autotests.clients.duckClients.DuckDeleteClient;
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
@Feature("Удаление уточки")
public class DuckDeleteTest extends DuckDeleteClient {

    @Test(description = "Проверка удаления уточки")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duck = new DuckProperties()
                .color("red")
                .height(0.05)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        String duckId = createDuckInDatabase(runner, duck);

        deleteDuck(runner, duckId);
        validateResponseByResource(runner, HttpStatus.OK, "deleteTest/duckDelete.json");
    }
}
