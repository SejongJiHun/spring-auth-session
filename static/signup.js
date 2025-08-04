document.getElementById("signup-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const nickname = document.getElementById("nickname").value;

    const response = await fetch("http://localhost:8080/api/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password, nickname })
    });

    const messageEl = document.getElementById("message");

    if (response.ok) {
        messageEl.textContent = "회원가입 성공! 로그인 페이지로 이동합니다.";
        setTimeout(() => window.location.href = "index.html", 1500);
    } else {
        messageEl.textContent = "회원가입 실패. 이메일 중복 또는 입력값을 확인하세요.";
    }
});
