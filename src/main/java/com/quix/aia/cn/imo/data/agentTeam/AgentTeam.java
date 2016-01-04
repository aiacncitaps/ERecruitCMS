package com.quix.aia.cn.imo.data.agentTeam;

public class AgentTeam {
	private String co;
	private String city;
	private String ssc;
	private String office;
	private String teamcode;
	private String teamName;
	private String teamstatus;
	
	
	
	public AgentTeam(){
		co="";
		city="";
		ssc="";
		office="";
		teamcode="";
		teamName="";
		teamstatus="";
		
				
	}
	
	public String getCo() {
		return co;
	}
	public void setCo(String co) {
		this.co = co;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSsc() {
		return ssc;
	}
	public void setSsc(String ssc) {
		this.ssc = ssc;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getTeamcode() {
		return teamcode;
	}
	public void setTeamcode(String teamcode) {
		this.teamcode = teamcode;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamstatus() {
		return teamstatus;
	}
	public void setTeamstatus(String teamstatus) {
		this.teamstatus = teamstatus;
	}
	

}
