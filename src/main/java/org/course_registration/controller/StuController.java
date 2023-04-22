package org.course_registration.controller;

import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.response.CommonReturnType;
import org.course_registration.service.StuService;
import org.course_registration.service.model.StuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller("stu")
@RequestMapping("/stu")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class StuController extends BaseController {
    @Autowired
    private StuService stuService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telephone")String telephone) {
        Random random = new Random();
        int randomInt = random.nextInt(99999) + 100000;
        String otpCode = String.valueOf(randomInt);
        httpServletRequest.getSession().setAttribute(telephone, otpCode);
        System.out.println("手机号：" + telephone + "，验证码：" + otpCode);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "role")Integer role,
                                     @RequestParam(name = "name")String name,
//                                     @RequestParam(name = "gender")Byte gender,
                                     @RequestParam(name = "telephone")String telephone,
                                     @RequestParam(name = "password")String password,
                                     @RequestParam(name = "otpCode")String otpCode) throws BusinessException {
        String inSessionOtpCode = (String)this.httpServletRequest.getSession().getAttribute(telephone);
        if (!com.alibaba.druid.util.StringUtils.equals(otpCode, inSessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "验证码错误");
        }
        StuModel stuModel = new StuModel();
        stuModel.setName(name);
//            stuModel.setGender(new Byte(String.valueOf(gender.intValue())));
        stuModel.setTelephone(telephone);
        stuModel.setEncryptedPassword(password);
        stuService.register(stuModel);
        return CommonReturnType.create(null);
    }
}
