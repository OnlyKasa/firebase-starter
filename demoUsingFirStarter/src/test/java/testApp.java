import com.google.firebase.database.FirebaseDatabase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = SpringJUnit4ClassRunner.class)
public class testApp {
    @Autowired
    private FirebaseDatabase database ;

    @Test
    public void test(){
        database.getReference();
        database.getReference().getKey();
        System.out.println("debug");
    }

    @Test
    public void contextLoads() {
    }
}
