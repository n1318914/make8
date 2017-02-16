package com.yundaren.support.biz;

import java.util.List;

import lombok.Setter;

import org.springside.modules.mapper.BeanMapper;

import com.yundaren.support.dao.TradeDao;
import com.yundaren.support.po.TradeInfoPo;
import com.yundaren.support.vo.TradeInfoVo;

public class TradeBiz {

	@Setter
	private TradeDao tradeDao;

	public long addTradeInfo(TradeInfoVo tradeVo) {
		TradeInfoPo tradePo = BeanMapper.map(tradeVo, TradeInfoPo.class);
		return tradeDao.addTradeInfo(tradePo);
	}

	public TradeInfoVo getTradeInfoByPID(String pid) {
		TradeInfoVo tradeVo = null;
		TradeInfoPo tradePo = tradeDao.getTradeInfoByPID(pid);
		if (tradePo != null) {
			tradeVo = BeanMapper.map(tradePo, TradeInfoVo.class);
		}
		return tradeVo;
	}

	public long updateTradeInfo(TradeInfoVo tradeVo) {
		TradeInfoPo tradePo = BeanMapper.map(tradeVo, TradeInfoPo.class);
		return tradeDao.updateTradeInfo(tradePo);
	}

	// 获取平台项目成交总金额
	public double getTotalProjectAmount() {
		return tradeDao.getTotalProjectAmount();
	}

	// 获取平台项目交易总金额
	public double getTransactionAmountList() {
		double totalAmount = 0;
		List<String> listPrice = tradeDao.getTransactionAmountList();
		for (String rangePrice : listPrice) {
			totalAmount += translateRange2Fix(rangePrice);
		}
		return totalAmount;
	}

	// 平台现在用的是范围价格，计算交易金额取范围中间值
	private double translateRange2Fix(String rangePrice) {
		double fixPrice = 0;
		if ("100-1000元".equals(rangePrice)) {
			fixPrice = 500;
		} else if ("1000-5000元".equals(rangePrice)) {
			fixPrice = 3000;
		} else if ("5000-10000元".equals(rangePrice)) {
			fixPrice = 8000;
		} else if ("10000-30000元".equals(rangePrice)) {
			fixPrice = 20000;
		} else if ("3万-5万元".equals(rangePrice)) {
			fixPrice = 40000;
		} else if ("5万-10万".equals(rangePrice)) {
			fixPrice = 80000;
		} else if ("10万以上".equals(rangePrice)) {
			fixPrice = 100000;
		} else {
			// 待商议默认1.5W
			fixPrice = 15000;
		}
		return fixPrice;
	}
}
