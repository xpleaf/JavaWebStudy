package cn.itcast.springmvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.springmvc.pojo.Item;

@Controller
public class ItemController {
	
	@RequestMapping("/products/itemList.action")
	public ModelAndView queryItemList() {
		// 创建页面需要显示的商品数据
		List<Item> list = new ArrayList<>();
		list.add(new Item(1, "1 华为 荣耀 8", 2399, new Date(), "质量好!1")); 
		list.add(new Item(2, "2 华为 荣耀 8", 2399, new Date(), "质量好!2")); 
		list.add(new Item(3, "3 华为 荣耀 8", 2399, new Date(), "质量好!3")); 
		list.add(new Item(4, "4 华为 荣耀 8", 2399, new Date(), "质量好!4")); 
		list.add(new Item(5, "5 华为 荣耀 8", 2399, new Date(), "质量好!5")); 
		list.add(new Item(6, "6 华为 荣耀 8", 2399, new Date(), "质量好!6"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemList", list);
//		modelAndView.setViewName("/WEB-INF/jsp/itemList.jsp");
		modelAndView.setViewName("itemList");
		
		return modelAndView;
	}
}
