package com.tohours.imo.bean;

import java.util.List;
import java.util.UUID;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

@Table("attract_talent")
public class Talent extends BasePojo{
	public  String uuid(){
        return UUID.randomUUID().toString();
    }
	@Name
	@Prev(els={@EL("$me.uuid()")})
	private String id;
	@Column
	private String name;//名字
	@Column
	private String sex;//性别
	@Column
	private String birthday;//生日
	@Column("agent_code")
	private String agentCode;
	@Column("agent_id")
	private String agentId;
	@One(target = Agent.class, field = "agentId")
	private Agent agent;
	@Column("chat_time")
	private String chatTime;
	@Column("sub_company_id")//分公司
	private String subCompanyId;
	@Column("region")//地区
	private String region;
	@Many(target = Report.class, field = "talentId")//report集合，一对多的关系
	private List<Report> reportList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public List<Report> getReportList() {
		return reportList;
	}
	public void setReportList(List<Report> reportList) {
		this.reportList = reportList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChatTime() {
		return chatTime;
	}
	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getSubCompanyId() {
		return subCompanyId;
	}
	public void setSubCompanyId(String subCompanyId) {
		this.subCompanyId = subCompanyId;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
}
