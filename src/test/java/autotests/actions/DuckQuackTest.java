package autotests.actions;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckQuackTest extends BaseTest {

    @Test(description = "Проверка того, что уточка с с нечётным id и корректным звуком крякает")
    @CitrusTest
    public void successfulQuackWithUnevenIdAndValidSound(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "1", "2", "2");
        validateSuccessResponse(runner, "{\n" +
                "\"sound\":\"quack-quack, quack-quack\"\n" +
                "}");
    }

    @Test(description = "Проверка того, что уточка с чётным id и корректным звуком крякает")
    @CitrusTest
    public void successfulQuackWithEvenIdAndValidSound(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "2", "2", "2");
        validateSuccessResponse(runner, "{\n" +
                "\"sound\":\"quack-quack, quack-quack\"\n" +
                "}");
    }

    public void duckQuack(TestCaseRunner runner, String id, String repetition, String soundCount) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/quack")
                        .queryParam("id", id)
                        .queryParam("repetitionCount", repetition)
                        .queryParam("soundCount", soundCount));
    }
}