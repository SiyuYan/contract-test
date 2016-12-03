import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.TestCase.assertEquals;

public class pactTest {

    private static final String URL = "http://localhost:9002";
    private static final String path = "/cost/rest/analysis/services/statistic";
    ObjectMapper mapper = new ObjectMapper();

    @Rule
    public PactProviderRule provider = new PactProviderRule("service_provider", "localhost", 9002, this);

    @Pact(provider = "service_provider", consumer = "app_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) throws JsonProcessingException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain;charset=ISO-8859-1");

        ArrayList<User> users = newArrayList(
                new User("siyu", "yan", 18, "female"),
                new User("siyu1", "yan1", 18, "female"));

        return builder
                .given("require user data list")
                .uponReceiving("a_request_for_statistic")
                .path(path)
                .method("GET")
                .headers(headers)
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(mapper.writeValueAsString(users))

                .given("search specific user data")
                .uponReceiving("a_request_for_statistic")
                .path(path + "/siyu")
                .method("GET")
                .headers(headers)
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(mapper.writeValueAsString(users.get(0)))
                .toFragment();
    }

    @PactVerification("service_provider")
    @Test
    public void runTest() throws IOException, URISyntaxException {
        assertEquals(new ConsumerClient(URL).getStatusCode(path), 200);
        ArrayList<User> users = newArrayList(
                new User("siyu", "yan", 18, "female"),
                new User("siyu1", "yan1", 18, "female"));

        String nameValuePair = String.valueOf(new BasicNameValuePair("firstName", "siyu"));

        assertEquals(new ConsumerClient(URL).getAsString(path, null), mapper.writeValueAsString(users));
        assertEquals(new ConsumerClient(URL).getAsString(path + "/siyu", nameValuePair), mapper.writeValueAsString(users.get(0)));
    }
}
