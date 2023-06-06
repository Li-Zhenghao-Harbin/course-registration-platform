package org.course_registration.service.impl;

import org.course_registration.dao.TchTransactionDOMapper;
import org.course_registration.dataobject.TchTransactionDO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.service.TchTransactionService;
import org.course_registration.service.model.TchTransactionModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TchTransactionServiceImpl implements TchTransactionService {
    @Autowired
    private TchTransactionDOMapper tchTransactionDOMapper;

    @Override
    @Transactional
    public TchTransactionModel createTransaction(BigDecimal amount, String description, Date time, Integer tchId) throws BusinessException {
        if (amount == null || description == null || tchId == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        TchTransactionModel tchTransactionModel = new TchTransactionModel();
        tchTransactionModel.setAmount(amount);
        tchTransactionModel.setDescription(description);
        tchTransactionModel.setTime(time);
        tchTransactionModel.setTchId(tchId);
        TchTransactionDO tchTransactionDO = convertFromModel(tchTransactionModel);
        tchTransactionDOMapper.insertSelective(tchTransactionDO);
        return tchTransactionModel;
    }

    private TchTransactionDO convertFromModel(TchTransactionModel tchTransactionModel) {
        if (tchTransactionModel == null) {
            return null;
        }
        TchTransactionDO tchTransactionDO = new TchTransactionDO();
        BeanUtils.copyProperties(tchTransactionModel, tchTransactionDO);
        tchTransactionDO.setAmount(tchTransactionModel.getAmount().doubleValue());
        return tchTransactionDO;
    }

    @Override
    public List<TchTransactionModel> listTransaction(Integer tchId) {
        List<TchTransactionDO> tchTransactionDOList = tchTransactionDOMapper.listTransaction(tchId);
        List<TchTransactionModel> tchTransactionModelList = tchTransactionDOList.stream().map(tchTransactionDO -> {
            TchTransactionModel tchTransactionModel = this.convertModelFromDataObject(tchTransactionDO);
            return tchTransactionModel;
        }).collect(Collectors.toList());
        return tchTransactionModelList;
    }

    private TchTransactionModel convertModelFromDataObject(TchTransactionDO tchTransactionDO) {
        if (tchTransactionDO == null) {
            return null;
        }
        TchTransactionModel tchTransactionModel = new TchTransactionModel();
        BeanUtils.copyProperties(tchTransactionDO, tchTransactionModel);
        tchTransactionModel.setAmount(new BigDecimal(tchTransactionDO.getAmount()));
        return tchTransactionModel;
    }
}
