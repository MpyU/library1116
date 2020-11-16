package com.library.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice implements Serializable {
    /**
     id int primary key auto_increment comment "ID",
     message varchar(255) comment "消息",
     status int comment "为0的话，就表示全发，不为0就发用户",
     publish_date date comment "发布消息时间"
     */
    private Integer id;
    private String message;
    private Integer status;
    private Date publishDate;
}
