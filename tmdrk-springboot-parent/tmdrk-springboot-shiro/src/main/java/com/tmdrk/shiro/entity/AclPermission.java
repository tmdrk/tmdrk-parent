package com.tmdrk.shiro.entity;

public class AclPermission {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACL_PERMISSION.id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACL_PERMISSION.name
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACL_PERMISSION.description
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    private String description;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACL_PERMISSION.id
     *
     * @return the value of ACL_PERMISSION.id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACL_PERMISSION.id
     *
     * @param id the value for ACL_PERMISSION.id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACL_PERMISSION.name
     *
     * @return the value of ACL_PERMISSION.name
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACL_PERMISSION.name
     *
     * @param name the value for ACL_PERMISSION.name
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACL_PERMISSION.description
     *
     * @return the value of ACL_PERMISSION.description
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACL_PERMISSION.description
     *
     * @param description the value for ACL_PERMISSION.description
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}