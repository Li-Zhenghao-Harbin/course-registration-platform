package org.course_registration.controller;

import org.course_registration.controller.viewobject.StuTransactionVO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.response.CommonReturnType;
import org.course_registration.service.StuTransactionService;
import org.course_registration.service.model.StuModel;
import org.course_registration.service.model.StuTransactionModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller("stuTransaction")
@RequestMapping("/stuTransaction")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class StuTransactionController extends BaseController {
    @Autowired
    private StuTransactionService stuTransactionService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/createTransaction", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    private CommonReturnType createTransaction(@RequestParam(name = "amount") BigDecimal amount,
                                               @RequestParam(name = "description")String description) throws BusinessException {
        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        StuModel stuModel = (StuModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        StuTransactionModel stuTransactionModel = stuTransactionService.createTransaction(amount, description, new Date(), stuModel.getId());
        return CommonReturnType.create(stuTransactionModel);
    }

    @RequestMapping(value = "/listTransaction", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    private CommonReturnType listTransaction() throws BusinessException {
        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        StuModel stuModel = (StuModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        List<StuTransactionModel> stuTransactionModelList = stuTransactionService.listTransaction(stuModel.getId());
        List<StuTransactionVO> stuTransactionVOList = stuTransactionModelList.stream().map(stuTransactionModel -> {
            StuTransactionVO stuTransactionVO = this.convertVOFromModel(stuTransactionModel);
            return stuTransactionVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(stuTransactionVOList);
    }

    private StuTransactionVO convertVOFromModel(StuTransactionModel stuTransactionModel) {
        if (stuTransactionModel == null) {
            return null;
        }
        StuTransactionVO stuTransactionVO = new StuTransactionVO();
        BeanUtils.copyProperties(stuTransactionModel, stuTransactionVO);
        stuTransactionVO.setAmount(stuTransactionModel.getAmount());
        return stuTransactionVO;
    }
}
