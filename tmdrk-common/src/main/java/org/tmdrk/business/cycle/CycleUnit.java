package org.tmdrk.business.cycle;

/**
 * 活动周期单位
 * The enum Cycle unit.
 */
public enum CycleUnit {

    /**
     * Hour cycle unit.
     */
    HOUR("hour", "小时", 60 * 60 * 1000),

    /**
     * Day cycle unit.
     */
    DAY("day", "天", HOUR.getMillis() * 24),

    /**
     * Week cycle unit.
     */
    WEEK("week", "周", DAY.getMillis() * 7),

    /**
     * Month cycle unit.
     */
    MONTH("month", "月", DAY.getMillis() * 31),

    /**
     * Perpetual cycle unit.
     */
    PERPETUAL("perpetual", "永久", 0);

    private String unit;
    private String desc;
    private long millis;

    CycleUnit(String unit, String desc, long millis) {
        this.unit = unit;
        this.desc = desc;
        this.millis = millis;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Gets desc.
     *
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Gets millis.
     *
     * @return the millis
     */
    public long getMillis() {
        return millis;
    }

    /**
     * Parse cycle unit.
     * 解析
     *
     * @param unit the unit
     * @return the cycle unit
     */
    public static CycleUnit parse(String unit) {
        switch (unit) {
            case "hour":
                return HOUR;
            case "day":
                return DAY;
            case "week":
                return WEEK;
            case "month":
                return MONTH;
            case "perpetual":
                return PERPETUAL;
            default:
                return null;
        }
    }
}
