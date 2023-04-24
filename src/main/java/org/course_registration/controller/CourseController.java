package org.course_registration.controller;

import com.alibaba.druid.util.StringUtils;
import org.course_registration.controller.viewobject.CourseVO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.response.CommonReturnType;
import org.course_registration.service.CourseService;
import org.course_registration.service.model.CourseModel;
import org.course_registration.service.model.StuModel;
import org.course_registration.service.model.TchModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller("course")
@RequestMapping("/course")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class CourseController extends BaseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createCourse(@RequestParam(name = "title")String title,
                                         @RequestParam(name = "description")String description,
                                         @RequestParam(name = "check_code")String checkCode,
                                         @RequestParam(name = "start_time")Date startTime,
                                         @RequestParam(name = "duration")Integer duration,
                                         @RequestParam(name = "price")BigDecimal price,
                                         @RequestParam(name = "stock")Integer stock) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(title) ||
                StringUtils.isEmpty(description) ||
                StringUtils.isEmpty(checkCode) ||
                startTime == null ||
                duration == null ||
                price == null ||
                stock == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        CourseModel courseModel = new CourseModel();
        courseModel.setTitle(title);
        courseModel.setDescription(description);
        courseModel.setCheckCode(checkCode);
        courseModel.setStartTime(startTime);
        courseModel.setDuration(duration);
        courseModel.setPrice(price);
        courseModel.setStock(stock);
        TchModel tchModel = (TchModel) httpServletRequest.getSession().getAttribute("LOGIN_INFO");
        courseModel.setTchId(tchModel.getId());
        courseService.createCourse(courseModel);
        CourseVO courseVO = convertFromModel(courseModel);
        return CommonReturnType.create(courseVO);
    }

    private CourseVO convertFromModel(CourseModel courseModel) {
        if (courseModel == null) {
            return null;
        }
        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(courseModel, courseVO);
        return courseVO;
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    private CommonReturnType listCourse() {
        List<CourseModel> courseModelList = courseService.listCourse();
        List<CourseVO> courseVOList = courseModelList.stream().map(courseModel -> {
            CourseVO courseVO = convertFromModel(courseModel);
            return courseVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(courseVOList);
    }
}
