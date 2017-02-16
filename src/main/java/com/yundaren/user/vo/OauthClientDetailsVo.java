package com.yundaren.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OauthClientDetailsVo implements Serializable {

	private Date createTime ;

    private boolean archived;

    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;
}