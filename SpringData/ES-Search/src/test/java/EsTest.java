import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author binSin
 * @date 2021/9/2
 */
public class EsTest {

    @Autowired
    private RestHighLevelClient client;

    void testCreateIndex(){
        CreateIndexRequest request = new CreateIndexRequest("test_index");

    }
}
