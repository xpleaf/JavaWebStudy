package com.itheima.jd.dao;

import java.util.List;

import com.itheima.jd.pojo.ProductModel;
import com.itheima.jd.vo.QueryVo;

public interface SolrDao {
	public List<ProductModel> getResultModelFromSolr(QueryVo vo) throws Exception;
}
