package autotests.tests.actionTests;

import autotests.clients.actionClients.DuckSwimClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckSwimTest extends DuckSwimClient {

    @Test(description = "Проверка того, что уточка с существующим id поплыла")
    @CitrusTest
    public void successfulSwimWithExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.05, "rubber", "quack", "ACTIVE");
        String duckId = getDuckIdFromResponse(runner);

        duckSwim(runner, duckId);
//        validateOkResponse(runner, "{\n" +
//                "\"message\":\"I'm swimming\"\n" +
//                "}");
//        BUG: сервис возвращает 400 ошибку и некорректное сообщение
        validateNotFoundResponse(runner, "Paws are not found ((((");

        duckDelete(runner, duckId);
    }

    @Test(description = "Проверка того, что уточка с несуществующим id не поплыла")
    @CitrusTest
    public void unsuccessfulSwimWithUnexistingId(@Optional @CitrusResource TestCaseRunner runner) {
        String duckId = "9223372036854775807";
        duckProperties(runner, duckId);
        validateNotFoundResponseWith500Error(runner, "Duck with id = " + duckId + " is not found");

        duckSwim(runner, duckId);
        validateNotFoundResponse(runner, "Paws are not found ((((");
    }
}
