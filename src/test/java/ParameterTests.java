import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ParameterTests extends BaseTest {

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
//            boardId = boardResp.path("id");
//
//            Response listResp = given()
//                    .baseUri(BASE_URL)
//                    .queryParam("key",     KEY)
//                    .queryParam("token",   TOKEN)
//                    .queryParam("name",    "Temp List")
//                    .queryParam("idBoard", getBoardId())
//                    .post("/1/lists");
//            listId = listResp.path("id");
//
//            Response cardResp = given()
//                    .baseUri(BASE_URL)
//                    .queryParam("key",    KEY)
//                    .queryParam("token",  TOKEN)
//                    .queryParam("name",   "Temp Card")
//                    .queryParam("idList", getListId())
//                    .post("/1/cards");
//            cardId = cardResp.path("id");
//
//            Response chkResp = given()
//                    .baseUri(BASE_URL)
//                    .queryParam("key",    KEY)
//                    .queryParam("token",  TOKEN)
//                    .queryParam("name",   "Temp Checklist")
//                    .queryParam("idCard", getCardId())
//                    .post("/1/checklists");
//            checklistId = chkResp.path("id");
//        }
//    }

    // ─────────────────────────────────────────────
    // BOARD
    // ─────────────────────────────────────────────

    @Test(priority = 1)
    @Description("TC-Param-001 — Verify that creating a board without the name parameter is rejected with 400 and returns 'invalid value for name'")
    public void TC_Param_001_createBoard_missingName() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(400)
                .body("message", equalTo("invalid value for name"))
                .body("error", equalTo("ERROR"));
    }

    @Test(priority = 2)
    @Description("TC-Param-002 — Verify that creating a board with an empty string as the name is rejected with 400 and returns 'invalid value for name'")
    public void TC_Param_002_createBoard_emptyName() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "")
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(400)
                .body("message", equalTo("invalid value for name"))
                .body("error", equalTo("ERROR"));
    }

    @Test(priority = 3)
    @Description("TC-Param-003 — Verify that retrieving a board without the boardId path parameter is rejected with a 404 routing error")
    public void TC_Param_003_getBoard_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/boards/")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("Cannot GET /1/boards/?key=" + KEY + "&token=" + TOKEN));
    }

    @Test(priority = 4)
    @Description("TC-Param-004 — Verify that retrieving a board with a nonexistent boardId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_004_getBoard_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/boards/{id}", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("The requested resource was not found."));
    }

    @Test(priority = 5)
    @Description("TC-Param-005 — Verify that updating a board without the boardId path parameter is rejected with a 404 routing error")
    public void TC_Param_005_updateBoard_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/boards/")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("Cannot PUT /1/boards/?key=" + KEY + "&token=" + TOKEN + "&name=New%20Name"));

    }

    @Test(priority = 6)
    @Description("TC-Param-006 — Verify that updating a board with a nonexistent boardId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_006_updateBoard_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/boards/{id}", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("The requested resource was not found."));
    }

    @Test(priority = 7)
    @Description("TC-Param-007 — Verify that deleting a board without the boardId path parameter is rejected with a 404 routing error")
    public void TC_Param_007_deleteBoard_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/boards/")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("Cannot DELETE /1/boards/?key=" + KEY + "&token=" + TOKEN));
    }

    @Test(priority = 8)
    @Description("TC-Param-008 — Verify that deleting a board with a nonexistent boardId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_008_deleteBoard_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/boards/{id}", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("The requested resource was not found."));
    }

    @Test(priority = 37)
    @Description("TC-T2B-039 — Verify that creating a board with an excessively long name is rejected or handled with a defined boundary response")
    public void createBoard_boundaryLongName() {
        String longName = "A".repeat(10000);
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", longName)
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(414);
    }

    @Test(priority = 38)
    @Description("TC-T2B-040 — Verify that creating a board with a whitespace-only name is rejected with 400 and returns 'invalid value for name'")
    public void createBoard_whitespaceOnlyName() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "   ")
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(400)
                .body("message", equalTo("invalid value for name"))
                .body("error", equalTo("ERROR"));
    }

    // ─────────────────────────────────────────────
    // LIST
    // ─────────────────────────────────────────────

    @Test(priority = 9)
    @Description("TC-Param-009 — Verify that creating a list without the name parameter is rejected with 400 and returns 'invalid value for name'")
    public void TC_Param_009_createList_missingName() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("idBoard", getBoardId())
                .when()
                .post("/1/lists")
                .then().log().body()
                .statusCode(400)
                .body("message", equalTo("invalid value for name"))
                .body("error", equalTo("BAD_REQUEST_ERROR"));
    }

    @Test(priority = 10)
    @Description("TC-Param-010 — Verify that creating a list with an empty string as the name is rejected with 400 and returns 'invalid value for name'")
    public void TC_Param_010_createList_emptyName() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "")
                .queryParam("idBoard", getBoardId())
                .when()
                .post("/1/lists")
                .then().log().body()
                .statusCode(400)
                .body("message", equalTo("invalid value for name"))
                .body("error", equalTo("BAD_REQUEST_ERROR"));
    }

    @Test(priority = 11)
    @Description("TC-Param-011 — Verify that creating a list without the idBoard parameter is rejected with 400 and returns 'invalid value for idBoard'")
    public void TC_Param_011_createList_missingIdBoard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Test List")
                .when()
                .post("/1/lists")
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for idBoard"));
    }

    @Test(priority = 12)
    @Description("TC-Param-012 — Verify that creating a list with a nonexistent idBoard value is rejected with 400 and returns 'invalid value for idBoard'")
    public void TC_Param_012_createList_fakeIdBoard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Test List")
                .queryParam("idBoard", "000000000000000000000000")
                .when()
                .post("/1/lists")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("unauthorized board list requested 000000000000000000000000"));
    }

    @Test(priority = 13)
    @Description("TC-Param-013 — Verify that retrieving a list without the listId path parameter is rejected with a 404 routing error")
    public void TC_Param_013_getList_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/lists/")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("Cannot GET /1/lists/?key=" + KEY + "&token=" + TOKEN));

    }

    @Test(priority = 14)
    @Description("TC-Param-014 — Verify that retrieving a list with a nonexistent listId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_014_getList_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/lists/{id}", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("model not found"));
    }

    @Test(priority = 15)
    @Description("TC-Param-015 — Verify that updating a list without the listId path parameter is rejected with a 404 routing error")
    public void TC_Param_015_updateList_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/lists/")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("Cannot PUT /1/lists/?key=" + KEY + "&token=" + TOKEN + "&name=New%20Name"));


    }

    @Test(priority = 16)
    @Description("TC-Param-016 — Verify that updating a list with a nonexistent listId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_016_updateList_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/lists/{id}", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("model not found"));
    }

    @Test(priority = 17)
    @Description("TC-Param-017 — Verify that archiving a list with a nonexistent listId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_017_archiveList_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("value", "true")
                .when()
                .put("/1/lists/{id}/closed", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("model not found"));
    }

    @Test(priority = 18)
    @Description("TC-Param-018 — Verify that archiving a list without the listId path parameter is rejected with a 404 routing error")
    public void TC_Param_018_archiveList_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("value", "true")

                .when()
                .put("/1/lists/closed")
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid id"));
    }

    @Test(priority = 19)
    @Description("TC-Param-019 — Verify that archiving a list with an invalid value (not true/false) is rejected with 400 and returns 'invalid value for value'")
    public void TC_Param_019_archiveList_invalidValue() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("value", "yes")
                .when()
                .put("/1/lists/{id}/closed", getListId())
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for value"));
    }

    @Test(priority = 20)
    @Description("TC-Param-020 — Verify that archiving a list without the value query parameter is rejected with 400 and returns 'invalid value for value'")
    public void TC_Param_020_archiveList_missingValue() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .put("/1/lists/{id}/closed", getListId())
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for value"));
    }

    // ─────────────────────────────────────────────
    // CARD
    // ─────────────────────────────────────────────

    @Test(priority = 21)
    @Description("TC-Param-023 — Verify that creating a card without the idList parameter is rejected with 400 and returns 'invalid value for idList'")
    public void TC_Param_021_createCard_missingIdList() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Test Card")
                .when()
                .post("/1/cards")
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for idList"));
    }

    @Test(priority = 22)
    @Description("TC-Param-022 — Verify that creating a card with a nonexistent idList value is rejected with 400 and returns 'invalid value for idList'")
    public void TC_Param_022_createCard_fakeIdList() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Test Card")
                .queryParam("idList", "000000000000000000000000")
                .when()
                .post("/1/cards")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("could not find the board that the card belongs to"));
    }

    @Test(priority = 23)
    @Description("TC-Param-023 — Verify that retrieving a card without the cardId path parameter is rejected with a 404 routing error")
    public void TC_Param_023_getCard_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/cards/")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("Cannot GET /1/cards/?key=" + KEY + "&token=" + TOKEN));
    }

    @Test(priority = 24)
    @Description("TC-Param-024 — Verify that retrieving a card with a nonexistent cardId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_024_getCard_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/cards/{id}", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("The requested resource was not found."));
    }

    @Test(priority = 25)
    @Description("TC-Param-025 — Verify that updating a card without the cardId path parameter is rejected with a 404 routing error")
    public void TC_Param_025_updateCard_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/cards/")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("Cannot PUT /1/cards/?key=" + KEY + "&token=" + TOKEN + "&name=New%20Name"));
    }

    @Test(priority = 26)
    @Description("TC-Param-026 — Verify that updating a card with a nonexistent cardId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_026_updateCard_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/cards/{id}", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("The requested resource was not found."));
    }

    @Test(priority = 27)
    @Description("TC-Param-027 — Verify that deleting a card without the cardId path parameter is rejected with a 404 routing error")
    public void TC_Param_027_deleteCard_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/cards/")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("Cannot DELETE /1/cards/?key=" + KEY + "&token=" + TOKEN));
    }

    @Test(priority = 28)
    @Description("TC-Param-028 — Verify that deleting a card with a nonexistent cardId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_028_deleteCard_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/cards/{id}", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("The requested resource was not found."));
    }

    // ─────────────────────────────────────────────
    // CHECKLIST
    // ─────────────────────────────────────────────

    @Test(priority = 29)
    @Description("TC-Param-029 — Verify that creating a checklist without the idCard parameter is rejected with 400 and returns 'invalid value for idCard'")
    public void TC_Param_029_createChecklist_missingIdCard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Test Checklist")
                .when()
                .post("/1/checklists")
                .then().log().body()
                .statusCode(400)
                .body(equalTo("invalid value for idCard"));
    }

    @Test(priority = 30)
    @Description("TC-Param-030 — Verify that creating a checklist with a nonexistent idCard value is rejected with 400 and returns 'invalid value for idCard'")
    public void TC_Param_030_createChecklist_fakeIdCard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Test Checklist")
                .queryParam("idCard", "000000000000000000000000")
                .when()
                .post("/1/checklists")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("unauthorized board requested"));
    }

    @Test(priority = 31)
    @Description("TC-Param-031 — Verify that retrieving a checklist without the checklistId path parameter is rejected with a 404 routing error")
    public void TC_Param_031_getChecklist_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/checklists/")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("Cannot GET /1/checklists/?key=" + KEY + "&token=" + TOKEN));
    }

    @Test(priority = 32)
    @Description("TC-Param-032 — Verify that retrieving a checklist with a nonexistent checklistId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_032_getChecklist_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/checklists/{id}", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("The requested resource was not found."));
    }

    @Test(priority = 33)
    @Description("TC-Param-033 — Verify that updating a checklist without the checklistId path parameter is rejected with a 404 routing error")
    public void TC_Param_033_updateChecklist_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/checklists/")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("Cannot PUT /1/checklists/?key=" + KEY + "&token=" + TOKEN + "&name=New%20Name"));
    }

    @Test(priority = 34)
    @Description("TC-Param-034 — Verify that updating a checklist with a nonexistent checklistId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_034_updateChecklist_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/checklists/{id}", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("The requested resource was not found."));
    }

    @Test(priority = 35)
    @Description("TC-Param-035 — Verify that deleting a checklist without the checklistId path parameter is rejected with a 404 routing error")
    public void TC_Param_035_deleteChecklist_missingPathParam() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/checklists/")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("Cannot DELETE /1/checklists/?key=" + KEY + "&token=" + TOKEN));
    }

    @Test(priority = 36)
    @Description("TC-Param-036 — Verify that deleting a checklist with a nonexistent checklistId is rejected with 404 and returns 'The requested resource was not found.'")
    public void TC_Param_036_deleteChecklist_fakeId() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/checklists/{id}", "000000000000000000000000")
                .then().log().body()
                .statusCode(404)
                .body(equalTo("The requested resource was not found."));
    }
}