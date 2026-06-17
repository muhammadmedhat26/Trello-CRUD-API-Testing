import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


public class DeleteGetTests extends BaseTest {


    @Test
    @Description("TC-T1-017 — Verify that an existing checklist can be deleted successfully")
    public void TC_CRUD_017_deleteChecklist() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/checklists/{id}", getChecklistId())
                .then().log().body()
                .statusCode(200);

    }

    @Test(dependsOnMethods = "TC_CRUD_017_deleteChecklist")
    @Description("TC-T1-018 — Verify that a deleted checklist can no longer be retrieved and the API returns 404 Not Found")
    public void TC_CRUD_018_getChecklist_verifyDeletion() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/checklists/{id}", getChecklistId())
                .then().log().body()
                .statusCode(404)
                .body(containsString("The requested resource was not found"));
    }

    @Test(dependsOnMethods = "TC_CRUD_018_getChecklist_verifyDeletion")
    @Description("TC-T1-019 — Verify that an existing card can be deleted successfully")
    public void TC_CRUD_019_deleteCard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/cards/{id}", getCardId())
                .then().log().body()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "TC_CRUD_019_deleteCard")
    @Description("TC-T1-020 — Verify that a deleted card can no longer be retrieved and the API returns 404 Not Found")
    public void TC_CRUD_020_getCard_verifyDeletion() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/cards/{id}", getCardId())
                .then().log().body()
                .statusCode(404)
                .body(containsString("The requested resource was not found"));
    }

    @Test(dependsOnMethods = "TC_CRUD_020_getCard_verifyDeletion")
    @Description("TC-T1-021 — Verify that an existing list can be archived by setting closed to true and the response reflects the change")
    public void TC_CRUD_021_archiveList() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("value", "true")
                .when()
                .put("/1/lists/{id}/closed", getListId())
                .then().log().body()
                .statusCode(200)
                .body("closed", equalTo(true))
                .body("id", equalTo(getListId()));
        System.out.println(getListId());
    }

    @Test(dependsOnMethods = "TC_CRUD_021_archiveList")
    @Description("TC-T1-022 — Verify that an archived list still returns 200 with closed = true when retrieved and is not treated as a deleted resource")
    public void TC_CRUD_022_getList_verifyArchived() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/lists/{id}", getListId())
                .then().log().body()
                // Must be 200 NOT 404 — archived lists are not deleted
                .statusCode(200)
                .body("closed", equalTo(true))
                .body("id", equalTo(getListId()));
    }

    @Test(dependsOnMethods = "TC_CRUD_022_getList_verifyArchived")
    @Description("TC-T1-023 — Verify that an existing list can be archived by setting closed to true and the response reflects the change")
    public void TC_CRUD_023_unarchiveList() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("value", "false")
                .when()
                .put("/1/lists/{id}/closed", getListId())
                .then().log().body()
                .statusCode(200)
                .body("closed", equalTo(false))
                .body("id", equalTo(getListId()));
        System.out.println(getListId());
    }

    @Test(dependsOnMethods = "TC_CRUD_023_unarchiveList")
    @Description("TC-T1-024 — Verify that an unarchived list still returns 200 with closed = false when retrieved and is not treated as a fully deleted resource")
    public void TC_CRUD_024_getList_verifyUnarchived() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/lists/{id}", getListId())
                .then().log().body()
                // Must be 200 NOT 404 — archived lists are not deleted
                .statusCode(200)
                .body("closed", equalTo(false))
                .body("id", equalTo(getListId()));
    }


    @Test(dependsOnMethods = "TC_CRUD_024_getList_verifyUnarchived")
    @Description("TC-T1-025 — Verify that an existing board can be deleted successfully")
    public void TC_CRUD_025_deleteBoard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("/1/boards/{id}", getBoardId())
                .then().log().body()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "TC_CRUD_025_deleteBoard")
    @Description("TC-T1-026 — Verify that a deleted board can no longer be retrieved and the API returns 404 Not Found")
    public void TC_CRUD_026_getBoard_verifyDeletion() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/boards/{id}", getBoardId())
                .then().log().body()
                .statusCode(404);
    }
}
