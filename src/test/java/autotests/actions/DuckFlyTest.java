package autotests.actions;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckFlyTest extends BaseTest {

    @Test(description = "Проверка того, что уточка с активными крыльями полетела")
    @CitrusTest
    public void successfulFlyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.05, "rubber", "quack", "ACTIVE");
        saveDuckIdFromResponse(runner);
        duckFly(runner, "${duckId}");
        validateSuccessResponse(runner, "{\n" +
                "\"message\":\"I'm flying\"\n" +
                "}");
    }

    @Test(description = "Проверка того, что уточка со связанными крыльями не полетела")
    @CitrusTest
    public void unsuccessfulFlyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "brown", 0.15, "wood", "quack", "FIXED");
        saveDuckIdFromResponse(runner);
        duckFly(runner, "${duckId}");
        validateSuccessResponse(runner, "{\n" +
                "\"message\":\"I can't fly\"\n" +
                "}");
    }

    @Test(description = "Проверка того, что уточка с крыльями в неопределенном состоянии не полетела")
    @CitrusTest
    public void unsuccessfulFlyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "black", 0.2, "wood", "quack", "UNDEFINED");
        saveDuckIdFromResponse(runner);
        duckFly(runner, "${duckId}");
        validateSuccessResponse(runner, "{\n" +
                "\"message\":\"Wings are not detected :(\"\n" +
                "}");
    }

    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/fly")
                        .queryParam("id", id));
    }
}