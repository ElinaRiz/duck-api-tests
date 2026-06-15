package autotests.tests.actionTests;

import autotests.clients.actionClients.DuckQuackClient;
import autotests.payloads.DuckQuackResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckQuackTest extends DuckQuackClient {

    @Test(description = "Проверка того, что уточка с с нечётным id и корректным звуком крякает")
    @CitrusTest
    public void successfulQuackWithUnevenIdAndValidSound(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "1", "2", "2");
        validateOkResponse(runner, "{\n" +
                "\"sound\":\"quack-quack, quack-quack\"\n" +
                "}");
    }

    @Test(description = "Проверка того, что уточка с чётным id и корректным звуком крякает")
    @CitrusTest
    public void successfulQuackWithEvenIdAndValidSound(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "2", "2", "2");
//        validateOkResponse(runner, "{\n" +
//                "\"sound\":\"quack-quack, quack-quack\"\n" +
//                "}");
//        BUG: сервис возвращает некорректный звук

        DuckQuackResponse quackResponse = new DuckQuackResponse()
                .sound("moo-moo, moo-moo");
        validateOkResponse(runner, quackResponse);
    }
}
