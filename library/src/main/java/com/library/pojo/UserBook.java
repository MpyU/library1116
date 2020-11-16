package com.library.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBook implements Serializable {
    /**
     id int primary key auto_increment comment "ID",
     uid int comment "借书用户ID",
     bid int comment "书ID",
     status int comment "是否返还，0表示未返还，1表示已返回",
     lend_date date comment "借阅时间",
     return_date date comment "返还时间"
     */
    private Integer id;
    private Integer uid;
    private Integer bid;
    private Integer status;
    private Date lendDate;
    private Date returnDate;
}
