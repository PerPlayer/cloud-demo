import com.alibaba.fastjson.JSON;
import com.cloud.producer.ProducerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = ProducerApplication.class)
@WebAppConfiguration
public class MockMvcTest {

    @Autowired
    private WebApplicationContext context;

    private String exdata = null;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test() throws Exception{
        save();
        update();
        query();
        delete();
    }

    private void save() throws Exception{
        String entity = "{\"id\":\"8e84f0fb94f242c3b84b0a358caab234\",\"createTime\":null,\"modifyTime\":null,\"status\":0,\"version\":0,\"content\":\"yyy\",\"taskId\":\"222\",\"title\":\"ttt\",\"weight\":0}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/entry/save")
                .header("Content-Type", "application/json;charset=UTF-8")
                .content(entity))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        exdata = (String)JSON.parseObject(result.getResponse().getContentAsString(), Map.class).get("id");
    }

    private void update() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/entry/update")
                .header("Content-Type", "application/json;charset=UTF-8")
                .param("id", exdata)
                .param("column", "content")
                .param("value", "呵呵呵"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    public void query() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/entry/query/id")
                .header("Content-Type", "application/json;charset=UTF-8")
                .param("id", exdata))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    private void delete() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/entry/delete")
                .header("Content-Type", "application/json;charset=UTF-8")
                .param("id", exdata))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
