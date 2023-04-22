package org.course_registration.service.impl;

import com.alibaba.druid.util.StringUtils;
import org.course_registration.dao.StuDOMapper;
import org.course_registration.dao.StuPasswordDOMapper;
import org.course_registration.dataobject.StuDO;
import org.course_registration.dataobject.StuPasswordDO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.service.StuService;
import org.course_registration.service.model.StuModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StuServiceImpl implements StuService {
    @Autowired
    private StuDOMapper stuDOMapper;

    @Autowired
    private StuPasswordDOMapper stuPasswordDOMapper;

    @Override
    public StuModel getStuById(Integer id) {
        StuDO stuDO = stuDOMapper.selectByPrimaryKey(id);
        if (stuDO == null) {
            return null;
        }
        StuPasswordDO stuPasswordDO = stuPasswordDOMapper.selectByUserId(stuDO.getId());
        return convertFromDataObject(stuDO, stuPasswordDO);
    }

    @Override
    @Transactional
    public void register(StuModel stuModel) throws BusinessException {
        if (stuModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (StringUtils.isEmpty(stuModel.getName())
        || stuModel.getGender() == null
        || StringUtils.isEmpty(stuModel.getTelephone())) {
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
    }

    @Override
    public StuModel validateLogin(String telephone, String encryptedPassword) throws BusinessException {
        return null;
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
}
