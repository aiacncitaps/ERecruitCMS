package com.tohours.imo.bean;

import java.util.UUID;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;
@Table("attract_peoples")
public class Peoples extends BasePojo{
	public  String uuid(){
        return UUID.randomUUID().toString();
    }
	@Name
	@Prev(els={@EL("$me.uuid()")})
	private String id;
	@Column
	private String name;
	@Column("join_date")
	private String joinDate;
	@Column("old_job")
	private String oldJob;
	@Column("share_word")
	private String shareWord;
	@Column
	private String fileId;
	@Column
	private String filePath;
	@Column
	private String fileName;
	@Column
	private String fileType;
	@Column
	private String contentType;
	@Column("old_mark")
	private String oldMark;
	@Column("new_mark")
	private String newMark;
	@Column
	private String sub_excel_id;
	@Column
	private Boolean deleteFlag;
	@Column("sub_company")
	private String subCompany;
	@Column
	private String marks;
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
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getOldJob() {
		return oldJob;
	}
	public void setOldJob(String oldJob) {
		this.oldJob = oldJob;
	}
	public String getOldMark() {
		return oldMark;
	}
	public void setOldMark(String oldMark) {
		this.oldMark = oldMark;
	}
	public String getNewMark() {
		return newMark;
	}
	public void setNewMark(String newMark) {
		this.newMark = newMark;
	}
	public String getShareWord() {
		return shareWord;
	}
	public void setShareWord(String shareWord) {
		this.shareWord = shareWord;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getSub_excel_id() {
		return sub_excel_id;
	}
	public void setSub_excel_id(String sub_excel_id) {
		this.sub_excel_id = sub_excel_id;
	}
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getSubCompany() {
		return subCompany;
	}
	public void setSubCompany(String subCompany) {
		this.subCompany = subCompany;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public Integer getSquence() {
		return squence;
	}
	public void setSquence(Integer squence) {
		this.squence = squence;
	}
	
}
