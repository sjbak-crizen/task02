<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>상담 글쓰기</title>
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>상담 글쓰기</h2>
    <form action="write" method="post" class="mt-4">
        <%--    [POST 방식의 필수성]
                1. 보안: GET 방식과 달리 데이터가 주소창에 노출되지 않아 보안에 유리합니다.
                2. 용량: 주소창 제한이 있는 GET과 달리, 대용량의 본문 내용(textarea)을 온전하게 전송할 수 있습니다.
                3. 설계: DB에 새로운 데이터를 생성(INSERT)하는 작업은 REST 설계 원칙상 POST 방식을 사용하는 것이 정석입니다. --%>        <table class="table table-bordered">
            <tr>
                <th class="table-light align-middle" style="width: 15%;">제목</th>
                <td>
                    <input type="text" name="counsel_title" class="form-control" placeholder="제목을 입력하세요" required>
                </td>
            </tr>
            <tr>
                <th class="table-light align-middle">작성자</th>
                <td>
                    <input type="text" name="counsel_writer" class="form-control"
                           value="<sec:authentication property='principal.user_name'/>" readonly>
                    <%--                [작성자 자동 매핑 및 보안]
                    <sec:authentication>: 세션에 저장된 사용자 정보를 가져와 입력창에 미리 채워줍니다.
                    --%>
                </td>
            </tr>
            <tr>
                <th class="table-light align-middle">내용</th>
                <td>
                    <textarea name="counsel_content" class="form-control" rows="10" placeholder="내용을 입력하세요" required></textarea>
                </td>
            </tr>
        </table>

        <div class="text-right">
            <button type="submit" class="btn btn-primary">등록</button>
            <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
            <%--            history.back(): 브라우저의 이전 페이지로 이동하여 목록으로 돌아가는 기능을 수행합니다. --%>
        </div>
    </form>
</div>
</body>
</html>