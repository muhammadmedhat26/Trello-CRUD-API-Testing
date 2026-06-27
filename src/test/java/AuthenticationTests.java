import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthenticationTests extends BaseTest {

    // ─────────────────────────────────────────────
    // BOARD
    // ─────────────────────────────────────────────

    @Test(priority = 1)
    @Description("TC-Auth-001 — Verify that creating a board without the key parameter is rejected with 401")
    public void TC_Auth_001_createBoard_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .queryParam("name", "Test Board")
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 2)
    @Description("TC-Auth-002 — Verify that creating a board without the token parameter is rejected with 401")
    public void TC_Auth_002_createBoard_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("name", "Test Board")
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 3)
    @Description("TC-Auth-003 — Verify that retrieving a board without the key parameter is rejected with 401")
    public void TC_Auth_003_getBoard_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/boards/{id}", getBoardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 4)
    @Description("TC-Auth-004 — Verify that retrieving a board without the token parameter is rejected with 401")
    public void TC_Auth_004_getBoard_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .when()
                .get("/1/boards/{id}", getBoardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 5)
    @Description("TC-Auth-005 — Verify that updating a board without the key parameter is rejected with 401")
    public void TC_Auth_005_updateBoard_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/boards/{id}", getBoardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 6)
    @Description("TC-Auth-006 — Verify that updating a board without the token parameter is rejected with 401")
    public void TC_Auth_006_updateBoard_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("name", "New Name")
                .when()
                .put("/1/boards/{id}", getBoardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 7)
    @Description("TC-Auth-007 — Verify that deleting a board without the key parameter is rejected with 401")
    public void TC_Auth_007_deleteBoard_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/boards/{id}", getBoardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 8)
    @Description("TC-Auth-008 — Verify that deleting a board without the token parameter is rejected with 401")
    public void TC_Auth_008_deleteBoard_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .when()
                .delete("/1/boards/{id}", getBoardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    // ─────────────────────────────────────────────
    // LIST
    // ─────────────────────────────────────────────

    @Test(priority = 9)
    @Description("TC-Auth-009 — Verify that creating a list without the key parameter is rejected with 401")
    public void TC_Auth_009_createList_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .queryParam("name", "Test List")
                .queryParam("idBoard", getBoardId())
                .when()
                .post("/1/lists")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 10)
    @Description("TC-Auth-010 — Verify that creating a list without the token parameter is rejected with 401")
    public void TC_Auth_010_createList_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("name", "Test List")
                .queryParam("idBoard", getBoardId())
                .when()
                .post("/1/lists")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 11)
    @Description("TC-Auth-011 — Verify that retrieving a list without the key parameter is rejected with 401")
    public void TC_Auth_011_getList_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/lists/{id}", getListId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 12)
    @Description("TC-Auth-012 — Verify that retrieving a list without the token parameter is rejected with 401")
    public void TC_Auth_012_getList_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .when()
                .get("/1/lists/{id}", getListId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 13)
    @Description("TC-Auth-013 — Verify that updating a list without the key parameter is rejected with 401")
    public void TC_Auth_013_updateList_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/lists/{id}", getListId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 14)
    @Description("TC-Auth-014 — Verify that updating a list without the token parameter is rejected with 401")
    public void TC_Auth_014_updateList_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("name", "New Name")
                .when()
                .put("/1/lists/{id}", getListId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 15)
    @Description("TC-Auth-015 — Verify that deleting a list without the key parameter is rejected with 401")
    public void TC_Auth_015_deleteList_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/lists/{id}", getListId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 16)
    @Description("TC-Auth-016 — Verify that deleting a list without the token parameter is rejected with 401")
    public void TC_Auth_016_deleteList_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .when()
                .delete("/1/lists/{id}", getListId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    // ─────────────────────────────────────────────
    // CARD
    // ─────────────────────────────────────────────

    @Test(priority = 17)
    @Description("TC-Auth-017 — Verify that creating a card without the key parameter is rejected with 401")
    public void TC_Auth_017_createCard_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .queryParam("name", "Test Card")
                .queryParam("idList", getListId())
                .when()
                .post("/1/cards")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 18)
    @Description("TC-Auth-018 — Verify that creating a card without the token parameter is rejected with 401")
    public void TC_Auth_018_createCard_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("name", "Test Card")
                .queryParam("idList", getListId())
                .when()
                .post("/1/cards")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 19)
    @Description("TC-Auth-019 — Verify that retrieving a card without the key parameter is rejected with 401")
    public void TC_Auth_019_getCard_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/cards/{id}", getCardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 20)
    @Description("TC-Auth-020 — Verify that retrieving a card without the token parameter is rejected with 401")
    public void TC_Auth_020_getCard_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .when()
                .get("/1/cards/{id}", getCardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 21)
    @Description("TC-Auth-021 — Verify that updating a card without the key parameter is rejected with 401")
    public void TC_Auth_021_updateCard_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/cards/{id}", getCardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 22)
    @Description("TC-Auth-022 — Verify that updating a card without the token parameter is rejected with 401")
    public void TC_Auth_022_updateCard_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("name", "New Name")
                .when()
                .put("/1/cards/{id}", getCardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 23)
    @Description("TC-Auth-023 — Verify that deleting a card without the key parameter is rejected with 401")
    public void TC_Auth_023_deleteCard_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/cards/{id}", getCardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 24)
    @Description("TC-Auth-024 — Verify that deleting a card without the token parameter is rejected with 401")
    public void TC_Auth_024_deleteCard_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .when()
                .delete("/1/cards/{id}", getCardId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    // ─────────────────────────────────────────────
    // CHECKLIST
    // ─────────────────────────────────────────────

    @Test(priority = 25)
    @Description("TC-Auth-025 — Verify that creating a checklist without the key parameter is rejected with 401")
    public void TC_Auth_025_createChecklist_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .queryParam("name", "Test Checklist")
                .queryParam("idCard", getCardId())
                .when()
                .post("/1/checklists")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 26)
    @Description("TC-Auth-026 — Verify that creating a checklist without the token parameter is rejected with 401")
    public void TC_Auth_026_createChecklist_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("name", "Test Checklist")
                .queryParam("idCard", getCardId())
                .when()
                .post("/1/checklists")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 27)
    @Description("TC-Auth-027 — Verify that retrieving a checklist without the key parameter is rejected with 401")
    public void TC_Auth_027_getChecklist_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/checklists/{id}", getChecklistId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 28)
    @Description("TC-Auth-028 — Verify that retrieving a checklist without the token parameter is rejected with 401")
    public void TC_Auth_028_getChecklist_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .when()
                .get("/1/checklists/{id}", getChecklistId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 29)
    @Description("TC-Auth-029 — Verify that updating a checklist without the key parameter is rejected with 401")
    public void TC_Auth_029_updateChecklist_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .queryParam("name", "New Name")
                .when()
                .put("/1/checklists/{id}", getChecklistId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 30)
    @Description("TC-Auth-030 — Verify that updating a checklist without the token parameter is rejected with 401")
    public void TC_Auth_030_updateChecklist_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("name", "New Name")
                .when()
                .put("/1/checklists/{id}", getChecklistId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 31)
    @Description("TC-Auth-031 — Verify that deleting a checklist without the key parameter is rejected with 401")
    public void TC_Auth_031_deleteChecklist_noKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/checklists/{id}", getChecklistId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 32)
    @Description("TC-Auth-032 — Verify that deleting a checklist without the token parameter is rejected with 401")
    public void TC_Auth_032_deleteChecklist_noToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .when()
                .delete("/1/checklists/{id}", getChecklistId())
                .then().log().body()
                .statusCode(401)
                .body(equalTo("missing scopes"));
    }

    @Test(priority = 33)
    @Description("TC-Auth-033 — Verify that creating a board with an invalid key value is rejected with 401 and returns 'invalid key'")
    public void TC_Auth_033_createBoard_invalidKey() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", "INVALID_KEY_00000")
                .queryParam("token", TOKEN)
                .queryParam("name", "Test Board")
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid key"));
    }

    @Test(priority = 34)
    @Description("TC-Auth-034 — Verify that creating a board with an invalid token value is rejected with 401 and returns 'invalid app token'")
    public void TC_Auth_034_createBoard_invalidToken() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", "INVALID_TOKEN_00000")
                .queryParam("name", "Test Board")
                .when()
                .post("/1/boards/")
                .then().log().body()
                .statusCode(401)
                .body(equalTo("invalid app token"));
    }

    @Test(priority = 35)
    @Description("TC-Auth-035 — Verify that retrieving a board without both key and token is rejected with 401 and returns 'unauthorized permission requested'")
    public void TC_Auth_035_getBoard_noKeyNoToken() {
        given()
                .baseUri(BASE_URL)
                .when()
                .get("/1/boards/{id}", boardId)
                .then().log().body()
                .statusCode(401)
                .body(equalTo("unauthorized permission requested"));
    }
}