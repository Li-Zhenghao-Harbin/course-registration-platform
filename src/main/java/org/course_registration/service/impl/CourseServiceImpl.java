package org.course_registration.service.impl;

import org.course_registration.dao.CourseCheckCodeDOMapper;
import org.course_registration.dao.CourseDOMapper;
import org.course_registration.dao.CourseStockDOMapper;
import org.course_registration.dataobject.CourseCheckCodeDO;
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

    @Autowired
    private CourseCheckCodeDOMapper courseCheckCodeDOMapper;

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

        CourseCheckCodeDO courseCheckCodeDO = convertCheckCodeFromModel(courseModel);
        courseCheckCodeDO.setCourseId(courseModel.getId());
        courseCheckCodeDOMapper.insertSelective(courseCheckCodeDO);
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

    private CourseCheckCodeDO convertCheckCodeFromModel(CourseModel courseModel) {
        if (courseModel == null) {
            return null;
        }
        CourseCheckCodeDO courseCheckCodeDO = new CourseCheckCodeDO();
        BeanUtils.copyProperties(courseModel, courseCheckCodeDO);
        return courseCheckCodeDO;
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

    @Override
    public List<CourseModel> listTchCourse(Integer tchId) {
        List<CourseDO> courseDOList = courseDOMapper.listTchCourse(tchId);
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
        CourseDO courseDO = courseDOMapper.selectByPrimaryKey(id);
        if (courseDO == null) {
            return null;
        }
        CourseStockDO courseStockDO = courseStockDOMapper.selectByCourseId(courseDO.getId());
        return convertFromDataObject(courseDO, courseStockDO);
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer courseId) throws BusinessException {
        int affectedRow = courseStockDOMapper.decreaseStock(courseId);
        return affectedRow > 0;
    }

    @Override
    @Transactional
    public void increaseSales(Integer courseId) throws BusinessException {
        courseDOMapper.increaseSales(courseId);
    }
}
