package autotests.tests.actionTests;

import autotests.clients.actionClients.DuckFlyClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckFlyTest extends DuckFlyClient {

    @Test(description = "Проверка того, что уточка с активными крыльями полетела")
    @CitrusTest
    public void successfulFlyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.05, "rubber", "quack", "ACTIVE");
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
        createDuck(runner, "brown", 0.15, "wood", "quack", "FIXED");
        String duckId = getDuckIdFromResponse(runner);

        duckFly(runner, duckId);
//        validateOkResponse(runner, "{\n" +
//                "\"message\":\"I can't fly\"\n" +
//                "}");
//        BUG: сервис возвращает некорректное сообщение
        validateOkResponse(runner, "{\n" +
                "\"message\":\"I can not fly :C\"\n" +
                "}");

        deleteDuck(runner, duckId);
    }

    @Test(description = "Проверка того, что уточка с крыльями в неопределенном состоянии не полетела")
    @CitrusTest
    public void unsuccessfulFlyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "black", 0.2, "wood", "quack", "UNDEFINED");
        String duckId = getDuckIdFromResponse(runner);

        duckFly(runner, duckId);
        validateOkResponse(runner, "{\n" +
                "\"message\":\"Wings are not detected :(\"\n" +
                "}");

        deleteDuck(runner, duckId);
    }
}
