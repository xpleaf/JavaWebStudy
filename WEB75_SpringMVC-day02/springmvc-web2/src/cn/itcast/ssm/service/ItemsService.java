package cn.itcast.ssm.service;

import java.util.List;

import cn.itcast.ssm.pojo.Items;

public interface ItemsService {
	/**
	 * 查询商品列表
	 *
	 * @return
	 */
	List<Items> queryItemList();

	Items queryItemById(int id);

	/**
	 * 根据id更新商品
	 *
	 * @param item
	 */
	void updateItemById(Items item);
}
