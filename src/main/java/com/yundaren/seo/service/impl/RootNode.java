package com.yundaren.seo.service.impl;

import lombok.Data;

@Data
public class RootNode extends Node{
	public RootNode(){
		this.id = 0;
		this.urlId = "0";
		this.name = "ROOT";
	}
}
