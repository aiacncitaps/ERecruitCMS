package com.tohours.imo.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("attract_individuality")
public class Individuality extends BasePojo {
	@Id
	private int agentId;
	@Column("sqe_num")
	private Long sqeNum;

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public Long getSqeNum() {
		return sqeNum;
	}

	public void setSqeNum(Long sqeNum) {
		this.sqeNum = sqeNum;
	}

}
