package cn.e3mall.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.search.service.SearchItemService;

public class ItemChangeListener implements MessageListener {
	
	@Autowired
	private SearchItemService searchItemServiceImpl;

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = null;
			Long itemId = null;
			// 先等1秒再去数据库查询商品数据，否则可能事务还没有提交找不到添加的商品
			Thread.sleep(1000);
			// 取商品id
			if(message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				itemId = Long.parseLong(textMessage.getText());
			}
			// 向索引库添加文档
			searchItemServiceImpl.addDocument(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
