import com.google.common.net.UrlEscapers;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


@Component
public class ConsumerClient {

    private static final String TESTREQHEADER = "Content-Type";
    private static final String TESTREQHEADERVALUE = "text/plain;charset=ISO-8859-1";
    private String url;

    public ConsumerClient(String url) {
        this.url = url;
    }

    public int options(String path) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url + encodePath(path));
        request.addHeader(TESTREQHEADER, TESTREQHEADERVALUE);
        System.out.println(client.execute(request).getEntity().getContent().read());
        return client.execute(request).getStatusLine().getStatusCode();
    }

    private String encodePath(String path) {
        return Arrays.asList(path.split("/"))
                .stream().map(UrlEscapers.urlPathSegmentEscaper()::escape).collect(Collectors.joining("/"));
    }
}