package com.yundaren.basedata.service;

import java.util.List;

import com.yundaren.basedata.vo.FlinkVo;

public interface FlinkService {

	public List<FlinkVo> getAllFlink();

	public void addFlink(FlinkVo flinkVo);

	public void deleteFlink(int id);

	public void modifyFlink(FlinkVo flinkVo);
}
