<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>상담 글 수정</title>
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>상담 글 수정</h2>

    <form action="update" method="post" class="mt-4">

        <%-- 어떤 글을 수정하는지 DB가 알아야 하므로 글 번호(PK)를 몰래 숨겨서 같이 보냅니다. --%>
        <input type="hidden" name="seq_counsel" value="${counsel.seq_counsel}">

        <table class="table table-bordered">
            <tr>
                <th class="table-light align-middle" style="width: 15%;">제목</th>
                <td>
                    <input type="text" name="counsel_title" class="form-control" value="${counsel.counsel_title}" required>
                </td>
            </tr>
            <tr>
                <th class="table-light align-middle">작성자</th>
                <td>
                    <input type="text" class="form-control" value="${counsel.counsel_writer}" readonly>
                </td>
            </tr>
            <tr>
                <th class="table-light align-middle">내용</th>
                <td>
                    <textarea name="counsel_content" class="form-control" rows="10" required>${counsel.counsel_content}</textarea>
                </td>
            </tr>
        </table>

        <div class="text-right">
            <button type="submit" class="btn btn-warning">수정 완료</button>
            <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
        </div>
    </form>
</div>
</body>
</html>