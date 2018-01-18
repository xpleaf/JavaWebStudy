package com.itheima.jd.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itheima.jd.pojo.ProductModel;
import com.itheima.jd.vo.QueryVo;

@Repository
public class SolrDaoImpl implements SolrDao {
	@Autowired
	private SolrServer solrServer;

	@Override
	public List<ProductModel> getResultModelFromSolr(QueryVo vo) throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		// 关键词
		if (null != vo.getQueryString() && !"".equals(vo.getQueryString())) {
			solrQuery.setQuery(vo.getQueryString());
			solrQuery.set("df", "product_keywords");

		}
		// 商品类型
		if (null != vo.getCatalog_name() && !"".equals(vo.getCatalog_name().trim())) {
			solrQuery.addFilterQuery("product_catalog_name:" + vo.getCatalog_name());
		}
		// 价格
		if (null != vo.getPrice() && !"".equals(vo.getPrice().trim())) {
			String[] split = vo.getPrice().split("-");
			if (split.length == 2) {
				solrQuery.addFilterQuery("product_price:[" + split[0] + " TO " + split[1] + "]");
			} else {
				solrQuery.addFilterQuery("product_price:[" + split[0] + " TO *]");
			}
		}
		// 价格排序
		if ("1".equals(vo.getSort())) {
			solrQuery.setSort("product_price", ORDER.desc);
		} else {
			solrQuery.setSort("product_price", ORDER.asc);
		}

		// 显示的域
		// solrQuery.setFields("id,product_name,product_price");

		// 1:设置高亮开关
		solrQuery.setHighlight(true);
		// 2：需要高亮的域
		solrQuery.addHighlightField("product_name");
		// 3：高亮的简单样式 前缀
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		// 4：高亮的简单样式 后缀
		solrQuery.setHighlightSimplePost("</span>");
		QueryResponse response = solrServer.query(solrQuery);
		// 取出高亮
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

		// 取出结果集
		SolrDocumentList docs = response.getResults();
		// 打印总条数
		System.out.println("总条数：" + docs.getNumFound());
		// 结果集
		List<ProductModel> productModels = new ArrayList<ProductModel>();
		// 遍历结果集
		for (SolrDocument doc : docs) {

			ProductModel productModel = new ProductModel();
			// ID
			String id = (String) doc.get("id");
			productModel.setPid(id);
			// 商品名称，避免后面没有高亮时就不会设置商品名称
			productModel.setName((String) doc.get("product_name"));
			//
			Map<String, List<String>> map = highlighting.get(id);
			List<String> list = map.get("product_name");
			// 判断高亮必须不为空
			if (null != list && list.size() > 0) {
				String name = list.get(0);
				productModel.setName(name);
			}
			// 价格
			productModel.setPrice((float) doc.get("product_price"));
			// 商品图片
			productModel.setPicture((String) doc.get("product_picture"));
			// 商品类型
			productModel.setCatalog_name((String) doc.get("product_catalog_name"));

			productModels.add(productModel);
		}
		return productModels;
	}

}