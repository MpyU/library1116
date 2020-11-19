package com.library.component;

import com.library.pojo.Notice;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

@Component
public class Consumer {
   @RabbitListener(queues="notice_queue")
    public void myListener(String msg) throws Exception {
       ObjectInputStream objectInputStream=new ObjectInputStream(new ByteArrayInputStream(msg.getBytes()));
       Notice notice=(Notice)objectInputStream.readObject();
        System.out.println("收到的消息是:" + notice);
    }


}
