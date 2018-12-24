<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="utf-8" contentType="text/html; utf-8" isELIgnored="false" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript">
        function previousPage() {
            location.href = "${pageContext.request.contextPath}/poetries/tSSearch?page=${requestScope.paging.page - 1}&keyword=${requestScope.keyword}";
        }

        function nextPage() {
            location.href = "${pageContext.request.contextPath}/poetries/tSSearch?page=${requestScope.paging.page + 1}&keyword=${requestScope.keyword}";
        }
    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/poetries/tSSearch" method="post">
    <input type="text" name="keyword" style="width: 300px" value="${requestScope.keyword}">
    <input type="submit" value="唐诗搜索">
</form>
    <table>
        <tr>
            <td style="width: 200px">诗名</td>
            <td style="width: 50px">诗人</td>
            <td>内容</td>
        </tr>
        <c:forEach items="${requestScope.poetriess}" var="poetries">
            <tr>
                <td style="width: 200px">${poetries.title}</td>
                <td style="width: 50px">${poetries.poets.name}</td>
                <td>${poetries.content}</td>
            </tr>
        </c:forEach>
        <tr></tr>
        <tr></tr>
        <tr></tr>
        <tr>
            <td>
                <c:if test="${requestScope.paging.page != 1}">
                    <input type="button" value="上一页" onclick="previousPage();">
                </c:if>
            </td>
            <td>${requestScope.paging.page}/${requestScope.paging.totalPage}</td>
            <td>
                <c:if test="${requestScope.paging.page != requestScope.paging.totalPage}">
                    <input type="button" value="下一页" onclick="nextPage();">
                </c:if>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;共查询到${requestScope.paging.total}条记录</td>
        </tr>
    </table>
</body>
</html>