package cn.e3mall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.service.SearchItemService;
import cn.e3mall.search.service.mapper.ItemMapper;

@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	/**
	 * 将商品数据导入索引库
	 * 
	 * @return
	 */
	@Override
	public E3Result importItems() {
		try {
			// 查询商品列表
			List<SearchItem> itemList = itemMapper.getItemList();
			// 导入索引库
			for (SearchItem searchItem : itemList) {
				// 创建文档对象
				SolrInputDocument document = new SolrInputDocument();
				// 向文档中添加域
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				// 写入索引库
				solrServer.add(document);
			}
			// 提交
			solrServer.commit();
			// 返回成功
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, "商品导入失败");
		}
	}

	/**
	 * 将单个商品导入索引库
	 */
	public E3Result addDocument(long itemId) throws Exception {
		// 1.根据商品ID查询商品信息
		SearchItem searchItem = itemMapper.getItemById(itemId);
		// 2.创建SolrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();
		// 3.使用添加域到document对象
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getTitle());
		document.addField("item_sell_point", searchItem.getSell_point());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_image", searchItem.getImage());
		document.addField("item_category_name", searchItem.getCategory_name());
		// 4.向索引库中添加文档
		solrServer.add(document);
		solrServer.commit();

		return E3Result.ok();
	}

}
