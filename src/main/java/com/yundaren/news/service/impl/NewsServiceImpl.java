package com.yundaren.news.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import lombok.Setter;

import com.yundaren.common.util.HttpProtocolUtil;
import com.yundaren.news.service.NewsService;
import com.yundaren.news.vo.NewsVo;
import com.yundaren.support.config.PublicConfig;

public class NewsServiceImpl implements NewsService {
    @Setter 
    PublicConfig publicConfig;
    
	@Override
	public List<NewsVo> getNews() {
		// TODO Auto-generated method stub
		
		String newsSource = publicConfig.getNewsSource();
		int refreshRate = publicConfig.getNewsRefreshRate();
		int displayItemCount = publicConfig.getNewsDisplayItemCount();
		
		String content = HttpProtocolUtil.getContent(newsSource, new HashMap<String, String>(), "GET");
		List<String> titlesList = new ArrayList<String>();
		List<String> linksList = new ArrayList<String>();
		List<String> dateList = new ArrayList<String>();
		
		parseNewsContent(content,titlesList,linksList,dateList);
		NewsVo newsVo;
		List<NewsVo> newsVos = new ArrayList<NewsVo>();
		
		for(int i = 0; i < titlesList.size(); i++){
			if(i > displayItemCount - 1){
				break;
			}
			
			newsVo = new NewsVo();
			
			newsVo.setTitle(titlesList.get(i).trim());
			newsVo.setLinkUrl(linksList.get(i).trim());
			newsVo.setPubDate(dateList.get(i).trim());
			newsVos.add(newsVo);
		}
	    
		return newsVos;
	}
	
	private void parseNewsContent(String content,List<String> titles,List<String> links,List<String> dateList){
		if(content == null || content.trim().isEmpty()){
			return;
		}
		
		boolean isInItemArea = false;
		
		String[] contents = content.split("\n");
		
		String lineContent = "";
		
		for(int i = 0; i < contents.length; i++){
			lineContent = contents[i];
			
			if(lineContent != null && !lineContent.trim().isEmpty()){
				if(isInItemArea){
					if(lineContent.contains("<title>")){
						String tmpTitle = lineContent.replaceAll("<title>","");
						tmpTitle = tmpTitle.replaceAll("</title>", "");
						
						titles.add(tmpTitle);
					}else if(lineContent.contains("<link>")){
						String tmpLink = lineContent.replaceAll("<link>","");
						tmpLink = tmpLink.replaceAll("</link>", "");
						
						links.add(tmpLink);
					}else if(lineContent.contains("<pubDate>")){
						String tmpPubDateStr = lineContent.replaceAll("<pubDate>", "");
						tmpPubDateStr = tmpPubDateStr.replaceAll("</pubDate>", "");
						tmpPubDateStr = tmpPubDateStr.trim();
						
					    SimpleDateFormat sdf =   new SimpleDateFormat( "EEE, dd MMM yyyy HH:mm:ss Z",Locale.US);
					    SimpleDateFormat sdfSimple =   new SimpleDateFormat("yyyy-MM-dd");
					    
					    try {
							Date pubDate = sdf.parse(tmpPubDateStr);
							String simplePubDateStr = sdfSimple.format(pubDate);
							dateList.add(simplePubDateStr);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}   
					}
				}
				if(lineContent.contains("<item>")){
					isInItemArea = true;
				}
				
				if(lineContent.contains("</item>")){
					isInItemArea = false;
				}
			}
		}
	}

}
