package org.course_registration.dao;

import org.apache.ibatis.annotations.Param;
import org.course_registration.dataobject.CourseDO;
import org.course_registration.service.model.CourseModel;

import java.util.List;

public interface CourseDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_info
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_info
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    List<CourseDO> listCourse();

    List<CourseDO> listTchCourse(Integer tchId);

    int insert(CourseDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_info
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    int insertSelective(CourseDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_info
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    CourseDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_info
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    int updateByPrimaryKeySelective(CourseDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_info
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    int updateByPrimaryKey(CourseDO record);

    int increaseSales(@Param("id")Integer id);
}