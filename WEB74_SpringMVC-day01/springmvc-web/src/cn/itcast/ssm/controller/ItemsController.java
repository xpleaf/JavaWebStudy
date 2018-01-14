package cn.itcast.ssm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.pojo.Items;
import cn.itcast.ssm.service.ItemsService;
import cn.itcast.ssm.vo.QueryVo;

@Controller
public class ItemsController {

	@Autowired
	private ItemsService itemService;

	/**
	 * 显示商品列表
	 *
	 * @return
	 */
	@RequestMapping("/itemList")
	public ModelAndView queryItemList() {
		// 获取商品数据
		List<Items> list = this.itemService.queryItemList();
		ModelAndView modelAndView = new ModelAndView();
		// 把商品数据放到模型中
		modelAndView.addObject("itemList", list);
		// 设置逻辑视图
		modelAndView.setViewName("itemList");
		return modelAndView;
	}

	/**
	 * 根据id查询商品 *
	 * 
	 * @param request
	 *            * @return
	 */
	@RequestMapping("/itemEdit")
	public String queryItemById(@RequestParam(value = "itemId", required = true, defaultValue = "1") Integer id,
			ModelMap model) {
		// 从 request 中获取请求参数
		// String strId = request.getParameter("id");
		// Integer id = Integer.valueOf(strId);
		// 根据 id 查询商品数据
		Items item = this.itemService.queryItemById(id);
		/*
		 * // 把结果传递给页面 ModelAndView modelAndView = new ModelAndView(); // 把商品数据放在模型中
		 * modelAndView.addObject("item", item); // 设置逻辑视图
		 * modelAndView.setViewName("itemEdit"); return modelAndView;
		 */

		model.addAttribute("item", item);

		return "itemEdit";
	}

	/**
	 * 更新商品,绑定 pojo 类型
	 *
	 * @param item
	 * @param model
	 *            * @return
	 */
	@RequestMapping("/updateItem")
	public String updateItem(Items item) {
		// 调用服务更新商品
		this.itemService.updateItemById(item);
		// 返回逻辑视图
		return "success";
	}

	// 绑定包装数据类型 
	@RequestMapping("/queryItem")
	public String queryItem(QueryVo queryVo) {
		System.out.println(queryVo.getItem().getId());
		System.out.println(queryVo.getItem().getName());
		return "success";
	}
}
