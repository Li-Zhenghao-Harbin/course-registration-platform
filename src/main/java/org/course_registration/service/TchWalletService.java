package org.course_registration.service;

import org.course_registration.error.BusinessException;
import org.course_registration.service.model.TchWalletModel;

import java.math.BigDecimal;

public interface TchWalletService {
    TchWalletModel getWalletById(Integer id);
    void withdrawal(TchWalletModel tchWalletModel, BigDecimal amount) throws BusinessException;
    void increaseBalance(TchWalletModel tchWalletModel, BigDecimal amount) throws BusinessException;
}
