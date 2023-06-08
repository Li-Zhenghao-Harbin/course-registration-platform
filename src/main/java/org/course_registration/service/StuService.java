package org.course_registration.service;

import org.course_registration.error.BusinessException;
import org.course_registration.service.model.StuModel;
import org.course_registration.service.model.StuModel;

public interface StuService {
    StuModel getStuById(Integer id);
    String getPasswordById(Integer stuId);
    void register(StuModel stuModel) throws BusinessException;
    StuModel validateLogin(String telephone, String encryptedPassword) throws BusinessException;
    void modifyInfo(StuModel stuModel) throws BusinessException;
    void modifyPassword(StuModel stuModel) throws BusinessException;
}
