package org.course_registration.controller;

import com.alibaba.druid.util.StringUtils;
import org.course_registration.controller.viewobject.TchVO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.response.CommonReturnType;
import org.course_registration.service.TchService;
import org.course_registration.service.model.TchModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller("tch")
@RequestMapping("/tch")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class TchController extends BaseController {
    @Autowired
    private TchService tchService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/getOtp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telephone")String telephone) {
        Random random = new Random();
        int randomInt = random.nextInt(99999) + 100000;
        String otpCode = String.valueOf(randomInt);
        httpServletRequest.getSession().setAttribute(telephone, otpCode);
        System.out.println("手机号：" + telephone + "，验证码：" + otpCode);
        return CommonReturnType.create(null);
    }

    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "name")String name,
                                     @RequestParam(name = "gender")Byte gender,
                                     @RequestParam(name = "telephone")String telephone,
                                     @RequestParam(name = "password")String password,
                                     @RequestParam(name = "otpCode")String otpCode) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(name) ||
                gender == null ||
                StringUtils.isEmpty(telephone)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        String inSessionOtpCode = (String)this.httpServletRequest.getSession().getAttribute(telephone);
        if (!com.alibaba.druid.util.StringUtils.equals(otpCode, inSessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "验证码错误");
        }
        TchModel tchModel = new TchModel();
        tchModel.setName(name);
        tchModel.setGender(new Byte(String.valueOf(gender.intValue())));
        tchModel.setTelephone(telephone);
        tchModel.setDescription("讲师：" + name);
        tchModel.setEncryptedPassword(this.EncodeByMd5(password));
        tchService.register(tchModel);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telephone")String telephone,
                                  @RequestParam(name = "password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(telephone) ||
                StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        TchModel tchModel = tchService.validateLogin(telephone, this.EncodeByMd5(password));
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_INFO", tchModel);
        TchVO tchVO = convertFromModel(tchModel);
        return CommonReturnType.create(tchVO);
    }

    @RequestMapping(value = "/modifyInfo", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType modifyInfo(@RequestParam(name = "name")String name,
                                       @RequestParam(name = "gender")Byte gender,
                                       @RequestParam(name = "telephone")String telephone,
                                       @RequestParam(name = "description")String description) throws BusinessException {
        if (StringUtils.isEmpty(name) ||
                gender == null ||
                StringUtils.isEmpty(telephone) ||
                StringUtils.isEmpty(description)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        TchModel tchModel = (TchModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        tchModel.setName(name);
        tchModel.setGender(gender);
        tchModel.setTelephone(telephone);
        tchModel.setDescription(description);
        tchService.modifyInfo(tchModel);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/request", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType request() throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        TchModel tchModel = (TchModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        TchVO tchVO = convertFromModel(tchModel);
        return CommonReturnType.create(tchVO);
    }

    private TchVO convertFromModel(TchModel tchModel) {
        if (tchModel == null) {
            return null;
        }
        TchVO tchVO = new TchVO();
        BeanUtils.copyProperties(tchModel, tchVO);
        return tchVO;
    }
}
