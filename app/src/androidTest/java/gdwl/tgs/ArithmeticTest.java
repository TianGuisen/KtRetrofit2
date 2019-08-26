package gdwl.tgs;
import androidx.test.runner.AndroidJUnit4;
import com.orhanobut.logger.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ArithmeticTest {
    /**
     * 冒泡排序思想
     * 相邻元素22比较,如果前元素比后元素大,则换位
     * 循环次数:size-1
     * 每次循环的比较次数:size-循环次数-1
     */
    @Test
    public void bubbling(){
        int a[]={12,4,984,6,55,41,999,33,1577,626};
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j]>a[j+1]) {
                    int x = a[j];
                    a[j]=a[j+1];
                    a[j+1]=x;
                }
            }
        }
        Logger.d(a);
    }
    
}
