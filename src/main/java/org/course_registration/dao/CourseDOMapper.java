package org.course_registration.dao;

import org.course_registration.dataobject.CourseDO;

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
}