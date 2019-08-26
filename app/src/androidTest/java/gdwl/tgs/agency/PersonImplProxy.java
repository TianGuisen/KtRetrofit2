package gdwl.tgs.agency;

import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 田桂森 2019/8/22
 * https://www.jianshu.com/p/fc285d669bc5
 */
public class PersonImplProxy {

    private IPerson IPerson = new IPersonImpl();

    /**
     * 创建代理
     *
     * @return 返回值是接口类型
     */
    public IPerson createProxy() {
        /**
         * 产生某个对象的代理对象
         * ClassLoader loader    当前代理对象的类加载器
         * Class<?>[] interfaces 代理对象的接口
         * InvocationHandler h   InvocationHandler对象
         */
        return (IPerson) Proxy.newProxyInstance(PersonImplProxy.class.getClassLoader(), IPerson.getClass().getInterfaces(), new InvocationHandler() {

            /**
             * @param proxy 把代理对象自身传进去
             * @param method 代表当前调用的方法
             * @param args 当前调用方法的参数
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 编写代码
                // 获取方法名
                String methodName = method.getName();
                if ("sing".equals(methodName)) {
                    Logger.d("准备唱歌");
                    method.invoke(IPerson, args);
                    Logger.d("结束唱歌");
                } else if ("dance".equals(methodName)) {
                    Logger.d("准备跳舞");
                    method.invoke(IPerson, args);
                    Logger.d("结束跳舞");
                } 
                return null;
            }
        });
    }

}