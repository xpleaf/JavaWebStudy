package cn.itcast.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.crm.mapper.CustomerMapper;
import cn.itcast.crm.pojo.Customer;
import cn.itcast.crm.pojo.QueryVo;
import cn.itcast.crm.utils.Page;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public Page<Customer> queryCustomerByQueryVo(QueryVo queryVo) {
		// 设置查询条件,从哪一条数据开始查，其实就是设置起始索引，参考WEB21_ProductService的解释
		queryVo.setStart((queryVo.getPage() - 1) * queryVo.getRows());
		// 查询数据结果集
		List<Customer> list = this.customerMapper.queryCustomerByQueryVo(queryVo);
		// 查询到的数据总条数
		int total = this.customerMapper.queryCountByQueryVo(queryVo);
		// 封装返回的 page 对象，参数分别为：数据总数，当前页数，每页显示数据条数，当前页的数据
		Page<Customer> page = new Page<>(total, queryVo.getPage(), queryVo.getRows(), list);
		// 事实上拿到这个page对象之后就可以自己在前端实现分页的逻辑，只是项目中使用了自定义的标签来进行这个操作
		// 因为如果前端分页的逻辑比较复杂的话，写在自定义的标签中时，就不会乱
		return page;
	}

	@Override
	public Customer queryCustomerById(Long id) {

		Customer customer = this.customerMapper.queryCustomerById(id);
		return customer;
	}

	@Override
	public void updateCustomerById(Customer customer) {
		this.customerMapper.updateCustomerById(customer);
	}

	@Override
	public void deleteCustomerById(Long id) {
		this.customerMapper.deleteCustomerById(id);
	}
}