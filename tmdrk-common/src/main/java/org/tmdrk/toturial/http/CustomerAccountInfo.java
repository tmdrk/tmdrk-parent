package org.tmdrk.toturial.http;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * CustomerAccountInfo
 * 富登客户开户信息
 * @author Jie.Zhou
 * @date 2020/11/20 19:58
 */
@Data
@Accessors(chain = true)
public class CustomerAccountInfo {
    /**
     * 错误码
     */
    private String _RejCode;
    /**
     * 错误信息
     */
    private String _RejMessage;
    /**
     * 开户日期
     */
    private String OpenAccountDate;
    /**
     * 客户经理编号
     */
    private String ClientManager;
    /**
     * 是否为行内员工 N：不是 Y：是
     */
    private String IsStaff;
    /**
     * 开户机构
     */
    private String DeptName;
}
