<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>상담 게시판</title>
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>상담 게시판</h2>
    <div class="text-right mb-2">
        <a href="write" class="btn btn-primary">글쓰기</a>
    </div>
    <table class="table table-bordered table-hover">
        <thead class="thead-light">
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="counsel">
            <tr>
                <td>${counsel.seq_counsel}</td>
                <td><a href="view?seq_counsel=${counsel.seq_counsel}">${counsel.counsel_title}</a></td>
                <td>${counsel.counsel_writer}</td>
                <td>${counsel.counsel_date}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>