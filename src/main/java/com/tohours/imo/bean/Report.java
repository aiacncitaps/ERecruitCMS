package com.tohours.imo.bean;

import java.util.UUID;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

@Table("attract_report")
public class Report extends BasePojo{
	public  String uuid(){
        return UUID.randomUUID().toString();
    }
	@Name
	@Prev(els={@EL("$me.uuid()")})
	private String id;
	@Column("talent_id")
	private String talentId;
	@Column
	private byte[] data;
	@Column("file_name")
	private String fileName;
	@Column("image_url")
	private String imageUrl;
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTalentId() {
		return talentId;
	}
	public void setTalentId(String talentId) {
		this.talentId = talentId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
