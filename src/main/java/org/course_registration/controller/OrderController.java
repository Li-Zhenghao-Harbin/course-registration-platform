package org.course_registration.controller;

import org.course_registration.controller.viewobject.OrderVO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.response.CommonReturnType;
import org.course_registration.service.CourseService;
import org.course_registration.service.OrderService;
import org.course_registration.service.TchService;
import org.course_registration.service.model.CourseModel;
import org.course_registration.service.model.OrderModel;
import org.course_registration.service.model.StuModel;
import org.course_registration.service.model.TchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TchService tchService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/createOrder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "courseId")Integer courseId) throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        StuModel stuModel = (StuModel)httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        OrderModel orderModel = orderService.createOrder(stuModel, courseId);
        return CommonReturnType.create(orderModel);
    }

    @RequestMapping(value = "/listStuOrder", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listStuOrder() throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        StuModel stuModel = (StuModel)httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        List<OrderModel> orderModelList = orderService.listStuOrder(stuModel.getId());
        List<OrderVO> orderVOList = orderModelList.stream().map(orderModel -> {
            OrderVO orderVO = convertFromModel(orderModel);
            return orderVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(orderVOList);
    }

    private OrderVO convertFromModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderVO orderVO = new OrderVO();
        CourseModel courseModel = courseService.getCourseById(orderModel.getCourseId());
        orderVO.setCourseId(courseModel.getId());
        orderVO.setTitle(courseModel.getTitle());
        orderVO.setStartTime(courseModel.getStartTime());
        orderVO.setDuration(courseModel.getDuration());
        TchModel tchModel = tchService.getTchById(courseModel.getTchId());
        orderVO.setTchId(tchModel.getId());
        orderVO.setTchName(tchModel.getName());
        return orderVO;
    }
}
