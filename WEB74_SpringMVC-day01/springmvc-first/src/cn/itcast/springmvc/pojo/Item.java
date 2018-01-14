package cn.itcast.springmvc.pojo;

import java.util.Date;

public class Item {
	// 商品 id
	private int id;
	// 商品名称
	private String name;
	// 商品价格
	private double price;
	// 商品创建时间
	private Date createtime;
	// 商品 述
	private String detail;
	
	public Item(int id, String name, double price, Date createtime, String detail) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.createtime = createtime;
		this.detail = detail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
