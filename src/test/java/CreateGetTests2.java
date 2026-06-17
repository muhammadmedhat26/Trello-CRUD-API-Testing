import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateGetTests2 extends BaseTest {


    @Test
    @Description("TC-T1-001 — Verify that a new board is created successfully when a valid name and credentials are provided")
    public void TC_CRUD_001_createBoard() {
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Automation Board2")
                .queryParam("desc", "This is my board for my tasks") // optional
                .queryParam("defaultLists", "false") // optional
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Automation Board2"))
                .body("desc", equalTo("This is my board for my tasks"))
                .body("closed", equalTo(false))
                .time(lessThan(5000L))
                .extract().response();

        // REMOVED / CHANGED:
        // boardId = response.path("id");

        // ADDED: Saves to memory and updates properties file
        setBoardId(response.path("id"));

        // HIGHLIGHT: Changed field access to use the getter method safely
        System.out.println(getBoardId());
    }

    @Test(dependsOnMethods = "TC_CRUD_001_createBoard")
    @Description("TC-T1-002 — Verify that the newly created board can be retrieved by ID and all fields match the submitted creation data")
    public void TC_CRUD_002_getBoard_verifyCreation() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                // HIGHLIGHT: Changed 'boardId' to 'getBoardId()'
                .get("/1/boards/{id}", getBoardId())
                .then()
                .log().body()
                .statusCode(200)
                // HIGHLIGHT: Changed 'boardId' to 'getBoardId()' in assertions
                .body("id", equalTo(getBoardId()))
                .body("name", equalTo("Automation Board2"))
                .body("desc", equalTo("This is my board for my tasks"))
                .body("closed", equalTo(false));
    }

    @Test(dependsOnMethods = "TC_CRUD_002_getBoard_verifyCreation")
    @Description("TC-T1-003 — Verify that a new list is created successfully inside an existing board when a valid name and board ID are provided")
    public void TC_CRUD_003_createList() {
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Automation List")
                // HIGHLIGHT: Changed 'boardId' to 'getBoardId()'
                .queryParam("idBoard", getBoardId())
                .when()
                .post("/1/lists")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Automation List"))
                // HIGHLIGHT: Changed 'boardId' to 'getBoardId()'
                .body("idBoard", equalTo(getBoardId()))
                .body("closed", equalTo(false))
                .extract().response();

        // REMOVED / CHANGED:
        // listId = response.path("id");

        // ADDED: Saves to memory and updates properties file
        setListId(response.path("id"));
    }

    @Test(dependsOnMethods = "TC_CRUD_003_createList")
    @Description("TC-T1-004 — Verify that the newly created list can be retrieved by ID and all fields match the submitted creation data")
    public void TC_CRUD_004_getList_verifyCreation() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                // HIGHLIGHT: Changed 'listId' to 'getListId()'
                .get("/1/lists/{id}", getListId())
                .then()
                .log().body()
                .statusCode(200)
                // HIGHLIGHT: Changed fields to getter methods
                .body("id", equalTo(getListId()))
                .body("name", equalTo("Automation List"))
                .body("idBoard", equalTo(getBoardId()))
                .body("closed", equalTo(false));
    }

    @Test(dependsOnMethods = "TC_CRUD_004_getList_verifyCreation")
    @Description("TC-T1-005 — Verify that a new card is created successfully inside an existing list when a valid list ID is provided")
    public void TC_CRUD_005_createCard() {
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Automation Card")
                // HIGHLIGHT: Changed 'listId' to 'getListId()'
                .queryParam("idList", getListId())
                .when()
                .post("/1/cards")
                .then().log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Automation Card"))
                // HIGHLIGHT: Changed 'listId' to 'getListId()'
                .body("idList", equalTo(getListId()))
                .body("closed", equalTo(false))
                .extract().response();

        // REMOVED / CHANGED:
        // cardId = response.path("id");

        // ADDED: Saves to memory and updates properties file
        setCardId(response.path("id"));
    }

    @Test(dependsOnMethods = "TC_CRUD_005_createCard")
    @Description("TC-T1-006 — Verify that the newly created card can be retrieved by ID and all fields match the submitted creation data")
    public void TC_CRUD_006_getCard_verifyCreation() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                // HIGHLIGHT: Changed 'cardId' to 'getCardId()'
                .get("/1/cards/{id}", getCardId())
                .then()
                .log().body()
                .statusCode(200)
                // HIGHLIGHT: Changed fields to getter methods
                .body("id", equalTo(getCardId()))
                .body("name", equalTo("Automation Card"))
                .body("idList", equalTo(getListId()))
                .body("closed", equalTo(false));
    }

    @Test(dependsOnMethods = "TC_CRUD_006_getCard_verifyCreation")
    @Description("TC-T1-007 — Verify that a new checklist is created successfully when attached to an existing card using a valid card ID")
    public void TC_CRUD_007_createChecklist() {
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Automation Checklist")
                // HIGHLIGHT: Changed 'cardId' to 'getCardId()'
                .queryParam("idCard", getCardId())
                .when()
                .post("/1/checklists")
                .then().log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Automation Checklist"))
                // HIGHLIGHT: Changed 'cardId' to 'getCardId()'
                .body("idCard", equalTo(getCardId()))
                .extract().response();

        // REMOVED / CHANGED:
        // checklistId = response.path("id");

        // ADDED: Saves to memory and updates properties file
        setChecklistId(response.path("id"));
    }

    @Test(dependsOnMethods = "TC_CRUD_007_createChecklist")
    @Description("TC-T1-008 — Verify that the newly created checklist can be retrieved by ID and all fields match the submitted creation data")
    public void TC_CRUD_008_getChecklist_verifyCreation() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                // HIGHLIGHT: Changed 'checklistId' to 'getChecklistId()'
                .get("/1/checklists/{id}", getChecklistId())
                .then()
                .log().body()
                .statusCode(200)
                // HIGHLIGHT: Changed fields to getter methods
                .body("id", equalTo(getChecklistId()))
                .body("name", equalTo("Automation Checklist"))
                .body("idCard", equalTo(getCardId()));
    }
}