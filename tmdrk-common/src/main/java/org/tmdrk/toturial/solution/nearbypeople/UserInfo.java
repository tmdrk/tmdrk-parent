package org.tmdrk.toturial.solution.nearbypeople;

/**
 * @ClassName UserInfo
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/17 18:36
 * @Version 1.0
 **/
public class UserInfo {
    private String userName;//用户名
    private Float longitude;//经度
    private Float latitude;//维度

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    private Double distance;
    public UserInfo(String userName,Float longitude,Float latitude){
        this(userName,longitude,latitude,null);
    }

    public UserInfo(String userName,Float longitude,Float latitude,Double distance){
        this.userName = userName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

}
