package com.dxh.code.service.impl;

import com.dxh.code.anno.Autowired;
import com.dxh.code.anno.Service;
import com.dxh.code.anno.Transactional;
import com.dxh.code.dao.AccountDao;
import com.dxh.code.pojo.Account;
import com.dxh.code.service.TransferService;

/**
 * @author 应癫
 */
@Service
@Transactional
public class TransferServiceImpl implements TransferService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);
            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);
            accountDao.updateAccountByCardNo(to);
//            int i = 1/0;
            accountDao.updateAccountByCardNo(from);

    }
}
