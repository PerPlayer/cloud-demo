import com.cloud.config.ApplicationContextUtil;
import com.cloud.producer.ProducerApplication;
import com.cloud.producer.codegenerator.GenerateService;
import com.cloud.producer.properties.ProducerProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
public class SpringTest {

    @Autowired
    private GenerateService generateService;

    @Autowired
    private ApplicationContextUtil contextUtil;

    @Value("${cloud-base.name}")
    private String value;

    @Test
    public void generateTest() throws Exception{

        ProducerProperties bean = ApplicationContextUtil.getBean(ProducerProperties.class);
        System.out.println(bean);
        System.out.println(value);
//        PPage pg = new PPage();
//        PPage<Map> page = generateService.queryTableList("crawler", pg);
//        System.out.println(page);
//        generateService.generateCode("crawler_log");
    }
}
