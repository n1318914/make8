package com.yundaren.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yundaren.basedata.service.RegionService;
import com.yundaren.basedata.vo.CityVo;
import com.yundaren.basedata.vo.DistrictVo;
import com.yundaren.basedata.vo.ProvinceVo;
import com.yundaren.basedata.vo.RegionVo2;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.constants.SeoConstants;
import com.yundaren.seo.service.SeoService;
import com.yundaren.seo.service.impl.ChildNode;
import com.yundaren.seo.service.impl.Node;
import com.yundaren.seo.service.impl.RootNode;
import com.yundaren.seo.service.impl.SiblingNode;
import com.yundaren.seo.vo.SeoItemVo;

@Controller
@Slf4j
public class SeoController implements InitializingBean {
	@Setter 
	private RegionService regionService;
	@Setter 
	private SeoService seoService;
	
	private RootNode rootNode;
	
	private final int SILBING_NODES = SeoConstants.APP_TYPES.size();;
	private final int ITEMS_IN_PAGE = 8;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		//initSeoStructure();
	}
	
	
	/**
	 * 初始化SEO的结构
	 */
	private void initSeoStructure(){		
		if(rootNode != null && rootNode.getChildNodes().size() > 0){
			return;
		}
		
		int allSeoItemCount = seoService.getAllSeoItemCount();
		
		rootNode = new RootNode();
		
		//遍历所有的省
		List<ProvinceVo> provinceList = regionService.getAllProvinces2();
		
		Iterator<ProvinceVo> irProvince = provinceList.iterator();
		int index = 1;
		int superIndex = 1;
		int itemStartIndex = 0;
		
		while(irProvince.hasNext()){
			ProvinceVo po = irProvince.next();
			
			ChildNode provinceNode = new ChildNode();
			provinceNode.setId(index);
			rootNode.addChild(provinceNode);
			provinceNode.setUrlId(rootNode.getUrlId() + index);
			provinceNode.setName(po.getName());
			
			if(superIndex > allSeoItemCount){
				itemStartIndex = superIndex % allSeoItemCount;
			}
			
			//provinceNode.setItems(seoService.getSeoItemByRangId(itemStartIndex, itemStartIndex + ITEMS_IN_PAGE));
			provinceNode.setSeoItemStartIndex(itemStartIndex);
			provinceNode.setSeoItemEndIndex(itemStartIndex + ITEMS_IN_PAGE);
			
			superIndex = superIndex + ITEMS_IN_PAGE;
			
			//添加姐妹节点
			for(int i = 2; i <= SILBING_NODES;i++){
				Node siblingNode = new SiblingNode();
				siblingNode.setId(i);
				siblingNode.setName(po.getName() + SeoConstants.APP_TYPES.get(i-1));
				siblingNode.setUrlId(provinceNode.getUrlId() + "-" + i);
				provinceNode.addSiblingNode(siblingNode);
				
				if(superIndex > allSeoItemCount){
					itemStartIndex = superIndex % allSeoItemCount;
				}else{
					itemStartIndex = superIndex;
				}
				
				//System.out.println("startIndex:" + itemStartIndex);
				//siblingNode.setItems(seoService.getSeoItemByRangId(itemStartIndex, itemStartIndex + ITEMS_IN_PAGE));
				siblingNode.setSeoItemStartIndex(itemStartIndex);
				siblingNode.setSeoItemEndIndex(itemStartIndex + ITEMS_IN_PAGE);
				

				superIndex = superIndex + ITEMS_IN_PAGE;
			}

			//遍历每个省里面的市
			List<CityVo> cityVoList = regionService.getCitiesByProvinceId(po.getId());
			Iterator<CityVo> irCity = cityVoList.iterator();
			
			int index2 = 1;
			
			while(irCity.hasNext()){
				CityVo co = irCity.next();
				ChildNode cityNode = new ChildNode();
				cityNode.setId(index2);
				cityNode.setName(co.getName());
				provinceNode.addChild(cityNode);
				cityNode.setUrlId(provinceNode.getUrlId() + index2);
				
				if(superIndex > allSeoItemCount){
					itemStartIndex = superIndex % allSeoItemCount;
				}else{
					itemStartIndex = superIndex;
				}
				
				//cityNode.setItems(seoService.getSeoItemByRangId(itemStartIndex, itemStartIndex + ITEMS_IN_PAGE));
				cityNode.setSeoItemStartIndex(itemStartIndex);
				cityNode.setSeoItemEndIndex(itemStartIndex + ITEMS_IN_PAGE);
				superIndex = superIndex + ITEMS_IN_PAGE;
	
				
				//添加姐妹节点
				for(int i = 2; i <= SILBING_NODES;i++){
					Node siblingNode = new SiblingNode();
					siblingNode.setId(i);
					siblingNode.setName(co.getName() + SeoConstants.APP_TYPES.get(i-1));
					siblingNode.setUrlId(cityNode.getUrlId() + "-" + i);
					cityNode.addSiblingNode(siblingNode);
					
					if(superIndex > allSeoItemCount){
						itemStartIndex = superIndex % allSeoItemCount;
					}else{
						itemStartIndex = superIndex;
					}
					
					//siblingNode.setItems(seoService.getSeoItemByRangId(itemStartIndex, itemStartIndex + 5));
					siblingNode.setSeoItemStartIndex(itemStartIndex);
					siblingNode.setSeoItemEndIndex(itemStartIndex + ITEMS_IN_PAGE);
					superIndex = superIndex + ITEMS_IN_PAGE;
				}
		
				
				//遍历每个市里面的区
				List<DistrictVo> districtList = regionService.getDistrictsByCityId(co.getId());
				Iterator<DistrictVo> irDistrict = districtList.iterator();
				
				int index3 = 1;
				
				while(irDistrict.hasNext()){
					DistrictVo dv = irDistrict.next();
					ChildNode districtNode = new ChildNode();
					districtNode.setId(index2);
					districtNode.setName(dv.getName());
					cityNode.addChild(districtNode);
					districtNode.setUrlId(cityNode.getUrlId() + index3);
					
					if(superIndex > allSeoItemCount){
						itemStartIndex = superIndex % allSeoItemCount;
					}else{
						itemStartIndex = superIndex;
					}
					
					//districtNode.setItems(seoService.getSeoItemByRangId(itemStartIndex, itemStartIndex + 5));
					districtNode.setSeoItemStartIndex(itemStartIndex);
					districtNode.setSeoItemEndIndex(itemStartIndex + ITEMS_IN_PAGE);
					
					superIndex = superIndex + ITEMS_IN_PAGE;
					
					//添加姐妹节点
					for(int i = 2; i <= SILBING_NODES;i++){
						Node siblingNode = new SiblingNode();
						siblingNode.setId(i);
						siblingNode.setName(dv.getName() + SeoConstants.APP_TYPES.get(i-1));
						siblingNode.setUrlId(districtNode.getUrlId() + "-" + i);
						districtNode.addSiblingNode(siblingNode);
						
						if(superIndex > allSeoItemCount){
							itemStartIndex = superIndex % allSeoItemCount;
						}else{
							itemStartIndex = superIndex;
						}
						
						//siblingNode.setItems(seoService.getSeoItemByRangId(itemStartIndex, itemStartIndex + 5));
						siblingNode.setSeoItemStartIndex(itemStartIndex);
						siblingNode.setSeoItemEndIndex(itemStartIndex + ITEMS_IN_PAGE);
						
						superIndex = superIndex + ITEMS_IN_PAGE;
					}
					
					++index3;
				}
				
				++index2;
			}
			
			++index;
		}
	}
	
	public Node getRootNode(){
		return rootNode;
	}
	
	/**
	 * SEO首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = PageForwardConstants.SEO_INDEX, method = RequestMethod.GET)
	public String getSeoIndex(HttpServletRequest request){
		long starTime = System.currentTimeMillis();
		initSeoStructure();
		long endTime = System.currentTimeMillis();
		
		long time = endTime - starTime;
		
		log.info("initialize seo structure in " + time + "ms");
		List<Node> provinceNodes =  rootNode.getChildNodes();
		
		request.setAttribute("provinceNodes", provinceNodes);
		
		return "infos/index";
	}
	
	/**
	 * SEO省份页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = PageForwardConstants.SEO_PROVINCE, method = RequestMethod.GET)
	public String getSeoProvince(HttpServletRequest request,String urlId){
		initSeoStructure();
		
		if(urlId == null && urlId.trim().isEmpty()){
			return null;
		}
		
		boolean isSibling = false;
		String pUrlId = "";
		
		//parse the right id from urlId,for example "02-1"
		if(urlId.indexOf("-") >= 0){
			pUrlId = urlId.substring(0,urlId.indexOf("-"));
			isSibling = true;
		}else{
			pUrlId = urlId;
		}
		
		
		Node provinceNode =  rootNode.getChildNodeByUrlId(pUrlId);
		List<Node> cityNodes = provinceNode.getChildNodes();
		List<Node> siblingNodes = provinceNode.getSiblingNodes();
		Node siblingNode = null;
		
		if(isSibling){
			siblingNode = provinceNode.getSiblingNodeByUrlId(urlId);
			provinceNode = siblingNode;
		}
		
		if(provinceNode.getItems() == null || provinceNode.getItems().size() == 0){
			provinceNode.setItems(seoService.getSeoItemByRangId(provinceNode.getSeoItemStartIndex(), provinceNode.getSeoItemEndIndex()));
		}
		
		request.setAttribute("provinceNode", provinceNode);
		request.setAttribute("cityNodes", cityNodes);
		request.setAttribute("siblingNodes", siblingNodes);
		
		return "infos/province";
	}
	

	/**
	 * SEO市页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = PageForwardConstants.SEO_CITY, method = RequestMethod.GET)
	public String getSeoCity(HttpServletRequest request,String pUrlId,String urlId){
		initSeoStructure();
		
		//parse the right id from urlId,for example "02-1"
		if(pUrlId.indexOf("-") >= 0){
			pUrlId = pUrlId.substring(0,pUrlId.indexOf("-"));
		}
		
		boolean isSibling = false;
		String cUrlId = "";
		
		//parse the right id from urlId,for example "02-1"
		if(urlId.indexOf("-") >= 0){
			cUrlId = urlId.substring(0,urlId.indexOf("-"));
			isSibling = true;
		}else{
			cUrlId = urlId;
		}
		
		Node provinceNode =  rootNode.getChildNodeByUrlId(pUrlId);
		Node cityNode = provinceNode.getChildNodeByUrlId(cUrlId);
		List<Node> districtNodes = cityNode.getChildNodes();
		List<Node> siblingNodes = cityNode.getSiblingNodes();
		Node siblingNode = null;
		
        if(isSibling){
        	siblingNode = cityNode.getSiblingNodeByUrlId(urlId);
        	cityNode = siblingNode;
        }
		
        if(cityNode.getItems() == null || cityNode.getItems().size() == 0){
        	cityNode.setItems(seoService.getSeoItemByRangId(cityNode.getSeoItemStartIndex(), cityNode.getSeoItemEndIndex()));
        }
		
		request.setAttribute("provinceNode",provinceNode);
		request.setAttribute("cityNode", cityNode);
		request.setAttribute("districtNodes", districtNodes);
		request.setAttribute("siblingNodes", siblingNodes);
		
		return "infos/city";
	}
	
	/**
	 * SEO市区页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = PageForwardConstants.SEO_DISTRICT, method = RequestMethod.GET)
	public String getSeoDistrict(HttpServletRequest request,String pUrlId,String cUrlId,String urlId){
		initSeoStructure();
		
		//parse the right id from urlId,for example "02-1"
		if(pUrlId.indexOf("-") >= 0){
			pUrlId = pUrlId.substring(0,pUrlId.indexOf("-"));
		}
		
		if(cUrlId.indexOf("-") >= 0){
			cUrlId = cUrlId.substring(0,cUrlId.indexOf("-"));
		}
		
		boolean isSibling = false;
		String dUrlId = "";
		
		//parse the right id from urlId,for example "02-1"
		if(urlId.indexOf("-") >= 0){
			isSibling = true;
			dUrlId = urlId.substring(0,urlId.indexOf("-"));
		}else{
			dUrlId = urlId;
		}
		
		Node provinceNode = rootNode.getChildNodeByUrlId(pUrlId);
		Node cityNode = provinceNode.getChildNodeByUrlId(cUrlId);
		List<Node> districtNodes = cityNode.getChildNodes();
		
		Node districtNode = cityNode.getChildNodeByUrlId(dUrlId);
		List<Node> siblingNodes = districtNode.getSiblingNodes();
		
		//其他区
		List<Node> otherDistrictNodes = new ArrayList<Node>();
		
		Iterator<Node> districtIr = districtNodes.iterator();
		
		while(districtIr.hasNext()){
			Node tmpDistrictNode = districtIr.next();
			
			if(tmpDistrictNode != null && !tmpDistrictNode.getUrlId().equals(urlId)){
				otherDistrictNodes.add(tmpDistrictNode);
			}
		}
		
		Node siblingNode = null;
		
        if(isSibling){
        	siblingNode = districtNode.getSiblingNodeByUrlId(urlId);
        	districtNode = siblingNode;
        }
		
        if(districtNode.getItems() == null || districtNode.getItems().size() == 0){
        	districtNode.setItems(seoService.getSeoItemByRangId(districtNode.getSeoItemStartIndex(), districtNode.getSeoItemEndIndex()));
        }
		
		
		request.setAttribute("provinceNode",provinceNode);
		request.setAttribute("cityNode", cityNode);
		request.setAttribute("districtNode", districtNode);
		request.setAttribute("otherDistrictNodes", otherDistrictNodes);
		request.setAttribute("siblingNodes",siblingNodes);
		
		return "infos/district";
	}

}
