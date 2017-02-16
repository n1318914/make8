function drawProjectProgress(divID,pStatusCode,pStepContent,pDate,topURL){
    var ppHTML = "";
    var pStep =  retriveStepInternal(pStatusCode);
    
    /*ppHTML += '<div class="container-fluid">' +
			    '<div class="project-progress">' +
			    	 '<div class="upper-part">' +
			    		'<ul class="list-inline">' + 
			    			'<li id="h1" class="pro-base">1</li>' +
			    			'<li id="b1" class="bar"></li>' + 
			    			'<li id="h2" class="pro-base">2</li>' +
			    			'<li id="b2" class="bar"></li>' +
			    			'<li id="h3" class="pro-base">3</li>' + 
			    			'<li id="b3" class="bar"></li>' +
			    			'<li id="h4" class="pro-base">4</li>' + 
			    			'<li id="b4" class="bar"></li>' +
			    			'<li id="h5" class="pro-base">5</li>' +
			    		'</ul>' +
			    		'</div>' + 
			    		'<div class="content-part">' + 
				    		'<ul class="list-inline">' +
				    	     generateProgressContent(pStep,pStepContent,pDate) + 
				    		'</ul>' +
			    		'</div>' +
				    '</div>' +
				   '</div>';
   
  
   
   // alert(ppHTML);
	
	$("#" + divID).html(ppHTML);
		
	for(i = 1; i <= pStep; ++i){
		if(i < pStep){
			$("#h" + i).addClass("step-finish");
			$("#b" + i).addClass("bar-finish");
			$("#c" + i).addClass("step-finish");
		}else{
			$("#h" + i).addClass("step-doing");
			$("#b" + i).addClass("bar-finish");
			$("#c" + i).addClass("step-finish");
		}
		
	}*/
	
	
	var stepImg = topURL + "img/project-step" + pStep + ".svg";
	
	var ppHTML = '<div class="container">' +
			      '<div class="project-progress" style="background:url(' + stepImg + ') no-repeat;height:165px;">';
			      /*'<div class="upper-part">' +
			      '<ul class="list-inline">' +
			         '<li><img src="' + stepImg + '"></li>' +
	              '</ul>' +
	              '</div>';*/
	
	
	ppHTML += '<div class="project-progress-desc">'  + 
	          '<ul class="list-inline">' + generateProgressContent(pStep,pStepContent,pDate) + '</ul></div>' +
		      '</div>' +
		      '</div>';
		      
    //alert(ppHTML);
	
	$("#" + divID).html(ppHTML);
}

function retriveStepInternal(pStatusCode){
	var pStep = 1;
	
	if(pStatusCode == 1){
		pStep = 1;
	}else if(pStatusCode == 2){
		pStep = 2;
	}else if(pStatusCode == 3 || pStatusCode == 4 || pStatusCode == 5 ){
		pStep = 3;
	}else if(pStatusCode == 6){
		pStep = 4;
	}else if(pStatusCode == 9 || pStatusCode == 11){
		pStep = 5;
	}
	
	return pStep;
}

function generateProgressContent(pCurrentStep,pStepContent,pDate){
	var pContentStr = "";
	
	for(var i = 1; i <= 5;i++){
		pContentStr += 	generateProgressContentItem(i,pCurrentStep,pStepContent[i-1],pDate[i-1]);
	}
	
	//alert(pContentStr);
	
	return pContentStr;
}

function generateProgressContentItem(pStep,pCurrentStep,pStepContent,pDateStr){
	var stepClass = "step-desc";
	var isDoingorAfter = false;
	
	if(pCurrentStep < pStep){
		isDoingorAfter = true;	
	}
	
	if(isDoingorAfter){
		stepClass = "step-desc-d";
	}else{
		if(pStep == 2 || pStep == 3 || pStep == 4){
			stepClass = "step-desc-a";
		}else{
			stepClass = "step-desc";
		}
	}
	
	var pContentItem = '<li id="c' + pStep + '" class="' + stepClass + '">'  + pStepContent + '<br>';
	
	if(pDateStr != null && pDateStr.trim() != ""){
		pContentItem += pDateStr.trim();
	}else{
		pContentItem += "&#12288;";
	}
	
	pContentItem += "</li>";
	
	return pContentItem;
}
		
