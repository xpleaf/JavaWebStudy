package cn.itcast.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.ssm.mapper.ItemsMapper;
import cn.itcast.ssm.pojo.Items;

@Service
public class ItemsServiceImpl implements ItemsService {
	
	@Autowired
	private ItemsMapper itemsMapper;

	@Override
	public List<Items> queryItemList() {

		// 从数据库查询商品数据
		List<Items> list = this.itemsMapper.selectByExampleWithBLOBs(null);
		return list;
	}

	@Override
	public Items queryItemById(int id) {
		Items item = itemsMapper.selectByPrimaryKey(id);
		return item;
	}

	@Override
	public void updateItemById(Items item) {
		itemsMapper.updateByPrimaryKeySelective(item);
	}

}
