package com.itheima.jd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.jd.dao.SolrDao;
import com.itheima.jd.pojo.ProductModel;
import com.itheima.jd.vo.QueryVo;

@Service
public class SolrServiceImpl implements SolrService {

	@Autowired
	private SolrDao solrDao;

	public List<ProductModel> getResultModelFromSolr(QueryVo vo) throws Exception {
		return solrDao.getResultModelFromSolr(vo);
	}

}
