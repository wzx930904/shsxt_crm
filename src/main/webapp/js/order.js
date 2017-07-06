function formatState(val) {
	if (val == 0) {
		return "未回款";
	} else {
		return "已回款";
	}
}

function formatAction(val,row) {
	return "<a href='javascript:openOrderDetailsDialog("+row.id+")'>查看订单详情</a>";
}

//打开详情窗口
function openOrderDetailsDialog(orderId) {
	
	//加载详细数据
	$.post('detail',{orderId:orderId},function(result) {
		$("#fm").form("load",result);
		if (result.state == 0) {
			$("#state").val("未回款");
		} else {
			$("#state").val("已回款");
		}
	});
	$.post(ctx+"/order_details/getTotalPrice",{orderId:orderId},function(result){
		$("#totalMoney").val(result.result);
	});
	$("#dg2").datagrid("load",{"orderId":orderId});
	$("#dlg").dialog("open").dialog("setTitle","订单明细");
}