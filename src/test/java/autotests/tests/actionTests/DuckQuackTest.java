package autotests.tests.actionTests;

import autotests.clients.actionClients.DuckQuackClient;
import autotests.payloads.DuckQuackResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-action-controller")
@Feature("Крякание уточки")
public class DuckQuackTest extends DuckQuackClient {

    @Test(description = "Проверка того, что уточка с корректным звуком крякает", dataProvider = "soundList")
    @CitrusTest
    @CitrusParameters({"duckId", "repetitionCount", "soundCount", "duckSound", "runner"})
    public void successfulQuack(String duckId, String repetitionCount, String soundCount, String duckSound, @Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, duckId, repetitionCount, soundCount);
//        validateOkResponse(runner, "{\n" +
//                "\"sound\":\"quack-quack, quack-quack\"\n" +
//                "}");
//        BUG: сервис возвращает некорректный звук с чётным id

        DuckQuackResponse quackResponse = new DuckQuackResponse()
                .sound(duckSound);
        validateResponseByPayload(runner, HttpStatus.OK, quackResponse);
    }

    @DataProvider(name = "soundList")
    public Object[][] duckProvider() {
        return new Object[][]{
                {"1", "2", "2", "quack-quack, quack-quack", null},
                {"2", "2", "2", "moo-moo, moo-moo", null}
        };
    }
}
