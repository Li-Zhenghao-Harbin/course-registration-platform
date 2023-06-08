package org.course_registration.controller;

import org.course_registration.controller.viewobject.StuWalletVO;
import org.course_registration.controller.viewobject.TchWalletVO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.response.CommonReturnType;
import org.course_registration.service.StuTransactionService;
import org.course_registration.service.StuWalletService;
import org.course_registration.service.TchTransactionService;
import org.course_registration.service.TchWalletService;
import org.course_registration.service.model.StuModel;
import org.course_registration.service.model.StuWalletModel;
import org.course_registration.service.model.TchModel;
import org.course_registration.service.model.TchWalletModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller("stuWallet")
@RequestMapping("/stuWallet")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class StuWalletController extends BaseController {
    @Autowired
    private StuWalletService stuWalletService;

    @Autowired
    private StuTransactionService stuTransactionService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/getWallet", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getWallet() throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        StuModel stuModel = (StuModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        StuWalletModel stuWalletModel = stuWalletService.getWalletById(stuModel.getId());
        StuWalletVO stuWalletVO = convertFromWalletModel(stuWalletModel);
        return CommonReturnType.create(stuWalletVO);
    }

    @RequestMapping(value = "/recharge", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType recharge(@RequestParam(name = "amount") BigDecimal amount) throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        StuModel stuModel = (StuModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        StuWalletModel stuWalletModel = stuWalletService.getWalletById(stuModel.getId());
        stuWalletService.recharge(stuWalletModel, amount);
        return CommonReturnType.create(null);
    }

    private StuWalletVO convertFromWalletModel(StuWalletModel stuWalletModel) {
        if (stuWalletModel == null) {
            return null;
        }
        StuWalletVO stuWalletVO = new StuWalletVO();
        BeanUtils.copyProperties(stuWalletModel, stuWalletVO);
        stuWalletVO.setBalance(stuWalletModel.getBalance());
        return stuWalletVO;
    }
}
