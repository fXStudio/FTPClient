package com.expressway.ftp.client.models;

/**
 * @author Ajaxfan
 */
public class MtcModel {
	private String recordNo;
	private String admId;
	private String tagid;
	private String date;
	private String code;

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getAdmId() {
		return admId;
	}

	public void setAdmId(String admId) {
		this.admId = admId;
	}

	public String getTagid() {
		return tagid;
	}

	public void setTagid(String tagid) {
		this.tagid = tagid;
	}

	public String getDate() {
		return date != null ? date.replaceAll("/", "") : date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
