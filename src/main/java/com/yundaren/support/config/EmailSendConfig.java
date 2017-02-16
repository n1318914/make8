package com.yundaren.support.config;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

@Data
public class EmailSendConfig implements Cloneable {
	private String host;
	private String port;
	private String userName;
	private String password;
	private String receiver;
	// 密送
	private String bcc;
	private String nick;
	// 发送间隔
	private long interval;
	// 每次群发个数
	private int copies;
	private String mimeType = "text/html";

	public String[] getReceiver() {
		if (!StringUtils.isEmpty(receiver)) {
			if (!receiver.contains(",")) {
				return new String[] {receiver};
			} else {
				return receiver.split(",");
			}
		}
		return null;
	}
	
	public String[] getBcc() {
		if (!StringUtils.isEmpty(bcc)) {
			if (!bcc.contains(",")) {
				return new String[] {bcc};
			} else {
				return bcc.split(",");
			}
		}
		return null;
	}

	@Override
	public EmailSendConfig clone() {
		EmailSendConfig config = null;
		try {
			config = (EmailSendConfig) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return config;
	}
}
