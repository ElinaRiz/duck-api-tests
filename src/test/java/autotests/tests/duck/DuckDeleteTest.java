package autotests.tests.duck;

import autotests.clients.duck.DuckDeleteClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckDeleteTest extends DuckDeleteClient {

    @Test(description = "Проверка удаления уточки")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.05, "rubber", "quack", "ACTIVE");
        String duckId = getDuckIdFromResponse(runner);
        duckDelete(runner, duckId);
        validateOkResponse(runner, "{\n" +
                "\"message\":\"Duck is deleted\"\n" +
                "}");
    }
}