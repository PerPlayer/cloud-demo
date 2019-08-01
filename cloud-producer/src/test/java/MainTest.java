import com.cloud.producer.codegenerator.GenerateService;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class MainTest {

    private static int num = 0;

    public static void main(String[] args) {

        new GenerateService().generate(null);

        System.exit(0);
        System.out.println(UUID.fromString("a1a4a1b7-f06d-4e4c-8d55-5742b94528ed").toString());
    }
}
