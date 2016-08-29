package com.expressway.ftp.client.messages;

/**
 * 方法调用后的返回信息
 * 
 * @author FXStudio.Ajaxfan
 */
public final class FeedBackMessage {
	/** 操作是否成功 */
	private boolean success;

	/** 失败信息 */
	private String failureReason;

	/**
	 * 构造函数
	 * 
	 * @param success
	 * @param failureReason
	 */
	public FeedBackMessage(boolean success) {
		this(true, null);
	}

	/**
	 * 构造函数
	 * 
	 * @param success
	 * @param failureReason
	 */
	public FeedBackMessage(boolean success, String failureReason) {
		this.success = success;
		this.failureReason = failureReason;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getFailureReason() {
		return failureReason;
	}
}
