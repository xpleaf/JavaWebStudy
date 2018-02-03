package cn.e3mall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;

	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public E3Result addCart(long userId, long itemId, int num) {
		// a)判断购物车中是否有此商品
		Boolean flag = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
		// b)如果有，数量相加
		if (flag) {
			// 从hash中取商品数据
			String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
			// 转换成java对象
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			// 数量相加
			tbItem.setNum(tbItem.getNum() + num);
			// 写入hash
			jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
			// 返回添加成功
			return E3Result.ok();
		}
		// c)如果没有，根据商品id查询商品信息。
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		// 设置商品数量
		tbItem.setNum(num);
		String image = tbItem.getImage();
		// 取一张图片
		if (StringUtils.isNotBlank(image)) {
			tbItem.setImage(image.split(",")[0]);
		}
		// d)把商品信息添加到购物车
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
		return E3Result.ok();

	}

	@Override
	public E3Result mergeCart(long userId, List<TbItem> itemList) {
		// 遍历商品列表
		for (TbItem tbItem : itemList) {
			addCart(userId, tbItem.getId(), tbItem.getNum());
		}
		return E3Result.ok();

	}

	@Override
	public List<TbItem> getCartList(long userId) {
		// 从redis中根据用户id查询商品列表
		List<String> strList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
		List<TbItem> resultList = new ArrayList<>();
		// 把json列表转换成TbItem列表
		for (String string : strList) {
			TbItem tbItem = JsonUtils.jsonToPojo(string, TbItem.class);
			// 添加到列表
			resultList.add(tbItem);
		}
		return resultList;
	}

	@Override
	public E3Result updateCartItemNum(long userId, long itemId, int num) {
		// 从hash中取商品信息
		String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
		// 转换成java对象
		TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
		// 更新数量
		tbItem.setNum(num);
		// 写入hash
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
		return E3Result.ok();

	}

	@Override
	public E3Result deleteCartItem(long userId, long itemId) {
		// 根据商品id删除hash中对应的商品数据。
		jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
		return E3Result.ok();

	}

	@Override
	public E3Result clearCartItem(long userId) {
		// 删除购物车信息
		jedisClient.del(REDIS_CART_PRE + ":" + userId);
		return E3Result.ok();
	}

}
