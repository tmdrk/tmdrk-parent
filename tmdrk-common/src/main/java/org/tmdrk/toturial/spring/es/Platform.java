package org.tmdrk.toturial.spring.es;

import org.tmdrk.toturial.spring.es.dto.Role;

public enum Platform {
    /**
     * 积分商城
     */
    POINT_MALL(1, "POINT_MALL", "积分商城", "MERCHANT", Role.TENANT_ADMIN),
    /**
     * 直播专区
     */
    WEBCAST(2, "WEBCAST", "直播专区", "WEBCAST", Role.TENANT_ADMIN),
    /**
     * 扶贫专区
     */
    RELIEF(3, "RELIEF", "扶贫专区", "FP_MERCHANT", Role.TENANT_ADMIN),
    /**
     * 分期商城
     */
    WELFARE(4, "WELFARE", "分期商城", "FQ_MERCHANT", Role.TENANT_ADMIN),
    /**
     * 购物
     */
    MALL(5, "MALL", "购物商城", "GW_MERCHANT", Role.TENANT_ADMIN),
    /**
     * 选品商户
     */
    SELECTION_MERCHANT(6, "SELECTION_MERCHANT", "选品", "SELECTION_MERCHANT", Role.TENANT_ADMIN),

    /**
     * 系统管理
     */
    SYSTEM(0, "SYSTEM", "系统管理员", "SYSTEM", Role.SUPER_ADMIN),

    /**
     * 积分管理
     */
    POINTS_ADMIN(1, "POINT_MALL", "积分商城管理", "POINTS_ADMIN", Role.PLATFORM_ADMIN),
    /**
     * 直播管理
     */
    WEBCAST_ADMIN(2, "WEBCAST", "直播专区管理", "WEBCAST_ADMIN", Role.PLATFORM_ADMIN),
    /**
     * 扶贫管理
     */
    FP_ADMIN(3, "RELIEF", "扶贫专区管理", "FP_ADMIN", Role.PLATFORM_ADMIN),
    /**
     * 分期商城
     */
    WELFARE_ADMIN(4, "WELFARE", "分期商城管理", "WELFARE_ADMIN", Role.PLATFORM_ADMIN),
    /**
     * 购物
     */
    MALL_ADMIN(5, "MALL", "购物商城管理", "MALL_ADMIN", Role.PLATFORM_ADMIN),
    /**
     * 选品
     */
    SELECTION_ADMIN(6, "SELECTION_MERCHANT", "选品管理", "SELECTION_ADMIN", Role.PLATFORM_ADMIN);


    private final int    platform;
    private final String enumName;
    private final String name;
    private final String type;
    private final Role   role;

    Platform(int platform, String enumName, String name, String type, Role role) {
        this.platform = platform;
        this.enumName = enumName;
        this.name = name;
        this.type = type;
        this.role = role;
    }

    public int getPlatform() {
        return platform;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Role getRole() {
        return role;
    }

    public String getEnumName() {
        return enumName;
    }

    /**
     * 解析
     *
     * @param str
     * @return
     */
    public static Platform parse(String str) {
        if (str == null) {
            return null;
        }
        for (Platform value : values()) {
            if (value.name().equals(str)
                    || value.getType().equals(str)
                    || String.valueOf(value.getPlatform()).equals(str)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 解析
     *
     * @param integer
     * @return
     */
    public static Platform parse(Integer integer) {
        if (integer == null) {
            return null;
        }
        return parse(integer.toString());
    }

    public boolean in(Platform... platforms) {
        for (Platform pf : platforms) {
            if (this == pf) {
                return true;
            }
        }
        return false;
    }

    public boolean notIn(Platform... platforms) {
        return !in(platforms);
    }
}
