package org.course_registration.controller;

import org.course_registration.controller.viewobject.TchTransactionVO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.response.CommonReturnType;
import org.course_registration.service.TchTransactionService;
import org.course_registration.service.model.TchModel;
import org.course_registration.service.model.TchTransactionModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller("tchTransaction")
@RequestMapping("/tchTransaction")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class TchTransactionController extends BaseController {
    @Autowired
    private TchTransactionService tchTransactionService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/createTransaction", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    private CommonReturnType createTransaction(@RequestParam(name = "amount")BigDecimal amount,
                                               @RequestParam(name = "description")String description) throws BusinessException {
        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        TchModel tchModel = (TchModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        TchTransactionModel tchTransactionModel = tchTransactionService.createTransaction(amount, description, new Date(), tchModel.getId());
        return CommonReturnType.create(tchTransactionModel);
    }

    @RequestMapping(value = "/listTransaction", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    private CommonReturnType listTransaction() throws BusinessException {
        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        TchModel tchModel = (TchModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        List<TchTransactionModel> tchTransactionModelList = tchTransactionService.listTransaction(tchModel.getId());
        List<TchTransactionVO> tchTransactionVOList = tchTransactionModelList.stream().map(tchTransactionModel -> {
            TchTransactionVO tchTransactionVO = this.convertVOFromModel(tchTransactionModel);
            return tchTransactionVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(tchTransactionVOList);
    }

    private TchTransactionVO convertVOFromModel(TchTransactionModel tchTransactionModel) {
        if (tchTransactionModel == null) {
            return null;
        }
        TchTransactionVO tchTransactionVO = new TchTransactionVO();
        BeanUtils.copyProperties(tchTransactionModel, tchTransactionVO);
        tchTransactionVO.setAmount(tchTransactionModel.getAmount());
        return tchTransactionVO;
    }
}
