package autotests.tests.actionTests;

import autotests.clients.actionClients.DuckPropertiesClient;
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

    @Test(description = "Проверка получения характеристик уточки", dataProvider = "duckList")
    @CitrusTest
    @CitrusParameters({"duckId", "resourcePath", "runner"})
    public void successfulProperties(String duckId, String resourcePath, @Optional @CitrusResource TestCaseRunner runner) {
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
                {"1", "propertiesTest/duckPropertiesWithUnevenIdAndYellowColor.json", null},
                {"2", "propertiesTest/duckPropertiesWithEvenId.json", null},
                {"3", "propertiesTest/duckPropertiesWithUnevenIdAndBrownColor.json", null},
                {"4", "propertiesTest/duckPropertiesWithEvenId.json", null},
                {"5", "propertiesTest/duckPropertiesWithUnevenIdAndOrangeColor.json", null}
        };
    }
}
