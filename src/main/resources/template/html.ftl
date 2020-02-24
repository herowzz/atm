<#setting number_format="#">
<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>
  <title>测试结果</title>
</head>
<body>
    <table width="100%">
      <tr bgcolor="#ddd" style="height:40px;">
        <td width="10%">编号</td>
        <td width="15%">模块</td>
        <td width="15%">用例</td>
        <td width="18%">执行结果</td>
        <td width="20%">信息</td>
        <td width="10%">截图</td>
      </tr>
      <#list rsList as item>
        <tr style="height:40px;">
          <td>${item.moduleOrder}.${item.caseOrder}</td>
          <td>${item.moduleName}</td>
          <td>${item.caseName}</td>
          <td>${item.success?then("<font color='green'>成功</font>", "<font color='red'>失败</font>") + item.suspend?then("  <font color='orange'>-主流程中断</font>","") }</td>
          <td>${item.errorInfo!}</td>
          <td>
          	<#if item.errorPic??>
          		<a href="${item.errorPic}" target="_blank">查看</a>
          	</#if>
          </td>
        </tr>
      </#list>
    </table>
</body>
</html>