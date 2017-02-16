package com.yundaren.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;

import com.mysql.fabric.xmlrpc.base.Array;
import com.yundaren.basedata.service.DictService;
import com.yundaren.basedata.service.RegionService;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.basedata.vo.RegionVo;


@Slf4j
public class LabelUtil implements InitializingBean {

	@Setter
	private static DictService dictService;
	
	@Setter
	private static RegionService regionService;

	@Override
	public void afterPropertiesSet() throws Exception {
		// 初始化标签数据
		dictService.getAllDictItem();
	}

	/**
	 * 返回标签名
	 * 
	 * @param type
	 *            标签类型
	 * @param ids
	 *            ID字符串(1,2,3,4)
	 * @return ID对应的名称数组
	 */
	public static String[] getItemName(String type, String ids) {
		if(ids==null)return new String[]{""};
		List<DictItemVo> listItem = dictService.getAllDictItem();
		List<String> listName = new ArrayList<String>();	
		List<String> items = Arrays.asList(ids.split(","));
		for (DictItemVo itemVo : listItem) {
			// 如果标签ID匹配
			if (itemVo.getType().equalsIgnoreCase(type) && items.contains(String.valueOf(itemVo.getValue()))) {
				listName.add(itemVo.getName());
			}
		}
		if(listName.size()==0)return new String[]{""};
		return listName.toArray(new String[] {});
	}
	
	/**
	 * 返回标签名
	 * 
	 * @param type
	 *            标签类型
	 * @param ids
	 *            ID字符串1
	 * @return ID对应的名称数组
	 */
	public static String getSingleItemName(String type, String id) {
		String[] itemNames = getItemName(type,id);
		String itemName = "";
		
		if(itemNames != null && itemNames.length >= 1){
			itemName = itemNames[0];
		}
		
		return itemName;
	}
	
	public static List<RegionVo> getRegionList() {
		List<RegionVo> listProvince = regionService.getAllProvinces();
		List<RegionVo> listAllCity = regionService.getListAll();
		for (RegionVo province : listProvince) {
			for (RegionVo city : listAllCity) {
				if (province.getId() == city.getParentId()) {
					province.getCites().add(city);
				}
			}
		}
		return listProvince;
	}

	public static String[] getItemNamesByType(String type){
		if(type == null || type.isEmpty()){
			return null;
		}
		
		List<DictItemVo> listItem = dictService.getAllDictItem();
		List<String> listName = new ArrayList<String>();
		
		for (DictItemVo itemVo : listItem) {
			if(itemVo.getType().equalsIgnoreCase(type)){
				listName.add(itemVo.getName());
			}
		}
		
		return listName.toArray(new String[] {});
	}
}
