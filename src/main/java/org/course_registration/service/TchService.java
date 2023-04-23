package org.course_registration.service;

import org.course_registration.error.BusinessException;
import org.course_registration.service.model.TchModel;

public interface TchService {
    TchModel getTchById(Integer id);
    void register(TchModel stuModel) throws BusinessException;
    TchModel validateLogin(String telephone, String encryptedPassword) throws BusinessException;
}
