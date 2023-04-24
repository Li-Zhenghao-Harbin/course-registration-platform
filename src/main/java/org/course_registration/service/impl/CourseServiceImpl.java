package org.course_registration.service.impl;

import org.course_registration.dao.CourseDOMapper;
import org.course_registration.dao.CourseStockDOMapper;
import org.course_registration.dataobject.CourseDO;
import org.course_registration.dataobject.CourseStockDO;
import org.course_registration.error.BusinessException;
import org.course_registration.error.EmBusinessError;
import org.course_registration.service.CourseService;
import org.course_registration.service.model.CourseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDOMapper courseDOMapper;

    @Autowired
    private CourseStockDOMapper courseStockDOMapper;

    @Override
    @Transactional
    public CourseModel createCourse(CourseModel courseModel) throws BusinessException {
        if (courseModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        CourseDO courseDO = convertFromModel(courseModel);
        courseDOMapper.insertSelective(courseDO);
        courseModel.setId(courseDO.getId());
        CourseStockDO courseStockDO = convertStockFromModel(courseModel);
        courseStockDO.setCourseId(courseModel.getId());
        courseStockDOMapper.insertSelective(courseStockDO);
        return courseModel;
    }

    private CourseDO convertFromModel(CourseModel courseModel) {
        if (courseModel == null) {
            return null;
        }
        CourseDO courseDO = new CourseDO();
        BeanUtils.copyProperties(courseModel, courseDO);
        courseDO.setPrice(courseModel.getPrice().doubleValue());
        return courseDO;
    }

    private CourseStockDO convertStockFromModel(CourseModel courseModel) {
        if (courseModel == null) {
            return null;
        }
        CourseStockDO courseStockDO = new CourseStockDO();
        BeanUtils.copyProperties(courseModel, courseStockDO);
        return courseStockDO;
    }

    @Override
    public List<CourseModel> listCourse() {
        List<CourseDO> courseDOList = courseDOMapper.listCourse();
        List<CourseModel> courseModelList = courseDOList.stream().map(courseDO -> {
            CourseStockDO courseStockDO = courseStockDOMapper.selectByCourseId(courseDO.getId());
            CourseModel courseModel = convertFromDataObject(courseDO, courseStockDO);
            return courseModel;
        }).collect(Collectors.toList());
        return courseModelList;
    }

    private CourseModel convertFromDataObject(CourseDO courseDO, CourseStockDO courseStockDO) {
        CourseModel courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDO, courseModel);
        courseModel.setPrice(new BigDecimal(courseDO.getPrice()));
        courseModel.setStock(courseStockDO.getStock());
        return courseModel;
    }

    @Override
    public CourseModel getCourseById(Integer id) {
        return null;
    }

    @Override
    public boolean decreaseStock(Integer courseId) throws BusinessException {
        return false;
    }

    @Override
    public void increaseSales(Integer courseId) throws BusinessException {

    }
}
