import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ParameterTests2 extends BaseTest {

//    @BeforeClass
//    public void setupIsolatedData() {
//        if (getBoardId() == null || getListId() == null || getCardId() == null || getChecklistId() == null) {
//            System.out.println("Running in isolation mode: Generating temporary Trello IDs...");
//
//            Response boardResp = given()
//                    .baseUri(BASE_URL)
//                    .queryParam("key",   KEY)
//                    .queryParam("token", TOKEN)
//                    .queryParam("name",  "Temp Trello Board")
//                    .post("/1/boards/");
//            setBoardId(boardResp.path("id"));
//
//            Response listResp = given()
//                    .baseUri(BASE_URL)
//                    .queryParam("key",     KEY)
//                    .queryParam("token",   TOKEN)
//                    .queryParam("name",    "Temp List")
//                    .queryParam("idBoard", getBoardId())
//                    .post("/1/lists");
//            setListId(listResp.path("id"));
//
//            Response cardResp = given()
//                    .baseUri(BASE_URL)
//                    .queryParam("key",    KEY)
//                    .queryParam("token",  TOKEN)
//                    .queryParam("name",   "Temp Card")
//                    .queryParam("idList", getListId())
//                    .post("/1/cards");
//            setCardId(cardResp.path("id"));
//
//            Response chkResp = given()
//                    .baseUri(BASE_URL)
//                    .queryParam("key",    KEY)
//                    .queryParam("token",  TOKEN)
//                    .queryParam("name",   "Temp Checklist")
//                    .queryParam("idCard", getCardId())
//                    .post("/1/checklists");
//            setChecklistId(chkResp.path("id"));
//        }
//    }

    // ─────────────────────────────────────────────
    // BOARD
    // ─────────────────────────────────────────────

    @Test
    @Description("TC-T2B-001 — Verify that creating a board without the name parameter is rejected with 400 and returns 'invalid value for name'")
    public void createBoard_missingName() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",   KEY)
                .queryParam("token", TOKEN)
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(400)
                .body("message", equalTo("invalid value for name"))
                .body("error",   equalTo("ERROR"));
    }

    @Test
    @Description("TC-T2B-002 — Verify that creating a board with an empty string as the name is rejected with 400 and returns 'invalid value for name'")
    public void createBoard_emptyName() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",   KEY)
                .queryParam("token", TOKEN)
                .queryParam("name",  "")
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(400)
                .body("message", equalTo("invalid value for name"))
                .body("error",   equalTo("ERROR"));
    }

    // ─────────────────────────────────────────────
    // LIST
    // ─────────────────────────────────────────────

    @Test
    @Description("TC-T2B-003 — Verify that creating a list without the name parameter is rejected with 400 and returns 'invalid value for name'")
    public void createList_missingName() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",     KEY)
                .queryParam("token",   TOKEN)
                .queryParam("idBoard", getBoardId())
                .when()
                .post("/1/lists")
                .then().log().body()
                .statusCode(400)
                .body("message", equalTo("invalid value for name"))
                .body("error",   equalTo("BAD_REQUEST_ERROR"));
    }

    @Test
    @Description("TC-T2B-004 — Verify that creating a list without the idBoard parameter is rejected with 400 and returns 'invalid value for idBoard'")
    public void createList_missingIdBoard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",   KEY)
                .queryParam("token", TOKEN)
                .queryParam("name",  "Test List")
                .when()
                .post("/1/lists")
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for idBoard"));
    }

    @Test
    @Description("TC-T2B-005 — Verify that creating a list with a nonexistent idBoard value is rejected with 400 and returns 'invalid value for idBoard'")
    public void createList_fakeIdBoard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",     KEY)
                .queryParam("token",   TOKEN)
                .queryParam("name",    "Test List")
                .queryParam("idBoard", "0000002")
                .when()
                .post("/1/lists")
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for idBoard"));
    }

    // ─────────────────────────────────────────────
    // CARD
    // ─────────────────────────────────────────────

    @Test
    @Description("TC-T2B-006 — Verify that creating a card without the idList parameter is rejected with 400 and returns 'invalid value for idList'")
    public void createCard_missingIdList() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",   KEY)
                .queryParam("token", TOKEN)
                .queryParam("name",  "Test Card")
                .when()
                .post("/1/cards")
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for idList"));
    }

    @Test
    @Description("TC-T2B-007 — Verify that creating a card with a nonexistent idList value is rejected with 400 and returns 'invalid value for idList'")
    public void createCard_fakeIdList() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",    KEY)
                .queryParam("token",  TOKEN)
                .queryParam("name",   "Test Card")
                .queryParam("idList", "000000000000000002")
                .when()
                .post("/1/cards")
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for idList"));
    }

    // ─────────────────────────────────────────────
    // CHECKLIST
    // ─────────────────────────────────────────────

    @Test
    @Description("TC-T2B-008 — Verify that creating a checklist without the idCard parameter is rejected with 400 and returns 'invalid value for idCard'")
    public void createChecklist_missingIdCard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",   KEY)
                .queryParam("token", TOKEN)
                .queryParam("name",  "Test Checklist")
                .when()
                .post("/1/checklists")
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for idCard"));
    }

    @Test
    @Description("TC-T2B-009 — Verify that creating a checklist with a nonexistent idCard value is rejected with 400 and returns 'invalid value for idCard'")
    public void createChecklist_fakeIdCard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",    KEY)
                .queryParam("token",  TOKEN)
                .queryParam("name",   "Test Checklist")
                .queryParam("idCard", "0000000002")
                .when()
                .post("/1/checklists")
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for idCard"));
    }

    // ─────────────────────────────────────────────
    // ARCHIVE LIST
    // ─────────────────────────────────────────────

    @Test
    @Description("TC-T2B-014 — Verify that archiving a list without the value query parameter is rejected with 400 and returns 'invalid value for value'")
    public void archiveList_missingValue() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",   KEY)
                .queryParam("token", TOKEN)
                .when()
                .put("/1/lists/{id}/closed", getListId())
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for value"));
    }

    @Test
    @Description("TC-T2B-015 — Verify that archiving a list with an invalid value (not true/false) is rejected with 400 and returns 'invalid value for value'")
    public void archiveList_invalidValue() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key",   KEY)
                .queryParam("token", TOKEN)
                .queryParam("value", "yes")
                .when()
                .put("/1/lists/{id}/closed", getListId())
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for value"));
    }
}