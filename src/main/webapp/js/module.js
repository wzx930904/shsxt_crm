$(function(){
	$("#grade").combobox({//层级改变
		//层级改变时触发
		onChange:function(grade) {//select的change事件
			$("#parentId").combobox("clear");
			if (grade==0) {
				$("#parentIdDiv").hide();
			}else {
				$("#parentIdDiv").show();
			}
			$("#parentId").combobox({//当层级改变时发送请求查询所对应的父级菜单并改变父级菜单的下拉值，
				valueField:'id',//值字段
				textField:'moduleName',//显示的字段
				url:'find_by_grade?grade='+(grade-1),//父级点
				panelHeight:'auto',
			})
		}
	})
})

function formatGrade(val,row) {
	if (val==0){
		return "根级";
	} else if (val==1) {
		return "第一级";
	} else {
		return "第二级";
	}
}

//添加或修改
function saveModule() {
	var id = $("#id").val();
	var url = "add";
	if (id != null && $.trim(id).length >0 && !isNaN(id)){
		url="update";
	}
	$("#fm").form("submit",{
		url:url,
		onSubmit:function(){
			if ($("#moduleName").val==null) {
				$.messager.alert("系统提示","请输入模块名称");
				return false;
			}
			return $(this).form("validate");
		},
		success:function(result){
			var result=JSON.parse(result);
			if(result.resultCode==1) {
				$.messager.alert("系统提示","保存成功");
				resetValue();
				$("#dlg").dialog("close");
				$("#dg").datagrid("reload");
			}else {
				$messager.alert("系统提示",result.message);
				return;
			}
		}
	})
}

function deleteModule() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length == 0) {
		$.messager.alert("系统提示","请选择要删除的模块");
		return;
	}
	var strIds = [];
	for (var i=0;i<selectedRows.length;i++) {
		strIds.push(selectedRows[i].id);
	}
	var ids = strIds.join(",");
	$.messager.confirm("系统提示","您确定要删除这<font color='red'>"+selectedRows.length+"</font>条数据吗",function(r){
		if (r) {
			$.post('delete',{ids:ids},function(resp){
				if (resp.resultCode==1) {
					$.messager.alert("系统提示","删除成功");
					$('#dg').datagrid("reload");
				} else {
					$.messager.alert("系统提示","数据删除失败，请联系管理员");
				}
			})
		}
	})
}

function openAddDialog() {
	$("#dlg").dialog("open").dialog("setTitle","添加模块信息");
	resetValue();
	openParentCombobox();
}

function openModifyDialog() {
	var selectRows = $("#dg").datagrid("getSelections");
	if (selectRows.length != 1) {
		$.messager.alert("系统提示","请选择一条记录");
		return;
	}
	var row =selectRows[0];
	$("#dlg").dialog("open").dialog("setTitle","修改模块信息");
	$("#fm").form("load",row);
	$("#id").val(row.id);
	openParentCombobox(row);
}

function openParentCombobox(row) {
	var grade = $("#grade").combobox("getValue");
	
	if (grade==0) {
		$("#parentIdDiv").hide();
		return;
	} else {
		$("#parentIdDiv").show();
		$("#parentId").combobox({//当层级改变时发送请求查询所对应的父级菜单并改变父级菜单的下拉值，
			valueField:'id',//值字段
			textField:'moduleName',
			url:'find_by_grade?grade='+(grade-1),
			panelHeight:'auto',
			value:row.parentId
		})
	}
}


function resetValue() {
	$("#moduleName").val("");
	$("#moduleStyle").val("");
	$("#orders").val("");
	$("#url").val("");
	$("#optValue").val("");
	$("#grade").val("");
	$("#parentIdDiv").val("");
	$("#grade").combobox("setValue",0)
}

function closeDialog() {
	$("#dlg").dialog("close");
}