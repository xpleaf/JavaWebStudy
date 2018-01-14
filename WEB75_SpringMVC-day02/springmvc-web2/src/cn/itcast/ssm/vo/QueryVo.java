package cn.itcast.ssm.vo;

import java.util.List;

import cn.itcast.ssm.pojo.Items;

public class QueryVo {
	private Items item;

	private Integer[] ids;

	// 用对象的属性接收List集合
	private List<Items> itemList;

	public List<Items> getItemList() {
		return itemList;
	}

	public void setItemList(List<Items> itemList) {
		this.itemList = itemList;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public Items getItem() {
		return item;
	}

	public void setItem(Items item) {
		this.item = item;
	}

}
