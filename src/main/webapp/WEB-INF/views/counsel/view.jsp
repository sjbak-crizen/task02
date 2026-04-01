<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%-- [시큐리티 태그라이브러리 선언]
     Spring Security에서 제공하는 로그인 정보 확인, 권한별 화면 제어 기능을
     JSP 태그 형태로 간편하게 사용하기 위해 선언합니다. --%><!DOCTYPE html>
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
            <%-- [수정/삭제 권한 제어]
                 현재 로그인한 유저의 ID와 Role을 변수에 담습니다. --%>
            <sec:authentication property="principal.user_id" var="loginUserId" />
            <sec:authentication property="principal.role" var="loginUserRole" />

            <%-- 작성자 본인이거나, 관리자(ROLE_ADMIN)일 경우에만 수정/삭제 버튼 노출 --%>
            <c:if test="${loginUserId eq counsel.counsel_writer or loginUserRole eq 'ROLE_ADMIN'}">
                <a href="update?seq_counsel=${counsel.seq_counsel}" class="btn btn-warning btn-sm">수정</a>
                <a href="delete?seq_counsel=${counsel.seq_counsel}" class="btn btn-danger btn-sm">삭제</a>
            </c:if>

            <a href="list" class="btn btn-secondary btn-sm">목록</a>
        </div>
    </div>

    <h4>댓글</h4>
    <div class="list-group mb-3">
        <c:forEach items="${comments}" var="comment">
            <%-- [댓글 리스트 출력]
                하나의 게시글(counsel)에 달린 여러 개의 댓글 목록(List<CommentVO>)을
                순차적으로 돌면서 화면에 리스트 형식으로 뿌려줍니다. --%>
            <div class="list-group-item">
                <b>${comment.comment_writer}</b> <span class="text-muted" style="font-size:0.8em">${comment.comment_date}</span>
                <p class="mb-0">${comment.comment_content}</p>
            </div>
        </c:forEach>
    </div>

    <form action="comment/write" method="post" class="border p-3 bg-light">
        <input type="hidden" name="seq_counsel" value="${counsel.seq_counsel}">
        <%-- [Hidden 파라미터 활용]
            댓글은 반드시 특정 게시글에 종속되어야 합니다. 사용자에게 보여줄 필요는 없지만
            서버에 데이터를 보낼 때 '어떤 글에 달리는 댓글인지' 부모 글 번호를 숨겨서 같이 보냅니다. --%>
        <div class="form-row">
            <div class="col-md-2">
                <input type="text" name="comment_writer" class="form-control"
                       value="<sec:authentication property='principal.user_id'/>" readonly>
                <%-- [시큐리티 세션 정보 추출]
                    <sec:authentication>: 현재 로그인한 사용자의 정보를 가져옵니다.
                    principal.user_id: CustomAuthenticationProvider에서 저장한 유저의 '고유 아이디(ID)'를 가져옵니다.--%>
            </div>
            <div class="col-md-9">
                <input type="text" name="comment_content" class="form-control" placeholder="댓글을 입력하세요" required>
            </div>
            <div class="col-md-1">
                <button type="submit" class="btn btn-success w-100">등록</button>
            </div>
        </div>
    </form>
    <%--    [댓글 전송 영역]
            사용자가 댓글 작성자 이름과 내용을 입력하고 '등록'을 누르는 영역입니다.
            method="post": 댓글 내용은 길이가 길 수 있고 보안이 중요하므로 POST 방식을 사용합니다. --%>
</div>
</body>
</html>