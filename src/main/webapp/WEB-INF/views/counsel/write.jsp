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
<%--    method="get"으로 바꾸면, 전송 시 주소창이 localhost:8080/counsel/write?counsel_title=안녕&counsel_content=... 처럼 지저분해집니다. 게다가 브라우저 주소창은 글자 수 제한이 있어서 긴 글을 쓰면 데이터가 짤려버립니다.--%>
        <table class="table table-bordered">
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
        </div>
    </form>
</div>
</body>
</html>