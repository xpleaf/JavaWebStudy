package cn.xpleaf.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJManager {

	// 复杂查询索引
	@Test
	public void queryIndex2() throws Exception {
		// 创建连接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 创建一个query对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("钻石");
//		query.set("q", "钻石");
		// 过滤条件
		query.setFilterQueries("product_catalog_name:幽默杂货");
		// 排序条件
		query.setSort("product_price", ORDER.asc);
		// 分页处理
		query.setStart(0);
		query.setRows(10);
		// 结果中域的列表
		query.setFields("id", "product_name", "product_price", "product_catalog_name", "product_picture");
		// 设置默认搜索域
		query.set("df", "product_keywords");
		// 高亮显示
		query.setHighlight(true);
		// 高亮显示的域
		query.addHighlightField("product_name");
		// 高亮显示的前缀
		query.setHighlightSimplePre("<em>");
		// 高亮显示的后缀
		query.setHighlightSimplePost("</em>");
		// 执行查询
		QueryResponse queryResponse = solrServer.query(query);
		// 取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		// 共查询到商品数量
		System.out.println("共查询到商品数量:" + solrDocumentList.getNumFound());
		// 遍历查询的结果
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			// 取高亮显示
			String productName = "";
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
			// 判断是否有高亮内容
			if (null != list) {
				productName = list.get(0);
			} else {
				productName = (String) solrDocument.get("product_name");
			}

			System.out.println(productName);
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_catalog_name"));
			System.out.println(solrDocument.get("product_picture"));

		}
	}

	// 查询索引
	@Test
	public void queryIndex() throws Exception {
		// 创建连接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 创建一个query对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("*:*");
		// 执行查询
		QueryResponse queryResponse = solrServer.query(query);
		// 取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		// 共查询到商品数量
		System.out.println("共查询到商品数量:" + solrDocumentList.getNumFound());
		// 遍历查询的结果
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("product_name"));
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_catalog_name"));
			System.out.println(solrDocument.get("product_picture"));

		}
	}

	// 根据查询条件删除文档
	@Test
	public void deleteDocumentByQuery() throws Exception {
		// 创建连接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 根据查询条件删除文档
		solrServer.deleteByQuery("*:*");
		// 提交修改
		solrServer.commit();
	}

	// 删除文档，根据id删除
	@Test
	public void deleteDocumentByid() throws Exception {
		// 创建连接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 根据id删除文档
		solrServer.deleteById("c0001");
		// 提交修改
		solrServer.commit();
	}

	// 向索引库中添加索引
	@Test
	public void addDocument() throws Exception {
		// 和solr服务器创建连接
		// 参数：solr服务器的地址
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");	//默认使用第一个solr实例，即collection1
//		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection2");
		// 创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		// 向文档中添加域
		// 第一个参数：域的名称，域的名称必须是在schema.xml中定义的
		// 第二个参数：域的值
		document.addField("id", "c0001");
		document.addField("title_ik", "使用solrJ添加的文档");
		document.addField("content_ik", "文档的内容");
		document.addField("product_name", "商品名称");
		// 把document对象添加到索引库中
		solrServer.add(document);
		// 提交修改
		solrServer.commit();

	}

}
