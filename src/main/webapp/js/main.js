function openTab(text,url,iconCls){
	if($("#tabs").tabs("exists",text)){//如果有
		$("#tabs").tabs("select",text);//选中页签
	} else{//如果不存在打开页签
		var content="<iframe frameborder=0 scrollin='auto' style='width:100%;height:100%' src='"+url+"'></iframe>";
		$("#tabs").tabs('add',{
			title:text,
			iconCls:iconCls,
			closable:true,
			content:content
		});
	}
}