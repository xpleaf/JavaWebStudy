package cn.itheima.service.impl;

import org.hibernate.Hibernate;

import cn.itheima.dao.UserDao;
import cn.itheima.dao.impl.UserDaoImpl;
import cn.itheima.domain.User;
import cn.itheima.service.UserService;
import cn.itheima.utils.HibernateUtils;

public class UserServiceImpl implements UserService {

	private UserDao ud ;

	@Override
	public User login(User user) {
		
		//打开事务
		HibernateUtils.getCurrentSession().beginTransaction();
		//1.调用Dao根据登陆名称查询User对象
		User existU = ud .getByUserCode(user.getUser_code());
		//提交事务
		HibernateUtils.getCurrentSession().getTransaction().commit();
		
		if(existU==null){
			//获得不到=>抛出异常提示用户名不存在
			throw new RuntimeException("用户名不存在!");
		}
		//2 比对密码是否一致
		if(!existU.getUser_password().equals(user.getUser_password())){
			//不一致=>抛出异常提示密码错误
			throw new RuntimeException("密码错误!");
		}
		//3 将数据库查询的User返回
		return existU;
	}

	public void setUd(UserDao ud) {
		this.ud = ud;
	}

}
