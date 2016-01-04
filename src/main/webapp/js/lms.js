var popUpWindow = null;


function hideRegForm(){
	document.getElementById("regtable").style.display='none';
}
function showRegForm(){
	document.getElementById("regtable").style.display='block';
}

function checkNumeric(txtbx)
{
	var exp = /[^\d]/g;
	txtbx.value = txtbx.value.replace(exp,'');
}
function checkAlpha(txtbx)
{
	var exp = /[^\D]/g;
	txtbx.value = txtbx.value.replace(exp,'');
}

function copyEmail(frm){
	frm.email2.value=frm.email1.value;
	frm.email3.value=frm.email1.value;
}

function copyMobileNo(frm){
	var exp = /[^\d]/g;
	frm.mobile1.value = frm.mobile1.value.replace(exp,'');
	frm.mobile2.value=frm.mobile1.value;
	frm.mobile3.value=frm.mobile1.value;
}


function changeNight(frm){
	if(frm.dinner1.value=='Y'){
		frm.dinner2[1].selected=true;
		frm.dinner3[1].selected=true;
	}
	else if(frm.dinner1.value=='N'){
		frm.dinner2[2].selected=true;
		frm.dinner3[2].selected=true;
	}
	else if(frm.dinner1.value=='-'){
		frm.dinner2[0].selected=true;
		frm.dinner3[0].selected=true;
	}
}

function changeSalutation1(frm){
	if(frm.gender1.value=='F'){
		frm.salutation1[1].selected=true;
	}
	else if(frm.gender1.value=='M'){
		frm.salutation1[0].selected=true;
	}
}

function changeSalutation3(frm){
	if(frm.gender3.value=='F'){
		frm.salutation3[1].disabled=false;
		frm.salutation3[3].disabled=false;
		frm.salutation3[1].selected=true;
		frm.salutation3[0].disabled=true;
		frm.salutation3[2].disabled=true;
		
	}
	else if(frm.gender3.value=='M'){
		frm.salutation3[0].disabled=false;
		frm.salutation3[2].disabled=false;
		frm.salutation3[0].selected=true;
		frm.salutation3[1].disabled=true;
		frm.salutation3[3].disabled=true;
	}
	
}

function changeSalutation2(frm){
	if(frm.gender2.value=='F'){
		frm.salutation2[1].disabled=false;
		frm.salutation2[3].disabled=false;
		frm.salutation2[1].selected=true;
		frm.salutation2[0].disabled=true;
		frm.salutation2[2].disabled=true;
	}
	else if(frm.gender2.value=='M'){
		frm.salutation2[0].disabled=false;
		frm.salutation2[2].disabled=false;
		frm.salutation2[0].selected=true;
		frm.salutation2[1].disabled=true;
		frm.salutation2[3].disabled=true;
	}
	
}

function validateQLChallengeRegForm(){
var secCheck= true;
var ctr=0;
var list='';
var showmsg = true;
	if(document.frmqlch.rdstatus[0].checked==true){
		if(document.frmqlch.acceptTerms.checked==true){
		ctr = ctr+1;
		list = list + '\n- Qualifier';
		if(document.frmqlch.ppname1.value.trim()==''){
			alert('Please enter your full name as appearing on your passport.')
			secCheck= false;
		}
		else if(document.frmqlch.passport1.value.trim()==''){
			alert('Please enter your passport number.')
			secCheck= false;
		}
		else if(allowChars(document.frmqlch.passport1, '0123456789') == false || document.frmqlch.passport1.value.length!=8){
			alert('Please enter a valid 8 digit passport number.')
			secCheck= false;
		}
		else if(document.frmqlch.birthyear1.value<1900  || document.frmqlch.birthyear1.value>2009){
			alert('Please enter a valid birth year.')
			secCheck= false;
		}
		else if(document.frmqlch.nric1.value.trim()==''){
			alert('Please enter your NRIC number.')
			secCheck= false;
		}
		else if(allowChars(document.frmqlch.nric1, '0123456789') == false || document.frmqlch.nric1.value.length!=12){
			alert('Please enter a valid 12 digit NRIC number.')
			secCheck= false;
		}
		else if(document.frmqlch.accomodation1.value !='S' && document.frmqlch.rmname1.value.trim()==''){
			alert('Please enter your Room-mate\'s name.')
			secCheck= false;
		}
		else if(document.frmqlch.mobile1.value.trim()==''){
			alert('Please enter your mobile number.')
			secCheck= false;
		}
		else if(allowChars(document.frmqlch.mobile1, '0123456789') == false || document.frmqlch.mobile1.value.length!=10){
			alert('Please enter a valid 10 digit mobile number.')
			secCheck= false;
		}
		else if(!validateEmail(document.frmqlch.email1.value.trim())){
			alert('Please enter your valid email ID. This ID would be used to send you the confirmation mail.');
			secCheck=false;
		} // validation for 1st user ends here
		else if(document.frmqlch.ppname2.value.trim()!=''){ // validation for 2nd user starts here
			ctr = ctr+1;
			list = list + '\n- Spouse/Partner';
			if(document.frmqlch.ppname2.value.trim()==''){
				alert('Please enter the full name of the 1st person accompanying you, as appearing on his/her passport.')
				secCheck= false;
			}
			else if(document.frmqlch.passport2.value.trim()==''){
				alert('Please enter the passport number of the 1st person accompanying you.')
				secCheck= false;
			}
			else if(allowChars(document.frmqlch.passport2, '0123456789') == false || document.frmqlch.passport2.value.length!=8){
				alert('Please enter a valid 8 digit passport number for the 1st person accompanying you.')
				secCheck= false;
			}
			else if(document.frmqlch.birthyear2.value<1900  || document.frmqlch.birthyear2.value>2009){
				alert('Please enter a valid birth year for the 1st person accompanying you.')
				secCheck= false;
			}
			else if(document.frmqlch.nric2.value.trim()==''){
				alert('Please enter NRIC number for the 1st person accompanying you.')
				secCheck= false;
			}
			else if(allowChars(document.frmqlch.nric2, '0123456789') == false || document.frmqlch.nric2.value.length!=12){
				alert('Please enter a valid 12 digit NRIC number for the 1st person accompanying you.')
				secCheck= false;
			}
			else if(document.frmqlch.accomodation2.value !='S' && document.frmqlch.rmname2.value.trim()==''){
				alert('Please enter the Room-mate\'s name of the 1st person accompanying you.')
				secCheck= false;
			}
			else if(document.frmqlch.mobile2.value.trim()==''){
				alert('Please enter the mobile number of the 1st person accompanying you.')
				secCheck= false;
			}
			else if(allowChars(document.frmqlch.mobile2, '0123456789') == false || document.frmqlch.mobile2.value.length!=10){
				alert('Please enter a valid 10 digit mobile number for the 1st person accompanying you.')
				secCheck= false;
			}
		}
		
		if(document.frmqlch.type3.value!='-' && secCheck==true){ // validation for 3rd user starts here
			ctr = ctr+1;
			list = list + '\n- Child/Infant';
			if(document.frmqlch.ppname3.value.trim()==''){
				alert('Please enter the full name of the 2nd person accompanying you, as appearing on his/her passport.')
				secCheck= false;
			}
			else if(document.frmqlch.passport3.value.trim()==''){
				alert('Please enter the passport number of the 2nd person accompanying you.')
				secCheck= false;
			}
			else if(allowChars(document.frmqlch.passport3, '0123456789') == false || document.frmqlch.passport3.value.length!=8){
				alert('Please enter a valid 8 digit passport number for the 2nd person accompanying you.')
				secCheck= false;
			}
			else if(document.frmqlch.birthyear3.value<1900  || document.frmqlch.birthyear3.value>2009){
				alert('Please enter a valid birth year for the 2nd person accompanying you.')
				secCheck= false;
			}
			else if(document.frmqlch.nric2.value.trim()==''){
				alert('Please enter NRIC number for the 2nd person accompanying you.')
				secCheck= false;
			}
			else if(document.frmqlch.nric2.value.trim()==''){
				alert('Please enter NRIC number for the 2nd person accompanying you.')
				secCheck= false;
			}
			//else if(allowChars(document.frmqlch.nric2, '0123456789') == false || document.frmqlch.nric2.value.length!=12){
			//	alert('Please enter a valid 12 digit NRIC number for the 2nd person accompanying you.')
			//	secCheck= false;
			//}
			else if(document.frmqlch.accomodation3.value !='S' && document.frmqlch.rmname3.value.trim()==''){
				alert('Please enter the Room-mate\'s name of the 2nd person accompanying you.')
				secCheck= false;
			}
			else if(document.frmqlch.mobile3.value.trim()==''){
				alert('Please enter the mobile number of the 2nd person accompanying you.')
				secCheck= false;
			}
			else if(allowChars(document.frmqlch.mobile3, '0123456789') == false || document.frmqlch.mobile3.value.length!=10){
				alert('Please enter a valid 10 digit mobile number for the 2nd person accompanying you.')
				secCheck= false;
			}
		}
		//if(){
			
		//}
		//else{
		//	document.frmPledge.formhtml.value=document.getElementById('pledgeform').innerHTML;
			//return true;
		//}
		}
		else{
			alert('You have to read and accept all information content of Travel Brief Pack. Please click on "I have read and accept all information content of Travel Brief Pack".');
			secCheck = false;
		}
	}
	else if(document.frmqlch.rdstatus[1].checked==true){
		showmsg = false;
		if(document.frmqlch.acceptTerms.checked==false){
			alert('You have to read and accept all information content of Travel Brief Pack. Please click on "I have read and accept all information content of Travel Brief Pack".');
			secCheck=false;
		}
	}
	else if(document.frmqlch.rdstatus[0].checked==false && document.frmqlch.rdstatus[1].checked==false){
		alert('Please click on the \'Yes\' or \'No\' option to confirm your attendance for the trip.');
		secCheck= false;
		showmsg=false;
	}
	else{
		showmsg=false;
	}
	
	if(secCheck==true && showmsg==true){
		//document.frmqlch.formhtml.value=document.getElementById('pledgeform').innerHTML;
		//document.frmqlch.submit();
		if(!confirm('You have selected to register for '+ctr+' person/s:'+list)){
			return false;
		}
	}
	
	return secCheck;
}

function validateBaliSprintRegForm(){
var secCheck= true;
var ctr=0;
var list='';
var showmsg = true;
	if(document.frmbali.rdstatus[0].checked==true){
		if(document.frmbali.acceptTerms.checked==true){
		ctr = ctr+1;
		list = list + '\n- Qualifier';
		if(document.frmbali.ppname1.value.trim()==''){
			alert('Please enter your full name as appearing on your passport.')
			secCheck= false;
		}
		else if(document.frmbali.passport1.value.trim()==''){
			alert('Please enter your passport number.')
			secCheck= false;
		}
		else if(allowChars(document.frmbali.passport1, '0123456789') == false || document.frmbali.passport1.value.length!=8){
			alert('Please enter a valid 8 digit passport number.')
			secCheck= false;
		}
		else if(document.frmbali.birthyear1.value<1900  || document.frmbali.birthyear1.value>2009){
			alert('Please enter a valid birth year.')
			secCheck= false;
		}
		else if(document.frmbali.nric1.value.trim()==''){
			alert('Please enter your NRIC number.')
			secCheck= false;
		}
		else if(allowChars(document.frmbali.nric1, '0123456789') == false || document.frmbali.nric1.value.length!=12){
			alert('Please enter a valid 12 digit NRIC number.')
			secCheck= false;
		}
		else if(document.frmbali.accomodation1.value !='S' && document.frmbali.rmname1.value.trim()==''){
			alert('Please enter your Room-mate\'s name.')
			secCheck= false;
		}
		else if(document.frmbali.mobile1.value.trim()==''){
			alert('Please enter your mobile number.')
			secCheck= false;
		}
		else if(allowChars(document.frmbali.mobile1, '0123456789') == false || document.frmbali.mobile1.value.length!=10){
			alert('Please enter a valid 10 digit mobile number.')
			secCheck= false;
		}
		else if(!validateEmail(document.frmbali.email1.value.trim())){
			alert('Please enter your valid email ID. This ID would be used to send you the confirmation mail.');
			secCheck=false;
		}
		else if(document.frmbali.dinner1.value.trim()=='-'){
			alert('Please specify your preference for upgrading one additional night.');
			secCheck=false;
		} // validation for 1st user ends here
		else if(document.frmbali.ppname2.value.trim()!=''){ // validation for 2nd user starts here
			ctr = ctr+1;
			list = list + '\n- Spouse/Partner';
			if(document.frmbali.ppname2.value.trim()==''){
				alert('Please enter the full name of the 1st person accompanying you, as appearing on his/her passport.')
				secCheck= false;
			}
			else if(document.frmbali.passport2.value.trim()==''){
				alert('Please enter the passport number of the 1st person accompanying you.')
				secCheck= false;
			}
			else if(allowChars(document.frmbali.passport2, '0123456789') == false || document.frmbali.passport2.value.length!=8){
				alert('Please enter a valid 8 digit passport number for the 1st person accompanying you.')
				secCheck= false;
			}
			else if(document.frmbali.birthyear2.value<1900  || document.frmbali.birthyear2.value>2009){
				alert('Please enter a valid birth year for the 1st person accompanying you.')
				secCheck= false;
			}
			else if(document.frmbali.nric2.value.trim()==''){
				alert('Please enter NRIC number for the 1st person accompanying you.')
				secCheck= false;
			}
			else if(allowChars(document.frmbali.nric2, '0123456789') == false || document.frmbali.nric2.value.length!=12){
				alert('Please enter a valid 12 digit NRIC number for the 1st person accompanying you.')
				secCheck= false;
			}
			else if(document.frmbali.accomodation2.value !='S' && document.frmbali.rmname2.value.trim()==''){
				alert('Please enter the Room-mate\'s name of the 1st person accompanying you.')
				secCheck= false;
			}
		}
		
		if(document.frmbali.type3.value!='-' && secCheck==true){ // validation for 3rd user starts here
			ctr = ctr+1;
			list = list + '\n- Child/Infant';
			if(document.frmbali.ppname3.value.trim()==''){
				alert('Please enter the full name of the 2nd person accompanying you, as appearing on his/her passport.')
				secCheck= false;
			}
			else if(document.frmbali.passport3.value.trim()==''){
				alert('Please enter the passport number of the 2nd person accompanying you.')
				secCheck= false;
			}
			else if(allowChars(document.frmbali.passport3, '0123456789') == false || document.frmbali.passport3.value.length!=8){
				alert('Please enter a valid 8 digit passport number for the 2nd person accompanying you.')
				secCheck= false;
			}
			else if(document.frmbali.birthyear3.value<1900  || document.frmbali.birthyear3.value>2009){
				alert('Please enter a valid birth year for the 2nd person accompanying you.')
				secCheck= false;
			}
			else if(document.frmbali.nric2.value.trim()==''){
				alert('Please enter NRIC number for the 2nd person accompanying you.')
				secCheck= false;
			}
			else if(document.frmbali.nric2.value.trim()==''){
				alert('Please enter NRIC number for the 2nd person accompanying you.')
				secCheck= false;
			}
			//else if(allowChars(document.frmqlch.nric2, '0123456789') == false || document.frmqlch.nric2.value.length!=12){
			//	alert('Please enter a valid 12 digit NRIC number for the 2nd person accompanying you.')
			//	secCheck= false;
			//}
			else if(document.frmbali.accomodation3.value !='S' && document.frmbali.rmname3.value.trim()==''){
				alert('Please enter the Room-mate\'s name of the 2nd person accompanying you.')
				secCheck= false;
			}
		}
		//if(){
			
		//}
		//else{
		//	document.frmPledge.formhtml.value=document.getElementById('pledgeform').innerHTML;
			//return true;
		//}
		}
		else{
			alert('You have to read and accept all information content of Travel Brief Pack. Please click on "I have read and accept all information content of Travel Brief Pack".');
			secCheck = false;
		}
	}
	else if(document.frmbali.rdstatus[1].checked==true){
		showmsg = false;
		if(document.frmbali.acceptTerms.checked==false){
			alert('You have to read and accept all information content of Travel Brief Pack. Please click on "I have read and accept all information content of Travel Brief Pack".');
			secCheck=false;
		}
	}
	else if(document.frmbali.rdstatus[0].checked==false && document.frmbali.rdstatus[1].checked==false){
		alert('Please click on the \'Yes\' or \'No\' option to confirm your attendance for the trip.');
		secCheck= false;
		showmsg=false;
	}
	else{
		showmsg=false;
	}
	
	if(secCheck==true && showmsg==true){
		//document.frmqlch.formhtml.value=document.getElementById('pledgeform').innerHTML;
		//document.frmqlch.submit();
		if(!confirm('You have selected to register for '+ctr+' person/s:'+list)){
			return false;
		}
	}
	
	return secCheck;
}

function validatePledgeForm(){
	if(document.frmPledge.txtQAEmail.value.trim()==""){
		alert("Please enter your email ID. This ID would be used to send you the confirmation mail.");
		return false;
	}
	else if(document.frmPledge.txtQANric.value.trim()==""){
		alert("Please enter the NRIC number.");
		return false;
	}
	else if(!document.frmPledge.chkPledge.checked){
		alert("You have not pledged to participate in this concert challenge.")
		return false;
	}
	else if(!validateEmail(document.frmPledge.txtQAEmail.value.trim())){
		alert('Please enter a valid e-mail address.');
	}
	else{
		document.frmPledge.formhtml.value=document.getElementById('pledgeform').innerHTML;
		return true;
		//document.frmPledge.submit();
		
	}
	return false;
}

function validateNumeric(field) {
	var valid = "0123456789"
	var ok = "yes";
	var temp;
	if (field.value.length > 0){
		//for (var i=0; i<field.value.length; i++) {
			//temp = "" + field.value.substring(i, i+1);
		if (valid.indexOf(field.value.substring(field.value.length-1, field.value.length)) == "-1"){
			field.value=field.value.substring(0, field.value.length-1);
		}
		//}
	}
	
	/*if (field.value.length < 1){
		ok = "empty";
	}	
	for (var i=0; i<field.value.length; i++) {
		temp = "" + field.value.substring(i, i+1);
		if (valid.indexOf(temp) == "-1") ok = "notNumeric";
	}
	
	if (ok == "empty" ) {
			alert("The field cannot be blank!");
			field.focus();
			field.select();
    }
	if (ok == "notNumeric") {
		alert("Invalid entry!  Only numbers are accepted!");
		field.focus();
		field.select();
    }*/
}
function validateNumericF(field) {
	var valid = ".0123456789"
	var ok = "yes";
	var temp;
	if (field.value.length > 0){
		//for (var i=0; i<field.value.length; i++) {
			//temp = "" + field.value.substring(i, i+1);
		if (valid.indexOf(field.value.substring(field.value.length-1, field.value.length)) == "-1"){
			field.value=field.value.substring(0, field.value.length-1);
		}
		//}
	}
	}

String.prototype.isNumeric = function () {
   	if(/^[0-9]*$/.test(this))
		return true;
	else
		return false;
}

function validateStarccForm(){
	
	if(document.frmStarcc.txtQAProposalNo.value.trim()==""){
		alert("Please enter the Proposal number.");
		return false;
	}
	else if(!document.frmStarcc.txtQAProposalNo.value.trim().isNumeric()){
		alert("Please enter a valid 7/8 digit Proposal number.");
		return false;
	}
	else if(document.frmStarcc.txtQAProposalNo.value.trim().length!=8){
		if(document.frmStarcc.txtQAProposalNo.value.trim().length!=7){
			alert("Please enter a valid 7/8 digit Proposal number.");
			return false;
		}
	}
	else if(document.frmStarcc.txtQAProposalNo.value.trim().length!=7){
		if(document.frmStarcc.txtQAProposalNo.value.trim().length!=8){
			alert("Please enter a valid 7/8 digit Proposal number.");
			return false;
		}
	}
	
	if(document.frmStarcc.txtQAEmail.value.trim()==""){
		alert("Please enter your email ID. This ID would be used to send you the confirmation mail.");
		return false;
	}
	else if(!validateEmail(document.frmStarcc.txtQAEmail.value.trim())){
		alert('Please enter a valid e-mail address.');
	}
	else if(document.frmStarcc.txtQANric.value.trim()==""){
		alert("Please enter the NRIC number.");
		return false;
	}
	else if(document.frmStarcc.txtQANric.value.trim().length!=12 || !document.frmStarcc.txtQANric.value.trim().isNumeric()){
		alert("Please enter a valid 12 digit NRIC number.");
		return false;
	}
	else if(document.frmStarcc.txtQAProposalType.value.trim()==""){
		alert("Please enter the Proposal Type.");
		return false;
	}
	else if(document.frmStarcc.txtQACasesize.value.trim()==""){
		alert("Please enter the Case Size (API).");
		return false;
	}
	//!document.frmStarcc.txtQACasesize.value.trim().isNumeric() || 
	else if(document.frmStarcc.txtQACasesize.value.length<4 || document.frmStarcc.txtQACasesize.value.length>9){
		alert("Please enter a valid 4 to 9 digit numeric value for Case Size(API).");
		return false;
	}
	
	else if(document.frmStarcc.txtQACasesize.value < 1200){
		alert("Case Size(API) value can't be less than 1200.");
		return false;
	}
	else{
		var field = document.frmStarcc.txtQACasesize.value;
		var valid = "0123456789.";
		for (var i=0; i<field.length; i++) {
			if(valid.indexOf(field.charAt(i))==-1){
				alert("Please enter a valid numeric value for Case Size(API).");
				return false;
			}
		}
		if(!document.frmStarcc.chkPledge.checked){
			alert("You have not agreed to the rules and regulations of this contest.\nPlease tick the checkbox if you agree to the rules and regulations.")
			return false;
		}
		document.frmStarcc.formhtml.value=document.getElementById('starccform').innerHTML;
		return true;
	}
	
	return false;
}

function validateMccpForm(){
	
	document.frmPledge.formhtml.value=document.getElementById('pledgeform').innerHTML;
	var er = "";
	if(document.frmPledge.txtQAContactNo.value.trim()==""){
		er = er + "Please enter your Contact No."
	}else if(allowChars(document.frmPledge.txtQAContactNo, '0123456789') == false){
		er = er + "\nPlease enter valid Contact No."
	}
	if(document.frmPledge.txtQAPropNo.value.trim()==""){
		er = er + "\nPlease enter your Proposal No."
	}else if(Number(document.frmPledge.txtQAPropNo.value.length) != 5 || 
		allowChars(document.frmPledge.txtQAPropNo, '0123456789') == false 
		){
		er = er + "\nSorry, You have keyed in an invalid proposal no.  Please key in again. Thank You."
	}
	
	/*
	if(document.frmPledge.txtSubDate.value.trim()==""){
		er = er + "\nPlease enter submission date.";
	}else if(!validDate(document.frmPledge.txtSubDate.value)){
		er = er + "\nPlease enter valid submission date.";
	}
	*/
	
	if(er.length != '0' ){
		alert(er);
		return false;
	}else{
		return true;
	}

}

function validDate(dateval){
	var splitDate = dateval.split("/");
	
	if(splitDate[0] > 31){
		return false;
	}else if(splitDate[1] > 12){
		return false;
	}else if(splitDate[2] < 1900 && splitDate[2] > 2100){
		return false;
	}else{
		return true;
	}
	

}

function allowChars(fld,chars)
{ // provide a string of acceptable chars for a field
  if(!fld.value.length||fld.disabled) return true;
  for(var i= 0; i < fld.value.length; i++)
  {
    if(chars.indexOf(fld.value.charAt(i)) == -1)
    { return false; }
  }

  return true;
}

function validateEmail(email) {
   var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
   return reg.test(email)
}

String.prototype.trim = function() {
	a = this.replace(/^\s+/, '');
	return a.replace(/\s+$/, '');
};

function aboutus_links() { //v3.0
	
	code='';
	code = code + '<a href="About%20PBA/Vision & Mission.ppt">Vision & Mission</a><br><br>';
	code = code + '<a href="About%20PBA/PBA%20Logo.ppt">PBA Logo</a><br><br>';
	code = code + '<b>PBA 5 Faculties:</b><br><br>';
	code = code + '<a href="About%20PBA/Faculty%20of%20Sales%20&%20Financial%20Planning.ppt">Faculty of Sales & Financial Planning</a><br><br>';
	code = code + '<a href="About%20PBA/Faculty%20of%20Products%20&%20Technical.ppt">Faculty of Products & Technical</a><br><br>';
	code = code + '<a href="About%20PBA/Faculty%20of%20Personal%20Effectiveness.ppt">Faculty of Personal Effectiveness</a><br><br>';
	code = code + '<a href="About%20PBA/Faculty%20of%20Agency%20Management.ppt">Faculty of Agency Management</a><br><br>';
	code = code + '<a href="About%20PBA/Faculty%20of%20Leadership.ppt">Faculty of Leadership</a><br><br>';
	
	code = code + '<a href="#">PBA Video</a><br><br>';
	
  	aboutus.innerHTML= code;
	
}

function submenu(sect) { //v3.0
  if(sect == "acl") {
  	code='';
	code = code + '<a href="../ACL/Login.html">User Login</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	submenu_links.innerHTML= code;
  }
else if(sect == "admin") {
  	code='<table width="100%" cellpadding="0" cellspacing="4" bordercolor="#dddddd" border="0">';
  	code = code + '<tr><td width="10%" height=10>&nbsp;</td><td width="30%" height=10><div align="left"><a href="ContentManager?key=categoryMaintenance">Category Maintenance</a></div></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=trainingAttendanceMaintenance">Training Attendance Maintenance</a></div></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=deductionMaintenance">Deduction Payment</a></div></td></tr>';
  	
  	code = code + '<tr><td width="10%" height=10>&nbsp;</td><td width="30%" height=10><div align="left"><a href="ContentManager?key=facultyMaintenance">Faculty Maintenance</a></div></td>';
  	//code = code + '<tr><td width="10%" height=10>&nbsp;</td><td width="30%" height=10></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=examAttendanceMaintenance">Exam Attendance Maintenance</a></div></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=expenditureMaintenance">Expenditure Maintenance</a></div></td></tr>';
  	
   	code = code + '<tr><td width="10%" height=10>&nbsp;</td><td width="30%" height=10><div align="left"><a href="ContentManager?key=courseMaintenance">Course Maintenance</a></div></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=cpdHourMaintenance">CPD Hour Maintenance</a></div></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=miscExpenditureMaintenance">Misc Expenditure Maintenance</a></div></td></tr>';
  	
   	code = code + '<tr><td width="10%" height=10>&nbsp;</td><td width="30%" height=10><div align="left"><a href="ContentManager?key=classMaintenance">Class Maintenance</a></div></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=trainingFeedbackMaintenance">Training Feedback Maintenance</a></div></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=auditTrail">Audit Trail</a></div></td></tr>';
  	
  	code = code + '<tr><td width="10%" height=10>&nbsp;</td><td width="30%" height=10><div align="left"><a href="ContentManager?key=examMaintenance">Exam Maintenance</a></div></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=facilityenquiry">Facility Maintenance</a></div></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=eventMaintenance">Event Maintenance</a></div></td></tr>';
  	
  	code = code + '<tr><td width="10%" height=10>&nbsp;</td><td width="30%" height=10><div align="left"><a href="ContentManager?key=examSessionMaintenance">Exam Session Maintenance</a></div></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=pwpReportMaintenance">PWP Report Maintenance</a></div></td>';
  	code = code + '<td width="30%" height=10><div align="left"><a href="ContentManager?key=#">&nbsp;</a></div></td></tr>';
  		 
	code = code + '</table>';		
  	submenu_links.innerHTML= code;
  }
  
  
  else  if(sect == "schedule") {
  	code='';
	code = code + '<a href="ContentManager?key=trainingSchedule">Training Schedule</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	code = code + '<a href="ContentManager?key=examSchedule">Exam Schedule</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	code = code + '<a href="ContentManager?key=eventSchedule">Event Schedule</a>';
	
  	submenu_links.innerHTML= code;
  }
  
  else  if(sect == "registration") {
  	code='';
	code = code + '<a href="ContentManager?">Training Registration</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	code = code + '<a href="ContentManager?">Exam Registration</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	code = code + '<a href="ContentManager?">Event Registration</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	code = code + '<a href="ContentManager?key=userRegistrationApproval">User Registration Approval</a>';
	
  	submenu_links.innerHTML= code;
  }
  
  else  if(sect == "enquiry") {
  	code='';
	code = code + '<a href="ContentManager?key=trainingRegistrationEnquiry">Training Registration Enquiry</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	code = code + '<a href="ContentManager?key=examRegistrationEnquiry">Exam Registration Enquiry</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	code = code + '<a href="ContentManager?key=eventRegistrationEnquiry">Event Registration Enquiry</a>';

	
  	submenu_links.innerHTML= code;
  }
  
  else  if(sect == "reporting") {
  	code='<table width="100%" cellpadding="0" cellspacing="4" bordercolor="#dddddd" border="0">';
  	
  	code = code + '<tr><td width="5%" height=10>&nbsp;</td><td width="25%" height=10><div align="left"><a href="ContentManager?key=agencyCPDReport">Agency CPD Report</a></td>';
	code = code + '<td width="25%" height=10><div align="left"><a href="ContentManager?key=rfpCfpReport">RFP & CFP Report</a></td>'; 
	code = code + '<td width="20%" height=10><div align="left"><a href="ContentManager?key=productionReport">Production Report</a></td>'; 
	code = code + '<td width="25%" height=10><div align="left"><a href="ContentManager?key=regionalExpenditureReport">Regional Expenditure Report</a></td>'; 
	code = code + '</tr>';
  	
  	code = code + '<tr><td width="5%" height=10>&nbsp;</td><td width="25%" height=10><div align="left"><a href="ContentManager?key=Agency_Exam_Result">Agency Exam Result</a></td>';
	code = code + '<td width="25%" height=10><div align="left"><a href="ContentManager?key=teachingHourReport">Teaching Hour Report</a></td>'; 
	code = code + '<td width="20%" height=10><div align="left"><a href="ContentManager?key=trackingReportRegion">Production By Region</a></td>'; 
	code = code + '<td width="25%" height=10><div align="left"><a href="ContentManager?key=monthlyExpenditureReport">Monthly Expenditure Report</a></td>';
	code = code + '</tr>';
	
	code = code + '<tr><td width="5%" height=10>&nbsp;</td><td width="25%" height=10><div align="left"><a href="ContentManager?key=trainingReport">Training Report</a></td>';
	code = code + '<td width="25%" height=10><div align="left"><a href="ContentManager?key=trainingFeedbackReport">Training Feedback Report</a></td>'; 
	code = code + '<td width="20%" height=10><div align="left"><a href="ContentManager?key=trackingReportCourse">Production By Course</a></td>'; 
	code = code + '<td width="25%" height=10><div align="left"><a href="ContentManager?key=moderatorExpenditureReport">Moderator Expenditure Report</a></td>';
	code = code + '</tr>';
	
	code = code + '<tr><td width="5%" height=10>&nbsp;</td><td width="25%" height=10><div align="left"><a href="ContentManager?key=examReportEnquiry">Exam Report</a></td>';
	code = code + '<td width="25%" height=10><div align="left"><a href="ContentManager?key=cpdReport">LIAM CPD Report</a></td>'; 
	code = code + '<td width="20%" height=10><div align="left"><a href="ContentManager?key=trackingReportClass">Production By Class</a></td>'; 
	code = code + '<td width="25%" height=10><div align="left"><a href="ContentManager?key=miscExpenditureReport">Misc Expenditure Report</a></td>';
	code = code + '</tr>';
	
	//code = code + '</tr><tr><td width="5%" height=10>&nbsp;</td>';
	code = code + '</tr><tr><td width="5%" height=10>&nbsp;</td><td width="25%" height=10><div align="left"><a href="ContentManager?key=cpdManagementReport">CPD Management Report</a></td>';
	code = code + '<td width="25%" height=10><div align="left"><a href="ContentManager?key=cpdReportPi">PIAM CPD Report</a></td>';
	code = code + '<td width="20%" height=10><div align="left"><a href="ContentManager?key=PIAMReport">PIAM Report</a></td>'; 
	code = code + '<td width="25%" height=10><div align="left"><a href="ContentManager?key=trainerEvaluationReport">Trainer Evaluation Report</a></td>'; 
	
	
	//code = code + '</tr><tr><td width="5%" height=10>&nbsp;</td>';
	code = code + '</tr><tr><td width="5%" height=10>&nbsp;</td><td width="25%" height=10><div align="left"><a href="ContentManager?key=pruReport&reportType=1">AIA Wealth Planner</a></td>'; 
	code = code + '<td width="25%" height=10><div align="left"><a href="ContentManager?key=pruReport&reportType=2">Executive Wealth Manager</a></td>';
	code = code + '<td width="20%" height=10><div align="left"><a href="ContentManager?key=pruReport&reportType=3">Senior Wealth Manager</a></td>';
	//code = code + '<td width="20%" height=10><div align="left"><a href="#">PIAM Report</a></td>';
	code = code + '<td width="25%" height=10><div align="left"><a href="#"></a></td>';
	code = code + '</tr>';
  	
  	
	code = code + '</table>';
  	submenu_links.innerHTML= code;
  }
  
  else  if(sect == "management") {
  	code='';
  	/*Modified 02.05.2006 - Start*/
	code = code + '<a href="ContentManager?key=resourceManagementSystem">Resource Management System</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	code = code + '<a href="ContentManager?key=traininghall">Facility Reservation System</a>';
	/*Modified 02.05.2006 - End*/
	
  	submenu_links.innerHTML= code;
  }
  
   else  if(sect == "course") {
  	code='';
	code = code + '<a href="ContentManager?">Course Overview</a>';
	
  	submenu_links.innerHTML= code;
  }
  
}

function administrationFormSubmit ( action )
{
  document.administrationForm.actionType.value = action ;
  document.administrationForm.submit() ;
  
}

function subFormSubmit ( action )
{		
  document.subForm.actionType.value = action ;
  document.subForm.submit() ;
}

function subForm2Submit ( action )
{		
  document.subForm2.actionType.value = action ;
  document.subForm2.submit() ;
}

function launchPopUp (url)
{		
  popUpWindow = window.open(url);
}

function closePopUp()
{
	opener.location.reload(true);
    self.close();
}

function administrationFormSubmit ( action )
{
  document.administrationForm.actionType.value = action ;
  document.administrationForm.submit() ;
}

function closePopUpFromParent()
{
	if(popUpWindow != null)
		popUpWindow.close();
	
	popUpWindow = null;	
}

function examFlagChange() 
{
   if (document.administrationForm.examFlag[0].checked) {
      //yes
      toggleBox("divExam1", 1);
      toggleBox("divExam2", 1);
      }
   else    {
      toggleBox("divExam1", 0);
      toggleBox("divExam2", 0);   }
}


function clickedUpload() 
{
      toggleBox("divFileUpload", 1);
}

function addWalkInNameICAgentCodeHide()
{
	//alert("hi");
   	  toggleBox("divCandidateNameLabel",1);
   	  toggleBox("divCandidateNameField",1);
	  toggleBox("divICNumberLabel",0);
   	  toggleBox("divAgentCodeLabel",1);
   	  toggleBox("divAgentCodeField",1);

      toggleBox("divAgentIdField", 1);
	  toggleBox("divAgentIdCaption", 1);
	  toggleBox("divICNumberField",0);
	  toggleBox("divStaffIdCaption",0);

}
function changeLabelStaff(){
		document.getElementById("divAgentCodeLabel").style.color="#545454";
		document.getElementById("divAgentCodeLabel").style.fontFamily="DINFactRegularRegular";
		$('#divAgentCodeLabel').html("Agency Code/ Department");
	
}
function changeLabelGuest(){
	
	document.getElementById("divAgentCodeLabel").style.color="#545454";
	document.getElementById("divAgentCodeLabel").style.fontFamily="DINFactRegularRegular";
	$('#divAgentCodeLabel').html("Agency Code");

}
function addWalkInStaffHide() 
{//alert("hi");
  toggleBox("divAgentIdField", 0);
  toggleBox("divAgentIdCaption", 0);
  toggleBox("divStaffIdCaption",1);
  toggleBox("divICNumberField",1);
  toggleBox("divAgentCodeLabel", 1);
  toggleBox("divAgentCodeField", 1);
  changeLabelStaff();
  document.getElementById("agency_code").readOnly = false;
}
function addWalkInGuestHide() 
{//alert("hi");
  toggleBox("divAgentIdField", 1);
  toggleBox("divAgentIdCaption", 1);
  toggleBox("divStaffIdCaption",1);
  toggleBox("divICNumberField",1);
  toggleBox("divAgentCodeLabel", 1);
  toggleBox("divAgentCodeField", 1);
  changeLabelGuest();
  document.getElementById("agency_code").readOnly = true;
}
function addWalkInGuest(){
	document.getElementById("agency_code").readOnly = true;
}
function addWalkIn() 
{
	//alert("hi");
	toggleBox("divICNumberField",0);
  toggleBox("divStaffIdCaption",0);
  //toggleBox("divCandidateNameLabel",1);
}
function addWalkInChange() 
{
   var myindex=document.forms[0].userTypeParam.value;
   if (myindex == "SF")
   {
		toggleBox("divAgentIdField", 0);
		toggleBox("divAgentIdCaption", 0);
   	  	
   	  	toggleBox("divCandidateNameLabel",1);
   	  	toggleBox("divCandidateNameField",1);
   	  //	toggleBox("divICNumberLabel",0);
   	 // 	toggleBox("divICNumberField",0);
   	  	toggleBox("divAgentCodeLabel",1)
   	  	toggleBox("divAgentCodeField",1)
   	  	

   }
   else if (myindex == 'AL' || myindex == 'SA')
   {	
   	  toggleBox("divCandidateNameLabel",0);
   	  toggleBox("divCandidateNameField",0);
	 // toggleBox("divICNumberLabel",0);
   	 // toggleBox("divICNumberField",0);
   	  toggleBox("divAgentCodeLabel",0);
   	  toggleBox("divAgentCodeField",0);

      toggleBox("divAgentIdField", 1);
	  toggleBox("divAgentIdCaption", 1);
   }
  else
  {
   	  toggleBox("divCandidateNameLabel",1);
   	  toggleBox("divCandidateNameField",1);
	//  toggleBox("divICNumberLabel",0);
   	//  toggleBox("divICNumberField",0);
   	  toggleBox("divAgentCodeLabel",1);
   	  toggleBox("divAgentCodeField",1);

      toggleBox("divAgentIdField", 1);
	  toggleBox("divAgentIdCaption", 1);
  
  }   
}
function addWalkInChange1() 
{//alert("hi");
   var myindex=document.forms[0].userTypeParam.value;
   if (myindex == "SF")
   {
		toggleBox("divAgentIdField", 0);
		toggleBox("divAgentIdCaption", 0);
		toggleBox("divStaffIdCaption", 1);
   	  	
   	  	toggleBox("divCandidateNameLabel",1);
   	  	toggleBox("divCandidateNameField",1);
   	  //	toggleBox("divICNumberLabel",0);
   	  	toggleBox("divICNumberField",1);
   	  	toggleBox("divAgentCodeLabel",1);
   	  	toggleBox("divAgentCodeField",1);
   	  	

   }
   else if (myindex == 'AL' || myindex == 'SA' || myindex == 'PC')
   {	
   	  toggleBox("divCandidateNameLabel",1);
   	  toggleBox("divCandidateNameField",1);
   	  toggleBox("divStaffIdCaption", 0);
	 // toggleBox("divICNumberLabel",0);
   	  toggleBox("divICNumberField",0);
   	  toggleBox("divAgentCodeLabel",1);
   	  toggleBox("divAgentCodeField",1);

      toggleBox("divAgentIdField", 1);
	  toggleBox("divAgentIdCaption", 1);
   }
  else
  {
   	  toggleBox("divCandidateNameLabel",1);
   	  toggleBox("divCandidateNameField",1);
   	toggleBox("divStaffIdCaption", 1);
	//  toggleBox("divICNumberLabel",0);
   	  toggleBox("divICNumberField",1);
   	  toggleBox("divAgentCodeLabel",1);
   	  toggleBox("divAgentCodeField",1);

      toggleBox("divAgentIdField", 1);
	  toggleBox("divAgentIdCaption", 1);
  
  }   
}

function toggleTrainingAttnChkBx(attnChkBox, completeChkBx, assgnCreditChkBx, str)
{	
	if(attnChkBox.checked == true)
	{
					if(str=="F")
					{
						document.getElementById(assgnCreditChkBx).disabled=false;
						
					}
					document.getElementById(completeChkBx).disabled=false;
					
				
	}
	else
	{
				document.getElementById(completeChkBx).checked=false;
				document.getElementById(completeChkBx).disabled = true;
				
				document.getElementById(assgnCreditChkBx).checked=false;
				document.getElementById(assgnCreditChkBx).disabled=true;				
	
			
	}	
}

function toggleExamAttnChkBx(attnChkBox, completeChkBx, assgnCreditChkBx, assgnExamMarkTxtField, str)
{
	
	if(attnChkBox.checked == true)
	{

					if(str=="F")
					{
						document.getElementById(assgnCreditChkBx).disabled = false;
					}
				
					document.getElementById(completeChkBx).disabled = false;
					document.getElementById(assgnExamMarkTxtField).disabled = false;					
				
	}
	else
	{
				document.getElementById(assgnExamMarkTxtField).value="0";
				document.getElementById(completeChkBx).checked = false;
				document.getElementById(assgnCreditChkBx).checked = false;				

				document.getElementById(assgnExamMarkTxtField).disabled = true;
				document.getElementById(completeChkBx).disabled = true;
				document.getElementById(assgnCreditChkBx).disabled = true;	
					
	}	
}




function registerForChange() 
{
var myindex=document.forms[0].registerFor.selectedIndex;


   if (myindex==0) {
      //Leader

      toggleBox("divLeadAgent1", 1);
      toggleBox("divLeadAgent2", 1);
      toggleBox("divLeadAgent3", 1);
      toggleBox("divLeadAgent4", 1);
      toggleBox("divStaff1", 0);
      toggleBox("divStaff2", 0);
      toggleBox("divAS1", 0);
      toggleBox("divAS2", 0);
      toggleBox("divAS3", 0);
      toggleBox("divAS4", 0);
      toggleBox("divAS5", 0);
      toggleBox("divAS6", 0);
      toggleBox("divAS7", 0);
      toggleBox("divGT1", 0);
      toggleBox("divGT2", 0);
      toggleBox("divGT3", 0);
      toggleBox("divGT4", 0);
      toggleBox("divGT5", 0);
      toggleBox("divGT6", 0);
      toggleBox("divGT7", 0);
   }
   if(myindex==1){
      //Agent
    
      toggleBox("divLeadAgent1", 1);
      toggleBox("divLeadAgent2", 1);
      toggleBox("divLeadAgent3", 1);
      toggleBox("divLeadAgent4", 1);
      toggleBox("divStaff1", 0); 
      toggleBox("divStaff2", 0); 
      toggleBox("divAS1", 0);
      toggleBox("divAS2", 0);
      toggleBox("divAS3", 0);
      toggleBox("divAS4", 0);
      toggleBox("divAS5", 0);
      toggleBox("divAS6", 0);
      toggleBox("divAS7", 0);
      toggleBox("divGT1", 0);
      toggleBox("divGT2", 0);
      toggleBox("divGT3", 0);
      toggleBox("divGT4", 0);
      toggleBox("divGT5", 0);
      toggleBox("divGT6", 0);
      toggleBox("divGT7", 0);
   }
   if(myindex==2){
      //Staff

      toggleBox("divLeadAgent1", 0);
      toggleBox("divLeadAgent2", 0);
      toggleBox("divLeadAgent3", 0);
      toggleBox("divLeadAgent4", 0);
      toggleBox("divStaff1", 1); 
      toggleBox("divStaff2", 1);
      toggleBox("divAS1", 0);
      toggleBox("divAS2", 0);
      toggleBox("divAS3", 0);
      toggleBox("divAS4", 0);
      toggleBox("divAS5", 0);
      toggleBox("divAS6", 0);
      toggleBox("divAS7", 0);
      toggleBox("divGT1", 0);
      toggleBox("divGT2", 0);
      toggleBox("divGT3", 0);
      toggleBox("divGT4", 0);
      toggleBox("divGT5", 0);
      toggleBox("divGT6", 0);
      toggleBox("divGT7", 0);
   }
   if(myindex==3){
      //Agency Staff

      toggleBox("divLeadAgent1", 0);
      toggleBox("divLeadAgent2", 0);
      toggleBox("divLeadAgent3", 0);
      toggleBox("divLeadAgent4", 0);
      toggleBox("divStaff1", 0); 
      toggleBox("divStaff2", 0); 
      toggleBox("divAS1", 1);
      toggleBox("divAS2", 1);
      toggleBox("divAS3", 1);
      toggleBox("divAS4", 1);
      toggleBox("divAS5", 1);
      toggleBox("divAS6", 1);
      toggleBox("divAS7", 1);
      toggleBox("divGT1", 0);
      toggleBox("divGT2", 0);
      toggleBox("divGT3", 0);
      toggleBox("divGT4", 0);
      toggleBox("divGT5", 0);
      toggleBox("divGT6", 0);
      toggleBox("divGT7", 0);
   }
   if(myindex==4){
      //Guest

      toggleBox("divLeadAgent1", 0);
      toggleBox("divLeadAgent2", 0);
      toggleBox("divLeadAgent3", 0);
      toggleBox("divLeadAgent4", 0);
      toggleBox("divStaff1", 0); 
      toggleBox("divStaff2", 0); 
      toggleBox("divAS1", 0);
      toggleBox("divAS2", 0);
      toggleBox("divAS3", 0);
      toggleBox("divAS4", 0);
      toggleBox("divAS5", 0);
      toggleBox("divAS6", 0);
      toggleBox("divAS7", 0);
      toggleBox("divGT1", 1);
      toggleBox("divGT2", 1);
      toggleBox("divGT3", 1);
      toggleBox("divGT4", 1);
      toggleBox("divGT5", 1);
      toggleBox("divGT6", 1);
      toggleBox("divGT7", 1);
   }
}

function attStatusChange() 
{

var myindex=document.forms[0].attStatus.selectedIndex;

   if (myindex==2) {
      //Att status = Others

      toggleBox("divComments1", 1);
   }
   else{
   
      toggleBox("divComments1", 0);
   }
   
}

function modInvChange() 
{

var myindex=document.forms[0].modNum.selectedIndex;

	toggleBox("divMod1", 0);
    toggleBox("divMod2", 0);
    toggleBox("divMod3", 0);
    toggleBox("divMod4", 0);
    toggleBox("divMod5", 0); 
 
    
    myindex = myindex+1;

   if (myindex>0) 
   {

      //selected 1
      toggleBox("divMod1", 1);
   }
  if (myindex>1) 
   {

      //selected 2
      toggleBox("divMod2", 1);
      $('.select, .checkbox').jqTransform({imgPath:'jqtransformplugin/img/'});
   }
   if (myindex>2) 
   {

      //selected 3
      toggleBox("divMod3", 1);
   }
   if (myindex>3) 
   {
      //selected 4
      toggleBox("divMod4", 1);
   }
   if (myindex>4) 
   {
      //selected 5
      toggleBox("divMod5", 1);
   }
  
   
}

 function toggleBox(szDivID, iState) // 1 visible, 0 hidden
{
    if(document.layers)	   //NN4+
    {
       document.layers[szDivID].display = iState ? "block" : "none";
    }
    else if(document.getElementById)	  //gecko(NN6) + IE 5+
    {
        var obj = document.getElementById(szDivID);
        obj.style.display = iState ? "block" : "none";
    }
    else if(document.all)	// IE 4
    {
        document.all[szDivID].style.display = iState ? "block" : "none";
    }
}

function confirmDeleteWithMsg(item, delUrl) {
  if (confirm(item)) {
    document.location = delUrl;
  }
}

function confirmDelete(item, delUrl) {
  if (confirm("This will delete the " + item + ". Proceed?")) {
    document.location = delUrl;
  }
}

function confirmApprove(item, delUrl) {
  if (confirm("This will approve the " + item + " registration. Proceed?")){ 
		  document.location = delUrl;
		}
}

function confirmApproveWithTermsConditions(item, delUrl) {
	  if (confirm("This will approve the " + item + " registration. Proceed?")) {
		  if(item=="training"){
			  if(confirm("In the event the agent failed to attend, pay or settle the amount stated thereof, I do hereby irrevocable undertake the fully reimburse the company the course fee and also authorise the company to deduct the said amount from my statement. ")){
				  document.location = delUrl;  
			  }  
		  }else
			  document.location = delUrl;
			  
	    
	  }
	}
function confirmDecline(item, delUrl) {
  if (confirm("This will decline the " + item + " registration. Proceed?")) {
    document.location = delUrl;
  }
}

function confirmDelelteUser(action, item, deleteUrl, url) {
  
  if(document.administrationForm.accntStatus.value == "DEL")
  {
  	var confirm_delete = confirm("You are about to delete user, " + item + ". Proceed?");
  	if (confirm_delete == true) {
    	document.location = deleteUrl;
  	}
  	else
  	{
 		document.location = "#";
  	}
  	
  }
  else
  {
  	document.administrationForm.actionType.value = action ;
  	document.administrationForm.submit() ;
  }
}

function submitRegistration(msg, action)
{
	if(confirm(msg))
	{
		document.administrationForm.actionType.value = action ;
  		document.administrationForm.submit() ;
	}
	
}

function promptMsg(msg) {
msg = msg.replace(/=/g,"\n");

  alert(msg);
}

function changeDrpDwnYear(value)
{	
	var yearToBx = document.subForm.reportYearTo;
	yearToBx.options.length = 0;
	
	yearToBx.options[yearToBx.options.length] = new Option(value, value);
	yearToBx.options.selected = 0;
}

function agencyRptRest(value)
{	
	document.subForm.reset();
	var yearToBx = document.subForm.reportYearTo;
	yearToBx.options.length = 0;
	
	yearToBx.options[yearToBx.options.length] = new Option(value, value);
	yearToBx.options.selected = 0;
	
}

function cpdManagementRptRest(value)
{	
	document.subForm.reset();
	var yearToBx = document.subForm.reportYearTo;
	yearToBx.options.length = 0;
	
	yearToBx.options[yearToBx.options.length] = new Option(value, value);
	yearToBx.options.selected = 0;
	
}

function changeAgentApptToYear(value)
{	
	var yearToBx = document.subForm.apptYearTo;
	for(var i=0; i<yearToBx.options.length; i++)
	{
		if(yearToBx.options[i].value == value)
		{	
			if(i + 2 < yearToBx.options.length)
			{
				document.subForm.apptYearTo.options.selected = i+2;	
			}
		}
	}
}

function breakOutChange() {


	  toggleBox("brkOutLabel1", 0);
	  toggleBox("brkOutLabel2", 0);
	  toggleBox("brkOutLabel3", 0);
	  toggleBox("brkOutLabel4", 0);
	  toggleBox("brkOutLabel5", 0);
      toggleBox("brkOutField1", 0);
	  toggleBox("brkOutField2", 0);
	  toggleBox("brkOutField3", 0);
	  toggleBox("brkOutField4", 0);
	  toggleBox("brkOutField5", 0);
      
   if (document.administrationForm.breakOutSession[document.administrationForm.breakOutSession.selectedIndex].value >= 1 ) 
   {
      toggleBox("brkOutLabel1", 1);
      toggleBox("brkOutField1", 1);

   }
   if (document.administrationForm.breakOutSession[document.administrationForm.breakOutSession.selectedIndex].value >= 2 ) 
   {
      toggleBox("brkOutLabel2", 1);
      toggleBox("brkOutField2", 1);
    }
   if (document.administrationForm.breakOutSession[document.administrationForm.breakOutSession.selectedIndex].value >= 3 ) 
   {
      toggleBox("brkOutLabel3", 1);
      toggleBox("brkOutField3", 13);
   }
   if (document.administrationForm.breakOutSession[document.administrationForm.breakOutSession.selectedIndex].value >= 4 ) 
   {
      toggleBox("brkOutLabel4", 1);
      toggleBox("brkOutField4", 1);
   }
   if (document.administrationForm.breakOutSession[document.administrationForm.breakOutSession.selectedIndex].value >= 5 ) 
   {
      toggleBox("brkOutLabel5", 1);
      toggleBox("brkOutField5", 1);
   }
  
   function clearRegistrationForm()
 {
 	if(document.administrationForm.candidateName.disabled == false)
 		document.administrationForm.candidateName.value="";
 	
 	if(document.administrationForm.icNumber.disabled == false)
 		document.administrationForm.icNumber.value=""; 	

 	if(document.administrationForm.hpNumber.disabled == false)
 		document.administrationForm.hpNumber.value=""; 	 	

 	if(document.administrationForm.emailAddr.disabled == false)
 		document.administrationForm.emailAddr.value=""; 

 	if(document.administrationForm.agentId.disabled == false)
 		document.administrationForm.agentId.value="";  		

 	if(document.administrationForm.agentCode.disabled == false)
 		document.administrationForm.agentCode.value=""; 	

 	if(document.administrationForm.comments.disabled == false)
 		document.administrationForm.comments.value="";  	

 	if(document.administrationForm.department.disabled == false)
 		document.administrationForm.department.value="";   		

 	if(document.administrationForm.refAgentName.disabled == false)
 		document.administrationForm.refAgentName.value="";  	

 	if(document.administrationForm.refAgentId.disabled == false)
 		document.administrationForm.refAgentId.value=""; 

 	if(document.administrationForm.refAgentCode.disabled == false)
 		document.administrationForm.refAgentCode.value="";  		 					 			

 	if(document.administrationForm.leaderName.disabled == false)
 		document.administrationForm.leaderName.value=""; 

 	if(document.administrationForm.leaderAgentId.disabled == false)
 		document.administrationForm.leaderAgentId.value="";  	

 	if(document.administrationForm.leaderAgentCode.disabled == false)
 		document.administrationForm.leaderAgentCode.value="";  		

 	if(document.administrationForm.specialRequest.disabled == false)
 		document.administrationForm.specialRequest.value="";  		

	alert("1");
	if(document.administrationForm.attStatus.disabled == false)
	{
		alert("2");
 		document.administrationForm.attStatus.selectedIndex=0; 
 		toggleBox("divComments1", 0);  			 		
 	}
 }

 function clearExamRegistrationForm()
 {
 	if(document.administrationForm.candidateName.disabled == false)
 		document.administrationForm.candidateName.value="";
 	
 	if(document.administrationForm.icNumber.disabled == false)
 		document.administrationForm.icNumber.value=""; 	

 	if(document.administrationForm.hpNumber.disabled == false)
 		document.administrationForm.hpNumber.value=""; 	 	

 	if(document.administrationForm.emailAddr.disabled == false)
 		document.administrationForm.emailAddr.value=""; 

 	if(document.administrationForm.cdSerialNum.disabled == false)
 		document.administrationForm.cdSerialNum.value="";  		

 	if(document.administrationForm.agentId.disabled == false)
 		document.administrationForm.agentId.value="";  		

 	if(document.administrationForm.agentCode.disabled == false)
 		document.administrationForm.agentCode.value=""; 		

 	if(document.administrationForm.department.disabled == false)
 		document.administrationForm.department.value="";   		

 	if(document.administrationForm.refAgentName.disabled == false)
 		document.administrationForm.refAgentName.value="";  	

 	if(document.administrationForm.refAgentId.disabled == false)
 		document.administrationForm.refAgentId.value=""; 

 	if(document.administrationForm.refAgentCode.disabled == false)
 		document.administrationForm.refAgentCode.value="";  		 					 			

 	if(document.administrationForm.leaderName.disabled == false)
 		document.administrationForm.leaderName.value=""; 

 	if(document.administrationForm.leaderAgentId.disabled == false)
 		document.administrationForm.leaderAgentId.value="";  	

 	if(document.administrationForm.leaderAgentCode.disabled == false)
 		document.administrationForm.leaderAgentCode.value="";  		

 	if(document.administrationForm.comments.disabled == false)
 		document.administrationForm.comments.value="";  			 		
 } 

 function clearEventRegistrationForm()
 {
 	if(document.administrationForm.candidateName.disabled == false)
 		document.administrationForm.candidateName.value="";
 	
 	if(document.administrationForm.icNumber.disabled == false)
 		document.administrationForm.icNumber.value=""; 	

 	if(document.administrationForm.hpNumber.disabled == false)
 		document.administrationForm.hpNumber.value=""; 	 	

 	if(document.administrationForm.emailAddr.disabled == false)
 		document.administrationForm.emailAddr.value=""; 

 	if(document.administrationForm.cdSerialNum.disabled == false)
 		document.administrationForm.cdSerialNum.value="";  		

 	if(document.administrationForm.agentId.disabled == false)
 		document.administrationForm.agentId.value="";  		

 	if(document.administrationForm.agentCode.disabled == false)
 		document.administrationForm.agentCode.value=""; 		

 	if(document.administrationForm.department.disabled == false)
 		document.administrationForm.department.value="";   		

 	if(document.administrationForm.refAgentName.disabled == false)
 		document.administrationForm.refAgentName.value="";  	

 	if(document.administrationForm.refAgentId.disabled == false)
 		document.administrationForm.refAgentId.value=""; 

 	if(document.administrationForm.refAgentCode.disabled == false)
 		document.administrationForm.refAgentCode.value="";  		 					 			

 	if(document.administrationForm.leaderName.disabled == false)
 		document.administrationForm.leaderName.value=""; 

 	if(document.administrationForm.leaderAgentId.disabled == false)
 		document.administrationForm.leaderAgentId.value="";  	

 	if(document.administrationForm.leaderAgentCode.disabled == false)
 		document.administrationForm.leaderAgentCode.value="";  		

 	if(document.administrationForm.request.disabled == false)
 		document.administrationForm.request.value="";  			 		
 } 

  }

