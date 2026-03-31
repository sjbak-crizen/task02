<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>사용자 등록</title>
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script>
        // 폼 전송 전, 자바스크립트로 비밀번호 복잡도를 검사합니다.
        // 서버 부하를 줄이고 사용자에게 즉각적인 피드백을 줄 수 있다는 장점이 있습니다.
        function validateForm() {
            const pwd = document.getElementById("password").value;
            /* [정규표현식(Regex) 원리]
               ^ : 문자열의 시작
               (?=.*[A-Za-z]) : 최소한 하나의 영문자 포함
               (?=.*\d) : 최소한 하나의 숫자 포함
               (?=.*[@$!%*#?&]) : 최소한 하나의 특수문자 포함
               {8,} : 총 길이는 8자리 이상
               $ : 문자열의 끝 */
            const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

            if (!regex.test(pwd)) {
                alert("비밀번호는 숫자, 영문, 특수문자를 모두 포함하여 8자리 이상이어야 합니다!");
                document.getElementById("password").focus();
                return false; // 폼 전송 막기
            }
            return true; // 검사 통과 시 폼 전송 허용
        }
    </script>
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 500px;">
    <div class="card shadow-sm">
        <div class="card-body">
            <h3 class="text-center mb-4">사용자 등록 (회원가입)</h3>

            <form action="${pageContext.request.contextPath}/user/register" method="post" onsubmit="return validateForm();">
                <%--            [onsubmit 이벤트]
                사용자가 등록 버튼을 누르는 순간 validateForm() 함수를 먼저 실행합니다.
                함수 결과가 false이면 전송을 중단하고, true이면 지정된 action 주소로 POST 전송을 시작합니다. --%>

                <div class="form-group mb-3">
                    <label>아이디</label>
                    <input type="text" name="user_id" class="form-control" required>
                </div>
                <div class="form-group mb-3">
                    <label>비밀번호</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="숫자+영문+특수문자 포함 8자리 이상" required>
                </div>
                <div class="form-group mb-3">
                    <label>이름</label>
                    <input type="text" name="user_name" class="form-control" required>
                </div>

                <div class="form-group mb-3">
                    <label>사용자 그룹 (권한)</label>
                    <select name="role" class="form-control">
                        <%--                    [권한 선택]
                        DB에 저장될 ROLE_USER 또는 ROLE_ADMIN 값을 선택합니다.
                        스프링 시큐리티는 기본적으로 'ROLE_' 접두사가 붙은 권한 이름을 인식합니다. --%>
                        <option value="ROLE_USER">일반 사용자</option>
                        <option value="ROLE_ADMIN">관리자</option>
                    </select>
                </div>

                <div class="form-group mb-4">
                    <label>접속 허용 IP (선택사항)</label>
                    <input type="text" name="allowed_ip" class="form-control" placeholder="예: 127.0.0.1 (입력 시 해당 IP만 접속 가능)">
                    <%--                [추가 보안 로직]
                    입력된 IP 정보를 기반으로 CustomAuthenticationProvider에서
                    실제 접속 IP와 대조하여 로그인을 허용하거나 차단하는 용도로 사용됩니다. --%>
                </div>

                <button type="submit" class="btn btn-success w-100 mb-2">등록 완료</button>
                <button type="button" class="btn btn-secondary w-100" onclick="history.back()">취소</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>