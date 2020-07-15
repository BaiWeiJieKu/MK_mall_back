package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**购物车service
 * @author QinFen
 * @date 2019/9/7 0007 17:08
 */
public interface ICartService {

    /**添加购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    /**更新购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);

    /**删除购物车
     * @param userId
     * @param productIds
     * @return
     */
    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);

    /**查询购物车列表
     * @param userId
     * @return
     */
    ServerResponse<CartVo>list(Integer userId);

    /**选择或反选全部
     * @param userId
     * @param checked
     * @return
     */
    ServerResponse<CartVo>selectOrUnSelect(Integer userId,Integer productId,Integer checked);

    /**查询当前用户的购物车里的产品数量
     * @param user_id
     * @return
     */
    ServerResponse<Integer> getCartProductCount(Integer user_id);

}
