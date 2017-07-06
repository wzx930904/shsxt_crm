function openCustomerOrder() {
	var selectRows = $("#dg").datagrid("getSelections");
	if (selectRows.length != 1) {
		$.messager.alert("系统提示","请选择一条记录查看");
		return;
	}
	var url = ctx+"order/index?cusId="+selectRows[0].id;
	window.parent.openTab('客户历史订单查询',url,'icon-lsdd');
}