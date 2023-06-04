package org.course_registration.service;

import org.course_registration.service.model.TchWalletModel;

import java.math.BigDecimal;

public interface TchWalletService {
    TchWalletModel getWalletById(Integer id);
    void recharge(Integer id, BigDecimal amount);
    void withdrawal(Integer id, BigDecimal amount);
}
