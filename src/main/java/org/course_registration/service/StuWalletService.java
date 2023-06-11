package org.course_registration.service;

import org.course_registration.error.BusinessException;
import org.course_registration.service.model.StuWalletModel;

import java.math.BigDecimal;

public interface StuWalletService {
    StuWalletModel getWalletById(Integer id);
    void recharge(StuWalletModel stuWalletModel, BigDecimal amount) throws BusinessException;
    void decreaseBalance(StuWalletModel stuWalletModel, BigDecimal amount) throws BusinessException;
}
