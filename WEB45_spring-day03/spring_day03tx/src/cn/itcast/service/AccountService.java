package cn.itcast.service;

public interface AccountService {
	
	//转账方法
	void transfer(Integer from,Integer to,Double money);
	
}
