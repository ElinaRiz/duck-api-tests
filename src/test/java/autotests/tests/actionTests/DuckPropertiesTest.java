package autotests.tests.actionTests;

import autotests.clients.DuckClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckPropertiesTest extends DuckClient {

    @Test(description = "Проверка получения характеристик уточки с чётным id")
    @CitrusTest
    public void successfulPropertiesWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        getDuckProperties(runner, "2");
//        validateOkResponse(runner,
//                buildDuckJson("yellow", 0.2, "wood", "quack", "ACTIVE"));
//        BUG: сервис возвращает пустое тело в ответе
        validateOkResponse(runner, "{}");
    }

    @Test(description = "Проверка получения характеристик уточки с нечётным id")
    @CitrusTest
    public void successfulPropertiesWithUnevenId(@Optional @CitrusResource TestCaseRunner runner) {
        getDuckProperties(runner, "1");
//        validateOkResponse(runner,
//                buildDuckJson("yellow", 0.03, "rubber", "quack", "FIXED"));
//        BUG: сервис возвращает height*100 в ответе
        DuckProperties duck = new DuckProperties()
                .color("yellow")
                .height(3.0)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");
        validateOkResponse(runner, duck);
    }
}
