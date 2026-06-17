import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateGetTests extends BaseTest {


    @Test
    @Description("TC-T1-001 — Verify that a new board is created successfully when a valid name and credentials are provided")
    public void createBoard() {
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("key",          KEY)
                .queryParam("token",        TOKEN)
                .queryParam("name",         "Automation Board2")
                .queryParam("desc",         "This is my board for my tasks") // optional
                .queryParam("defaultLists", "false") // optional
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(200)
                .body("id",     notNullValue())
                .body("name",   equalTo("Automation Board2"))
                .body("desc",   equalTo("This is my board for my tasks"))
                .body("closed", equalTo(false))
                .time(lessThan(5000L))
                .extract().response();

        boardId = response.path("id");
        System.out.println(boardId);
    }

    @Test(dependsOnMethods = "createBoard")
    @Description("TC-T1-002 — Verify that the newly created board can be retrieved by ID and all fields match the submitted creation data")
    public void getBoard_verifyCreation() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",   KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/boards/{id}", boardId)
                .then()
                .log().body()
                .statusCode(200)
                .body("id",     equalTo(boardId))
                .body("name",   equalTo("Automation Board2"))
                .body("desc",   equalTo("This is my board for my tasks"))
                .body("closed", equalTo(false));
    }
    @Test(dependsOnMethods = "getBoard_verifyCreation")
    @Description("TC-T1-003 — Verify that a new list is created successfully inside an existing board when a valid name and board ID are provided")
    public void createList() {
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("key",     KEY)
                .queryParam("token",   TOKEN)
                .queryParam("name",    "Automation List")
                .queryParam("idBoard", boardId)
                .when()
                .post("/1/lists")
                .then()
                .log().body()
                .statusCode(200)
                .body("id",      notNullValue())
                .body("name",    equalTo("Automation List"))
                .body("idBoard", equalTo(boardId))
                .body("closed",  equalTo(false))
                .extract().response();

        listId = response.path("id");
    }

    @Test(dependsOnMethods = "createList")
    @Description("TC-T1-004 — Verify that the newly created list can be retrieved by ID and all fields match the submitted creation data")
    public void getList_verifyCreation() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",   KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/lists/{id}", listId)
                .then()
                .log().body()
                .statusCode(200)
                .body("id",      equalTo(listId))
                .body("name",    equalTo("Automation List"))
                .body("idBoard", equalTo(boardId))
                .body("closed",  equalTo(false));
    }

    @Test(dependsOnMethods = "getList_verifyCreation")
    @Description("TC-T1-005 — Verify that a new card is created successfully inside an existing list when a valid list ID is provided")
    public void createCard() {
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("key",    KEY)
                .queryParam("token",  TOKEN)
                .queryParam("name",   "Automation Card")
                .queryParam("idList", listId)
                .when()
                .post("/1/cards")
                .then().log().body()
                .statusCode(200)
                .body("id",     notNullValue())
                .body("name",   equalTo("Automation Card"))
                .body("idList", equalTo(listId))
                .body("closed", equalTo(false))
                .extract().response();

        cardId = response.path("id");
    }

    @Test(dependsOnMethods = "createCard")
    @Description("TC-T1-006 — Verify that the newly created card can be retrieved by ID and all fields match the submitted creation data")
    public void getCard_verifyCreation() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",   KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/cards/{id}", cardId)
                .then()
                .log().body()
                .statusCode(200)
                .body("id",     equalTo(cardId))
                .body("name",   equalTo("Automation Card"))
                .body("idList", equalTo(listId))
                .body("closed", equalTo(false));
    }

    @Test(dependsOnMethods = "getCard_verifyCreation")
    @Description("TC-T1-007 — Verify that a new checklist is created successfully when attached to an existing card using a valid card ID")
    public void createChecklist() {
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("key",    KEY)
                .queryParam("token",  TOKEN)
                .queryParam("name",   "Automation Checklist")
                .queryParam("idCard", cardId)
                .when()
                .post("/1/checklists")
                .then().log().body()
                .statusCode(200)
                .body("id",     notNullValue())
                .body("name",   equalTo("Automation Checklist"))
                .body("idCard", equalTo(cardId))
                .extract().response();

        checklistId = response.path("id");
    }

    @Test(dependsOnMethods = "createChecklist")
    @Description("TC-T1-008 — Verify that the newly created checklist can be retrieved by ID and all fields match the submitted creation data")
    public void getChecklist_verifyCreation() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",   KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/checklists/{id}", checklistId)
                .then()
                .log().body()
                .statusCode(200)
                .body("id",     equalTo(checklistId))
                .body("name",   equalTo("Automation Checklist"))
                .body("idCard", equalTo(cardId));
    }


}
