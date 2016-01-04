package com.tohours.imo.bean;

import java.util.UUID;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

@Table("attract_user")
public class User extends BasePojo {

	public  String uuid(){
        return UUID.randomUUID().toString();
    }
	@Name
	@Prev(els={@EL("$me.uuid()")})
	private String id;
	@Column
	private String name;
	@Column("passwd")
	private String password;
	@Column
	private String salt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
}