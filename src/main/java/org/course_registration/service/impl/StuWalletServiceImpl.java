package org.course_registration.service.impl;

import org.course_registration.dao.StuTransactionDOMapper;
import org.course_registration.dao.StuWalletDOMapper;
import org.course_registration.dataobject.StuWalletDO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.service.StuWalletService;
import org.course_registration.service.StuTransactionService;
import org.course_registration.service.model.StuWalletModel;
import org.course_registration.service.model.StuWalletModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class StuWalletServiceImpl implements StuWalletService {
    @Autowired
    private StuWalletDOMapper stuWalletDOMapper;

    @Autowired
    private StuTransactionDOMapper stuTransactionDOMapper;

    @Autowired
    private StuTransactionService stuTransactionService;

    @Override
    public StuWalletModel getWalletById(Integer id) {
        StuWalletDO stuWalletDO = stuWalletDOMapper.selectByStuId(id);
        if (stuWalletDO == null) {
            return null;
        }
        return convertFromDataObject(stuWalletDO);
    }

    @Override
    @Transactional
    public void recharge(StuWalletModel stuWalletModel, BigDecimal amount) throws BusinessException {
        if (stuWalletModel == null || amount == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (amount.doubleValue() <= 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "充值数值不合法");
        }
        stuWalletDOMapper.recharge(stuWalletModel.getStuId(), amount);
        stuTransactionService.createTransaction(amount, "充值", new Date(), stuWalletModel.getStuId());
    }


    private StuWalletModel convertFromDataObject(StuWalletDO walletDO) {
        if (walletDO == null) {
            return null;
        }
        StuWalletModel stuWalletModel = new StuWalletModel();
        BeanUtils.copyProperties(walletDO, stuWalletModel);
        stuWalletModel.setBalance(BigDecimal.valueOf(walletDO.getBalance()));
        return stuWalletModel;
    }
}
