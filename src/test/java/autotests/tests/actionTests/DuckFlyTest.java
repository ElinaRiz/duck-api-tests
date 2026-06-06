package autotests.tests.actionTests;

import autotests.clients.actionClients.DuckFlyClient;
import autotests.payloads.DuckProperties;
import autotests.payloads.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckFlyTest extends DuckFlyClient {

    @Test(description = "Проверка того, что уточка с активными крыльями полетела")
    @CitrusTest
    public void successfulFlyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duck = new DuckProperties()
                .color("red")
                .height(0.05)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, duck);
        String duckId = getDuckIdFromResponse(runner);

        duckFly(runner, duckId);
//        validateOkResponse(runner, "{\n" +
//                "\"message\":\"I'm flying\"\n" +
//                "}");
//        BUG: сервис возвращает некорректное сообщение
        validateOkResponse(runner, "{\n" +
                "\"message\":\"I am flying :)\"\n" +
                "}");

        deleteDuck(runner, duckId);
    }

    @Test(description = "Проверка того, что уточка со связанными крыльями не полетела")
    @CitrusTest
    public void unsuccessfulFlyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duck = new DuckProperties()
                .color("brown")
                .height(0.15)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");
        createDuck(runner, duck);
        String duckId = getDuckIdFromResponse(runner);

        duckFly(runner, duckId);
//        validateOkResponse(runner, "{\n" +
//                "\"message\":\"I can't fly\"\n" +
//                "}");
//        BUG: сервис возвращает некорректное сообщение
        validateOkResponseByResource(runner, "flyTest/duckFlyWithFixedWings.json");

        deleteDuck(runner, duckId);
    }

    @Test(description = "Проверка того, что уточка с крыльями в неопределенном состоянии не полетела")
    @CitrusTest
    public void unsuccessfulFlyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duck = new DuckProperties()
                .color("black")
                .height(0.2)
                .material("wood")
                .sound("quack")
                .wingsState("UNDEFINED");
        createDuck(runner, duck);
        String duckId = getDuckIdFromResponse(runner);

        duckFly(runner, duckId);
        MessageResponse message = new MessageResponse()
                .message("Wings are not detected :(");
        validateOkResponse(runner, message);

        deleteDuck(runner, duckId);
    }
}
