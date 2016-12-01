import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class pactTest {

    private static final String URL = "http://localhost:9001";

    @Rule
    public PactProviderRule provider = new PactProviderRule("service_provider", "localhost", 9001, this);

    @Pact(provider = "service_provider", consumer = "app_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain;charset=ISO-8859-1");

        return builder
                .given("require statistic data")
                .uponReceiving("a_request_for_statistic")
                .path("/cost/rest/analysis/services/statistic")
                .method("GET")
                .headers(headers)
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("{\"ok\": true}")
                .toFragment();
    }


    @PactVerification("service_provider")
    @Test
    public void runTest() throws IOException {
//        Map expectedResponse = new HashMap();
//        expectedResponse.put("ok", true);
        assertEquals(new ConsumerClient(URL).options("/cost/rest/analysis/services/statistic"), 200);
//        Map expectedResponse = new HashMap();
//        expectedResponse.put("responsetest", true);
//        expectedResponse.put("name", "harry");
//        assertEquals(new ConsumerClient(url).getAsMap("/", ""), expectedResponse);
//        assertEquals(new ConsumerClient(url).options("/second"), 200);

    }
}
