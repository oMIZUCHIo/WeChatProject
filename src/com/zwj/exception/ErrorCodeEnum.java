package com.zwj.exception;

import org.apache.commons.codec.binary.StringUtils;

//错误码
public enum ErrorCodeEnum {

    //操作成功
    SUCCESS("0000","操作成功"),

    //通用错误
    UNKNOWN_ERROR("1000","未知错误"),
    NOEXIST_ERROR_CODE("1001","请求了未知的错误码"),
    PARAM_EMPTY("1002","输入参数为空"),
    DATABASE_ERROR("1003","数据库出错"),
    EMPTY_RESULT("1004","查询结果为空"),

    //用户错误
    PASSWORD_ERROR("1005","密码错误"),
    USER_NOEXIST("1006","用户不存在"),
    USER_EXIST("1007","用户已存在"),
    PASSWORD_REPEAT("1008","两次输入密码不同"),

    //文件错误
    ICON_EMPTY("1009","所选图片为空"),
    ICON_TYPE_EMPTY("1010","文件类型为空"),

    //好友错误
    HAS_BEAN_FRIEND("1011","两用户已是好友"),
    ISNOT_FRIEND("1012","不是对方好友"),
    NOEXIST_FRIEND_GROUP("1013","无分组"),
    FRIEND_GROUP_EMPTY("1014","分组内为空"),

    //群聊错误
    ONLY_ADMIN_ALLOW("1015","只有群主有该操作权力"),
    ISNOT_MANAAGER("1016","该群员不是管理员"),
    ONLY_TWO_MANAAGER_ALLWO("1017","至多只能有两名管理员"),
    HAS_BEAN_MANAAGER("1018","已是管理员"),
    HAS_BEAN_ADMIN("1019","已是群主"),
    ONLY_ADMIN_MANAAGER_ALLWO("1020","只有群主和管理员有该操作权力"),

    //登录错误
    USER_HAS_LOGIN("1021","用户已登录过");

    // 错误码
    private final String code;

    // 描述
    private final String description;

    /**
     * @param code 错误码
     * @param description 描述
     */
    ErrorCodeEnum(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码查询枚举。
     * @param code 编码。
     */
    public static ErrorCodeEnum getByCode(String code) {
        for (ErrorCodeEnum value : ErrorCodeEnum.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return value;
            }
        }
        return NOEXIST_ERROR_CODE;
    }

    /**
     * 枚举是否包含此code
     * @param code 枚举code
     */
    public static Boolean contains(String code){
        for (ErrorCodeEnum value : ErrorCodeEnum.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return true;
            }
        }
        return  false;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
