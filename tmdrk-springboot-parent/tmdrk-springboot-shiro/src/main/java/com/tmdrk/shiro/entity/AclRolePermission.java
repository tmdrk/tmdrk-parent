package com.tmdrk.shiro.entity;

public class AclRolePermission {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACL_ROLE_PERMISSION.id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACL_ROLE_PERMISSION.role_id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    private Integer roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACL_ROLE_PERMISSION.permission_id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    private Integer permissionId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACL_ROLE_PERMISSION.id
     *
     * @return the value of ACL_ROLE_PERMISSION.id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACL_ROLE_PERMISSION.id
     *
     * @param id the value for ACL_ROLE_PERMISSION.id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACL_ROLE_PERMISSION.role_id
     *
     * @return the value of ACL_ROLE_PERMISSION.role_id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACL_ROLE_PERMISSION.role_id
     *
     * @param roleId the value for ACL_ROLE_PERMISSION.role_id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACL_ROLE_PERMISSION.permission_id
     *
     * @return the value of ACL_ROLE_PERMISSION.permission_id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACL_ROLE_PERMISSION.permission_id
     *
     * @param permissionId the value for ACL_ROLE_PERMISSION.permission_id
     *
     * @mbggenerated Fri May 15 13:34:04 GMT+08:00 2020
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}