package org.course_registration.service.impl;

import org.course_registration.dao.TchWalletDOMapper;
import org.course_registration.dataobject.TchWalletDO;
import org.course_registration.service.TchWalletService;
import org.course_registration.service.model.TchWalletModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TchWalletServiceImpl implements TchWalletService {
    @Autowired
    private TchWalletDOMapper tchWalletDOMapper;

    @Override
    public TchWalletModel getWalletById(Integer id) {
        TchWalletDO tchWalletDO = tchWalletDOMapper.selectByTchId(id);
        if (tchWalletDO == null) {
            return null;
        }
        return convertFromDataObject(tchWalletDO);
    }

    @Override
    public void recharge(Integer id, BigDecimal amount) {

    }

    @Override
    public void withdrawal(Integer id, BigDecimal amount) {

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
