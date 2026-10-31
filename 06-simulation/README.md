# 구현/시뮬레이션

## 한 줄 정의

이름 붙은 알고리즘 없이, 문제에 적힌 규칙과 순서를 **그대로** 코드로 옮긴다.

## 언제 쓰나 (판별 단서)

- 문제가 "~하면 ~한다", "1. … 2. … 3. …"처럼 **규칙과 순서**를 길게 설명한다 → 그 순서가 곧 코드 순서다 (String to Integer)
- 격자, 지도, 보드 위에서 로봇·사람·말이 **이동하고 방향을 바꾼다** → 방향 배열 + 경계 검사
- 배열을 **돌리거나 뒤집거나**, 테두리를 따라 돈다 → 인덱스 공식 (Spiral Matrix, 회전)
- 시간이 흐르며 칸들이 **동시에** 바뀐다 → 새 배열에 다음 상태를 쓴다 (Game of Life)
- 사람이 손으로 하는 계산을 그대로 한다 → 세로 덧셈, 자리올림 (Add Binary)
- 입력 크기: 보통 작다(격자 100×100, 명령 1000개 등). 규칙대로 해도 시간 안에 끝나는지 **먼저 계산**한다. 단계 수가 10⁹처럼 크면 그대로 돌리면 안 되고 주기나 공식을 찾는다.
- 헷갈리는 것: "자료구조가 뭐지?"에서 막히면 이 유형일 가능성이 크다. Add Binary, String to Integer에서 그랬다.

## 시간복잡도 / 공간복잡도

- 시간: (단계 수) × (한 단계에 드는 비용). 예: n×m 격자를 k번 갱신하면 O(k·n·m).
- 공간: 격자를 복사하면 O(n·m). 제자리에서 바꾸면 O(1)이지만 실수가 늘어난다.
- 짜기 전에 한 줄 계산: 10⁸번 안쪽이면 대개 통과한다.

## 자바에서 쓰는 것

**방향 배열과 경계 검사** (격자 이동의 기본)

```java
int[] dr = {-1, 0, 1, 0};   // 위, 오른쪽, 아래, 왼쪽 (시계 방향 순서)
int[] dc = {0, 1, 0, -1};

int d = 1;                  // 지금 방향: 오른쪽
int nr = r + dr[d];         // 다음 칸
int nc = c + dc[d];

if (nr < 0 || nr >= n || nc < 0 || nc >= m) {
    // 격자 밖 → 이동하지 않음 / 방향 바꾸기 등 문제 규칙대로
}

d = (d + 1) % 4;            // 오른쪽으로 90도
d = (d + 3) % 4;            // 왼쪽으로 90도  ((d - 1) % 4는 d = 0일 때 -1)
d = (d + 2) % 4;            // 뒤로 돌기
```

- `r`은 **행**(위아래, 세로 위치), `c`는 **열**(좌우, 가로 위치)이다. `x, y`로 쓰면 어느 쪽이 행인지 헷갈리니 `r, c`로 통일한다.
- 방향 순서를 **시계 방향**으로 정해두면 회전이 `+1`, `+3`으로 끝난다.
- 경계 검사는 배열에 **접근하기 전에** 한다. 조건에 `grid[nr][nc]`가 같이 있으면 경계 검사를 `&&` 앞에 둔다(Insert Interval, 투 포인터와 같은 이유).

**네 방향 이웃 훑기** (DFS, BFS에서 그대로 쓴다)

```java
for (int d = 0; d < 4; d++) {
    int nr = r + dr[d], nc = c + dc[d];
    if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue;   // 밖이면 건너뛰기
    // grid[nr][nc] 처리
}
```

**2차원 배열 회전** (n×m → m×n)

```java
int[][] rot = new int[m][n];                // 시계 방향 90도: 행과 열 크기가 바뀐다
for (int r = 0; r < n; r++)
    for (int c = 0; c < m; c++)
        rot[c][n - 1 - r] = a[r][c];
```

- 반시계 90도: `rot[m - 1 - c][r] = a[r][c]`
- 180도: `rot[n - 1 - r][m - 1 - c] = a[r][c]` (크기 그대로)
- 공식이 헷갈리면 2×3 같은 작은 배열로 **한 칸만** 손으로 옮겨서 확인한다.

**2차원 배열 복사** (동시에 바뀌는 시뮬레이션)

```java
int[][] copy = new int[n][];
for (int i = 0; i < n; i++) copy[i] = a[i].clone();   // 행마다 복사해야 진짜 복사
```

- `int[][] copy = a.clone();`은 바깥 배열만 복사한다. 안쪽 행은 원본과 **같은 배열**이라 하나를 바꾸면 둘 다 바뀐다.
- 모든 칸이 "이전 상태"를 보고 **동시에** 바뀌어야 하면, 읽기는 원본에서 하고 쓰기는 새 배열에 한다. 같은 배열에서 바로 바꾸면 뒤쪽 칸이 이미 바뀐 앞쪽 값을 읽는다.

**문자열 격자**

```java
String[] park = {"SOO", "OOO", "OOO"};
char[][] g = new char[park.length][];
for (int i = 0; i < park.length; i++) g[i] = park[i].toCharArray();
// g[r][c] 또는 park[r].charAt(c)
```

## 동작 과정

**방향 배열로 도는 Spiral Matrix** (3×3, 경계 밖이거나 이미 방문했으면 오른쪽으로 90도)

경계 4개를 줄이는 풀이([Spiral Matrix 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/array/SpiralMatrix.md))와 같은 문제를 다른 방법으로 푼다.

| 칸 (r, c) | 값 | 다음 칸 | 판단 | 방향 |
|---|---|---|---|---|
| (0, 0) | 1 | (0, 1) | 갈 수 있음 | 오른쪽 |
| (0, 1) | 2 | (0, 2) | 갈 수 있음 | 오른쪽 |
| (0, 2) | 3 | (0, 3) | 열 3은 밖 → 회전 | 아래 |
| (1, 2) | 6 | (2, 2) | 갈 수 있음 | 아래 |
| (2, 2) | 9 | (3, 2) | 행 3은 밖 → 회전 | 왼쪽 |
| (2, 1) | 8 | (2, 0) | 갈 수 있음 | 왼쪽 |
| (2, 0) | 7 | (2, -1) | 열 -1은 밖 → 회전 | 위 |
| (1, 0) | 4 | (0, 0) | 이미 방문 → 회전 | 오른쪽 |
| (1, 1) | 5 | | 9칸 다 읽음 → 끝 | |

결과: `[1, 2, 3, 6, 9, 8, 7, 4, 5]`. 경계 4개 방식과 달리 `if` 두 개 같은 예외 처리가 없고, "밖이거나 방문했으면 돈다" 하나로 끝난다. 대신 `visited` 배열이 필요하다.

**시계 방향 90도 회전** `[[1, 2, 3], [4, 5, 6]]` (2×3 → 3×2, `rot[c][n - 1 - r]`, n = 2)

| 원래 (r, c) | 값 | 새 위치 (c, 1 - r) |
|---|---|---|
| (0, 0) | 1 | (0, 1) |
| (0, 2) | 3 | (2, 1) |
| (1, 0) | 4 | (0, 0) |
| (1, 2) | 6 | (2, 0) |

결과: `[[4, 1], [5, 2], [6, 3]]`. 원래 맨 아래 줄 `4 5 6`이 왼쪽 세로줄이 된다.

## 자주 하는 실수

- **행과 열을 뒤바꿈** → `grid[c][r]`, `dr`/`dc` 혼동. `r`(행, 세로), `c`(열, 가로)로 이름을 통일하고, 크기는 `n = grid.length`(행 수), `m = grid[0].length`(열 수).
- **경계 검사 없이 접근** → `ArrayIndexOutOfBounds`. 다음 칸을 계산하면 접근하기 **전에** 범위부터.
- **왼쪽 회전을 `(d - 1) % 4`로 씀** → 자바에서 `-1 % 4`는 `-1`이다. `(d + 3) % 4`.
- **회전 결과 배열 크기를 원래대로 만듦** → n×m을 90도 돌리면 m×n이다.
- **`a.clone()`으로 2차원 배열 복사** → 얕은 복사. 행마다 `clone()`.
- **동시에 바뀌어야 하는데 제자리에서 바꿈** → 이미 바뀐 값을 읽는다. 새 배열에 쓰거나, 이전 상태를 따로 표시해둔다.
- **규칙 순서를 바꿈** → 문제의 번호 순서가 실행 순서다. 순서를 바꾸면 경계 경우에서 틀린다(String to Integer의 공백 → 부호 → 숫자).
- **0부터 세는지 1부터 세는지** → 문제가 "1번 행"이라고 하면 인덱스는 `- 1`.
- **경계 경우를 안 따져봄** → 한 줄짜리, 한 칸짜리, 빈 입력. Spiral Matrix의 한 줄 / 한 열에서 중복이 생겼던 것처럼.

## 직접 구현

- [ ] 백지에서 구현 (파일: `06-simulation/`)
  - [ ] 방향 배열 + `visited`로 Spiral Matrix (`SpiralDirection.java`)
  - [ ] 2차원 배열 시계 방향 90도 회전, n×m 직사각형 포함 (`Rotate.java`)
  - [ ] 격자에서 명령(`"E 2"`, `"S 1"` 같은)대로 이동, 밖이면 무시 (`Walk.java`)
- [ ] 작은 입력으로 `main`에서 검증
  - 1×1, 1×m(한 줄), n×1(한 열), 정사각형, 직사각형
  - 회전은 4번 돌리면 원래대로 돌아오는지 확인

## 연습 문제

| 문제 | 링크 | 상태 |
|---|---|---|
| Add Binary | [LeetCode 67](https://leetcode.com/problems/add-binary/) | 완료, 재풀이 필요 (손으로 하는 세로 덧셈) · [풀이 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/math/AddBinary.md) |
| Spiral Matrix | [LeetCode 54](https://leetcode.com/problems/spiral-matrix/) | 완료, 재풀이 필요 (경계 4개) · [풀이 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/array/SpiralMatrix.md) |
| String to Integer (atoi) | [LeetCode 8](https://leetcode.com/problems/string-to-integer-atoi/) | 완료, 재풀이 필요 (규칙 순서대로) · [풀이 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/string/StringToIntegerAtoi.md) |
| Insert Interval | [LeetCode 57](https://leetcode.com/problems/insert-interval/) | 완료, 재풀이 필요 (세 무리를 순서대로) · [풀이 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/array/InsertInterval.md) |
| 키패드 누르기 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/67256) | 아직 안 품 (좌표로 바꿔 거리 계산, 규칙 순서) |
| Rotate Image | [LeetCode 48](https://leetcode.com/problems/rotate-image/) | 아직 안 품 (회전 공식, 제자리에서 하려면 전치 + 좌우 뒤집기) |
| Robot Bounded In Circle | [LeetCode 1041](https://leetcode.com/problems/robot-bounded-in-circle/) | 아직 안 품 (방향 배열, 한 바퀴 뒤 위치와 방향) |
| Game of Life | [LeetCode 289](https://leetcode.com/problems/game-of-life/) | 아직 안 품 (8방향 이웃, 동시에 바뀜 → 복사본) |
| 행렬 테두리 회전하기 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/77485) | 아직 안 품 (테두리를 따라 한 칸씩 밀기, 1부터 세는 좌표) |

위 네 문제는 이미 푼 구현 문제다(재풀이 대상). 새 문제는 키패드 누르기 → Rotate Image → Robot Bounded In Circle → Game of Life → 행렬 테두리 회전하기 순서로.
