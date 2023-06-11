package org.course_registration.service;

import org.course_registration.error.BusinessException;
import org.course_registration.service.model.CourseModel;
import org.course_registration.service.model.OrderModel;
import org.course_registration.service.model.StuModel;

import java.util.List;

public interface OrderService {
    OrderModel createOrder(StuModel stuModel, Integer courseId) throws BusinessException;
    List<OrderModel> listOrder();
    List<OrderModel> listStuOrder(Integer stuId);
}
