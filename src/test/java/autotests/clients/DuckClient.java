package autotests.clients;

import autotests.BaseTest;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

public class DuckClient extends BaseTest {

    @Step("Создание уточки в базе данных")
    public String createDuckInDatabase(TestCaseRunner runner, DuckProperties duck) {
        String id = extractVariableFromDatabase(runner, "SELECT MAX(id) + 1 AS new_duck_id FROM duck", "new_duck_id", "duckId");

        String query = String.format("INSERT INTO duck (id, color, height, material, sound, wings_state)\n " +
                "VALUES (%1$s, '%2$s', %3$s, '%4$s', '%5$s', '%6$s')", id, duck.color(), duck.height(), duck.material(), duck.sound(), duck.wingsState());
        updateDatabase(runner, query);

        return id;
    }

    @Step("Удаление уточки из базы данных")
    public void deleteDuckFromDatabase(TestCaseRunner runner, String id) {
        String query = String.format("DELETE FROM duck WHERE id=%s", id);
        updateDatabase(runner, query);
    }

    @Step("Проверка характеристик уточки в базе данных")
    public void validateDuckInDatabase(TestCaseRunner runner, String id, DuckProperties duckProperties) {
        String query = String.format("SELECT * FROM duck WHERE id=%s", id);
        runner.$(query(testDb)
                .statement(query)
                .validate("color", duckProperties.color())
                .validate("height", String.valueOf(duckProperties.height()))
                .validate("material", duckProperties.material())
                .validate("sound", duckProperties.sound())
                .validate("wings_state", duckProperties.wingsState()));
    }
}
