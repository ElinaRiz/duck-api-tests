package autotests.duck;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckUpdateTest extends BaseTest {

    @Test(description = "Проверка изменения цвета и высоты уточки")
    @CitrusTest
    public void successfulUpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.05, "rubber", "quack", "ACTIVE");
        String duckId = getDuckIdFromResponse(runner);
        duckUpdate(runner, duckId, "green", 0.15, "rubber", "quack", "ACTIVE");
        validateOkResponse(runner, "{\n" +
                "\"message\":\"Duck with id = ${duckId} is updated\"\n" +
                "}");
    }

    @Test(description = "Проверка изменения цвета и звука уточки")
    @CitrusTest
    public void successfulUpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.05, "rubber", "quack", "ACTIVE");
        String duckId = getDuckIdFromResponse(runner);
        duckUpdate(runner, duckId,"white", 0.05, "rubber", "moo", "ACTIVE");
        validateOkResponse(runner, "{\n" +
                "\"message\":\"Duck with id = ${duckId} is updated\"\n" +
                "}");
    }

    public void duckUpdate(TestCaseRunner runner, String id, String color , double height , String material , String sound , String wingsState) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .put("/api/duck/update")
                        .queryParam("id", id)
                        .queryParam("color", color)
                        .queryParam("height", String.valueOf(height))
                        .queryParam("material", material)
                        .queryParam("sound", sound)
                        .queryParam("wingsState", wingsState));
    }
}