import com.google.common.net.UrlEscapers;
import org.apache.commons.lang.StringUtils;
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

    public int getStatusCode(String path) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url + encodePath(path));
        request.addHeader(TESTREQHEADER, TESTREQHEADERVALUE);
        return client.execute(request).getStatusLine().getStatusCode();
    }

    public String getAsString(String path, String queryString) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url).setPath(path);
        if (StringUtils.isNotEmpty(queryString)) {
            uriBuilder.setParameters();
        }
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(uriBuilder.toString());
        request.addHeader(TESTREQHEADER, TESTREQHEADERVALUE);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private String encodePath(String path) {
        return Arrays.asList(path.split("/"))
                .stream().map(UrlEscapers.urlPathSegmentEscaper()::escape).collect(Collectors.joining("/"));
    }
}