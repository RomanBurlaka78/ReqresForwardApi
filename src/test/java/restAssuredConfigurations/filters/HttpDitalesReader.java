package restAssuredConfigurations.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpDitalesReader implements Filter {

    private static final Logger logger = LogManager.getLogger(HttpDitalesReader.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        logRequest(requestSpec);
        Response response = ctx.next(requestSpec, responseSpec);//когда мы прервали запрос/ответ, то эта инструкция возобновляет
                                            //этот запрос/ответ по обычному сценарию.
        logResponse(response);
        return response;             //will return to the test for assertions
    }

    public void logRequest(FilterableRequestSpecification requestSpec){
        logger.always();
        logger.info("Request credentials:");
        logger.info("Base URL - {}", requestSpec.getBaseUri());
        logger.info("Request ContentType - {}", requestSpec.getContentType());
        logger.info("Base Path - {}", requestSpec.getBasePath());
        logger.info("UserDefinedPath - {}", requestSpec.getUserDefinedPath());
        logger.info("HTTP method - {}", requestSpec.getMethod());
        logger.info("HTTP body " + requestSpec.getBody());
    }

    public void logResponse(Response response){
        logger.info("");
        logger.info("Response credentials:");
        logger.info("response statusCode - " + response.getStatusCode());
        logger.info("response body - " + response.getBody());

    }

}
