package com.tohours.imo.bean;

import java.util.UUID;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

@Table("attract_setting")
public class Setting extends BasePojo {
	public  String uuid(){
        return UUID.randomUUID().toString();
    }
	@Name
	@Prev(els={@EL("$me.uuid()")})
	private String agentId;
	@Column("sqe_num")
	private Long sqeNum;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public Long getSqeNum() {
		return sqeNum;
	}

	public void setSqeNum(Long sqeNum) {
		this.sqeNum = sqeNum;
	}

}
