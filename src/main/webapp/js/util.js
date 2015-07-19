''// br 테그를 newline(\n)
function br2newline(str){
	return str.replace(/<br>/g, "\n")
}

//  newline(\n)을 br 테그로
function newline2br(str){
	return str.replace(/\n/g, "<br>")
}

// Html 테크 제거
function removeTags (str) {
	return str.replace(/<(?:.|\n)*?>/gm, '');
}

// 매칭 문자열 교체
function replaceAll(str, find, replace) {
	return str.replace(new RegExp(find, 'g'), replace);
}

//file encodeing utf-8 (2009-12-21)
{
	//현재 웹 어플에 의존적인 함수 정의
	$.APP = new Object();
	
	// 이미지 업로드 창 
	$.APP.openImageUpload = function(){
		$.POPUP.popupWindowCenter($.APP.getContextRoot("image/upload.do"), "imageupload", 500, 600, false, false, false);
	};
	
	/**
	 * 
	 * 맨 끝에 '/'가 포함된 URL 반환<br/>
	 * 예) /, /abcd/
	 * @returns
	 */
	$.APP.getContextRoot = function(url){
		if(url == null){
			return $("meta[name='contentRoot']").attr("content");
		}
		else{
			var rtnValue = $("meta[name='contentRoot']").attr("content") + url;
			// // -> / 변경
			return rtnValue.replace(/\/\//gi, "/");
		}
	}	
	
	$.APP.imgPopup = function(imgPath){
  	var url = getContextRoot(imgPath);
  	$("._img_popup a").on("click", function(){
  		$("._img_popup").dialog("close");
  	});
  	
  	$("._img_popup img").attr("src", url);
	 	$("._img_popup").dialog({
      resizable: false,
      modal: true,
      width:'auto'
		});
  };

}


// file encodeing utf-8 (2009-12-21)
// 문자열 연산 관련 클래스 

{
	$.STR = new Object();
	
	$.STR.ALPHABET  = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
	
	// 파일 확장자 체크 .(쩜)포함
	$.STR.getExt = function(val) {
		val = val.slice(val.lastIndexOf("\\") + 1);
		ext = val.slice(val.lastIndexOf(".")).toLowerCase();
		return ext;
	};

	// 이미지 확장자를 가지면 true, 아니면  false
	$.STR.isImage = function(filename){
		var ext = $.STR.getExt(filename);
		ext = ext.toLowerCase();
		return (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png");
	};
		
	// 파일 이름(확장자 제외)
	// val: 풀 경로 문자열
	// 디렉토리 구별자 (기본값 '\')
	$.STR.getFilenameWithOutExt = function(val, dirSeparator) {
		if(dirSeparator == null){
			dirSeparator = "\\";
		}
		var start = val.lastIndexOf(dirSeparator) + 1;
		var end = val.lastIndexOf(".");
		end = end > 0 ? end : val.length(); 
		var name = val.substring(start, end);
		return name ;
	};

	/**
	 * @param val
	 * @return 호스트 이름을 제외한 URL<br>
	 *         예)http://aaa.com/abc/efg.jsp?a=bb = > /abc/efg.jsp
	 */
	$.STR.gerUrl = function(val) {
		var url;
		if (val.indexOf("http://") != -1) {
			url = val.substring(val.indexOf("/", 8));
		}
		if(url.indexOf("?") != -1){
			url = url.substring(0, url.indexOf("?"));
		}
		return url;
	};
	// conditionStr 비교 하여 null면 nullStr 리턴
	$.STR.nvl = function(conditionStr, nullStr){
		if(conditionStr == null || conditionStr == ""){
			return nullStr;
		}
		return conditionStr;
	};
	
	
	// 문자열이 비어 있는지 체크(스페이스만 있는 문자열도 비여 있다고 판단)
	$.STR.isEmpty = function(toCheck) {
		var chkstr = $.STR.trim(toCheck) + "";
		var is_Space = true ;
		if ( ( chkstr == "") || ( chkstr == null ) )
			return true;

		for ( j = 0 ; is_Space && ( j < chkstr.length ) ; j++) {
			if( chkstr.substring( j , j+1 ) != " " )
				is_Space = false ;
		}

		return is_Space;
	};
	
	// 양쪽 공백을 제거
	$.STR.trim = function(b) {
    var i, startIdx, endIdx;
    if (b == null)
        return "";

    startIdx = 0;
    endIdx   = b.length;
     for(i=0; i < b.length ; i++)
        if ( b.charAt(i) != " " )
        {
            startIdx = i;
            break;
        }

     for(i=b.length; i >= 0 ; i--)
        if ( b.charAt(i-1) != " " )
        {
            endIdx = i;
            break;
        }
     return b.substring(startIdx, endIdx);
	};
	
	//기능   : 영문자.한글 별로 length를 return ..IE4.0 이상
	$.STR.strLeng = function(strIn) {
		var strOut  = 0;
		var agr     = navigator.userAgent;
		var isIE   = agr.indexOf("MSIE");
		
		if(isIE != -1){
			for ( i = 0 ; i < strIn.length ; i++)	{
				ch = strIn.charAt(i);
				if ((ch == "\n") || ((ch >= "ㅏ") && (ch <= "히")) || ((ch >="ㄱ") && (ch <="ㅎ")))
						strOut += 2;
				else
						strOut += 1;
			}
		}
		else{
			strOut = strIn.length ;
		}
		return (strOut);
	};
	
	//숫자인지를 체크
	$.STR.isNumber = function(val, allowable) {
		valid = true;
		cmp = "0123456789" + allowable;

		for (i=0; i<val.length; i++) {
			if (cmp.indexOf(val.charAt(i)) < 0)	{
				valid = false;
				break;
			}
		}
		return valid;
	};
	
	$.STR.isInteger = function(st) {
		if (!$.STR.isEmpty(st)) {
			for (j=0; j<st.length; j++) {
				if (((st.substring(j, j+1) < "0") || (st.substring(j, j+1) > "9")))
						return false;
			}
		}
		else {
				return false ;
		}
	  return true ;
	};
	
	//=============================================
	//  inputValue값의 cnt에서 inputValue의 길이를
	//  뺀 수만큼 padChar문자로 채워줌
	//=============================================
	$.STR.paddingLeftChar = function(inputValue, cnt, padChar) {
		var tmpValue = "";
		inputValue =$.STR.trim(inputValue);

		for(var i=0; i < (cnt-inputValue.length); i++)
			tmpValue += padChar;
		return (tmpValue + inputValue);
	};
};


// 팝업창 관련 
{
	$.POPUP = new Object();
	// 기본적인 크기와 위치 정보만으로 팝업창 띄우기
	$.POPUP.popupWindow = function(url, name, width, height, left, top) {
	  return this.popupWindowEx(url, name, width, height, left, top, 0, 0, 0);
	};

	// 창의 크기와 위치정보, 옵션들을 반영하여 팝업창 띄우기
	$.POPUP.popupWindowEx = function(url, name, width, height, left, top, isResizable, isScrollable, useStatus) {
	  var optArray = new Array();
	  var idx = 0;
	  if (width)        optArray[idx++] = "width=" + width;
	  if (height)       optArray[idx++] = "height=" + height;
	  if (left)         optArray[idx++] = "left=" + left;
	  if (top)          optArray[idx++] = "top=" + top;
	  if (isResizable)  optArray[idx++] = "resizable=" + isResizable;
	  if (isScrollable) optArray[idx++] = "scrollbars=" + isScrollable;
	  if (useStatus)    optArray[idx++] = "status=" + useStatus;

	  var opts = "";
	  for (idx = 0; idx < optArray.length; idx++) {
	    if (idx > 0)
	      opts += ",";
	    opts += optArray[idx];
	  }

	  return window.open(url, name, opts);
	};

	// 화면 중앙에 팝업창 띄우기
	$.POPUP.popupWindowCenter = function(url, name, width, height, isResizable, isScrollable, useStatus) {
	  var x = (screen.availWidth / 2) - (width / 2);
	  if (x < 0) x = 0;
	  var y = (screen.availHeight / 2) - (height / 2);
	  if (y < 0) y = 0;
	  return this.popupWindowEx(url, name, width, height, x, y, isResizable, isScrollable, useStatus);
	};

	// 팝업창에 로그 값을 출력 
	// alert 보다 유용하 점은 값을 복사 할 수 있다는 것
	$.POPUP.popupLog = function(logVal){
		var obj = this.popupWindowCenter("about:blank", "logWin", 100, 100, 1, 1, false);
		obj.document.write(logVal);
	};

	// 모달로 창 띄우기
	$.POPUP.modalOpen = function( url, args, width, height ){
		var env_options = "dialogHeight: " + height + "px; dialogWidth: " + width 
				+ "px;  edge: Raised; center: Yes; help: No; scroll: No; resizable: No; status: No;";
	  return window.showModalDialog( url, args, env_options);
	};
	
	$.POPUP.modalOpenEx = function( url, args, width, height, scroll, resizable, status )
	{
		var env_options = "dialogHeight: " + height + "px; dialogWidth: " + width 
				+ "px;  edge: Raised; center: Yes; help: No; scroll: "+scroll+"; resizable: "+resizable+"; status: "+status+";";
	 	return window.showModalDialog( url, args, env_options);
	};
	
	$.POPUP.modalessOpen = function( url, args, width, height ){
		var env_options = "dialogHeight: " + height + "px; dialogWidth: " + width 
				+ "px;  edge: Raised; center: Yes; unadorned: Yes ; help: No; scroll: No; resizable: No; status: No;";
		return window.showModelessDialog( url, args, env_options);
	};
}	



{
	$.COOKIE = new Object();
	$.COOKIE.getCookieVal = function(offset) {
	  var endstr = document.cookie.indexOf (";", offset);
	  if (endstr == -1) endstr = document.cookie.length;
	  return unescape(document.cookie.substring(offset, endstr));
	};

	$.COOKIE.getCookie = function(name) {
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		while (i < clen) {	//while open
			var j = i + alen;
			if (document.cookie.substring(i, j) == arg)
				 return this.getCookieVal (j);
			i = document.cookie.indexOf(" ", i) + 1;
			if (i == 0) break;
		}	//while close
		return null;
	};

	// 시간은 분단위
	$.COOKIE.setCookieVal = function(name, value, time) {
		pathname = location.pathname;
		var ExpDate = new Date();
		var myDomain = pathname.substring(0, pathname.lastIndexOf('/')) +'/';

		// 이렇게 쓸수도 있다.
		var myDomain = '/';
		ExpDate.setTime(ExpDate.getTime() + 1000*60* time);
		this.setCookie(name, value, ExpDate, myDomain);
	};

	$.COOKIE.setCookie = function(name, value) {
		var argv = this.setCookie.arguments;
		var argc = this.setCookie.arguments.length;
		var expires = (2 < argc) ? argv[2] : null;
		var path = (3 < argc) ? argv[3] : null;
		var domain = (4 < argc) ? argv[4] : null;
		var secure = (5 < argc) ? argv[5] : false;
		document.cookie = name + "=" + escape (value) +
			((expires == null) ? "" :
				 ("; expires=" + expires.toGMTString())) +
			((path == null) ? "" : ("; path=" + path)) +
			((domain == null) ? "" : ("; domain=" + domain)) +
			((secure == true) ? "; secure" : "");
	};
	// 브라우저 인스턴스 쿠기 남김
	$.COOKIE.setCookieInstacne = function(name, value, path){
		document.cookie = name + "=" + value + "; path="+path+";" ;
	};
}

{
	$.FORM = new Object();

	$.FORM.isEmptyRtnMsg = function(obj, msg, len) {

		var toCheck  = obj.value ;
		var chkstr   = $.STR.trim(toCheck) + "";
		var is_Space = true ;

		if ( $.STR.isEmpty(toCheck) ) {
			alert( msg );
			obj.focus();
			obj.select();
			return true ;
		}

		for ( j = 0 ; is_Space && ( j < chkstr.length ) ; j++) {
			if( chkstr.substring( j , j+1 ) != " " )
					is_Space = false ;
		}

		if ( is_Space )	{
			alert( msg );
			obj.focus();
			obj.select();
			return true ;
		}

		if (len != null) {
			if ($.STR.strLeng(obj.value) > len) {
				alert( msg +" 한글 "+len/2+",영문 "+len+"자 이내로 입력하십시오.");
				obj.focus();
				obj.select();
				return true ;
			}
			else {
				return false ;
			}
		}
		else
			return false ;
	};

	//다른것 isEmptyRtnMsg와 같고, len만 이하체크를 한다.
	$.FORM.isEmptyRtnMsg2 = function(obj, msg, len) {
		var toCheck  = obj.value ;
		var chkstr   = $.STR.trim(toCheck) + "";
		var is_Space = true ;
	
		if ( $.STR.isEmpty(toCheck) ) {
			alert( msg );
			obj.focus();
			obj.select();
			return true ;
		}
	
		for ( j = 0 ; is_Space && ( j < chkstr.length ) ; j++) {
			if( chkstr.substring( j , j+1 ) != " " )
					is_Space = false ;
		}
	
		if ( is_Space )	{
			alert( msg );
			obj.focus();
			obj.select();
			return true ;
		}
	
		if (len != null) {
			if ($.STR.strLeng(obj.value) < len) {
				alert( msg );
				obj.focus();
				obj.select();
				return true ;
			}
			else {
				return false ;
			}
		}
		else
			return false ;
	};
	
	$.FORM.select = function(obj){
		obj.focus();
		obj.select();
	};

	// 경고 메세지도 같이
	$.FORM.selectMsg = function(obj,msg){
		obj.focus();
		obj.select();
		alert(msg);
	};
	
	// 이메일 형식 체크
	// 형식에 위배되면 return false
	$.FORM.isValidEmail = function(obj){
		valid = false;
		val   = obj.value;

		// space within email?
		if (val.indexOf(" ") != -1)
			alert("Email주소에 공백은 허용되지 않습니다.!");
		else if (val.indexOf("@") < 1)
			alert("Email주소 지정이 잘못되었습니다. '@'이 누락되었습니다.");
		else if (val.indexOf(".") == -1)
			alert("Email주소 지정이 잘못되었습니다. '.'이 누락되었습니다.");
		else if (val.indexOf(".") - val.indexOf("@") == 1)
			alert("Email주소 지정이 잘못되었습니다. '@' 다음에 바로 '.'이 올 수 없습니다.");
		else if (val.charAt(val.length-1) == '.')
			alert("Email주소 지정이 잘못되었습니다. '.'은 Email주소 끝에 올 수 없습니다.");
		else
			valid = true;

		if (valid == false){
			obj.focus();
			obj.select();
		}

		return valid;
	};
	
	
	//  숫자여부 체크. 
	$.FORM.isIntegerRtnMsg = function(obj, msg, len) {
		var st = $.STR.trim(obj.value);
		if (!$.STR.isEmpty(st)) {
			for (j=0; j<st.length; j++)	{
				if (((st.substring(j, j+1) < "0")||(st.substring(j, j+1)>"9")))	{
					alert( msg +" 숫자로 입력하십시오.");
					obj.focus();
					obj.select();
					return false;
				}
			}
		}
		else {
			alert( msg +" 입력하십시오.");
			obj.focus();
			obj.select();
			return false ;
		}

		if ( len != null ) {
			if ( $.STR.strLeng(st) != len ) {
				alert( msg +" " +len+"자로 입력하십시오.");
				obj.focus();
				obj.select();
				return false ;
			}
		}

		return true ;
	};	
	
	// 숫자여부 체크. 자릿수 범위 지정
	$.FORM.isIntegerRtnMsgRange = function(obj, msg, slen, elen) {
		var st = $.STR.trim(obj.value);
		if (!$.STR.isEmpty(st)) {
			for (j=0; j<st.length; j++)	{
				if (((st.substring(j, j+1) < "0")||(st.substring(j, j+1)>"9")))	{
					alert( msg +" 숫자로 입력하십시오.");
					obj.focus();
					obj.select();
					return false;
				}
			}
		}
	  else {
			alert( msg +" 입력하십시오.");
			obj.focus();
			obj.select();
			return false ;
	  }
	  if ( elen != null ) {
			if ( $.STR.strLeng(st) < slen || $.STR.strLeng(st)> elen ) {
				alert( msg +" " +slen+" ~ "+elen+"자로 입력하십시오.");
				obj.focus();
				obj.select();
				return false ;
			}
	  }
	  return true ;
	};
	
	// 패스워드 체크 
	$.FORM.passwdCheck = function(obj1, obj2, len) {
	  var str = obj1.value;

		if ($.STR.strLeng(obj1.value) < len){
			alert("비밀번호는 " + len + "자 이상으로 입력하십시오");
			obj1.focus();
			obj1.select();
			return false;
		}
		
		if ( obj2.value == "" ){
			alert("비밀번호 확인을 위하여 비밀번호를 다시 입력하십시오.");
			obj2.focus();
			obj2.select();
			return false;
		}
		
		if ( obj1.value != obj2.value ){
			alert("비밀번호와 비밀번호 확인이 다릅니다.비밀번호를 확인후 다시 입력하십시오.");
			obj2.focus();
			obj2.select();
			return false;
		}
		return true ;
	};

	// 체크박스 전체 선택 해제
	$.FORM.checkboxAll = function (ck, target, desable) {
		
		var val = false;
		var i;
		
		if (ck == null)
			return;

		if(ck.checked) {
			val = true;
		}
		else {
			val = false;
		}
		if (target == null)
			return;

		if(target.length== null) {
			target.checked = val;
		}else {
			for(i=0;i<target.length;i++){
				
				target[i].checked = val;
				if(val == true & desable != null){
					target[i].disabled = true;
				}
				else{
					target[i].disabled = false;
				}
			}
		}
	};
	
	// 라디오(체크박스)버튼 체크여부
	// 하나 이상 체크되면 true, 아니면 false
	$.FORM.isChoicedRadio = function(rb, msg){

		// 객체 아님
		if(rb == null){
			return false;
		}

		// 배여 아님
		if (rb.length == null){
			if(rb.checked){
				return true;
			}
			else{
				alert(msg);
				rb.focus();
				return false;
			}
		}

		var ck = false;

		// 배열이면
		var i;

		for(i=0; i<rb.length; i++) {
			if(rb[i].checked){
				ck = true;
			}
		}
		if(ck){
			return true;
		}
		else{
			alert(msg);
			rb[0].focus();
			return false;
		}
	};

	$.FORM.eventComma = function (szName){
		var elements = document.getElementsByName(szName);  // Element[]을 리턴
		for(var i=0; i<elements.length; i++){
			elements[i].onkeyup = function(ev){     // keyup 이벤트
				// 브라우저에 따른 이벤트 처리
				var target = null;        // 이벤트가 발생된 녀석을 담을 변수   
				if(ev)           
					target = ev.target;       // 모질라 파이어폭스
				else
					target = window.event.srcElement;   // 익스플로어
    
				// 숫자를 원단위로 변환
				target.value = $.FORM.changeComma(target.value);
			};
			elements[i].value = $.FORM.changeComma(elements[i].value);
		} 
	};

	$.FORM.changeComma = function(szNumber){
		if(szNumber == "" || szNumber == "0")
			return "";
	  
		var returnValue = 0;
		var temp1 = szNumber.replace(/,/g,"");   // 입력 데이터를 숫자 형태로 변환
		var temp = temp1.split('.');
	 
		// 정수자리 원단위로 만들기
		var num1 = "";
		var comma = 1;
		for(var i = temp[0].length -1; i >= 0; i--){
			num1 += temp[0].charAt(i);

			if(comma % 3 == 0 && comma != 0){
				num1 += ",";
			} // end if
			comma++;
		} // end for
	 

		var num2 = "";
		for(var i = num1.length -1; i >= 0; i--){
			num2 += num1.charAt(i);
		} // end for
	 
		// 소수점이 있다면...
		if(temp.length > 1){

			// 소수점 자리 원 단위로 만들어서 리턴..!!
			var num3 = "";
			for(var i=1; i <= temp[1].length; i++){
				num3 += temp[1].charAt(i-1);
	   
				if((i%3 == 0) && (i != 0)){
					num3 += ",";
				}
			} // end for
	  
			var num4 = num2 + "." + num3;
			returnValue = num4.replace(/(^,)|(,$)/g,"");
		} // end if
		else
			returnValue = num2.replace(/(^,)|(,$)/g,"");; // 앞,뒤 콤마 제거
	  
		if(returnValue == "" || returnValue == ".")
			return "";
		else
			return returnValue;
	};
		

	// 실시간 글자수 체크
	// 이벤트는 onkeyup로
	// input : 입력객체
	// maxSize : 글자 입력 사이즈 byte단위
	// out: 출력객체, 파라미터 전달 안해됨 (<span id="blogInfoSize"></span> 이런식으로 사용하면 된다.)

	// <textarea name="info" onkeyup="$.FORM.inputLimitSize(this, 200, 'contentSize');;"></textarea>
	$.FORM.inputLimitSize = function(input, maxSize, outObject)	{
		var str = input.value;
		var len = $.STR.strLeng(str);

		if (maxSize < len) {
		  alert("한글 " + (maxSize/2) +"자 이내, 영문 " + maxSize + "자 이내로 입력하세요.");
			input.value = $.STR.cutAbsStr(str, maxSize);
		}
		var out = $("#" + outObject)[0];
		if(out != null)
			out.innerHTML = $.STR.strLeng(input.value);
	};
	

	// 숫자 입력 체크 
	$.FORM.checkNumber = function(textObj){
		if(!$.STR.isNumber(textObj.value, ",")){
			$.FORM.selectMsg(textObj, "숫자로 입력해주세요.");
			textObj.value="";;
		}
	};	
	
	
}


{
	$.DATE = new Object();

	$.DATE.montharr = function(m0,m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11) {
    this[0] = m0;
    this[1] = m1;
    this[2] = m2;
    this[3] = m3;
    this[4] = m4;
    this[5] = m5;
    this[6] = m6;
    this[7] = m7;
    this[8] = m8;
    this[9] = m9;
    this[10] = m10;
    this[11] = m11;
	};
	
	//YYYYMMDD를 check한다.
	$.DATE.checkDate = function( dateVal ) {
		var isDate  = true ;
		
		if ( dateVal.length != 8 )  {
			isDate = false ;
		}
		else {
			var yy = dateVal.substring(0,4) +"" ;
			var mm = dateVal.substring(4,6) +"" ;
			var dd = dateVal.substring(6,8) +"" ;
		
			if (!$.DATE.checkYYYY(yy)) {
					isDate = false ;
			}
		
			else if (!$.DATE.checkMM(mm))	{
				isDate = false ;
			}
		
			else if (!$.DATE.checkDD(yy,mm,dd))	{
				isDate = false ;
			}
		}
		return isDate ;
	};
	
	//YYYY-MM-DD를 check한다.
	$.DATE.checkDate2 = function( dateVal ) {
		var isDate  = true ;
		if ( dateVal.length != 10 ) {
			isDate = false ;
		}
		else {
			var yy = dateVal.substring(0,4) +"" ;
			var mm = dateVal.substring(5,7) +"" ;
			var dd = dateVal.substring(8,10) +"" ;
		
			if (!$.DATE.checkYYYY(yy)) {
				isDate = false ;
			}
			else if (!$.DATE.checkMM(mm)) {
				isDate = false ;
			}
			else if (!$.DATE.checkDD(yy,mm,dd))	{
				isDate = false ;
			}
		}
		return isDate ;
	};
	
	
	//HH를 Check한다.
	$.DATE.checkHH = function( toCheck ) {
		return ((!$.Str.isEmpty(toCheck)) && ($.STR.isInteger(toCheck)) && ( parseFloat(toCheck) >= 0 ) && (parseFloat(toCheck) <= 23));
	};
	
	//HHMM를 Check한다.
	$.DATE.checkHHMM = function( hh, mm ) {
		if ( $.DATE.checkHH(hh) ){
			return ((!$.Str.isEmpty(mm)) && ($.STR.isInteger(mm)) && ( parseFloat(mm) >= 0 ) && (parseFloat(mm) <= 59));
		}
		return false;
	};
	
	//HHMMSS를 Check한다.
	$.DATE.checkHHMMSS = function( hh, mm, ss ) {
		if ( $DATE.checkHHMM(hh,mm) ){
			return ((!$.STR.isEmpty(ss)) && ($.STR.isInteger(ss)) && ( parseFloat(ss) >= 0 ) && (parseFloat(ss) <= 59));
		}
		return false;
	};
	
	//MI를 Check한다.
	$.DATE.checkMI = function checkMI( toCheck ) {
	  return ((!$.STR.isEmpty(toCheck)) && ($.STR.isInteger(toCheck)) && ( parseFloat(toCheck) >= 0 ) && (parseFloat(toCheck) <= 59));
	};
	
	//YYYY 를 check한다.
	$.DATE.checkYYYY = function(toCheck) {
		return ( ( toCheck.length == 4) && ( $.STR.isInteger(toCheck)  ) && ( toCheck != "0000") );
	};
	
	//MM 를 check한다.
	$.DATE.checkMM = function(toCheck) {
		return ((!$.STR.isEmpty(toCheck)) && ($.STR.isInteger(toCheck)) && ( parseFloat(toCheck) > 0 ) && (parseFloat(toCheck) < 13));
	};
	
	//YYYY,MM,DD를 check한다.
	$.DATE.checkDD = function( yyyy, mm, toCheck) {
	  var isYMD   = false;
	  var monthDD = new $.DATE.montharr(31,28,31,30,31,30,31,31,30,31,30,31);
	  var im      = eval(mm) - 1;
	
	  if ( toCheck.length == 0 )
	       return false;
	
	  if ( !$.STR.isInteger(toCheck)  )
	       return false;
	
	  var dd = toCheck;
	
	  if ( ( (yyyy%4 == 0) && (yyyy%100 != 0) ) || (yyyy%400 == 0) )
	  {
	       monthDD[1] = 29;
	  }
	
	  if ( (0 < dd) && (dd <= monthDD[im]) )
	       isYMD = true;
	
	  return isYMD;
	};
	
	// yyyy-MM-dd 로 포맷된 날짜 리턴
	$.DATE.getDate = function(dd){
		var now;
		if(dd==null)
			now = new Date();
		else
			now = dd;

		var nowYear = $.STR.paddingLeftChar(now.getYear().toString(), 4, "0") ;
		var nowMonth = $.STR.paddingLeftChar((now.getMonth()+1).toString(), 2, "0") ;
		var nowDay = $.STR.paddingLeftChar(now.getDate().toString(), 2, "0") ;
		d = nowYear + "-" + nowMonth + "-" + nowDay;

		return d;
	};

	// yyyy-MM-dd 형식을 날짜로 변환
	$.DATE.getStr2Date = function(dateStr){
	    var dateinfo = dateStr.split("-");
	    return new Date(dateinfo[0] , dateinfo[1] -1 , dateinfo[2]);
	};

	// yyyy-MM-dd HH:mm:ss 형식을 날짜로 변환
	$.DATE.getStr2DateTime = function(dateStr){
		var datetime = dateStr.split(" ");
	    var dateinfo = datetime[0].split("-");
	    var timeinfo = datetime[1].split(":");
	    return new Date(dateinfo[0] , dateinfo[1] -1 , dateinfo[2], timeinfo[0], timeinfo[1], timeinfo[2]);
	};

	
	// 현재 날짜에서  diff 만큼 더한 날짜 리턴
	$.DATE.getDiffDay = function(diff){
		var day = 1000 * 60 * 60 * 24;
		var time = (new Date()).getTime() + (diff * day);
		return new Date(time);
	};
	// from 날짜와 to 날짜  - 날짜 형식 비교(yyyy-mm-dd 비교)
	$.DATE.getDiffDate = function(fromDate,toDate){
		var fDate = new Date();
		var fDates = fromDate.split("-");
		fDate.setFullYear(fDates[0], fDates[1], fDates[2]);
		var fTime = fDate.getTime();
		var tDate = new Date();
		var tDates = toDate.split("-");
		tDate.setFullYear(tDates[0], tDates[1], tDates[2]);
		var tTime = tDate.getTime();
		if(tTime>=fTime){
			return true;
		}
		return false;
	};
	// 동일 년도 비교
	$.DATE.getDiffYear = function(fromDate,toDate){
		var fDates = fromDate.split("-");
		var tDates = toDate.split("-");
		if(fDates[0]==tDates[0]){
			return true;
		}
		return false;
	};
	// 해당 년월의 마지막 날자 값  리턴
	$.DATE.getLastDay = function(fromYear,fromMonth){
		var dd = new Date(fromYear,fromMonth,0);
		return dd.getDate();
	};
	
	$.UTIL = new Object();

	//클립보드에 값 복사 하기
	$.UTIL.copyClipboard = function(text)	{
		if(!$.browser.msie){
      alert("해당 기능은 IE만 지원합니다. 직접 복사해 주세요.");
      return;
   }
		
		alert("현재 주소를 복사하였습니다. Ctrl+V를 눌러서 사용할 수 있습니다.");
		window.clipboardData.setData('text', text);
	};
	
}

//br 테그를 newline(\n)
function br2newline(str){
	return str.replace(/<br>/g, "\n");
}

//  newline(\n)을 br 테그로
function newline2br(str){
	return str.replace(/\n/g, "<br>");
}

// Html 테크 제거
function removeTags (str) {
	return str.replace(/<(?:.|\n)*?>/gm, '');
}

// 매칭 문자열 교체
function replaceAll(str, find, replace) {
	return str.replace(new RegExp(find, 'g'), replace);
}


//  사용예: (new Date()).format("yyyy-MM-dd HH:mm:ss")
Date.prototype.format = function(f) {
	if (!this.valueOf())
		return " ";

	var weekName = [ "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" ];
	var d = this;

	return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
		switch ($1) {
		case "yyyy":
			return d.getFullYear();
		case "yy":
			return (d.getFullYear() % 1000).zf(2);
		case "MM":
			return (d.getMonth() + 1).zf(2);
		case "dd":
			return d.getDate().zf(2);
		case "E":
			return weekName[d.getDay()];
		case "HH":
			return d.getHours().zf(2);
		case "hh":
			return ((h = d.getHours() % 12) ? h : 12).zf(2);
		case "mm":
			return d.getMinutes().zf(2);
		case "ss":
			return d.getSeconds().zf(2);
		case "a/p":
			return d.getHours() < 12 ? "오전" : "오후";
		default:
			return $1;
		}
	});
};

String.prototype.string = function(len) {
	var s = '', i = 0;
	while (i++ < len) {
		s += this;
	}
	return s;
};
String.prototype.zf = function(len) {
	return "0".string(len - this.length) + this;
};
Number.prototype.zf = function(len) {
	return this.toString().zf(len);
};

//  사용예: (new Date()).format("yyyy-MM-dd HH:mm:ss")
Date.prototype.format = function(f) {
	if (!this.valueOf())
		return " ";

	var weekName = [ "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" ];
	var d = this;

	return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
		switch ($1) {
		case "yyyy":
			return d.getFullYear();
		case "yy":
			return (d.getFullYear() % 1000).zf(2);
		case "MM":
			return (d.getMonth() + 1).zf(2);
		case "dd":
			return d.getDate().zf(2);
		case "E":
			return weekName[d.getDay()];
		case "HH":
			return d.getHours().zf(2);
		case "hh":
			return ((h = d.getHours() % 12) ? h : 12).zf(2);
		case "mm":
			return d.getMinutes().zf(2);
		case "ss":
			return d.getSeconds().zf(2);
		case "a/p":
			return d.getHours() < 12 ? "오전" : "오후";
		default:
			return $1;
		}
	});
};

String.prototype.string = function(len) {
	var s = '', i = 0;
	while (i++ < len) {
		s += this;
	}
	return s;
};
String.prototype.zf = function(len) {
	return "0".string(len - this.length) + this;
};
Number.prototype.zf = function(len) {
	return this.toString().zf(len);
};



