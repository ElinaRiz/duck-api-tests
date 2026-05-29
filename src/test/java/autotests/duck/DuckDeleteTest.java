package autotests.duck;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckDeleteTest extends BaseTest {

    @Test(description = "Проверка удаления уточки")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.05, "rubber", "quack", "ACTIVE");
        saveDuckIdFromResponse(runner);
        duckDelete(runner, "${duckId}");
        validateOkResponse(runner, "{\n" +
                "\"message\":\"Duck is deleted\"\n" +
                "}");
    }

    public void duckDelete(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .delete("/api/duck/delete")
                        .queryParam("id", id));
    }
}