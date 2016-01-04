package com.tohours.imo.bean;

import java.util.List;
import java.util.UUID;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;
@Table("attract_top_excellence")
public class TopExcellence extends BasePojo{
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
	@Column
	private Boolean deleteFlag;
	@Many(target = SubExcellence.class,field="top_excel_id")
	private List<SubExcellence> subExceList;
	@Column
	private Integer squence;//序列号
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
	public List<SubExcellence> getSubExceList() {
		return subExceList;
	}
	public void setSubExceList(List<SubExcellence> subExceList) {
		this.subExceList = subExceList;
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
