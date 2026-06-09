package autotests.tests.duckTests;

import autotests.clients.DuckBaseClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckDeleteTest extends DuckBaseClient {

    @Test(description = "Проверка удаления уточки")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.05, "rubber", "quack", "ACTIVE");
        String duckId = getDuckIdFromResponse(runner);

        deleteDuck(runner, duckId);
        validateOkResponse(runner, "{\n" +
                "\"message\":\"Duck is deleted\"\n" +
                "}");
    }
}
