package org.jetbrains.assignment.controllers;

import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.SneakyThrows;
import org.jetbrains.assignment.BaseTestClass;
import org.jetbrains.assignment.test_utils.ResourceUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static io.restassured.RestAssured.given;
import static org.jetbrains.assignment.controllers.RobotController.LOCATIONS_POST;
import static org.jetbrains.assignment.controllers.RobotController.MOVES_POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class RobotControllerTest extends BaseTestClass {

    @SneakyThrows
    @Test
    void changeLocationTest() {
        Assertions.assertTrue(robotRepository.findAll().isEmpty());
        sqlStatementCountValidator.reset();

        String actual = given()
                .filter(new ResponseLoggingFilter())
                .contentType(APPLICATION_JSON_VALUE)
                .body(ResourceUtils.readAsStringFromSourcePath("/web/request/DirectionRequest.json"))
                .post(buildURL(LOCATIONS_POST))
                .then()
                .statusCode(201)
                .extract().asString();
        sqlStatementCountValidator.validateCountEqualsOrLessThen(3);

        Assertions.assertFalse(robotRepository.findAll().isEmpty());

        String expected = ResourceUtils.readAsStringFromSourcePath("/web/response/CoordinateResponse.json");
        JSONAssert.assertEquals(expected, actual, true);
    }

    @SneakyThrows
    @Test
    void movedTest() {
        Assertions.assertTrue(robotRepository.findAll().isEmpty());
        sqlStatementCountValidator.reset();

        String actual = given()
                .filter(new ResponseLoggingFilter())
                .contentType(APPLICATION_JSON_VALUE)
                .body(ResourceUtils.readAsStringFromSourcePath("/web/request/CoordinateRequest.json"))
                .post(buildURL(MOVES_POST))
                .then()
                .statusCode(201)
                .extract().asString();
        sqlStatementCountValidator.validateCountEqualsOrLessThen(3);

        Assertions.assertFalse(robotRepository.findAll().isEmpty());

        String expected = ResourceUtils.readAsStringFromSourcePath("/web/response/DirectionResponse.json");
        JSONAssert.assertEquals(expected, actual, true);
    }

}