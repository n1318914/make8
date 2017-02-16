<html lang="zh-CN">
<#include "../page/common.ftl">
<head>
    <style>
/*找回密码*/
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
}

.new-item img {
    padding: 120px 0 0 300px;
}

.wbm-item-main {
    width:700px;
    height: 750px;
    margin: 0 auto;
    border: 1px solid #EEE;
}

.item-names {
    height: 75px;
    width: 90%;
    margin: 0 auto;
    border-bottom: 2px solid #F1F1F1;
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
    margin: -20px 0 0 80px;
    display: block;
}

.item-ask {
    width: 450px;
    height: auto;
    margin: -20px 0 0 110px;
    display: block;
}


.wbm-case {
    padding: 60px 0 0 170px;
    font-family: "微软雅黑";
    font-size: 16px;
    color: #3498DB;
}

.wbm-case a {
    text-decoration: none;
}

.wbm-cont {
    padding: 20px 0 0 170px;
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
    height: 550px;
}

.wbm-findth {
    width: 700px;
    height: 550px;
    margin: 0 auto;
    box-shadow: 5px 5px 10px #666;
}

.wbm-findtit{
    width: 700px;
    height: 95px;
    background: #3498DB;
}

.wbm-findtit img {
    float: left;
    padding: 30px 0 0 30px;
}

.wbm-findtit p {
    float: left;
    line-height: 95px;
    font-family: "微软雅黑";
    font-weight: bold;
    font-size: 2px;
    color: white;
    padding-left: 10px;
}

.wbm-url {
    font-size: 12px;
    width: 620px;
    height: 40px;
    background: #EDF7FD;
}

.now-find {
    width: 115px;
    height: 35px;
    background: #3498DB;
    border-radius: 5px;
    margin: 15px 0 0 60px;
    color: white;
    font-size: 16px;
    font-family: "微软雅黑";
}

.wbm-matter {
    height: 280px;
    width: 100%;
}

.wbm-matter {
    font-family: "微软雅黑";
    font-size: 15px;
}

.wbm-main1 {
    padding: 20px 0 0 40px;
}

.wbm-main2 {
    padding: 40px 0 0 60px;
}

.wbm-main3 {
    padding: 10px 0 0 60px;
}

.wbm-url {
    margin: 10px 0 0 60px;
    background: #EDF7FD;
}

.wbm-url p {
    line-height: 40px;
    padding-left: 10px;
    color: #3498DB;
}

.wbm-conpy {
    padding: 10px 50px 0 60px;
}

	</style>
</head>
<body>
<div class="wbm-find">
    <div class="wbm-findth">
        <div class="wbm-findtit">
            <img src="${ctx}/img/wbm-tit.gif">
            <p>邮箱验证</p>
        </div>
        <div class="wbm-matter">
            <p class="wbm-main1">尊敬的会员，欢迎来到码客帮！</p>
            <p class="wbm-main2"></p>
            <p class="wbm-main3">感谢你注册码客帮平台，请点击下面的链接完成邮箱验证:</p>
            <div class="wbm-url">
                <a href="${activeUrl}"><p>${activeUrl}</p></a>
            </div>
            <p class="wbm-conpy">链接将在24小时内失效，如果你无法点击以上链接，请复制网址到浏览器中直接打开。</p>
            <a href="${activeUrl}"><button class="now-find">马上激活</button></a>
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