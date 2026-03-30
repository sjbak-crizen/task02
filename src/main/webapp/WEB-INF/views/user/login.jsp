<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>상담 게시판 - 로그인</title>
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 400px;">
    <div class="card shadow-sm">
        <div class="card-body">
            <h3 class="text-center mb-4">로그인</h3>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" style="font-size: 0.9em;">
                        ${errorMessage}
                </div>
            </c:if>

            <c:if test="${param.logout == 'true'}">
                <div class="alert alert-success" style="font-size: 0.9em;">
                    성공적으로 로그아웃 되었습니다.
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/loginProcess" method="post">
                <div class="form-group mb-3">
                    <label>아이디</label>
                    <input type="text" name="user_id" class="form-control" placeholder="아이디를 입력하세요" required autofocus>
                </div>
                <div class="form-group mb-4">
                    <label>비밀번호</label>
                    <input type="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요" required>
                </div>
                <button type="submit" class="btn btn-primary w-100 mb-3">로그인</button>
            </form>

            <div class="text-center">
                <a href="${pageContext.request.contextPath}/user/register" class="text-decoration-none">새로운 계정 등록하기</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>