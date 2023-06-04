package org.course_registration.controller;

import org.course_registration.controller.viewobject.TchWalletVO;
import org.course_registration.dao.TchWalletDOMapper;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.response.CommonReturnType;
import org.course_registration.service.TchWalletService;
import org.course_registration.service.model.TchModel;
import org.course_registration.service.model.TchWalletModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller("tchWallet")
@RequestMapping("/tchWallet")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class TchWalletController extends BaseController {
    @Autowired
    private TchWalletService tchWalletService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/getWallet", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getWallet() throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        TchModel tchModel = (TchModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        TchWalletModel tchWalletModel = tchWalletService.getWalletById(tchModel.getId());
        TchWalletVO tchWalletVO = convertFromWalletModel(tchWalletModel);
        return CommonReturnType.create(tchWalletVO);
    }

    private TchWalletVO convertFromWalletModel(TchWalletModel tchWalletModel) {
        if (tchWalletModel == null) {
            return null;
        }
        TchWalletVO tchWalletVO = new TchWalletVO();
        BeanUtils.copyProperties(tchWalletModel, tchWalletVO);
        tchWalletVO.setBalance(tchWalletModel.getBalance());
        return tchWalletVO;
    }
}
