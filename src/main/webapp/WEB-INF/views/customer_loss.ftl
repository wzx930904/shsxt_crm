<!doctype html>
<html>
<head>
    <#include "include/common.header.ftl" >
</head>
<body style="margin: 1px">
<table id="dg" title="客户流失管理" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${ctx}/customer_loss/list" fit="true" toolbar="#tb" >
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="cusNo" width="50" align="center" hidden="true">客户编号</th>
        <th field="cusName" width="100" align="center">客户名称</th>
        <th field="cusManager" width="100" align="center">客户经理</th>
        <th field="lastOrderTime" width="100" align="center">上次下单日期</th>
        <th field="confirmLossTime" width="100" align="center">确认流失日期</th>
        <th field="state" width="80" align="center" formatter="formatState">客户状态</th>
        <th field="lossReason" width="200" align="center">流失原因</th>
        <th field="a" width="100" align="center" formatter="formatAction">操作</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        &nbsp;客户名称：&nbsp;<input type="text" id="s_cusName" size="20" onkeydown="if(event.keyCode==13) searchCustomerLoss()"/>
        &nbsp;客户经理：&nbsp;<input type="text" id="s_cusManager" size="20" onkeydown="if(event.keyCode==13) searchCustomerLoss()"/>
        &nbsp;客户状态：&nbsp;<select class="easyui-combobox" id="s_state" editable="false" panelHeight="auto" >
        <option value="">请选择...</option>
        <option value="0">暂缓流失</option>
        <option value="1">确认流失</option>
    </select>
        <a href="javascript:searchCustomerLoss()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>
<script type="text/javascript" src="${ctx}/js/customer.loss.js"></script>

</body>
</html>