package com.itheima.jd.vo;

public class QueryVo {
	// 关键词
	private String queryString;
	// 过滤条件 商品类型
	private String catalog_name;
	// 价格区间
	private String price;
	// 排序 1 正 0倒
	private String sort;

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getCatalog_name() {
		return catalog_name;
	}

	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
