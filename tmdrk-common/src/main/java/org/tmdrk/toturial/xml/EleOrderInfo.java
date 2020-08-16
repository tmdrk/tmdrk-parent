package org.tmdrk.toturial.xml;

import java.util.List;

/**
 * TODO
 *
 * @author Jie.Zhou
 * @date 2020/7/31 18:34
 */
public class EleOrderInfo {
    private String realName;
    private String mobile;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<EleOrderInfo.FoodsInfo> getFoodsInfo() {
        return FoodsInfo;
    }

    public void setFoodsInfo(List<EleOrderInfo.FoodsInfo> foodsInfo) {
        FoodsInfo = foodsInfo;
    }

    private String address;
    private List<FoodsInfo> FoodsInfo;

    class FoodsInfo{
        private String foodName;
        private Integer count;

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "FoodsInfo{" +
                    "foodName='" + foodName + '\'' +
                    ", count=" + count +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "EleOrderInfo{" +
                "realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", FoodsInfo=" + FoodsInfo +
                '}';
    }
}
