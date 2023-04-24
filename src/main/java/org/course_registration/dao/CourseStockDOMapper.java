package org.course_registration.dao;

import org.course_registration.dataobject.CourseStockDO;

public interface CourseStockDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_stock
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_stock
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    int insert(CourseStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_stock
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    int insertSelective(CourseStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_stock
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    CourseStockDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_stock
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    int updateByPrimaryKeySelective(CourseStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_stock
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    int updateByPrimaryKey(CourseStockDO record);
}