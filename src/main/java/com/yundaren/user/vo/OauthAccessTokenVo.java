package com.yundaren.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OauthAccessTokenVo implements Serializable{
	private static final long serialVersionUID = 4815943116938644760L;
    private String userName;
    private String clientId;
}
