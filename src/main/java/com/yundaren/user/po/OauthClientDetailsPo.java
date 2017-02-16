package com.yundaren.user.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OauthClientDetailsPo implements Serializable {
	private static final long serialVersionUID = -7372540669674036375L;

	private Date createTime;

	private boolean archived;

	private String clientId;

	private String resourceIds;

	private String clientSecret;
	/**
	 * Available values: read,write
	 */
	private String scope;

	/**
	 * grant types include "authorization_code", "password", "assertion", and "refresh_token". Default value
	 * is "authorization_code,refresh_token".
	 */
	private String authorizedGrantTypes = "authorization_code,refresh_token";

	/**
	 * The re-direct URI(s) established during registration (optional, comma separated).
	 */
	private String webServerRedirectUri;

	/**
	 * Authorities that are granted to the client (comma-separated). Distinct from the authorities granted to
	 * the user on behalf of whom the client is acting.
	 * <p/>
	 * For example: ROLE_USER
	 */
	private String authorities;

	/**
	 * The access token validity period in seconds (optional). If unspecified a global default will be applied
	 * by the token services.
	 */
	private Integer accessTokenValidity;

	/**
	 * The refresh token validity period in seconds (optional). If unspecified a global default will be
	 * applied by the token services.
	 */
	private Integer refreshTokenValidity;

	// optional
	private String additionalInformation;

	/**
	 * The client is trusted or not. If it is trust, will skip approve step default false.
	 */
	private boolean trusted;

}