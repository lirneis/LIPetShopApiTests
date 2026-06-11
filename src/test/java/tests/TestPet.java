package tests;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPet {

    @Test
    @TmsLink("https://team-w1q0.testit.software/projects/1/tests/39")
    @Feature("Pet")
    @Severity(SeverityLevel.NORMAL)
    @Owner("lirneis")
    @DisplayName("Попытка удалить несуществующего питомца (DELETE /pet/{petId})")
    public void testDeleteNonExistentPet() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .when()
                .delete(Base.BASE_URL + "/pet/9999");

        final String responseBody = response.getBody().asString();

        step("Check response code is 200", () ->
                assertEquals(200, response.getStatusCode(),
                        "Response code does not equal the expected result. " +
                                "Actual result:" + responseBody)
        );
        step("Check message is \"Pet deleted\"", () ->
                assertEquals("Pet deleted", responseBody,
                        "Error text does not match the expected response." +
                                "Actual text: " + responseBody)
        );
    }

    @Test
    @TmsLink("https://team-w1q0.testit.software/projects/1/tests/38")
    @Feature("Pet")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("lirneis")
    @DisplayName("Попытка обновить несуществующего питомца (PUT /pet/{petId})")
    public void testUpdateNonExistentPet() {
        Pet pet = new Pet();
        pet.setId(9999);
        pet.setName("Non-existent Pet");
        pet.setStatus("available");

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .body(pet)
                .when()
                .put(Base.BASE_URL + "/pet/");

        final String responseBody = response.getBody().asString();

        step("Check response code is 404", () ->
                assertEquals(404, response.getStatusCode(),
                        "Response code does not equal the expected result. " +
                                "Actual result:" + responseBody)
        );
        step("Check message is \"Pet not found\"", () ->
                assertEquals("Pet not found", responseBody,
                        "Error text does not match the expected response." +
                                "Actual text: " + responseBody)
        );

    }

    public enum Base {
        ;
        public static final String BASE_URL = "http://5.181.109.28:9090/api/v3";
    }
}
