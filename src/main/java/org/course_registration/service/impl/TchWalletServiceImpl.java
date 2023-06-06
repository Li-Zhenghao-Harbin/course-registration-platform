package org.course_registration.service.impl;

import org.course_registration.dao.TchTransactionDOMapper;
import org.course_registration.dao.TchWalletDOMapper;
import org.course_registration.dataobject.TchTransactionDO;
import org.course_registration.dataobject.TchWalletDO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.service.TchTransactionService;
import org.course_registration.service.TchWalletService;
import org.course_registration.service.model.TchModel;
import org.course_registration.service.model.TchTransactionModel;
import org.course_registration.service.model.TchWalletModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TchWalletServiceImpl implements TchWalletService {
    @Autowired
    private TchWalletDOMapper tchWalletDOMapper;

    @Autowired
    private TchTransactionDOMapper tchTransactionDOMapper;

    @Autowired
    private TchTransactionService tchTransactionService;

    @Override
    public TchWalletModel getWalletById(Integer id) {
        TchWalletDO tchWalletDO = tchWalletDOMapper.selectByTchId(id);
        if (tchWalletDO == null) {
            return null;
        }
        return convertFromDataObject(tchWalletDO);
    }

    @Override
    @Transactional
    public void withdrawal(TchWalletModel tchWalletModel, BigDecimal amount) throws BusinessException {
        if (tchWalletModel == null || amount == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (amount.doubleValue() <= 0 || amount.doubleValue() > tchWalletModel.getBalance().doubleValue()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "提现数值不合法");
        }
        tchWalletDOMapper.withdrawal(tchWalletModel.getTchId(), amount);
        tchTransactionService.createTransaction(amount.negate(), "提现", new Date(), tchWalletModel.getTchId());
    }

    private TchWalletModel convertFromDataObject(TchWalletDO walletDO) {
        if (walletDO == null) {
            return null;
        }
        TchWalletModel tchWalletModel = new TchWalletModel();
        BeanUtils.copyProperties(walletDO, tchWalletModel);
        tchWalletModel.setBalance(BigDecimal.valueOf(walletDO.getBalance()));
        return tchWalletModel;
    }
}
