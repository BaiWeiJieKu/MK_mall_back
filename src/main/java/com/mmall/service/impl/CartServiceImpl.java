package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

/**购物车service实现
 * @author QinFen
 * @date 2019/9/7 0007 17:08
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**添加购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count){
        //判断商品id和数量是否有效
        if (productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        //根据用户id和商品id查找当前购物车中是否有该产品记录
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null){
            //这个产品不在这个购物车中，需要新增一个这个产品的记录
            Cart cartItem = new Cart();
            //设置数量
            cartItem.setQuantity(count);
            //设置已勾选
            cartItem.setChecked(Const.Cart.CHECKED);
            //设置商品id
            cartItem.setProductId(productId);
            //设置用户id
            cartItem.setUserId(userId);
            //插入记录
            cartMapper.insert(cartItem);
        }else {
            //这个产品已经在购物车中了，
            //如果产品已存在，数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.list(userId);
    }

    /**更新购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count){
        //判断商品id和数量是否有效
        if (productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        //根据用户id和商品id查找当前购物车中是否有该产品记录
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart != null){
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return this.list(userId);
    }

    /**删除购物车
     * @param userId
     * @param productIds 商品id集合
     * @return
     */
    @Override
    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds){
        //把商品id集合切割开
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        //通过用户id和产品id集合批量删除
        cartMapper.deleteByUserIdProductIds(userId,productList);
        return this.list(userId);
    }

    /**查询购物车列表
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<CartVo>list(Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**选择或反选全部
     * @param userId
     * @param checked
     * @return
     */
    @Override
    public ServerResponse<CartVo>selectOrUnSelect(Integer userId,Integer productId, Integer checked){
        cartMapper.checkedOrUncheckedProduct(userId,checked,productId);
        return this.list(userId);
    }

    /**查询当前用户的购物车里的产品数量
     * @param user_id
     * @return
     */
    @Override
    public ServerResponse<Integer> getCartProductCount(Integer user_id){
        if (user_id == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(user_id));
    }

    /**获取CartVo限制
     * @param userId
     * @return
     */
    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        //根据用户id查询购物车列表
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        //购物车总价格
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (CollectionUtils.isNotEmpty(cartList)){
            for (Cart cartItem : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                //设置购物车id
                cartProductVo.setId(cartItem.getId());
                //设置用户id
                cartProductVo.setUserId(userId);
                //设置商品id
                cartProductVo.setProductId(cartItem.getProductId());
                //根据商品id查询当前商品
                Product product =productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null){
                    //设置商品主图
                    cartProductVo.setProductMainImage(product.getMainImage());
                    //设置产品名称
                    cartProductVo.setProductName(product.getName());
                    //设置商品副标题
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    //设置商品状态
                    cartProductVo.setProductStatus(product.getStatus());
                    //设置商品价格
                    cartProductVo.setProductPrice(product.getPrice());
                    //设置商品库存数量
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    //库存充足的时候
                    if (product.getStock() >= cartItem.getQuantity()){
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                if (cartItem.getChecked() == Const.Cart.CHECKED){
                    //如果已经勾选，增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        return cartVo;
    }

    /**是否全选
     * @param userId
     * @return
     */
    private boolean getAllCheckedStatus(Integer userId){
        if (userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
