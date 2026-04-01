<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>비밀번호 변경</title>
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script>
        // 폼 전송 전 프론트엔드 유효성 검사
        function validatePassword() {
            const newPwd = document.getElementById("newPassword").value;
            const confirmPwd = document.getElementById("confirmPassword").value;

            // 새 비밀번호가 규칙(숫자+영문+특수문자 포함 8자리 이상)에 맞는지 확인
            const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

            if (!regex.test(newPwd)) {
                alert("새 비밀번호는 숫자, 영문, 특수문자를 모두 포함하여 8자리 이상이어야 합니다!");
                document.getElementById("newPassword").focus();
                return false;
            }

            // 새 비밀번호와 비밀번호 확인 칸이 일치하는지 확인
            if (newPwd !== confirmPwd) {
                alert("새 비밀번호와 비밀번호 확인이 일치하지 않습니다!");
                document.getElementById("confirmPassword").focus();
                return false;
            }

            return true;
        }
    </script>
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 500px;">
    <div class="card shadow-sm">
        <div class="card-body">
            <h3 class="text-center mb-4">비밀번호 변경</h3>

            <%-- 컨트롤러에서 보낸 에러 메시지가 있을 경우 출력 (현재 비밀번호 틀림 등) --%>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" style="font-size: 0.9em;">
                        ${errorMessage}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/user/changePassword" method="post" onsubmit="return validatePassword();">

                <div class="form-group mb-3">
                    <label>현재 비밀번호</label>
                    <input type="password" name="currentPassword" class="form-control" required autofocus>
                </div>

                <div class="form-group mb-3">
                    <label>새 비밀번호</label>
                    <input type="password" id="newPassword" name="newPassword" class="form-control" placeholder="숫자+영문+특수문자 포함 8자리 이상" required>
                </div>

                <div class="form-group mb-4">
                    <label>새 비밀번호 확인</label>
                    <input type="password" id="confirmPassword" class="form-control" placeholder="새 비밀번호를 다시 입력하세요" required>
                </div>

                <button type="submit" class="btn btn-warning w-100 mb-2">비밀번호 변경</button>
                <button type="button" class="btn btn-secondary w-100" onclick="history.back()">취소</button>
            </form>

        </div>
    </div>
</div>
</body>
</html>