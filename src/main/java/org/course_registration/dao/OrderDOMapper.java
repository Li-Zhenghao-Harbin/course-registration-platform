package org.course_registration.dao;

import org.course_registration.dataobject.CourseDO;
import org.course_registration.dataobject.OrderDO;

import java.util.List;

public interface OrderDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Fri Jun 09 16:54:24 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Fri Jun 09 16:54:24 CST 2023
     */
    int insert(OrderDO record);

    List<OrderDO> listStuOrder(Integer stuId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Fri Jun 09 16:54:24 CST 2023
     */
    int insertSelective(OrderDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Fri Jun 09 16:54:24 CST 2023
     */
    OrderDO selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Fri Jun 09 16:54:24 CST 2023
     */
    int updateByPrimaryKeySelective(OrderDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Fri Jun 09 16:54:24 CST 2023
     */
    int updateByPrimaryKey(OrderDO record);
}