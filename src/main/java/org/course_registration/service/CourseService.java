package org.course_registration.service;

import org.course_registration.error.BusinessException;
import org.course_registration.service.model.CourseModel;
import org.course_registration.service.model.StuModel;

import java.util.List;

public interface CourseService {
    CourseModel createCourse(CourseModel courseModel) throws BusinessException;
    List<CourseModel> listCourse();
    List<CourseModel> listTchCourse(Integer tchId);
    CourseModel getCourseById(Integer id);
    boolean decreaseStock(Integer courseId) throws BusinessException;
    void increaseSales(Integer courseId) throws BusinessException;
}
