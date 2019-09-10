package com.mmall.service;

import com.mmall.common.ServerResponse;

import java.util.Map;

/**
 * @author QinFen
 * @date 2019/9/10 0010 10:52
 */
public interface IOrderService {

    /**支付订单，对接支付宝,使用支付宝沙箱
     * @param orderNo
     * @param userId
     * @param path
     * @return
     */
    ServerResponse pay(Long orderNo, Integer userId, String path);

    /**支付回调
     * @param params
     * @return
     */
    ServerResponse aliCallback(Map<String,String> params);

    /**查询订单支付状态
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);
}
