import com.cloud.producer.ProducerApplication;
import com.cloud.producer.codegenerator.GenerateService;
import com.cloud.producer.utils.PPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
public class SpringTest {

    @Autowired
    private GenerateService generateService;

    @Test
    public void generateTest() throws Exception{


        PPage pg = new PPage();
//        generateService.queryTableList("crawler", pg);
        generateService.generateCode("crawler_log");
    }
}
