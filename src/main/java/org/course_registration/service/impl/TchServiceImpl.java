package org.course_registration.service.impl;

import com.alibaba.druid.util.StringUtils;
import org.course_registration.dao.TchDOMapper;
import org.course_registration.dao.TchPasswordDOMapper;
import org.course_registration.dao.TchTransactionDOMapper;
import org.course_registration.dao.TchWalletDOMapper;
import org.course_registration.dataobject.TchDO;
import org.course_registration.dataobject.TchPasswordDO;
import org.course_registration.dataobject.TchTransactionDO;
import org.course_registration.dataobject.TchWalletDO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.service.TchService;
import org.course_registration.service.TchTransactionService;
import org.course_registration.service.TchWalletService;
import org.course_registration.service.model.TchModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.util.calendar.BaseCalendar;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TchServiceImpl implements TchService {
    @Autowired
    private TchDOMapper tchDOMapper;

    @Autowired
    private TchPasswordDOMapper tchPasswordDOMapper;

    @Autowired
    private TchWalletDOMapper tchWalletDOMapper;

    @Autowired
    private TchTransactionService tchTransactionService;

    @Override
    public TchModel getTchById(Integer id) {
        TchDO tchDO = tchDOMapper.selectByPrimaryKey(id);
        if (tchDO == null) {
            return null;
        }
        TchPasswordDO tchPasswordDO = tchPasswordDOMapper.selectByTchId(tchDO.getId());
        return convertFromDataObject(tchDO, tchPasswordDO);
    }

    @Override
    public String getPasswordById(Integer tchId) {
        TchPasswordDO tchPasswordDO = tchPasswordDOMapper.selectByTchId(tchId);
        return tchPasswordDO.getEncryptedPassword();
    }

    @Override
    @Transactional
    public void register(TchModel tchModel) throws BusinessException {
        if (tchModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        TchDO tchDO = convertFromModel(tchModel);
        try {
            tchDOMapper.insertSelective(tchDO);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已被注册");
        }
        tchModel.setId(tchDO.getId());
        TchPasswordDO tchPasswordDO = convertPasswordFromModel(tchModel);
        tchPasswordDOMapper.insertSelective(tchPasswordDO);
        TchWalletDO tchWalletDO = convertWalletFromModel(tchModel);
        tchWalletDOMapper.insertSelective(tchWalletDO);
        tchTransactionService.createTransaction(new BigDecimal(0), "创建账户", new Date(), tchModel.getId());
    }

    @Override
    @Transactional
    public void modifyInfo(TchModel tchModel) throws BusinessException {
        if (tchModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        TchDO tchDO = convertFromModel(tchModel);
        try {
            tchDOMapper.updateByPrimaryKeySelective(tchDO);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已被注册");
        }
    }

    @Override
    @Transactional
    public void modifyPassword(TchModel tchModel) throws BusinessException {
        if (tchModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        TchDO tchDO = convertFromModel(tchModel);
        tchModel.setId(tchDO.getId());
        TchPasswordDO tchPasswordDO = convertPasswordFromModel(tchModel);
        tchPasswordDOMapper.updatePasswordByTchId(tchPasswordDO);
    }

    @Override
    public TchModel validateLogin(String telephone, String encryptedPassword) throws BusinessException {
        TchDO tchDO = tchDOMapper.selectByTelephone(telephone);
        if (tchDO == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        TchPasswordDO tchPasswordDO = tchPasswordDOMapper.selectByTchId(tchDO.getId());
        TchModel tchModel = convertFromDataObject(tchDO, tchPasswordDO);
        if (!StringUtils.equals(encryptedPassword, tchModel.getEncryptedPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return tchModel;
    }

    private TchModel convertFromDataObject(TchDO tchDO, TchPasswordDO tchPasswordDO) {
        if (tchDO == null) {
            return null;
        }
        TchModel tchModel = new TchModel();
        BeanUtils.copyProperties(tchDO, tchModel);
        if (tchPasswordDO != null) {
            tchModel.setEncryptedPassword(tchPasswordDO.getEncryptedPassword());
        }
        return tchModel;
    }

    private TchDO convertFromModel(TchModel tchModel) {
        if (tchModel == null) {
            return null;
        }
        TchDO tchDO = new TchDO();
        BeanUtils.copyProperties(tchModel, tchDO);
        return tchDO;
    }

    private TchPasswordDO convertPasswordFromModel(TchModel tchModel) {
        if (tchModel == null) {
            return null;
        }
        TchPasswordDO tchPasswordDO = new TchPasswordDO();
        tchPasswordDO.setEncryptedPassword(tchModel.getEncryptedPassword());
        tchPasswordDO.setTchId(tchModel.getId());
        return tchPasswordDO;
    }

    private TchWalletDO convertWalletFromModel(TchModel tchModel) {
        if (tchModel == null) {
            return null;
        }
        TchWalletDO tchWalletDO = new TchWalletDO();
        tchWalletDO.setBalance(0.0);
        tchWalletDO.setTchId(tchModel.getId());
        return tchWalletDO;
    }
}
