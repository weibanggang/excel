<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        table {
            width: 800px;
            text-align: center;
        }

        table th, td {
            height: 32px;
        }
    </style>
</head>
<script src="${pageScope.request.ContextPath}/js/jquery-1.11.3.js"></script>
<body>
<div style="width: 900px;height: 200px;margin: auto;border: 1px solid red;overflow: auto">
    <table style="margin: auto" cellpadding="0" cellspacing="0">
        <thead>
        <th>员工编号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>学历</th>
        <th>月薪</th>
        <th>增加</th>
        <th>移除</th>
        </thead>
        <tbody id="addElement">
        <tr id="cloneElement">
            <td><input type="text" name="I_d"></td>
            <td><input type="text" name="name"></td>
            <td>
                <select name="sex">
                    <option value="">-请选择-</option>
                    <option value="男">男</option>
                    <option value="女">女</option>
                </select>
            </td>
            <td>
                <select name="Education">
                    <option>-请选择-</option>
                    <option value="专科">专科</option>
                    <option value="本科">本科</option>
                    <option value="硕士">硕士</option>
                </select>
            </td>
            <td><input type="number" name="salary"></td>
            <td><input type="button" value="+" onclick="add(this)"></td>
            <td><input type="button" value="-" onclick="del(this)"></td>
        </tr>
        </tbody>
    </table>
    <p style="margin-left:400px"><input type="button" onclick="btn_add(this)" value="批量添加"></p>
</div>
<p></p>
<div style="width: 900px;height: 200px;margin: auto;border: 1px solid red;overflow: auto">
    <p style="margin-left:400px">
        <input type="button" onclick="daochu()" value="导出"/>
        <input type="file" id="myfile" multiple="multiple"/>
        <input type="button" onclick="daoru()" value="导入"/>
    </p>

    <table style="margin: auto" cellpadding="0" cellspacing="0">
        <thead>
        <th>员工编号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>学历</th>
        <th>月薪</th>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="stu">
            <tr>
                <td>${stu.sId}</td>
                <td>${stu.sName}</td>
                <td>${stu.sSex}</td>
                <td>${stu.sEducation}</td>
                <td>${stu.sSalary}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
<script>

    function add(x) {
        $(x).parents("tr").clone().appendTo("#addElement");
    }

    function daochu() {
        window.location.href = "excel_download";
    }

    function daoru() {
        var fd = new FormData();
        fd.append("xxx", myfile.files[0]);
        $.ajax({
            method: 'post',
            url: "/upload",
            cache: false,
            contentType: false,
            data: fd,
            processData: false,
            success: function (data) {
                console.log(data);
                alert(data.msg);
                if (data.code == 200){
                    window.location.href="/index";
                }
              }
            ,error:function () {
                alert("出错");
            }
        })

    }

    function del(x) {
        var tr_length = $("#addElement").find("tr").length;

        if (tr_length == 1) {
            alert("不能再删除了，兄弟!!!!!!!!!");
            return false;
        }
        $(x).parent().closest("tr").remove();
    }

    function btn_add() {
        var data = [];
        $("#addElement tr").each(function (index, obj) {
            data.push({
                sId: $("input[name='I_d']", obj).val(),
                sName: $("input[name='name']", obj).val(),
                sSex: $("select[name='sex'] option:selected", obj).val(),
                sEducation: $("select[name='Education'] option:selected", obj).val(),
                sSalary: $("input[name='salary']", obj).val()
            });
        });
        $.ajax({
            url: "/insert",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (text) {
                alert(text.msg);
                if (text.code == 200) {
                    //重新渲染页面
                    window.location.href = "/index";
                }
            }
        });

    }

</script>
</html>
