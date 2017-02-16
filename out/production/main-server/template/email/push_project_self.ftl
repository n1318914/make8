<html>
<#include "../page/common.ftl">
<head>
	<meta charset="UTF-8">
		
     <style>
	 * {
    margin: 0;
    padding: 0;
}

a {
    text-decoration: none;
}

body {
    background: #FAFAFA;
}

.wbm-items {
    width: 100%;
    height: 150px;
}

.new-item {
    width: 700px;
    height: 150px;
    margin: 0 auto;
    background: url("${ctx}/img/new-item.gif");
	box-shadow: 5px 1px 10px #666;
}

.new-item img {
    padding: 120px 0 0 300px;
}

.wbm-item-main {
    width:700px;
    margin: 0 auto;
    border: 1px solid #EEE;
	box-shadow: 5px 5px 10px #666;
}

.item-names {
    height: 75px;
    width: 90%;
    margin: 20px auto;
    border-bottom: 2px solid #F1F1F1;
    text-align:center;
}

.wbm-jl {
    margin-left: 33px;
}

.item-names p {
    line-height: 75px;
    font-size: 18px;
    font-family: "微软雅黑";
    color: #3498DB;

}

.wbm-item-tit {
    color: #3498DB;
    font-family: "微软雅黑";
    font-size: 16px;
    padding: 20px 30px 0 30px;
}

.wbm-iname {
    color: #9E9E9E;
    padding-left: 30px;
}

.item-shows {
    width: 450px;
    height: auto;
    /*margin: -20px 0 0 80px;*/
    display: block;
}

.item-ask {
    width: 450px;
    height: auto;
    margin: -20px 0 0 110px;
    display: block;
}
.wbm-case {
    padding: 0px 0 0 30px;
    font-family: "微软雅黑";
    font-size: 16px;
    color: #3498DB;
}

.wbm-case a {
    text-decoration: none;
}

.wbm-cont {
    padding: 0px 0 0 30px;
    font-family: "微软雅黑";
    font-size: 16px;
    color:#000;
}

.wbm-maifoot .wbm-reply {
    padding:40px 0 0 30px;
    float: left;
}

.wbm-reply p {
    color: #9E9E9E;
    font-family: "微软雅黑";
    font-size: 16px;
}

.wbm-maifoot .wbm-link {
    float: right;
    padding: 40px 30px 0 0;
}

.wbm-link a {
    padding-left: 15px;
}

.faifoot {
    width: 100%;
    height: 130px;
    background: red;
}

.wbm-weixins {
    height: 85px;
    width: 200px;
    border: 1px solid #3498DB;
    background:#D7EBF9;
}

.wbm-weixins img {
    float: left;
    padding: 5px 0 0 5px;
}

.wbm-weixins p {
    float: left;
    font-size: 13px;
    padding:15px 0 0 8px;
}

.footimg img{
    margin-top: 25px;
}

.wbm-find {
    width: 100%;
    height: auto;
}

.wbm-findth {
    width: 700px;
    height: auto;
    margin: 0 auto;
    box-shadow: 10px 10px 15px #666;
}

.divider{
  	width: 90%;
    margin: 20px auto;
    border-bottom: 2px solid #F1F1F1;
}

	 </style>
</head>
<body style="background: #FAFAFA;">
<div class="wbm-items">
        <div class="new-item">
            <img src="${ctx}/img/itembann.gif">
        </div>
    </div>

    <div class="item-main">
        <div class="wbm-item-main">
            <div class="item-names">
                <p>${projectVo.name}</p>
                </div>

          <div class="wbm-item-type">
          <p class="wbm-item-tit wbm-jl">需求类型：<span class="wbm-iname" >${projectVo.type}</span></p>
          <p class="wbm-item-tit wbm-jl">项目预算：<span class="wbm-iname">${projectVo.budget}</span></p>
          <p class="wbm-item-tit wbm-jl">需求描述：
	         <!-- <span class="wbm-iname item-shows">
	          	${projectVo.content}
	          </span>-->
          </p>
          <div style="margin-left:65px;">
			${projectVo.content}
          </div>
          
            <div class="divider"></div>
            <p class="wbm-case"><a href="${viewUrl}">点此查看详情</a></p>
            <p class="wbm-cont">
            	为您筛选匹配到的项目，登陆参与后码客帮将与您取得联系
            </p>      
            </div>

            <div class="wbm-maifoot">
                <div class="wbm-reply">
                    <p>本邮件由码客帮发出，请勿直接回复</p>
                    <p>如果有任何疑问或建议，请联系我们</p>
                    <p>4000-818-530</p>
                </div>
            <div class="wbm-link">
                <a href="http://weibo.com/makebang"><img src="${ctx}/img/wbm-weibo.gif"></a>
                <img src="${ctx}/img/wbm-weixintt.gif">
                <div class="wbm-weixins">
                    <img src="${ctx}/img/wbm-weixin.gif">
                    <p>扫一扫,关注码客帮</p>
                    <p>订阅号:make8com</p>
                    </div>
                </div>
             </div>
                <div class="footimg">
                    <img src="${ctx}/img/footimg.gif">
                </div>
          </div>
      </div>
  </body>
</html>