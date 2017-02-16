

function retriveProvince(proviceURL){
	
	$.ajax({
            "type" : "POST",
            "async": false,
            "url" : proviceURL,
            "success" : function(data){
             }
	       });
}
