package org.course_registration.dataobject;

public class StuDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stu_info.id
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stu_info.name
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stu_info.gender
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    private Byte gender;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stu_info.telephone
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    private String telephone;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stu_info.id
     *
     * @return the value of stu_info.id
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stu_info.id
     *
     * @param id the value for stu_info.id
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stu_info.name
     *
     * @return the value of stu_info.name
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stu_info.name
     *
     * @param name the value for stu_info.name
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stu_info.gender
     *
     * @return the value of stu_info.gender
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stu_info.gender
     *
     * @param gender the value for stu_info.gender
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stu_info.telephone
     *
     * @return the value of stu_info.telephone
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stu_info.telephone
     *
     * @param telephone the value for stu_info.telephone
     *
     * @mbg.generated Fri Apr 21 22:28:05 BST 2023
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }
}