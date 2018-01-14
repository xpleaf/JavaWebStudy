package cn.itcast.ssm.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.exception.MyException;
import cn.itcast.ssm.pojo.Items;
import cn.itcast.ssm.service.ItemsService;
import cn.itcast.ssm.vo.QueryVo;

@Controller
@RequestMapping("item")
public class ItemsController {

	@Autowired
	private ItemsService itemService;

	/**
	 * 显示商品列表
	 *
	 * @return
	 * @throws MyException
	 */
	// @RequestMapping("/itemList")
	@RequestMapping({ "itemList", "itemListAll" })
	public ModelAndView queryItemList() throws MyException {
		// 自定义异常
		// if(true) {
		// throw new MyException("自定义异常出现了！");
		// }

		// 运行时异常
		// int i = 1/0;

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
	public String queryItemById(@RequestParam(value = "id", required = true, defaultValue = "1") Integer id,
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
	 * 使用 RESTful 风格开发接口，实现根据 id 查询商品 *
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/item/{id}")
	@ResponseBody
	public Items queryItemById(@PathVariable() Integer id) {
		Items item = this.itemService.queryItemById(id);
		return item;
	}

	/**
	 * 更新商品,绑定 pojo 类型
	 *
	 * @param item
	 * @param model
	 *            * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequestMapping(value = "/updateItem", method = RequestMethod.POST)
	public String updateItem(Items item, MultipartFile pictureFile) throws IllegalStateException, IOException {
		// 图片上传
		// 设置图片名称，不能重复，可以使用 uuid
		String picName = UUID.randomUUID().toString();
		// 获取文件名
		String oriName = pictureFile.getOriginalFilename();
		// 获取图片后缀
		String extName = oriName.substring(oriName.lastIndexOf("."));
		// 开始上传
		pictureFile.transferTo(new File("/Users/yeyonghao/server/upload/image/" + picName + extName));
		// 设置图片名到商品中
		item.setPic(picName + extName);
		// ---------------------------------------------
		// 更新商品
		// 调用服务更新商品
		this.itemService.updateItemById(item);
		// 返回逻辑视图
		return "redirect:/itemEdit.action?id=" + item.getId();
		// return "forward:/itemEdit.action";
	}

	// 绑定包装数据类型
	@RequestMapping(value = "/queryItem", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryItem(QueryVo queryVo, Integer[] ids, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.使用request进行转发
		// request.getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request,
		// response);

		// 2.使用response重定向到编辑页面
		// response.sendRedirect(request.getContextPath() + "/itemEdit.action");

		// 3.使用response直接显示
		// response.getWriter().write("hello, xpleaf");

		return "success";
	}

	/**
	 * 测试 json 的交互
	 * 
	 * @param item
	 *            * @return
	 */
	@RequestMapping("testJson")
	// @ResponseBody
	public @ResponseBody Items testJson(@RequestBody Items item) {
		return item;
	}

}
