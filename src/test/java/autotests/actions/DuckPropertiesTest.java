package autotests.actions;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckPropertiesTest extends BaseTest {

    @Test(description = "Проверка получения характеристик уточки с чётным id")
    @CitrusTest
    public void successfulPropertiesWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        duckProperties(runner, "2");
        validateSuccessResponse(runner,
                buildDuckJson("yellow", 0.2, "wood", "quack", "ACTIVE"));
    }

    @Test(description = "Проверка получения характеристик уточки с нечётным id")
    @CitrusTest
    public void successfulPropertiesWithUnEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        duckProperties(runner, "1");
        validateSuccessResponse(runner,
                buildDuckJson("yellow", 0.03, "rubber", "quack", "FIXED"));
    }

    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/properties")
                        .queryParam("id", id));
    }
}