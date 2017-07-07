function openAddDialog() {
	$("#dlg").dialog("open").dialog("setTitle","创建用户");
	resetValue();
}

function searchUser() {
	var userName = $("#s_userName").val();
	$("#dg").datagrid("load",{userName:userName});
}

function openModifyDialog() {
	var selectRows = $("#dg").datagrid("getSelections");
	if (selectRows.length != 1) {
		$.messager.alert("系统提示","请选择一条记录进行修改");
		return;
	}
	
	var row = selectRows[0];
	$("#dlg").dialog("open").dialog("setTitle","修改用户")
	$("#fm").form("load",row);
	$("#userId").val(row.id)
}

function saveUser() {
	var id=$("#id").val();
	var url = "add";
	if (id != null && $.trim(id).length >0 && !isNaN(id)) {
		url = "update";
	}
	$("#fm").form("submit",{
		url:url,
		onSubmit:function(){
			if ($("#roleIds").combobox("getValue") == ""||$("#roleIds").combobox("getValue") == null) {
				$.messager.alert("系统提示","请选择角色");
				return false;
			}
			return $(this).form("validate");
		},
		success:function(result) {
			var result = JSON.parse(result);
			if (result.resultCode == 1) {
				$.messager.alert("系统提示","操作成功");
				$("#dlg").dialog("close");
				$("#dg").datagrid("reload")
			} else {
				$.messager.alert("系统提示",result.result);
			}
		}
	})
}

function deleteUser() {
	var selectRows = $("#dg").datagrid("getSelections");
	if (selectRows.length < 1) {
		$.messager.alert("系统提示","请选择记录进行删除");
		return;
	}
	var strIds = [];
	for (var i=0;i<selectRows.length;i++) {
		strIds.push(selectRows[i].id);
	}
	var ids = strIds.join(",");
	var message = "<font color='red'>您确定要删除这"+selectRows.length+"条记录吗</font>"
	$.messager.confirm("系统提示",message,function(r){
		if (r) {
			$.post("delete",{ids:ids},function(result){
				if (result.resultCode==1) {
					$.messager.alert("系统提示","删除成功");
					$("#dg").datagrid("reload");
				}else {
					$.messager.alert("系统提示","删除失败，请联系管理员");
				}
			})
		}
	})
}

function resetValue() {
	$("#userName").val("");
	$("#password").val("");
	$("#trueName").val("");
	$("#email").val("");
	$("#phone").val("");
	$("#id").val("");
}

function closeDialog() {
	$("#dlg").dialog("close");
}