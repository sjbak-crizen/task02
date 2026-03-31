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
                <%--            [로그인 실패 피드백]
                CustomLoginFailureHandler에서 세션(Session)에 담아 보낸 에러 메시지(errorMessage)가
                있을 경우에만 빨간색 경고창(alert-danger)을 띄워 사용자에게 실패 원인을 알려줍니다. --%>
                <div class="alert alert-danger" style="font-size: 0.9em;">
                        ${errorMessage}
                </div>
            </c:if>

            <c:if test="${param.logout == 'true'}">
                <%--            [로그아웃 알림]
                security-context.xml의 logout-success-url에서 설정한 쿼리스트링(?logout=true)을
                체크하여, 로그아웃 직후에만 "성공적으로 로그아웃 되었습니다" 메시지를 보여줍니다. --%>
                <div class="alert alert-success" style="font-size: 0.9em;">
                    성공적으로 로그아웃 되었습니다.
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/loginProcess" method="post">
                <%--            [시큐리티 로그인 처리 주소]
                실제 로그인을 처리하는 로직은 Controller가 아닌, 스프링 시큐리티가 가로채서 처리합니다.
                action="/loginProcess"는 security-context.xml의 login-processing-url과 반드시 일치해야 합니다. --%>

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
                <%--                [회원가입 링크]
                    시큐리티 설정에서 /user/register** 경로를 permitAll로 풀었기 때문에
                    로그인하지 않은 사용자도 이 링크를 통해 회원가입 페이지로 이동할 수 있습니다. --%>
            </div>
        </div>
    </div>
</div>
</body>
</html>