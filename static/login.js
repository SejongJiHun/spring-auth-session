document.getElementById("login-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify({ email, password })
    });

    const messageEl = document.getElementById("message");

    if (response.ok) {
        messageEl.textContent = "로그인 성공! 세션 페이지로 이동합니다.";
        window.location.href = "session.html";
    } else {
        messageEl.textContent = "로그인 실패. 다시 시도하세요.";
    }
});
