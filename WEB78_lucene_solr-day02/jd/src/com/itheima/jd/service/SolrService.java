package com.itheima.jd.service;

import java.util.List;

import com.itheima.jd.pojo.ProductModel;
import com.itheima.jd.vo.QueryVo;

public interface SolrService {
	public List<ProductModel> getResultModelFromSolr(QueryVo vo) throws Exception;

}
