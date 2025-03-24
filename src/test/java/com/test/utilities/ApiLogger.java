import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ApiLogger implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                         FilterableResponseSpecification responseSpec,
                         FilterContext ctx) {
        
        Response response = ctx.next(requestSpec, responseSpec);
        
        System.out.println("\n=== API Call Details ===");
        System.out.println("URL: " + requestSpec.getURI());
        System.out.println("Method: " + requestSpec.getMethod());
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body:");
        System.out.println(response.getBody().asPrettyString());
        System.out.println("=====================\n");
        
        return response;
    }
} 