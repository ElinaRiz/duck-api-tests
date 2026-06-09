package autotests.tests.duckTests;

import autotests.clients.duckClients.DuckCreateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateTest extends DuckCreateClient {

    @Test(description = "Проверка создания уточки с material rubber")
    @CitrusTest
    public void successfulCreateWithRubberMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.05, "rubber", "quack", "ACTIVE");
//        validateOkResponse(runner,
//                buildDuckJson("red", 0.05, "rubber", "quack", "ACTIVE"));
//         BUG: сервис возвращает id в ответе
        String duckId = validateOkResponseAndGetDuckId(runner,
                getDuckBodyWithIdMatcher("red", 0.05, "rubber", "quack", "ACTIVE"));

        deleteDuck(runner, duckId);
    }

    @Test(description = "Проверка создания уточки с material wood")
    @CitrusTest
    public void successfulCreateWithWoodMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "brown", 0.15, "wood", "quack", "ACTIVE");
//        validateOkResponse(runner,
//                buildDuckJson("brown", 0.15, "wood", "quack", "ACTIVE"));
//        BUG: сервис возвращает id в ответе
        String duckId = validateOkResponseAndGetDuckId(runner,
                getDuckBodyWithIdMatcher("brown", 0.15, "wood", "quack", "ACTIVE"));

        deleteDuck(runner, duckId);
    }
}
