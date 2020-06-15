/**
 * 
 */
function go_next() {
	if(document.formm.okon1[0].checked == true){
		document.formm.action = "join_form"; // 요청 문자열
		document.formm.submit();
	} else if (document.formm.okon1[1].checked == true){
		alert("약관에 동의하셔야 합니다!");
	}
}

function idcheck() {
	if (document.formm.id.value==""){
		alert("아이디를 입력해 주세요!");
		document.formm.id.focus();
		return;
	}
	
	// 아이디 중복 체크 창 생성.
	var url = "id_check_form?id=" + document.formm.id.value;
	
	window.open(url, "_blank_1", "toolbar-no, menubar=no," +
			" scrollbars=yes, resizable=no, width=330, height= 200;");
}

function idok() {
	document.formm.action = "id_check_confirm";
	document.formm.submit();
}

// 동이름 입력 후 우편번호 조회 창 생성
function post_zip() {
	var url = "find_zip_num";
		
	window.open(url, "_blank_1", "toolbar-no, menubar=no," +
		" scrollbars=yes, resizable=no, width=330, height= 330;");
}

function go_save(){
	if (document.formm.id.value==""){
		alert("아이디를 입력해 주세요!");
		document.formm.id.focus();
		return;
	}
	if (document.formm.reid.value==""){
		alert("아이디 중복체크를 확인해 주세요!");
		document.formm.id.focus();
		return;
	}
	if (document.formm.pwd.value==""){
		alert("비밀번호를 입력해 주세요!");
		document.formm.pwd.focus();
		return;
	}
	if (document.formm.pwdCheck.value==""){
		alert("비밀번호를 한번 더 입력해 주세요!");
		document.formm.pwdCheck.focus();
		return;
	}
	if (document.formm.pwd.value != document.formm.pwdCheck.value){
		alert("비밀번호가 동일하지 않습니다!");
		document.formm.pwd.focus();
		return;
	}
	if (document.formm.name.value==""){
		alert("이름을 입력해 주세요!");
		document.formm.name.focus();
		return;
	}
	if (document.formm.email.value==""){
		alert("이메일을 입력해 주세요!");
		document.formm.email.focus();
		return;
	}
}