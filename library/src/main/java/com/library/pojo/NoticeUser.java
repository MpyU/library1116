package com.library.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//保存公有消息
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeUser {
    private Integer id;
   private Integer uid;
   private Integer nid;
    private Integer status;//代表未读，1代表已经读
    public NoticeUser(Integer uid,Integer nid,Integer status){
        this.uid=uid;
        this.nid=nid;
        this.status=status;
    }
}
