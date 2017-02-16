package com.yundaren.basedata.biz;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.collections.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.basedata.dao.FlinkDao;
import com.yundaren.basedata.po.FlinkPo;
import com.yundaren.basedata.vo.FlinkVo;

public class FlinkBiz {

	@Setter
	private FlinkDao flinkDao;

	public List<FlinkVo> getAllFlink() {
		List<FlinkVo> listVo = new ArrayList<FlinkVo>();
		List<FlinkPo> listPo = flinkDao.getAllFlink();
		if (!CollectionUtils.isEmpty(listPo)) {
			listVo = BeanMapper.mapList(listPo, FlinkVo.class);
		}
		return listVo;
	}

	public int addFlink(FlinkVo flinkVo) {
		FlinkPo po = BeanMapper.map(flinkVo, FlinkPo.class);
		return flinkDao.addFlink(po);
	}

	public int deleteFlink(int id) {
		return flinkDao.deleteFlink(id);

	}

	public int modifyFlink(FlinkVo flinkVo) {
		FlinkPo po = BeanMapper.map(flinkVo, FlinkPo.class);
		return flinkDao.modifyFlink(po);
	}

}
