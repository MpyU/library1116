package com.library.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice implements Serializable {
	public Notice(Integer nid) {
		this.id = nid;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * id int primary key auto_increment comment "ID", message varchar(255)
	 * comment "消息", status int comment "为0的话，就表示全发，不为0就发用户", publish_date date
	 * comment "发布消息时间"
	 */
	private Integer id;
	private String message;
	private Integer status;
	private String publishDate;
	private Integer uid;

	private User user;
}
