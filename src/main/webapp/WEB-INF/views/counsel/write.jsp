<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                    <input type="text" name="counsel_writer" class="form-control" placeholder="작성자명을 입력하세요" required>
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