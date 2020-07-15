package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 常量类
 * @auther QinFen
 *
 */
public class Const {
    /**
     * 当前用户
     */
    public static final String CURRENT_USER = "currentUser";
    /**
     * 邮箱
     */
    public static final String EMAIL = "email";
    /**
     * 用户名
     */
    public static final String USERNAME = "username";

    /**
     * 找回密码
     */
    public static final String TOKEN_PREFIX = "token_";

    /**
     * 商品列表排序
     */
    public interface ProductListOrderBy{
        /**
         * 价格排序
         */
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    /**
     * redis缓存过期时间
     */
    public interface RedisCacheExtime{
        int REDIS_SESSION_EXTIME = 60*30;//30分钟
    }

    /**
     * 购物车
     */
    public interface Cart{
        /**
         * 购物车已勾选
         */
        int CHECKED = 1;
        /**
         * 购物车未勾选
         */
        int UN_CHECKED = 0;
        /**
         * 限制数量失败
         */
        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        /**
         * 限制数量成功
         */
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    /**
     * 角色
     */
    public interface Role {
        /**
         * 普通用户
         */
        int ROLE_CUSTOMER = 0;
        /**
         * 管理员
         */
        int ROLE_ADMIN = 1;
    }

    /**
     * 产品状态枚举
     */
    public enum ProductStatusEnum{
        /**
         * 正在出售状态
         */
        ON_SALE(1,"在线");

        private String value;
        private int code;
        ProductStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 订单状态枚举
     */
    public enum OrderStatusEnum{
        /**
         * 订单已取消
         */
        CANCELED(0,"已取消"),
        /**
         * 订单未支付
         */
        NO_PAY(10,"未支付"),
        /**
         * 订单已支付
         */
        PAID(20,"已付款"),
        /**
         * 商品已发货
         */
        SHIPPED(40,"已发货"),
        /**
         * 订单完成
         */
        ORDER_SUCCESS(50,"订单完成"),
        /**
         * 订单关闭
         */
        ORDER_CLOSE(60,"订单关闭");

        OrderStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum:values()){
                if (orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    /**
     * 支付选项
     */
    public enum PayPlatformEnum{
        /**
         * 支付宝支付
         */
        ALIPAY(1,"支付宝");

        PayPlatformEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 支付类型
     */
    public enum PaymentTypeEnum{
        /**
         * 在线支付
         */
        ONLINE_PAY(1,"在线支付");

        PaymentTypeEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static PaymentTypeEnum codeOf(int code){
            for (PaymentTypeEnum paymentTypeEnum:values()){
                if (paymentTypeEnum.getCode() == code){
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    /**
     * 支付宝回调状态
     */
    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    public interface REDIS_LOCK{
        //关闭订单的分布式锁
        String CLOSE_ORDER_TASK_LOCK = "CLOSE_ORDER_TASK_LOCK";
    }
}
