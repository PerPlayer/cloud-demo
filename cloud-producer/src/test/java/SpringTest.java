import com.cloud.producer.ProducerApplication;
import com.cloud.producer.service.GenerateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProducerApplication.class)
public class SpringTest {

    @Autowired
    private GenerateService generateService;

    @Test
    public void generateTest(){
        generateService.generate("crawler_entry");
    }
}
