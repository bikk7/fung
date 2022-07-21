<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello Spring Boot</title>
<%--    <script type="text/javascript"--%>
<%--            src="https://ajax.aspnetcdn.com/ajax/jquery/jquery-3.5.1.min.js"></script>--%>
<%--    <script type="text/javascript">--%>

<%--         function post() {--%>
<%--	 var params = {--%>
<%--         'p_id': '123',--%>
<%--         'f_id' : '0010',--%>
<%--         'f_date' :"2020-09-04 01-53-56",--%>
<%--         'state':1--%>
<%--	 }--%>
<%--	 $.post({--%>
<%--	 url : "./insertFileMessage",--%>
<%--	 // 此处需要告知传递参数类型为JSON，不能缺少--%>
<%--	 contentType : "application/json",--%>
<%--	 // 将JSON转化为字符串传递--%>
<%--	 data : JSON.stringify(params),--%>
<%--	 // 成功后的方法--%>
<%--	 success : function(result) {--%>
<%--	 if (result == null || result.id == null) {--%>
<%--	 alert("插入失败");--%>
<%--	 return;--%>
<%--	 }--%>
<%--	 alert("插入成功");--%>
<%--	 }--%>
<%--	 });--%>
<%--	 }--%>
<%--         post();--%>
<%--    </script>--%>
</head>
<body>
<form method="post"
      action="./upload" enctype="multipart/form-data">
    <input type="file"  name="file" value="请选择上传的肺音文件" />
    <input type="file"  name="file" value="请选择上传的ct文件" />
    <input type="text"  name="p_id" value="123" />
    <input type="text"  name="collect_d_id" value="0084" />
    <input type="text"  name="f_id" value="0018" />
    <input type="text"  name="collect_time" value="2020-08-04 01-53-52" />
    <input type="text"  name="state" value="1" />
    <input type="text"  name="collect_site" value="肺叶" />
    <input type="text"  name="lung_description" value="正常" />
    <input type="text"  name="ct_description" value="正常" />
    <input type="submit" value="提交" />
</form>
</body>
</html>