package cn.e3mall.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;

public class PageHelperTest {

	@Test
	public void testPageHelper() {
		// 初始化spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		// 获得Mapper的代理对象
		TbItemMapper itemMapper = context.getBean(TbItemMapper.class);
		// 设置分页信息
		PageHelper.startPage(1, 30);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		// 取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		System.out.println(pageInfo.getTotal());		// 数据总条数
		System.out.println(pageInfo.getPages());		// 总页数
		System.out.println(pageInfo.getPageNum());	// 当前页
		System.out.println(pageInfo.getSize());		// 每页大小
	}
}
