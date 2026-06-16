package autotests.tests.actionTests;

import autotests.clients.actionClients.DuckPropertiesClient;
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

@Epic("Тесты на duck-action-controller")
@Feature("Получение характеристик уточки")
public class DuckPropertiesTest extends DuckPropertiesClient {

    DuckProperties duckProperties1 = new DuckProperties()
            .color("yellow")
            .height(0.03)
            .material("rubber")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckProperties duckProperties2 = new DuckProperties()
            .color("red")
            .height(0.03)
            .material("wood")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckProperties duckProperties3 = new DuckProperties()
            .color("brown")
            .height(0.03)
            .material("rubber")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckProperties duckProperties4 = new DuckProperties()
            .color("purple")
            .height(0.03)
            .material("wood")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckProperties duckProperties5 = new DuckProperties()
            .color("orange")
            .height(0.03)
            .material("rubber")
            .sound("quack")
            .wingsState("ACTIVE");


    @Test(description = "Проверка получения характеристик уточки", dataProvider = "duckList")
    @CitrusTest
    @CitrusParameters({"isEvenId", "duckProperties", "resourcePath", "runner"})
    public void successfulProperties(boolean isEvenId, DuckProperties duckProperties, String resourcePath, @Optional @CitrusResource TestCaseRunner runner) {
        String duckId = createDuckInDatabase(runner, duckProperties, isEvenId);
        executeAfterTest(runner, () -> deleteDuckFromDatabase(runner, duckId));

        getDuckProperties(runner, duckId);
//        validateOkResponse(runner,
//                buildDuckJson("red", 0.03, "wood", "quack", "ACTIVE"));
//        BUG: сервис возвращает пустое тело в ответе с чётным id
//        BUG: сервис возвращает height*100 в ответе с нечётным id
        validateResponseByResource(runner, HttpStatus.OK, resourcePath);
    }

    @DataProvider(name = "duckList")
    public Object[][] duckProvider() {
        return new Object[][]{
                {false, duckProperties1, "propertiesTest/duckPropertiesWithUnevenIdAndYellowColor.json", null},
                {true, duckProperties2, "propertiesTest/duckPropertiesWithEvenId.json", null},
                {false, duckProperties3, "propertiesTest/duckPropertiesWithUnevenIdAndBrownColor.json", null},
                {true, duckProperties4, "propertiesTest/duckPropertiesWithEvenId.json", null},
                {false, duckProperties5, "propertiesTest/duckPropertiesWithUnevenIdAndOrangeColor.json", null}
        };
    }
}
