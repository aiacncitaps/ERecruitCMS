package com.tohours.imo.bean;

import java.util.List;
import java.util.UUID;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

@Table("attract_agent")
public class Agent extends BasePojo{
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
	@Column("sub_company_id")
	private String subCompanyId;//分公司
	@Column("region")
	private String region;//地区
	@Column("agent_code")
	private String agentCode;//agentCode
	@Column
	private String password;//密码
	@Column("agent_long_code")
	private String agentLongCode;
	@Column
	private String source;
	@Column("device_num")
	private String deviceNum;//设备号
	@Many(target = Talent.class, field = "agentId")
	private List<Talent> talentList;
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
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSubCompanyId() {
		return subCompanyId;
	}
	public void setSubCompanyId(String subCompanyId) {
		this.subCompanyId = subCompanyId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Talent> getTalentList() {
		return talentList;
	}
	public void setTalentList(List<Talent> talentList) {
		this.talentList = talentList;
	}
	public String getAgentLongCode() {
		return agentLongCode;
	}
	public void setAgentLongCode(String agentLongCode) {
		this.agentLongCode = agentLongCode;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDeviceNum() {
		return deviceNum;
	}
	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	
}
