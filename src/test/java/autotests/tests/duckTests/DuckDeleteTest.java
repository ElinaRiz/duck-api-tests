package autotests.tests.duckTests;

import autotests.clients.DuckClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckDeleteTest extends DuckClient {

    @Test(description = "Проверка удаления уточки")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duck = new DuckProperties()
                .color("red")
                .height(0.05)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, duck);
        String duckId = getDuckIdFromResponse(runner);

        deleteDuck(runner, duckId);
        validateOkResponseByResource(runner, "deleteTest/duckDelete.json");
    }
}
