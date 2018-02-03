package cn.e3mall.search.service;

import cn.e3mall.common.utils.E3Result;

public interface SearchItemService {
	
	public E3Result importItems();
	public E3Result addDocument(long itemId) throws Exception;
}
