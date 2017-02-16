package com.yundaren.seo.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Data;
import com.yundaren.seo.vo.SeoItemVo;

@Data

public abstract class Node {
	protected long id;
	protected String urlId;
	protected String name;
	protected List<SeoItemVo> items;
	protected List<Node> childNodes;
	protected List<Node> siblingNodes;
	protected int seoItemStartIndex;
	protected int seoItemEndIndex;
	
	public void addChild(Node childNode){
		if(childNodes == null){
			childNodes = new ArrayList<Node>();
		}
		
		childNodes.add(childNode);
	}
	
	public void addSiblingNode(Node siblingNode){
		if(siblingNodes == null){
			siblingNodes = new ArrayList<Node>();
		}
		
		siblingNodes.add(siblingNode);
	}
	public Node getChildNodeByUrlId(String urlId){
		if(urlId == null || urlId.isEmpty()){
			return null;
		}
		
		Iterator<Node> childNodeIr = this.childNodes.iterator();
		
		while(childNodeIr.hasNext()){
			Node childNode = childNodeIr.next();
			
			if(childNode.urlId.equals(urlId)){
				return childNode;
			}
		}
		
		return null;
	}
	
	public Node getSiblingNodeByUrlId(String urlId){
		if(urlId == null || urlId.isEmpty()){
			return null;
		}
		
		Iterator<Node> siblingNodeIr = this.siblingNodes.iterator();
		
		while(siblingNodeIr.hasNext()){
			Node siblingNode = siblingNodeIr.next();
			
			if(siblingNode.urlId.equals(urlId)){
				return siblingNode;
			}
		}
		
		return null;
	}
}
