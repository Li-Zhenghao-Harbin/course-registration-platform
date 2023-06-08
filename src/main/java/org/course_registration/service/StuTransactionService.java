package org.course_registration.service;

import org.course_registration.error.BusinessException;
import org.course_registration.service.model.StuTransactionModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface StuTransactionService {
    StuTransactionModel createTransaction(BigDecimal amount, String description, Date time, Integer stuId) throws BusinessException;
    List<StuTransactionModel> listTransaction(Integer stuId);
}
