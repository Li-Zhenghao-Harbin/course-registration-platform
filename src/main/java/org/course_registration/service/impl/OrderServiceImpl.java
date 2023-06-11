package org.course_registration.service.impl;

import org.course_registration.dao.OrderDOMapper;
import org.course_registration.dao.SequenceDOMapper;
import org.course_registration.dataobject.CourseDO;
import org.course_registration.dataobject.CourseStockDO;
import org.course_registration.dataobject.OrderDO;
import org.course_registration.dataobject.SequenceDO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.service.*;
import org.course_registration.service.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private StuWalletService stuWalletService;

    @Autowired
    private StuTransactionService stuTransactionService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(StuModel stuModel, Integer courseId) throws BusinessException {
        CourseModel courseModel = courseService.getCourseById(courseId);
        if (courseModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "课程不存在");
        }
        if (stuModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "学生不存在");
        }
        StuWalletModel stuWalletModel = stuWalletService.getWalletById(stuModel.getId());
        if (stuWalletModel.getBalance().compareTo(courseModel.getPrice()) < 0) {
            throw new BusinessException(EmBusinessError.BALANCE_NOT_ENOUGH);
        }
        boolean result = courseService.decreaseStock(courseId);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        BigDecimal price = courseModel.getPrice();
        OrderModel orderModel = new OrderModel();
        orderModel.setStuId(stuModel.getId());
        orderModel.setCourseId(courseId);
        orderModel.setPrice(price);
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertFromModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        courseService.increaseSales(courseId);
        // stu wallet, tch wallet, stu transaction, tch transaction
        stuWalletService.decreaseBalance(stuWalletModel, price);
        stuTransactionService.createTransaction(price.negate(), "报名课程" + courseModel.getId(), new Date(), stuModel.getId());
        TchWalletModel tchWalletModel = tchWalletService.getWalletById(courseModel.getTchId());
        tchWalletService.increaseBalance(tchWalletModel, price);
        tchTransactionService.createTransaction(price, "学生报名课程" + courseModel.getId(), new Date(), tchWalletModel.getTchId());
        return null;
    }

    private String generateOrderNo() {
        StringBuilder sb = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        sb.append(nowDate);

        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            sb.append(0);
        }
        sb.append(sequenceStr);
        return sb.toString();
    }

    @Override
    public List<OrderModel> listOrder() {
        return null;
    }

    @Override
    public List<OrderModel> listStuOrder(Integer stuId) {
        List<OrderDO> orderDOList = orderDOMapper.listStuOrder(stuId);
        List<OrderModel> orderModelList = orderDOList.stream().map(orderDO -> {
            OrderModel orderModel = convertFromDataObject(orderDO);
            return orderModel;
        }).collect(Collectors.toList());
        return orderModelList;
    }

    private OrderDO convertFromModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setPrice(orderModel.getPrice().doubleValue());
        return orderDO;
    }

    private OrderModel convertFromDataObject(OrderDO orderDO) {
        if (orderDO == null) {
            return null;
        }
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDO, orderModel);
        return orderModel;
    }
}
