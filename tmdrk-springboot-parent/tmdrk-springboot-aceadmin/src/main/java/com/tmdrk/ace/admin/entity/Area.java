package com.tmdrk.ace.admin.entity;

public class Area {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.id
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.pid
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    private Integer pid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.name
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.shortname
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    private String shortname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.longitude
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    private String longitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.latitude
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    private String latitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.level
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    private Short level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.sort
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    private Integer sort;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.id
     *
     * @return the value of t_area.id
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.id
     *
     * @param id the value for t_area.id
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.pid
     *
     * @return the value of t_area.pid
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.pid
     *
     * @param pid the value for t_area.pid
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.name
     *
     * @return the value of t_area.name
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.name
     *
     * @param name the value for t_area.name
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.shortname
     *
     * @return the value of t_area.shortname
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.shortname
     *
     * @param shortname the value for t_area.shortname
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public void setShortname(String shortname) {
        this.shortname = shortname == null ? null : shortname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.longitude
     *
     * @return the value of t_area.longitude
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.longitude
     *
     * @param longitude the value for t_area.longitude
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.latitude
     *
     * @return the value of t_area.latitude
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.latitude
     *
     * @param latitude the value for t_area.latitude
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.level
     *
     * @return the value of t_area.level
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public Short getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.level
     *
     * @param level the value for t_area.level
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public void setLevel(Short level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.sort
     *
     * @return the value of t_area.sort
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.sort
     *
     * @param sort the value for t_area.sort
     *
     * @mbggenerated Tue Jan 19 14:36:52 CST 2021
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}