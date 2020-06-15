/**
 * 
 */
function go_cart() {
	if (document.formm.quantity.value == "") {
		alert("수량을 입력해 주세요!");
		document.formm.quantity.focus;
	} else {
		document.formm.action = "cart_insert";
		document.formm.submit();
	}
}