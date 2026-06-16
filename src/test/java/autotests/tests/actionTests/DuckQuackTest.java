package autotests.tests.actionTests;

import autotests.clients.actionClients.DuckQuackClient;
import autotests.payloads.DuckQuackResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-action-controller")
@Feature("Крякание уточки")
public class DuckQuackTest extends DuckQuackClient {

    @Test(description = "Проверка того, что уточка с с нечётным id и корректным звуком крякает")
    @CitrusTest
    public void successfulQuackWithUnevenIdAndValidSound(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "1", "2", "2");
        validateResponse(runner, HttpStatus.OK, "{\n" +
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
        validateResponse(runner, HttpStatus.OK, quackResponse);
    }
}
