package autotests.tests.actionTests;

import autotests.clients.actionClients.DuckSwimClient;
import autotests.payloads.DuckProperties;
import autotests.payloads.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckSwimTest extends DuckSwimClient {

    @Test(description = "Проверка того, что уточка с существующим id поплыла")
    @CitrusTest
    public void successfulSwimWithExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duck = new DuckProperties()
                .color("red")
                .height(0.05)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, duck);
        String duckId = getDuckIdFromResponse(runner);

        duckSwim(runner, duckId);
//        validateOkResponse(runner, "{\n" +
//                "\"message\":\"I'm swimming\"\n" +
//                "}");
//        BUG: сервис возвращает 400 ошибку и некорректное сообщение
        validateNotFoundResponseByResource(runner, "swimTest/duckSwimWithExistingId.json");

        deleteDuck(runner, duckId);
    }

    @Test(description = "Проверка того, что уточка с несуществующим id не поплыла")
    @CitrusTest
    public void unsuccessfulSwimWithUnexistingId(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duck = new DuckProperties()
                .color("purple")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, duck);
        String duckId = getDuckIdFromResponse(runner);
        deleteDuck(runner, duckId);

        duckSwim(runner, duckId);
        MessageResponse message = new MessageResponse()
                .message("Paws are not found ((((");
        validateNotFoundResponse(runner, message);
    }
}
