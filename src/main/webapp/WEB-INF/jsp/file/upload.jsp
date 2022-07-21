<%--
  Created by IntelliJ IDEA.
  User: 周晋
  Date: 2020/9/8
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>文件上传</title>
    <script type="text/javascript"
            src="https://ajax.aspnetcdn.com/ajax/jquery/jquery-3.5.1.min.js"></script>
    <script type="text/javascript">

        function post() {
            var params = {
                'p_id': '123',
                'f_id' : '0010',
                'f_date' :"2020-09-04 01:53:56",
                'state':1
            }
            $.post({
                url : "./insertFileMessage",
                // 此处需要告知传递参数类型为JSON，不能缺少
                contentType : "application/json",
                // 将JSON转化为字符串传递
                data : JSON.stringify(params),
                // 成功后的方法
                success : function(result) {
                    if (result == null || result.id == null) {
                        alert("插入失败");
                        return;
                    }
                    alert("插入成功");
                }
            });
        }
        post();
    </script>
</head>
<body>
<form method="post"
      action="./upload" enctype="multipart/form-data">
    <input type="file" id="" name="file" value="请选择上传的文件" />
    <input type="submit" value="提交" />
</form>
</body>
</html>
