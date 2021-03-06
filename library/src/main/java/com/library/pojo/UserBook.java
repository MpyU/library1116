package com.library.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBook implements Serializable {
    /**
     * com.library.pojo.UserBook
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
    private String lendDate;
    private String returnDate;


    private User user;
    private Book book;

    public UserBook(Integer id){
        this.id = id;
    }
}
