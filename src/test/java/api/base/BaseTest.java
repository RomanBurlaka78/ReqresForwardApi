package api.base;

import api.listeners.TestListener;
import api.specifications.ApiSpecifications;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {

    @BeforeClass
    public void setUp() {
        ApiSpecifications.installSpecification(ApiSpecifications.requestSpec());
        // Включение логов для всех запросов (опционально)
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}