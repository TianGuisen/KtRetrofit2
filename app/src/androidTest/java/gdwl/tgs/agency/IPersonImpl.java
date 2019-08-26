package gdwl.tgs.agency;

import com.orhanobut.logger.Logger;

/**
 * @author 田桂森 2019/8/22
 */
public class IPersonImpl implements IPerson {

    @Override
    public void sing() {
        Logger.d("开始唱歌");

    }

    @Override
    public void dance() {
        Logger.d("开始跳舞");
    }

}