package com.tohours.imo.bean;

import java.util.List;
import java.util.UUID;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;
@Table("attract_sub_excellence")
public class SubExcellence extends BasePojo{
	public  String uuid(){
        return UUID.randomUUID().toString();
    }
	@Name
	@Prev(els={@EL("$me.uuid()")})
	private String id;
	@Column
	private String name;
	@Column
	private Long fileCounts;
	@Column
	private String fileTypes;
	@Column
	private String filePaths;
	@Column
	private String fileNames;
	@Column
	private String contentTypes;
	@Column
	private String fileIds;
	@Many(target = Peoples.class,field="sub_excel_id")
	private List<Peoples> peoplesList;
	@Column
	private String top_excel_id;
	@Column
	private Boolean deleteFlag;
	@Column
	private Integer squence;//序列号
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getFileCounts() {
		return fileCounts;
	}
	public void setFileCounts(Long fileCounts) {
		this.fileCounts = fileCounts;
	}
	public String getFileTypes() {
		return fileTypes;
	}
	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}
	public String getFilePaths() {
		return filePaths;
	}
	public void setFilePaths(String filePaths) {
		this.filePaths = filePaths;
	}
	public String getFileNames() {
		return fileNames;
	}
	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}
	public String getContentTypes() {
		return contentTypes;
	}
	public void setContentTypes(String contentTypes) {
		this.contentTypes = contentTypes;
	}
	public String getFileIds() {
		return fileIds;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Peoples> getPeoplesList() {
		return peoplesList;
	}
	public void setPeoplesList(List<Peoples> peoplesList) {
		this.peoplesList = peoplesList;
	}
	public String getTop_excel_id() {
		return top_excel_id;
	}
	public void setTop_excel_id(String top_excel_id) {
		this.top_excel_id = top_excel_id;
	}
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Integer getSquence() {
		return squence;
	}
	public void setSquence(Integer squence) {
		this.squence = squence;
	}
}
