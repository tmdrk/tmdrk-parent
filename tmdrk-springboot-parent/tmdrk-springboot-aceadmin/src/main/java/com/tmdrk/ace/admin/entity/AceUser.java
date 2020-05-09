package com.tmdrk.ace.admin.entity;

public class AceUser {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACE_USER.id
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACE_USER.user_name
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACE_USER.password
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACE_USER.sex
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    private Byte sex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACE_USER.age
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    private Byte age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACE_USER.phone_number
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    private String phoneNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACE_USER.idcard
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    private String idcard;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACE_USER.id
     *
     * @return the value of ACE_USER.id
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACE_USER.id
     *
     * @param id the value for ACE_USER.id
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACE_USER.user_name
     *
     * @return the value of ACE_USER.user_name
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACE_USER.user_name
     *
     * @param userName the value for ACE_USER.user_name
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACE_USER.password
     *
     * @return the value of ACE_USER.password
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACE_USER.password
     *
     * @param password the value for ACE_USER.password
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACE_USER.sex
     *
     * @return the value of ACE_USER.sex
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACE_USER.sex
     *
     * @param sex the value for ACE_USER.sex
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACE_USER.age
     *
     * @return the value of ACE_USER.age
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public Byte getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACE_USER.age
     *
     * @param age the value for ACE_USER.age
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public void setAge(Byte age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACE_USER.phone_number
     *
     * @return the value of ACE_USER.phone_number
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACE_USER.phone_number
     *
     * @param phoneNumber the value for ACE_USER.phone_number
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACE_USER.idcard
     *
     * @return the value of ACE_USER.idcard
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACE_USER.idcard
     *
     * @param idcard the value for ACE_USER.idcard
     *
     * @mbggenerated Thu May 07 13:21:20 GMT+08:00 2020
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }
}