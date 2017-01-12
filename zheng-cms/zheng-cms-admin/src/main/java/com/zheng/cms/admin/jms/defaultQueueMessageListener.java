package com.zheng.cms.admin.jms;

import com.zheng.cms.dao.model.CmsUser;
import com.zheng.cms.rpc.api.UserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MQ消费者
 * Created by ZhangShuzheng on 2017/01/12.
 */
public class defaultQueueMessageListener implements MessageListener {

	private static Logger _log = LoggerFactory.getLogger(defaultQueueMessageListener.class);

	@Autowired
	ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    UserService userService;

	public void onMessage(final Message message) {
		// 使用线程池多线程处理
		threadPoolTaskExecutor.execute(new Runnable() {
			public void run() {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						_log.info("消费消息：{}", textMessage.getText());
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		});
	}

}
