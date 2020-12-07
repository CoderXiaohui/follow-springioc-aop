package com.dxh.code.utils;

import com.dxh.code.anno.Autowired;
import com.dxh.code.anno.Component;

import java.sql.SQLException;

/**
 * @author https://github.com/CoderXiaohui
 * @Description 事务管理器，负责手动事务的开启、提交、回滚
 * @Date 2020-11-22 00:50
 */
@Component
public class TransactionManger {
    @Autowired
    private ConnectionUtils connectionUtils;


    //开启
    public void beginTransaction() throws SQLException {
        connectionUtils.getCurrentThreadConn().setAutoCommit(false);
    }

    //提交
    public void commit() throws SQLException {
        connectionUtils.getCurrentThreadConn().commit();
    }


    //回滚
    public void rollback() throws SQLException {
        connectionUtils.getCurrentThreadConn().rollback();
    }

}
