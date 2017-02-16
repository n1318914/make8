package com.yundaren.user.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class OauthAccessTokenPo implements Serializable {
	private static final long serialVersionUID = -5296713773465382816L;
	private String userName;
	private String refreshToken;
	private String tokenId;
	private String clientId;
}
