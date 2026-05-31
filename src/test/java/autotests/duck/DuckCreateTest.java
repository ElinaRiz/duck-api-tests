package autotests.duck;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateTest extends BaseTest {

    @Test(description = "Проверка создания уточки с material rubber")
    @CitrusTest
    public void successfulCreateWithRubberMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.05, "rubber", "quack", "ACTIVE");
//        validateOkResponse(runner,
//                buildDuckJson("red", 0.05, "rubber", "quack", "ACTIVE"));
//         BUG: сервис возвращает id в ответе
        validateOkResponse(runner,
                buildDuckJsonWithIdForCreateValidate("red", 0.05, "rubber", "quack", "ACTIVE"));
        ;
    }

    @Test(description = "Проверка создания уточки с material wood")
    @CitrusTest
    public void successfulCreateWithWoodMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "brown", 0.15, "wood", "quack", "ACTIVE");
//        validateOkResponse(runner,
//                buildDuckJson("brown", 0.15, "wood", "quack", "ACTIVE"));
//        BUG: сервис возвращает id в ответе
        validateOkResponse(runner,
                buildDuckJsonWithIdForCreateValidate("brown", 0.15, "wood", "quack", "ACTIVE"));
    }
}