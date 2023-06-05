package org.course_registration.service;

import org.course_registration.error.BusinessException;
import org.course_registration.service.model.TchTransactionModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TchTransactionService {
    TchTransactionModel createTransaction(BigDecimal amount, String description, Date time, Integer tchId) throws BusinessException;
    List<TchTransactionModel> listTransaction(Integer tchId);
}
