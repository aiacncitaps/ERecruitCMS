function isAjaxUploadSupported(){
                var input = document.createElement("input");
                input.type = "file";

                return (
                    "multiple" in input &&
                        typeof File != "undefined" &&
                        typeof FormData != "undefined" &&
                        typeof (new XMLHttpRequest()).upload != "undefined" );
}

function getIframeContentJSON(iframe){
    //IE may throw an "access is denied" error when attempting to access contentDocument on the iframe in some cases
    try {
        // iframe.contentWindow.document - for IE<7
        var doc = iframe.contentDocument ? iframe.contentDocument: iframe.contentWindow.document,
            response;

        var innerHTML = doc.body.innerHTML;
        //plain text response may be wrapped in <pre> tag
        if (innerHTML.slice(0, 5).toLowerCase() == "<pre>" && innerHTML.slice(-6).toLowerCase() == "</pre>") {
            innerHTML = doc.body.firstChild.firstChild.nodeValue;
        }
        response = eval("(" + innerHTML + ")");
    } catch(err){
        response = {success: false};
    }

    return response;
}

function getBU(bucode,txt){
  
	$('#ajaxLoader').find(".lightbox").show();
	ImoUtilityData.getBU({
		callback : function(str) 
		{
			 list = str.list;
			 var slist ='<option value="0">请选择</option>';
			 for(var i=0; i<list.length;i++)
			 {
				 if(bucode==list[i].code){
					 slist += '<option selected="selected" value='+list[i].code+'>'+list[i].name+'</option>';
				 }else{
					 slist += '<option value='+list[i].code+'>'+list[i].name+'</option>';
				 }
					
	        	
	         }         

	         $('#bu').html(slist);
	         if(txt == 'E'){
		         $('#district').html('<option value="0">全部</option>');
		         $('#city').html('<option value="0">全部</option>');
		         $('#ssc').html('<option value="0">全部</option>');
		         $('#office').html('<option value="0">全部</option>');
				 $('#branch').html('<option value="0">全部</option>');
	         }
	         
	         $('#ajaxLoader').find(".lightbox").hide();
	       
         } 
    });
}
function getDistrict(bu,dist,txt){
	 
	 if(bu != '0'){
		 $('#ajaxLoader').find(".lightbox").show();
		 ImoUtilityData.getDistrict(bu,{
			callback : function(str) 
			{
				 list = str.list;
				 var slist ='<option value="0">全部</option>';
				 for(var i=0; i<list.length;i++)
				 {
					 if(dist==list[i].code){
						 slist += '<option selected="selected" value='+list[i].code+'>'+list[i].name+'</option>';
					 }else{
						slist += '<option value='+list[i].code+'>'+list[i].name+'</option>';
				 	}
		         }         

		         $('#district').html(slist);
		         if(txt == 'E'){
		        	 $('#city').html('<option value="0">全部</option>');
			         $('#ssc').html('<option value="0">全部</option>');
			         $('#office').html('<option value="0">全部</option>');
					 $('#branch').html('<option value="0">全部</option>');
		         }
		         $('#ajaxLoader').find(".lightbox").hide();
		         
	         } 
	    });
	 }else{
		 $('#district').html('<option value="0">全部</option>');
         $('#city').html('<option value="0">全部</option>');
         $('#ssc').html('<option value="0">全部</option>');
         $('#office').html('<option value="0">全部</option>');
		 $('#branch').html('<option value="0">全部</option>');
	 }
	}


function getBranch(dist,branch,txt){
	 
	 if(dist != '0'){
		 
		$('#ajaxLoader').find(".lightbox").show();
		 ImoUtilityData.getBranch(dist,{
			callback : function(str) 
			{
				 list = str.list;
				 var slist ='<option value="0">全部</option>';
				 for(var i=0; i<list.length;i++)
				 {
					 if(branch==list[i].code){
						 slist += '<option  selected="selected" value='+list[i].code+'>'+list[i].name+'</option>';
					 }else{
						 slist += '<option value='+list[i].code+'>'+list[i].name+'</option>'; 
					 }
		         }         

		         $('#branch').html(slist);
		         if(txt == 'E'){
		        	 
		        	 $('#city').html('<option value="0">全部</option>');
			         $('#ssc').html('<option value="0">全部</option>');
			         $('#office').html('<option value="0">全部</option>');
					
		         }
		         $('#ajaxLoader').find(".lightbox").hide();
		       
	         } 
	    });
	 }else{
		
		 $('#city').html('<option value="0">全部</option>');
		 $('#branch').html('<option value="0">全部</option>');
		 $('#office').html('<option value="0">全部</option>');
		 $('#ssc').html('<option value="0">全部</option>');
	 }
	}


function getCity(co,city,dist,txt){
	 
	 if(co != '0'){
		 $('#ajaxLoader').find(".lightbox").show();
		 ImoUtilityData.getCity(co,dist,{
			callback : function(str) 
			{
				 list = str.list;
				 var slist ='<option value="0">全部</option>';
				 for(var i=0; i<list.length;i++)
				 {
					 if(city==list[i].codeStr){
						 slist += '<option  selected="selected" value='+list[i].codeStr+'>'+list[i].name+'</option>';
					 }else{
						 slist += '<option value='+list[i].codeStr+'>'+list[i].name+'</option>'; 
					 }
		         }         

		         $('#city').html(slist);
		         if(txt == 'E'){
		        	 $('#ssc').html('<option value="0">全部</option>');
			         $('#office').html('<option value="0">全部</option>');
		         }
		         $('#ajaxLoader').find(".lightbox").hide();
		       
	         } 
	    });
	 }else{
		 $('#city').html('<option value="0">全部</option>');
		 $('#office').html('<option value="0">全部</option>');
		 $('#ssc').html('<option value="0">全部</option>');
	 }
	}


function getSSC(city,ssc,txt){
	 
	 if(city != '0'){
		 $('#ajaxLoader').find(".lightbox").show();
		 ImoUtilityData.getSSC(city,{
			callback : function(str) 
			{
				 list = str.list;
				 var slist ='<option value="0">全部</option>';
				 for(var i=0; i<list.length;i++)
				 {
					 if(ssc==list[i].codeStr){
						 slist += '<option  selected="selected" value='+list[i].codeStr+'>'+list[i].name+'</option>';
					 }else{
						slist += '<option value='+list[i].codeStr+'>'+list[i].name+'</option>';
				     }
		         }         

		         $('#ssc').html(slist);
		         
		         if(txt == 'E'){
			         $('#office').html('<option value="0">全部</option>');
		         }
		         $('#ajaxLoader').find(".lightbox").hide();
		       
	         } 
	    });
	 }else{
		 $('#ssc').html('<option value="0">全部</option>');
		 $('#office').html('<option value="0">全部</option>');
	 }
	}


function getOffice(ssc,office){
	
	 if(ssc != '0'){
		 $('#ajaxLoader').find(".lightbox").show();
		 ImoUtilityData.getOffice(ssc,{
			callback : function(str) 
			{
				 list = str.list;
				 var slist ='<option value="0">全部</option>';
				 for(var i=0; i<list.length;i++)
				 {
					 if(office==list[i].codeStr){
						 slist += '<option  selected="selected" value='+list[i].codeStr+'>'+list[i].name+'</option>';
					 }else{
						slist += '<option value='+list[i].codeStr+'>'+list[i].name+'</option>';
				     }
		         }         

		         $('#office').html(slist);
		         $('#ajaxLoader').find(".lightbox").hide();
		       
	         } 
	    });
	 }else{
		 $('#office').html('<option value="0">全部</option>');
	 }
	}


function getAgentTeam(city, ssc, branch,office){
	 
	if('全部' != ssc){
		 $('#ajaxLoader').find(".lightbox").show();
		 ImoUtilityData.getAgentTeam(city, ssc,branch,office,{
			callback : function(str) 
			{
				 list = str.list;
				 var slist ='<option value="0">全部</option>';
				 for(var i=0; i<list.length;i++)
				 {
					 if(ssc==list[i].aamTeamCode){
						 slist += '<option  selected="selected" value='+list[i].codeStr+'>'+list[i].name+'</option>';
					 }else{
						slist += '<option value='+list[i].codeStr+'>'+list[i].name+'</option>';
				     }
		         }         

		         $('#agentTeam').html(slist);
		         $('#ajaxLoader').find(".lightbox").hide();
		       
	         } 
	    });
	}else{
		$('#agentTeam').html('<option value="0">全部</option>');
	}
	}
function getBranchForLogin(){
	 ImoUtilityData.getBranchForLogin('2',{
		 
		callback : function(str) 
		{
			 list = str.list;
			 var slist ='<option value="" selected>请选择</option>';
			 slist += '<option value="9986">中国区</option>';
			 for(var i=0; i<list.length;i++)
			 {
					 slist += '<option value='+list[i].aamTeamCode+'>'+list[i].name+'</option>'; 
	         }   
			 

	         $('#branch').html(slist);	       
        } 
   });
}