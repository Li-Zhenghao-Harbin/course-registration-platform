package org.course_registration.service.impl;

import org.course_registration.dao.StuTransactionDOMapper;
import org.course_registration.dataobject.StuTransactionDO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.service.StuTransactionService;
import org.course_registration.service.model.StuTransactionModel;
import org.course_registration.service.model.StuTransactionModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StuTransactionServiceImpl implements StuTransactionService {
    @Autowired
    private StuTransactionDOMapper stuTransactionDOMapper;

    @Override
    @Transactional
    public StuTransactionModel createTransaction(BigDecimal amount, String description, Date time, Integer stuId) throws BusinessException {
        if (amount == null || description == null || stuId == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        StuTransactionModel stuTransactionModel = new StuTransactionModel();
        stuTransactionModel.setAmount(amount);
        stuTransactionModel.setDescription(description);
        stuTransactionModel.setTime(time);
        stuTransactionModel.setStuId(stuId);
        StuTransactionDO stuTransactionDO = convertFromModel(stuTransactionModel);
        stuTransactionDOMapper.insertSelective(stuTransactionDO);
        return stuTransactionModel;
    }

    private StuTransactionDO convertFromModel(StuTransactionModel stuTransactionModel) {
        if (stuTransactionModel == null) {
            return null;
        }
        StuTransactionDO stuTransactionDO = new StuTransactionDO();
        BeanUtils.copyProperties(stuTransactionModel, stuTransactionDO);
        stuTransactionDO.setAmount(stuTransactionModel.getAmount().doubleValue());
        return stuTransactionDO;
    }

    @Override
    public List<StuTransactionModel> listTransaction(Integer stuId) {
        List<StuTransactionDO> stuTransactionDOList = stuTransactionDOMapper.listTransaction(stuId);
        List<StuTransactionModel> stuTransactionModelList = stuTransactionDOList.stream().map(stuTransactionDO -> {
            StuTransactionModel stuTransactionModel = this.convertModelFromDataObject(stuTransactionDO);
            return stuTransactionModel;
        }).collect(Collectors.toList());
        return stuTransactionModelList;
    }

    private StuTransactionModel convertModelFromDataObject(StuTransactionDO stuTransactionDO) {
        if (stuTransactionDO == null) {
            return null;
        }
        StuTransactionModel stuTransactionModel = new StuTransactionModel();
        BeanUtils.copyProperties(stuTransactionDO, stuTransactionModel);
        stuTransactionModel.setAmount(new BigDecimal(stuTransactionDO.getAmount()));
        return stuTransactionModel;
    }
}
