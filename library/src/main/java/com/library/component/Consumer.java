package com.library.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.library.pojo.Notice;

//暂时不使用
//@Component
public class Consumer {

	// * 发送信息用异步任务，传送到RabbitMQ,再读取RabbitMQ保存到Redis,+数据库
	// * uid+message为key，用Redis的List保存未读消息的id,看到后就删除
	// * 缺点：必须为redis设置过期时间，否则容易占满内存
	@Autowired
	RedisTemplate redisTemplate;

	@RabbitListener(queues = "notice_queue")
	public void myListener(Notice notice) throws Exception {
		System.out.println("msg:" + notice);

	}

	public void timeWorker() {

	}

}
