package com.itheima.jd.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.jd.pojo.ProductModel;
import com.itheima.jd.service.SolrService;
import com.itheima.jd.vo.QueryVo;

/**
 * 商品搜索
 * 
 * @author lx
 *
 */
@Controller
public class ProductController {

	@Autowired
	SolrService solrService;

	// 搜索
	@RequestMapping("/list.action")
	public String list(QueryVo vo, Model model) throws Exception {
		// 结果集
		List<ProductModel> productList = solrService.getResultModelFromSolr(vo);
		model.addAttribute("productList", productList);
		model.addAttribute("catalog_name", vo.getCatalog_name());
		model.addAttribute("price", vo.getPrice());
		model.addAttribute("sort", vo.getSort());
		model.addAttribute("queryString", vo.getQueryString());
		return "product_list";
	}
}
