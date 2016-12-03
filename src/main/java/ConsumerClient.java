import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;


@Component
public class ConsumerClient {
    private String url;

    public ConsumerClient(String url) {
        this.url = url;
    }

    public String getAsString(String path) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url).setPath(path);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(uriBuilder.toString());
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}