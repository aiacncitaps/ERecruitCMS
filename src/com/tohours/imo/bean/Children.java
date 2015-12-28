package com.tohours.imo.bean;

import java.util.List;
import java.util.UUID;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

@Table("attract_children")
public class Children extends BasePojo{
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
	private Boolean nextFlag;
	@Column
	private String fileIds;
	@Column("resource_id")
	private String resourceId;
	@Many(target=ChildrenNext.class,field="childrenId")
	private List<ChildrenNext> childrenNextList;
	@Column
	private Integer squence;
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
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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
	public List<ChildrenNext> getChildrenNextList() {
		return childrenNextList;
	}
	public void setChildrenNextList(List<ChildrenNext> childrenNextList) {
		this.childrenNextList = childrenNextList;
	}
	public Boolean getNextFlag() {
		return nextFlag;
	}
	public void setNextFlag(Boolean nextFlag) {
		this.nextFlag = nextFlag;
	}
	public Integer getSquence() {
		return squence;
	}
	public void setSquence(Integer squence) {
		this.squence = squence;
	}
	public String getFileIds() {
		return fileIds;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	
}
