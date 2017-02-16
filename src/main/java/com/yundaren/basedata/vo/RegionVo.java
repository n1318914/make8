package com.yundaren.basedata.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RegionVo {
	private long id;
	private String name;
	private long parentId;
	private List<RegionVo> cites = new ArrayList<>();
}
