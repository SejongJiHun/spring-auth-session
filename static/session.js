let remainingMs = 0;
let timerInterval;

// 세션 남은 시간 화면 표시
function updateTimerDisplay() {
    const min = Math.floor(remainingMs / 60000);
    const sec = Math.floor((remainingMs % 60000) / 1000);
    document.getElementById("session-timer").textContent = `남은 시간: ${min}분 ${sec}초`;
}

// 1초마다 남은 시간 갱신
function startCountdown() {
    clearInterval(timerInterval);

    timerInterval = setInterval(() => {
        remainingMs -= 1000;
        updateTimerDisplay();

        if (remainingMs <= 0) {
            clearInterval(timerInterval);
            alert("세션이 만료되었습니다. 로그인 페이지로 이동합니다.");
            window.location.href = "index.html";
        }
    }, 1000);
}

// 사용자 정보 및 초기 세션 시간 가져오기
function fetchUserInfo() {
    fetch("http://localhost:8080/api/me", {
        method: "GET",
        credentials: "include"
    })
        .then((res) => {
            if (!res.ok) throw new Error();
            return res.json();
        })
        .then((data) => {
            const user = data.data.nickname ? data.data : data.data.user;
            document.getElementById("user-info").textContent =
                `환영합니다, ${user.nickname} (${user.email})`;

            // 초기 세션 시간 설정 (30분 = 1800000ms)
            remainingMs = 1800000;
            updateTimerDisplay();
            startCountdown();
        })
        .catch(() => {
            alert("세션이 만료되었거나 로그인되지 않았습니다.");
            window.location.href = "index.html";
        });
}

// 로그아웃 처리
document.getElementById("logout-btn").addEventListener("click", async () => {
    const res = await fetch("http://localhost:8080/api/logout", {
        method: "POST",
        credentials: "include"
    });

    if (res.ok) {
        alert("로그아웃 되었습니다.");
        window.location.href = "index.html";
    } else {
        alert("로그아웃 실패");
    }
});

// 세션 연장 처리
document.getElementById("keep-alive-btn").addEventListener("click", async () => {
    const res = await fetch("http://localhost:8080/api/ping", {
        method: "GET",
        credentials: "include"
    });

    if (res.ok) {
        remainingMs = 1800000; // 30분으로 초기화
        updateTimerDisplay();  // 화면 즉시 반영
        alert("세션이 30분으로 연장되었습니다.");
    } else {
        alert("세션 연장 실패. 다시 로그인 해주세요.");
        window.location.href = "index.html";
    }
});

fetchUserInfo();
