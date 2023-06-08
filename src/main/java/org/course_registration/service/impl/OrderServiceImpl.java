package org.course_registration.service.impl;

import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.response.CommonReturnType;
import org.course_registration.service.*;
import org.course_registration.service.model.CourseModel;
import org.course_registration.service.model.OrderModel;
import org.course_registration.service.model.StuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CourseService courseService;

    @Autowired
    private StuService stuService;

    @Autowired
    private TchWalletService tchWalletService;

    @Autowired
    private TchTransactionService tchTransactionService;

//    @Autowired
//    private StuWalletService stuWalletService;
//
//    @Autowired
//    private StuTransactionService stuTransactionService;

    @Override
    @Transactional
    public OrderModel createOrder(Integer stuId, Integer courseId) throws BusinessException {
        CourseModel courseModel = courseService.getCourseById(courseId);
        if (courseModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "课程不存在");
        }
        StuModel stuModel = stuService.getStuById(stuId);
        if (stuModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "学生不存在");
        }
        return null;
    }

    @Override
    public List<OrderModel> listOrder() {
        return null;
    }
}
