package api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

/**
 * Created by admin on 10.02.2020.
 *
 * Используя этот сервис с тестовым REST-APIhttps://reqres.in/, написать 2 теста.
 *
 * Первый – получить список пользователей (GET  https://reqres.in/api/users?page=2),
 * замапить на объект и проверить, что все поля пришли (достаточно на notNull).
 *
 * Второй – создать пользователя (POST https://reqres.in/api/users) с данными из примера на сайте:
 * подготовить тело запроса, отправить запрос с телом, замапить ответ на объект и проверить,
 * что в ответе те же самые значения из запроса.
 */
public class Test_GetPost {

    private static final String basePath = "https://reqres.in/api";
    private static WireMockServer wireMockServer;

    //@BeforeTest
    void setUp(){
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("http://127.0.0.1", 8089);
    }

    //@AfterTest
    void setDown(){
        wireMockServer.stop();
    }

    @Test
    void getReqres() throws InterruptedException {
        // заглушка, для проверки корректной работы на различных данных.
        // для использования - раскомментить вместе с @BeforeTest и @AfterTest. body - в /resources/__files
        // when().get("http://127.0.0.1:8089/wiremock")
        when().get(basePath + "/users?page=2")
                .then().assertThat()
                .statusCode(200)
                .body("page", equalTo(2))
                .body(matchesJsonSchemaInClasspath("schemas/UsersSchema.json")); //проверки на null - соответствие схеме

    }

    @Test
    void postReqres() throws InterruptedException {

        User newUser = new User("Amorpheus", "Cheerleader");

        Response response = given().body(newUser)
                .contentType("application/json")
                .when().post(basePath + "/users");

        response.then().assertThat().statusCode(201);

        User existUser = response.as(User.class);

        System.out.println(existUser.toString());
        System.out.println(newUser.toString());
        assertTrue(existUser.equals(newUser));
    }
}
