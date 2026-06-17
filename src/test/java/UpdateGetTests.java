import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class UpdateGetTests extends BaseTest {

    @Test
    @Description("TC-T1-009 — Verify that an existing board can be renamed successfully and the updated name is returned in the response")
    public void TC_CRUD_009_updateBoard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Updated Automation Board")
                .when()
                .put("/1/boards/{id}", getBoardId())
                .then().log().body()
                .statusCode(200)
                .body("id", equalTo(getBoardId()))
                .body("name", equalTo("Updated Automation Board"))
                .body("closed", equalTo(false));
    }

    @Test(dependsOnMethods = "TC_CRUD_009_updateBoard")
    @Description("TC-T1-010 — Verify that the updated board name persisted after the rename and all unchanged fields remain intact")
    public void TC_CRUD_010_getBoard_verifyUpdate() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/boards/{id}", getBoardId())
                .then().log().body()
                .statusCode(200)
                .body("name", equalTo("Updated Automation Board"))
                .body("closed", equalTo(false));
    }

    @Test(dependsOnMethods = "TC_CRUD_010_getBoard_verifyUpdate")
    @Description("TC-T1-011 — Verify that an existing list can be renamed successfully and the updated name is returned in the response")
    public void TC_CRUD_011_updateList() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Updated Automation List")
                .when()
                .put("/1/lists/{id}", getListId())
                .then().log().body()
                .statusCode(200)
                .body("name", equalTo("Updated Automation List"))
                .body("idBoard", equalTo(getBoardId()));
    }

    @Test(dependsOnMethods = "TC_CRUD_011_updateList")
    @Description("TC-T1-012 — Verify that the updated list name persisted after the rename and all unchanged fields remain intact")
    public void TC_CRUD_012_getList_verifyUpdate() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/lists/{id}", getListId())
                .then().log().body()
                .statusCode(200)
                .body("name", equalTo("Updated Automation List"))
                .body("closed", equalTo(false));
    }

    @Test(dependsOnMethods = "TC_CRUD_012_getList_verifyUpdate")
    @Description("TC-T1-013 — Verify that an existing card can be renamed successfully and the updated name is returned in the response")
    public void TC_CRUD_013_updateCard() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Updated Automation Card")
                .when()
                .put("/1/cards/{id}", getCardId())
                .then().log().body()
                .statusCode(200)
                .body("name", equalTo("Updated Automation Card"))
                .body("idList", equalTo(getListId()));
    }

    @Test(dependsOnMethods = "TC_CRUD_013_updateCard")
    @Description("TC-T1-014 — Verify that the updated card name persisted after the rename and all unchanged fields remain intact")
    public void TC_CRUD_014_getCard_verifyUpdate() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/cards/{id}", getCardId())
                .then().log().body()
                .statusCode(200)
                .body("name", equalTo("Updated Automation Card"))
                .body("closed", equalTo(false));
    }

    @Test(dependsOnMethods = "TC_CRUD_014_getCard_verifyUpdate")
    @Description("TC-T1-015 — Verify that an existing checklist can be renamed successfully and the updated name is returned in the response")
    public void TC_CRUD_015_updateChecklist() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "Updated Automation Checklist")
                .when()
                .put("/1/checklists/{id}", getChecklistId())
                .then().log().body()
                .statusCode(200)
                .body("name", equalTo("Updated Automation Checklist"))
                .body("idCard", equalTo(getCardId()));
    }

    @Test(dependsOnMethods = "TC_CRUD_015_updateChecklist")
    @Description("TC-T1-016 — Verify that the updated checklist name persisted after the rename and all unchanged fields remain intact")
    public void TC_CRUD_016_getChecklist_verifyUpdate() {
        given()
                .baseUri(BASE_URL)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/1/checklists/{id}", getChecklistId())
                .then().log().body()
                .statusCode(200)
                .body("name", equalTo("Updated Automation Checklist"))
                .body("idCard", equalTo(getCardId()));
    }

}
