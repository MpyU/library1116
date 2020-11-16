package com.library.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fine implements Serializable {
    /**
     id int primary key auto_increment comment "ID",
     uid int comment "用户ID",
     price double comment "罚款金额",
     fine_desc varchar(255) comment "罚款描述即原因",
     pay_date date comment "付款时间"
     */
    private Integer id;
    private Integer uid;
    private Double price;
    private String fineDesc;
    private Date payDate;
}
