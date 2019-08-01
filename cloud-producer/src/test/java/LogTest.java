import com.cloud.producer.ProducerApplication;
import com.cloud.producer.model.LogModel;
import com.cloud.producer.service.LogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
public class LogTest {

    @Autowired
    private LogService service;

    @Test
    public void test() {
        List<LogModel> models = service.queryAll();
        IntStream.range(0, models.size()>10?10:models.size()).forEach(index -> {
            System.out.println(models.get(index));
        });
    }
}
