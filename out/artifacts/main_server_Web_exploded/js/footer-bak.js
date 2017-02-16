function showFooter(footerfixed){
	var url = $("#topurl").val();
	
	var footerHTML = '<footer class="footer footer-advance';

    if(footerfixed == 1){
    	footerHTML += ' navbar-fixed-bottom';
    }
     
     footerHTML += '">';
     
     footerHTML += ' <div class="container"> ' +
                     '<div>' + 
                     '<ul class="list-inline text-center">' + 
                     '<li><a href="' + url + 'about/aboutus">关于我们</a></li>' +
                     '<li><a href="' + url + 'about/contactus">联系我们</a></li>' +
                     '<li><a href="' + url + 'public/qa">常见问题</a></li>' +
					 '<li><a href="' + url + 'about/contract">服务协议</a></li> ' + 
                     '<li><a href="http://blog.waibao.me" target=_blank>官方博客</a></li> ' + 
                     '<li><a href="' + url  + 'about/flink">友情链接</a></li> ' +
                     '</ul>' +
                     '<ul class="list-inline text-center">' +
                     '<!--<li class="divider"></li>-->' +
                     '<li><p>深圳市云达人科技有限公司 粤ICP备15083138号-2 &copy; 2015</p></li>' +
					 //'<p><img src="' + url + '../img/footer_logo.png"></p>' +
                     '</ul>' +
                     '<ul class="list-inline text-center">' +
                     '<li><a key="56443675efbfb05d87e1f95b" href="http://www.anquan.org/authenticate/cert/?site=waibao.me&at=enterprise" target="_blank">' +
                     '<img style="width:74px;height:27px;" src="' + url + 'img/qy_83x30.png"/></a></li>' + 
					 '<li><a href="http://webscan.360.cn/index/checkwebsite/url/waibao.me" target="_blank"><img border="0" src="http://img.webscan.360.cn/status/pai/hash/604e2bb2c85092c4a91f7c64bf8f4cb5/?size=74x27"/></a></li>' +					 
                     '</ul>' +
                     '</div>' +
                     '</div>';
	 	
	 	 footerHTML += '</footer>';
	     //alert(footerHTML);
					                
        $('#footerDiv').html(footerHTML);
}
}