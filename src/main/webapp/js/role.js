function openAddDialog() {
	$("#dlg").dialog("open").dialog("setTitle","添加角色");
	resetValue();
	$("#id").val("");
}

//添加和更新
function saveRole() {
	var id = $("#id").val();
	var url = "add";
	if (id != null && $.trim(id).length > 0 && !isNaN(id)) {
		url="update";
	}
	$("#fm").form("submit",{
		url:url,
		onSubmit:function(){
			console.log($("#roleName").val());
			if (null == $("#roleName").val() || $("#roleName").val() == "") {
				$.messager.alert("系统提示","请输入角色名称");
				return false;
			}
			return $(this).form("validate");
		},
		success:function(resp) {
			var resp=JSON.parse(resp);
			if (resp.resultCode==1) {
				$.messager.alert("系统提示","操作成功");
				resetValue();
				$("#dlg").dialog("close");
				$("#dg").datagrid("reload");
			} else{
				$.messager.alert("系统提示",resp.resultMessage);
				return;
			}
		}
	})
}

//删除
function deleteRoles() {
	var selectRows = $("#dg").datagrid("getSelections");
	if (selectRows.length == 0) {
		$.messager.alert("系统提示","请选择记录进行删除");
		return;
	}
	var strids =[];
	var message = "<font style:color='red'>您确定要删除"+selectRows.length+"条记录吗</font>";
	$.messager.confirm("系统提示",message,function(r) {
		if (r) {
			for (var i=0;i<selectRows.length;i++){
				strids.push(selectRows[i].id);
			}
			var ids = strids.join(",");
			$.post("delete",{ids:ids},function(result) {
				if (result.resultCode==1) {
					$.messager.alert("系统提示","删除成功");
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("系统提示","数据删除失败，请联系管理员");
					return;
				}
			})
		}
	})
}

function relatePermissions() {
	var selectRows = $("#dg").datagrid("getSelections");
	if (selectRows.length != 1) {
		$.messager.alert("系统提示","请选择一条要编辑的数据");
		return;
	}
	var roleId=selectRows[0].id;
	var url = ctx +'module/relate_permission?roleId='+roleId;
	window.parent.openTab('角色关联权限',url,'icon-user');
}

function openModifyDialog(){
	var selectRows = $("#dg").datagrid("getSelections");
	if (selectRows.length != 1) {
		$.messager.alert("系统提示","请选择一条要修改的记录");
		return;
	}
	var row = selectRows[0];
	$("#fm").form("load",row);
	$("#dlg").dialog("open").dialog("setTitle","修改角色");
}

function resetValue() {
	$("#roleName").val("");
	$("#roleRemark").val("");
	$("#id").val("");
}

function closeRoleDialog(){
	$("#dlg").dialog("close");
}