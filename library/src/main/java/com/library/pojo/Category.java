package com.library.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    /**
     * 	id int primary key auto_increment comment "ID",
     * 	category_name varchar(20) comment "类别名称",
     * 	category_desc varchar(255) comment "类别详情",
     * 	floor int comment "该类别所在楼层",
     * 	parent_id int comment "父类别ID"
     */
    private Integer id;
    private String categoryName;
    private String categoryDesc;
    private Integer floor;
    private Integer parentId;

}
