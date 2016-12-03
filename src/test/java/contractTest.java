//import au.com.dius.pact.consumer.ConsumerPactTest;
//import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
//import au.com.dius.pact.model.PactFragment;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.Assert.assertEquals;
//
//
//public class contractTest extends ConsumerPactTest {
//
//   // private static final String URL = "http://localhost:9001/cost";
//
//    @Override
//    public PactFragment createFragment(PactDslWithProvider builder) {
//        Map<String, String> headers = new HashMap<String, String>();
//        //headers.put("Content-Type", "application/json;charset=UTF-8");
//        headers.put("Content-Type", "text/plain;charset=UTF-8");
//        System.out.println("******Create");
//        return builder
//                .given("require statistic data")
//                .uponReceiving("a_request_for_statistic")
//                .path("/rest/analysis/services/statistic")
//                .method("GET")
//                .headers(headers)
//                .willRespondWith()
//                .status(200)
//                .headers(headers)
//                .body("{\"responsetest\": true, \"name\": \"harry\"}")
//                .toFragment();
//    }
//
//    @Override
//    protected String providerName() {
//        return "service_provider";
//    }
//
//    @Override
//    protected String consumerName() {
//        return "Consumer";
//    }
//
//    @Override
//    public void runTest(String url) throws IOException {
//        System.out.println("******Run");
//
//        assertEquals(new ConsumerClient(url).getStatusCode("/rest/analysis/services/statistic"), 200);
////        Map expectedResponse = new HashMap();
////        expectedResponse.put("responsetest", true);
////        expectedResponse.put("name", "harry");
////        assertEquals(new ConsumerClient(url).getAsMap("/", ""), expectedResponse);
////        assertEquals(new ConsumerClient(url).getStatusCode("/second"), 200);
//
//    }
//
//    @Test
//    public void name() throws Exception {
//        System.out.println("******TEST");
//
//
//    }
//}
