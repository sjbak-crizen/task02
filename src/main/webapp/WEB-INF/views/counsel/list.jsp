<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>상담 게시판</title>
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<%--    ${pageContext.request.contextPath}: EL(Expression Language) 문법으로,
                                            현재 프로젝트의 최상위 루트 경로(예: /task02)를 동적으로 가져옵니다.--%>
</head>
<body>
<div class="container mt-5">
<%--    mt-5: 위쪽 여백 5단계--%>
    <h2>상담 게시판</h2>
    <div class="text-right mb-2">
        <a href="write" class="btn btn-primary">글쓰기</a>
<%--        btn-primary: 파란색 메인 버튼--%>
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
<%--                <a href="view?seq_counsel=${counsel.seq_counsel}">: 몇 번 글을 클릭했는지 쿼리스트링(Query String) 형태로 파라미터를 달아서 컨트롤러에 전달해 주는 링크입니다..--%>
                <td>${counsel.counsel_writer}</td>
                <td>${counsel.counsel_date}</td>
            </tr>
        </c:forEach>
<%--        items="${list}": 컨트롤러에서 DB를 긁어와 model.addAttribute("list", ...)로 담아준 바로 그 데이터(List)를 꺼내옵니다.
            var="counsel": 데이터를 한 줄(Row)씩 꺼낼 때마다, 그 한 줄의 임시 이름을 counsel이라고 부르겠다고 지정합니다.--%>
        </tbody>
    </table>
</div>
</body>
</html>