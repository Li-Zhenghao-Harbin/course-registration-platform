package org.course_registration.service.impl;

import com.alibaba.druid.util.StringUtils;
import org.course_registration.dao.StuDOMapper;
import org.course_registration.dao.StuPasswordDOMapper;
import org.course_registration.dao.StuWalletDOMapper;
import org.course_registration.dataobject.*;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.service.StuService;
import org.course_registration.service.StuTransactionService;
import org.course_registration.service.model.StuModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class StuServiceImpl implements StuService {
    @Autowired
    private StuDOMapper stuDOMapper;

    @Autowired
    private StuPasswordDOMapper stuPasswordDOMapper;

    @Autowired
    private StuWalletDOMapper stuWalletDOMapper;

    @Autowired
    private StuTransactionService stuTransactionService;

    @Override
    public StuModel getStuById(Integer id) {
        StuDO stuDO = stuDOMapper.selectByPrimaryKey(id);
        if (stuDO == null) {
            return null;
        }
        StuPasswordDO stuPasswordDO = stuPasswordDOMapper.selectByStuId(stuDO.getId());
        return convertFromDataObject(stuDO, stuPasswordDO);
    }

    @Override
    public String getPasswordById(Integer stuId) {
        StuPasswordDO stuPasswordDO = stuPasswordDOMapper.selectByStuId(stuId);
        return stuPasswordDO.getEncryptedPassword();
    }

    @Override
    @Transactional
    public void register(StuModel stuModel) throws BusinessException {
        if (stuModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        StuDO stuDO = convertFromModel(stuModel);
        try {
            stuDOMapper.insertSelective(stuDO);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已被注册");
        }
        stuModel.setId(stuDO.getId());
        StuPasswordDO stuPasswordDO = convertPasswordFromModel(stuModel);
        stuPasswordDOMapper.insertSelective(stuPasswordDO);
        StuWalletDO stuWalletDO = convertWalletFromModel(stuModel);
        stuWalletDOMapper.insertSelective(stuWalletDO);
        stuTransactionService.createTransaction(new BigDecimal(0), "创建账户", new Date(), stuModel.getId());
    }

    @Override
    @Transactional
    public void modifyInfo(StuModel stuModel) throws BusinessException {
        if (stuModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        StuDO stuDO = convertFromModel(stuModel);
        try {
            stuDOMapper.updateByPrimaryKeySelective(stuDO);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已被注册");
        }
    }

    @Override
    @Transactional
    public void modifyPassword(StuModel stuModel) throws BusinessException {
        if (stuModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        StuDO stuDO = convertFromModel(stuModel);
        stuModel.setId(stuDO.getId());
        StuPasswordDO stuPasswordDO = convertPasswordFromModel(stuModel);
        stuPasswordDOMapper.updatePasswordByStuId(stuPasswordDO);
    }

    @Override
    public StuModel validateLogin(String telephone, String encryptedPassword) throws BusinessException {
        StuDO stuDO = stuDOMapper.selectByTelephone(telephone);
        if (stuDO == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        StuPasswordDO stuPasswordDO = stuPasswordDOMapper.selectByStuId(stuDO.getId());
        StuModel stuModel = convertFromDataObject(stuDO, stuPasswordDO);
        if (!StringUtils.equals(encryptedPassword, stuModel.getEncryptedPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return stuModel;
    }

    private StuModel convertFromDataObject(StuDO stuDO, StuPasswordDO stuPasswordDO) {
        if (stuDO == null) {
            return null;
        }
        StuModel stuModel = new StuModel();
        BeanUtils.copyProperties(stuDO, stuModel);
        if (stuPasswordDO != null) {
            stuModel.setEncryptedPassword(stuPasswordDO.getEncryptedPassword());
        }
        return stuModel;
    }

    private StuDO convertFromModel(StuModel stuModel) {
        if (stuModel == null) {
            return null;
        }
        StuDO stuDO = new StuDO();
        BeanUtils.copyProperties(stuModel, stuDO);
        return stuDO;
    }

    private StuPasswordDO convertPasswordFromModel(StuModel stuModel) {
        if (stuModel == null) {
            return null;
        }
        StuPasswordDO stuPasswordDO = new StuPasswordDO();
        stuPasswordDO.setEncryptedPassword(stuModel.getEncryptedPassword());
        stuPasswordDO.setStuId(stuModel.getId());
        return stuPasswordDO;
    }

    private StuWalletDO convertWalletFromModel(StuModel stuModel) {
        if (stuModel == null) {
            return null;
        }
        StuWalletDO stuWalletDO = new StuWalletDO();
        stuWalletDO.setBalance(0.0);
        stuWalletDO.setStuId(stuModel.getId());
        return stuWalletDO;
    }
}
