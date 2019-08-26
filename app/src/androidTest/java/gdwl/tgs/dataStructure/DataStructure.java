package gdwl.tgs.dataStructure;

/**
 * @author 田桂森 2019/8/15
 */

import androidx.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

@RunWith(AndroidJUnit4.class)
public class DataStructure {

    @Test
    public void hashMapTest() {
        HashMap<Integer, String> map1 = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            map1.put(i, "run");
        }
    }

    @Test
    public void hashTable() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("", "");
    }

    @Test
    public void listTest() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 9; i++) {
            arrayList.add(i);
        }
        arrayList.add(9);
        arrayList.add(10);
    }
    
    @Test
    public void hashSet(){
        HashSet hashSet=new HashSet();
        hashSet.add("");
    }
    @Test
    public void LinkedListTest() {
        LinkedList linkedList = new LinkedList();
        linkedList.add("run");
    }

    @Test
    public void stackTest() {
        Stack stack = new Stack();
    }
}
