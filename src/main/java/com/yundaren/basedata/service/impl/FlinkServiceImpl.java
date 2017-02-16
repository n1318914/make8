package com.yundaren.basedata.service.impl;

import java.util.List;

import lombok.Setter;

import com.yundaren.basedata.biz.FlinkBiz;
import com.yundaren.basedata.service.FlinkService;
import com.yundaren.basedata.vo.FlinkVo;

public class FlinkServiceImpl implements FlinkService {

	@Setter
	private FlinkBiz flinkBiz;

	@Override
	public List<FlinkVo> getAllFlink() {
		return flinkBiz.getAllFlink();
	}

	@Override
	public void addFlink(FlinkVo flinkVo) {
		flinkBiz.addFlink(flinkVo);
	}

	@Override
	public void deleteFlink(int id) {
		flinkBiz.deleteFlink(id);
	}

	@Override
	public void modifyFlink(FlinkVo flinkVo) {
		flinkBiz.modifyFlink(flinkVo);
	}

}
