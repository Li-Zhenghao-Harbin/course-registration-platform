package org.course_registration.controller;

import com.alibaba.druid.util.StringUtils;
import org.course_registration.controller.viewobject.StuVO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.response.CommonReturnType;
import org.course_registration.service.StuService;
import org.course_registration.service.model.StuModel;
import org.course_registration.service.model.TchModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller("stu")
@RequestMapping("/stu")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class StuController extends BaseController {
    @Autowired
    private StuService stuService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/getOtp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telephone")String telephone) {
        Random random = new Random();
        int randomInt = random.nextInt(99999) + 10000;
        String otpCode = String.valueOf(randomInt);
        httpServletRequest.getSession().setAttribute(telephone, otpCode);
        System.out.println("手机号：" + telephone + "，验证码：" + otpCode);
        return CommonReturnType.create(null);
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
        StuModel stuModel = new StuModel();
        stuModel.setName(name);
        stuModel.setGender(new Byte(String.valueOf(gender.intValue())));
        stuModel.setTelephone(telephone);
        stuModel.setEncryptedPassword(this.EncodeByMd5(password));
        stuService.register(stuModel);
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
        StuModel stuModel = stuService.validateLogin(telephone, this.EncodeByMd5(password));
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_INFO", stuModel);
        StuVO stuVO = convertFromModel(stuModel);
        return CommonReturnType.create(stuVO);
    }

    @RequestMapping(value = "/modifyInfo", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType modifyInfo(@RequestParam(name = "name")String name,
                                       @RequestParam(name = "gender")Byte gender,
                                       @RequestParam(name = "telephone")String telephone) throws BusinessException {
        if (StringUtils.isEmpty(name) ||
                gender == null ||
                StringUtils.isEmpty(telephone)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        StuModel stuModel = (StuModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        stuModel.setName(name);
        stuModel.setGender(gender);
        stuModel.setTelephone(telephone);
        stuService.modifyInfo(stuModel);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/modifyPassword", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType modifyPassword(@RequestParam(name = "previousPassword")String previousPassword,
                                           @RequestParam(name = "newPassword")String newPassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(previousPassword) ||
                StringUtils.isEmpty(newPassword)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        StuModel stuModel = (StuModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        if (previousPassword.equals(newPassword)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "新密码不能和旧密码相同");
        } else if (!EncodeByMd5(previousPassword).equals(stuService.getPasswordById(stuModel.getId()))) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "旧密码错误");
        }
        stuModel.setEncryptedPassword(EncodeByMd5(newPassword));
        stuService.modifyPassword(stuModel);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/request", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType request() throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        StuModel stuModel = (StuModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        StuVO stuVO = convertFromModel(stuModel);
        return CommonReturnType.create(stuVO);
    }

    private StuVO convertFromModel(StuModel stuModel) {
        if (stuModel == null) {
            return null;
        }
        StuVO stuVO = new StuVO();
        BeanUtils.copyProperties(stuModel, stuVO);
        return stuVO;
    }
}
