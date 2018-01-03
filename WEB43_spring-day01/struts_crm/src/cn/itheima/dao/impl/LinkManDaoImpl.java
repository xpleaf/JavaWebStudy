package cn.itheima.dao.impl;

import org.hibernate.Session;

import cn.itheima.dao.LinkManDao;
import cn.itheima.domain.LinkMan;
import cn.itheima.utils.HibernateUtils;

public class LinkManDaoImpl implements LinkManDao {

	public void save(LinkMan lm) {
		//1 获得session
		Session session = HibernateUtils.getCurrentSession();
		session.save(lm);
	}

}
