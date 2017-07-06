function formatState (val,row) {
	if (val == 0) {
		return "暂缓流失";
	} else {
		return "确认流失";
	}
}

function searchCustomerLoss() {
	var data = {
			 "cusName" : $("#s_cusName").val(),
			 "cusManager" : $("#s_cusManager").val(),
			 "state" : $("#s_state").combobox("getValue")
	};
	$("#dg").datagrid("load",data)
}

function formatAction(val,row) {
	if (row.state==1) {
		return "客户确认流失";
	} else {
		return "<a href='javascript:openCustomerReprieve("+row.id+")'>暂缓流失</a>"
	}
}

function openCustomerReprieve(id) {
	var url = ctx+"/customer_reprieve/index?lossId="+id;
	window.parent.openTab("客户流失暂缓措施管理",url,"icon-khlsgl");
}
