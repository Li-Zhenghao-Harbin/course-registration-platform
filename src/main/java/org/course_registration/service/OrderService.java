package org.course_registration.service;

import org.course_registration.error.BusinessException;
import org.course_registration.service.model.OrderModel;

import java.util.List;

public interface OrderService {
    OrderModel createOrder(Integer stuId, Integer courseId) throws BusinessException;
    List<OrderModel> listOrder();
}
