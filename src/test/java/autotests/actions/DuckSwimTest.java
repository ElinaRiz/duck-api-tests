package autotests.actions;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckSwimTest extends BaseTest {

    @Test(description = "Проверка того, что уточка с существующим id поплыла")
    @CitrusTest
    public void successfulSwimWithExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.05, "rubber", "quack", "ACTIVE");
        saveDuckIdFromResponse(runner);
        duckSwim(runner, "${duckId}");
        validateOkResponse(runner, "{\n" +
                "\"message\":\"I'm swimming\"\n" +
                "}");
    }

    @Test(description = "Проверка того, что уточка с несуществующим id не поплыла")
    @CitrusTest
    public void unsuccessfulSwimWithUnexistingId(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner, "9223372036854775807");
        validateNotFoundResponse(runner);
    }

    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/swim")
                        .queryParam("id", id));
    }

    public void validateNotFoundResponse(TestCaseRunner runner) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response(HttpStatus.NOT_FOUND));
    }
}