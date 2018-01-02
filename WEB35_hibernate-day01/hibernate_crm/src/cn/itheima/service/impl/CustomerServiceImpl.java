package cn.itheima.service.impl;

import cn.itheima.dao.CustomerDao;
import cn.itheima.dao.impl.CustomerDaoImpl;
import cn.itheima.domain.Customer;
import cn.itheima.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao = new CustomerDaoImpl();

	public void save(Customer c) {
		//调用Dao保存客户
		customerDao .save(c);
	}

}
