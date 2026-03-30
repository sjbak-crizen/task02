<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--시큐리티 태그를 쓰겠다는 선언--%>
<!DOCTYPE html>
<html>
<head>
    <title>글 보기</title>
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>상담 글 보기</h2>
    <div class="card mb-4">
        <div class="card-header">
            <b>${counsel.counsel_title}</b>
            <span class="float-right">${counsel.counsel_writer} | ${counsel.counsel_date}</span>
        </div>
        <div class="card-body" style="min-height: 200px;">
            ${counsel.counsel_content}
        </div>
        <div class="card-footer text-right">
            <a href="delete?seq_counsel=${counsel.seq_counsel}" class="btn btn-danger btn-sm">삭제</a>
            <a href="list" class="btn btn-secondary btn-sm">목록</a>
        </div>
    </div>

    <h4>댓글</h4>
    <div class="list-group mb-3">
        <c:forEach items="${comments}" var="comment">
            <div class="list-group-item">
                <b>${comment.comment_writer}</b> <span class="text-muted" style="font-size:0.8em">${comment.comment_date}</span>
                <p class="mb-0">${comment.comment_content}</p>
            </div>
        </c:forEach>
    </div>

    <form action="comment/write" method="post" class="border p-3 bg-light">
        <input type="hidden" name="seq_counsel" value="${counsel.seq_counsel}">
        <div class="form-row">
            <div class="col-md-2">
                <input type="text" name="comment_writer" class="form-control"
                       value="<sec:authentication property='principal.user_name'/>" readonly>
            </div>
            <div class="col-md-9">
                <input type="text" name="comment_content" class="form-control" placeholder="댓글을 입력하세요" required>
            </div>
            <div class="col-md-1">
                <button type="submit" class="btn btn-success w-100">등록</button>
            </div>
        </div>
    </form>
<%--    사용자가 댓글 작성자 이름과 내용을 입력하고 '등록'을 누르는 영역입니다.
    <form method="post">: 데이터가 주소창에 노출되지 않도록 안전한 POST 방식을 씁니다.
    <input type="hidden">: 부모 글의 PK 번호를 몰래 숨겨서 같이 제출하는 코드입니다.--%>
</div>
</body>
</html>