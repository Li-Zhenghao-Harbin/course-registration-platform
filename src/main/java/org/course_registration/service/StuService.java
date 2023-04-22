package org.course_registration.service;

import org.course_registration.error.BusinessException;
import org.course_registration.service.model.StuModel;

public interface StuService {
    StuModel getStuById(Integer id);
    void register(StuModel stuModel) throws BusinessException;
    StuModel validateLogin(String telephone, String encryptedPassword) throws BusinessException;
}
