package com.dxh.code.factory;

import com.dxh.code.anno.Autowired;
import com.dxh.code.anno.Component;
import com.dxh.code.utils.TransactionManger;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author https://github.com/CoderXiaohui
 * @Description 代理对象工厂，生成代理对象
 * @Date 2020-11-22 01:38
 */
@Component
public class ProxyFactory {
    @Autowired
    private TransactionManger transactionManger;


    /**
     *
     * @param obj 委托对象
     * @return 代理对象
     */
    public Object getJdkProxy(Object obj){
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                //开启事务，（关闭事务的自动提交）
                transactionManger.beginTransaction();
                try{
                    result = method.invoke(obj, args);
                    //提交事务
                    transactionManger.commit();
                }catch (Exception e){
                    transactionManger.rollback();
                    throw e;
                }
                return result;
            }
        });
    }

    /**
     * 使用CGLIB生成代理对象
     * @param obj 委托对象
     * @return
     */
    public Object getCglibProxy(Object obj){
        return Enhancer.create(obj.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                //开启事务，（关闭事务的自动提交）
                transactionManger.beginTransaction();
                result = method.invoke(obj, objects);
                //提交事务
                transactionManger.commit();
                return result;
            }
        });
    }

}
