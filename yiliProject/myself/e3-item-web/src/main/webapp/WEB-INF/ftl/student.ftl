<html>
	<head>
		<title>Student</title>
	</head>
	<body>
		<label>学号：</label>${student.id}
		<label>姓名：</label>${student.name}
		<label>年龄：</label>${student.age}
		<hr/>
		<table border="1">
			<tr>
			  <td>序号</td>
			  <td>学号</td>
			  <td>姓名</td>
			  <td>年龄</td>
			</tr>
			<#list stuList as stu>
				<#if stu_index % 2 == 0>
				<tr style="color:blue">
				<#else>
				<tr style="color:pink">
				</#if>
					<td>${stu_index}</td>
					<td>${stu.id}</td>
					<td>${stu.name}</td>
					<td>${stu.age}</td>
				</tr>
			</#list>
		</table>
		<br>
		当前日期：{date?string('yyyy/MM/dd HH:mm:ss')}<br>
		null值的处理：{val!"值为null"}<br>
		判断val的值是否为空：<br>
		<#if val>
			val中有内容
		<#else>
			val的值为null
		</#if>
		<br>
		引用模板测试：<br>
		<#include "hello.ftl">
	</body>
</html>