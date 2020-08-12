package org.tmdrk.toturial.spring.es.dto;

/**
 * 系统角色
 */
public enum Role {
    /**
     * 超级管理员
     */
    SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    /**
     * 平台管理员
     */
    PLATFORM_ADMIN("ROLE_PLATFORM_ADMIN"),
    /**
     * 租户管理员
     */
    TENANT_ADMIN("ROLE_TENANT_ADMIN"),
    /**
     * 租户
     */
    TENANT("ROLE_TENANT");
    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
