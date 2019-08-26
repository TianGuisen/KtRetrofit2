package gdwl.tgs.agency;
import androidx.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AgencyTest {
    @Test
    public void test(){
        PersonImplProxy proxy = new PersonImplProxy();
        IPerson IPerson = proxy.createProxy();
        IPerson.sing();
        IPerson.dance();
    }
    
}
