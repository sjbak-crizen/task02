<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- [JSTL 선언]
     JSP 파일 안에 복잡한 자바 코드(<% for(...) %>)를 쓰면 화면(View) 코드가 지저분해집니다.
     이를 방지하고 HTML 태그처럼 깔끔하게 반복문/조건문을 사용하기 위해 JSTL(표준 태그 라이브러리)을 끌어온 것입니다. --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
    <%-- ========================================================= --%>
    <%-- 상단 우측 메뉴 (인사말 + 비번변경 + 로그아웃 + 글쓰기) --%>
    <%-- ========================================================= --%>
    <div class="text-right mb-3 align-items-center">
        <%-- 1. 사용자 이름 표시 --%>
        <span class="mr-3 text-secondary">
            <b><sec:authentication property="principal.user_name"/></b>님 환영합니다!
        </span>

        <%-- 2. 비밀번호 변경 버튼 --%>
        <a href="${pageContext.request.contextPath}/user/changePassword" class="btn btn-outline-secondary btn-sm">비밀번호 변경</a>

        <%-- 3. 로그아웃 폼 (POST 방식 전송) --%>
        <form action="${pageContext.request.contextPath}/logout" method="post" style="display: inline-block; margin-right: 10px;">
            <button type="submit" class="btn btn-outline-danger btn-sm">로그아웃</button>
        </form>

        <%-- 4. 글쓰기 버튼 --%>
        <a href="write" class="btn btn-primary btn-sm">글쓰기</a>
            <%-- btn-primary: 파란색 메인 버튼--%>
    </div>
    <%-- ========================================================= --%>



    <form action="list" method="get" class="d-flex mb-3 bg-light p-3 border rounded align-items-center">
        <%-- [검색에 GET 방식을 쓴 이유]
            검색은 DB의 데이터를 수정/삭제하는 것이 아니라 단순히 '조회'만 하는 행위이므로 GET 방식을 사용했습니다.
            또한, GET 방식을 쓰면 검색어 조건이 URL에 남기 때문에 다른 사람에게 검색 결과를 링크로 공유하거나 즐겨찾기에 추가할 수 있다는 강력한 장점이 있습니다. --%>
        <select name="searchType" class="form-control mr-2" style="width: auto;">
            <option value="writer">작성자</option>
            <option value="date">작성일시 (예: 2024-03-30)</option>
            <option value="title">제목</option>
            <option value="title_content">제목+내용</option>
        </select>
        <input type="text" name="searchKeyword" class="form-control mr-2 flex-grow-1" placeholder="검색어를 입력하세요">
        <button type="submit" class="btn btn-dark" style="white-space: nowrap;">검색</button>
    </form>

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