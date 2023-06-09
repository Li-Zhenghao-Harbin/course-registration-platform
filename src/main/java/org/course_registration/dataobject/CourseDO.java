package org.course_registration.dataobject;

import java.util.Date;

public class CourseDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course_info.id
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course_info.title
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course_info.description
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course_info.check_code
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    private Date startTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course_info.duration
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    private Integer duration;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course_info.price
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    private Double price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course_info.sales
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    private Integer sales;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column course_info.tch_id
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    private Integer tchId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course_info.id
     *
     * @return the value of course_info.id
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course_info.id
     *
     * @param id the value for course_info.id
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course_info.title
     *
     * @return the value of course_info.title
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course_info.title
     *
     * @param title the value for course_info.title
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course_info.description
     *
     * @return the value of course_info.description
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course_info.description
     *
     * @param description the value for course_info.description
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course_info.check_code
     *
     * @return the value of course_info.check_code
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course_info.start_time
     *
     * @param startTime the value for course_info.start_time
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course_info.duration
     *
     * @return the value of course_info.duration
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course_info.duration
     *
     * @param duration the value for course_info.duration
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course_info.price
     *
     * @return the value of course_info.price
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course_info.price
     *
     * @param price the value for course_info.price
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course_info.sales
     *
     * @return the value of course_info.sales
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public Integer getSales() {
        return sales;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course_info.sales
     *
     * @param sales the value for course_info.sales
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public void setSales(Integer sales) {
        this.sales = sales;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column course_info.tch_id
     *
     * @return the value of course_info.tch_id
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public Integer getTchId() {
        return tchId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column course_info.tch_id
     *
     * @param tchId the value for course_info.tch_id
     *
     * @mbg.generated Mon Apr 24 20:38:55 BST 2023
     */
    public void setTchId(Integer tchId) {
        this.tchId = tchId;
    }
}