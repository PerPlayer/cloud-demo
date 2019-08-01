import com.cloud.producer.ProducerApplication;
import com.cloud.producer.codegenerator.GenerateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
public class SpringTest {

    @Autowired
    private GenerateService generateService;

    @Test
    public void generateTest(){
//        List<EntryModel> models = service.queryAll();
//        models.forEach(model->{
//            System.out.println(model.getTitle());
//        });
//        EntryModel model = service.queryById("1552014416588");
//        System.out.println(model.getTitle());

//        EntryModel model = new EntryModel();
//        model.setTitle("ttt");
//        model.setVersion(0);
//        model.setContent("yyy");
//        model.setTaskId("111");
//        EntryModel re = service.save(model);
//        System.out.println(re.getId());
//        System.out.println(service.update("507753330efa457a9e6d19a1c391f10c", "content", "zzz"));
//        System.out.println(service.delete("507753330efa457a9e6d19a1c391f10c"));

        generateService.generate("crawler_log");
    }
}
