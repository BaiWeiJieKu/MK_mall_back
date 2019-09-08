package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**收货地址
 * @author QinFen
 * @date 2019/9/8 0008 15:41
 */
public interface IShippingService {

    /**新增收货地址
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse add(Integer userId, Shipping shipping);

    /**删除收货地址
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse<String> del(Integer userId,Integer shippingId);

    /**更新收货地址
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse update(Integer userId, Shipping shipping);

    /**查询收货地址
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    /**获取收货地址列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
