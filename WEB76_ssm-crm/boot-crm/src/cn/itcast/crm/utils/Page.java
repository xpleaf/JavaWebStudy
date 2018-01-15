package cn.itcast.crm.utils;

import java.util.List;

public class Page<T> {

	// 总的条数
	private int total;
	// 当前页数
	private int page;
	// 每一页大小
	private int size;
	// 每一页的数据
	private List<T> rows;

	public Page(int total, int page, int size, List<T> rows) {
		super();
		this.total = total;
		this.page = page;
		this.size = size;
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
