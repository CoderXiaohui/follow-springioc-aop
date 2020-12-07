package com.dxh.code.servlet;

import com.dxh.code.factory.BeanFactory;
import com.dxh.code.utils.JsonUtils;
import com.dxh.code.pojo.Result;
import com.dxh.code.service.TransferService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应癫
 */
@WebServlet(name="transferServlet",urlPatterns = "/transferServlet")
public class TransferServlet extends HttpServlet {

    // 1. 实例化service层对象
//    private TransferService transferService = new TransferServiceImpl();
//    private TransferService transferService = (TransferService) BeanFactory.getBean("transferService");

    //从工厂获取委托对象，增强了对事物的控制
    //首先从BeanFactory获取到ProxyFactory代理工厂的实例化对象
//    private ProxyFactory proxyFactory = (ProxyFactory) BeanFactoryBackUp.getBean("proxyFactory");
//    private TransferService transferService = (TransferService) proxyFactory.getJdkProxy(BeanFactoryBackUp.getBean("transferService"));
     private TransferService transferService = (TransferService)BeanFactory.getBean("transferService");


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求体的字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);

        Result result = new Result();

        try {

            // 2. 调用service层方法
            transferService.transfer(fromCardNo,toCardNo,money);
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("201");
            result.setMessage(e.toString());
        }

        // 响应
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(JsonUtils.object2Json(result));
    }
}
