package gdwl.tgs.dataStructure;

import java.util.Objects;

/**
 * @author 田桂森 2019/8/15
 */
public class TestBean1 {
    int i ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestBean1 testBean1 = (TestBean1) o;
        return i == testBean1.i;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i);
    }
}
