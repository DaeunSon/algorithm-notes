# 이분 탐색

## 한 줄 정의

정렬되어 있거나 한쪽으로만 바뀌는 범위를 가운데에서 확인하고, 답이 없는 절반을 버리는 것을 반복해서 찾는다.

## 언제 쓰나 (판별 단서)

- "정렬된 배열에서 찾아라", "O(log n)으로 풀어라" → 값 찾기
- "처음으로 ~가 되는 위치", "~ 이상인 첫 위치", "넣을 자리" → 경계 찾기 (lower bound)
- "~가 가능한 **최소** 시간 / **최대** 길이" 같은 최적값 문제
  - 답 `x`를 정해놓고 "x로 가능한가?"를 확인할 수 있다
  - x가 커질수록 가능 여부가 한 번만 바뀐다 (`불가능 … 불가능 가능 … 가능`)
  - → 답의 범위를 이분 탐색한다 (매개변수 탐색)
- 입력 크기: 값의 범위가 10⁹처럼 커서 하나씩 다 확인하면 시간 초과일 때. 10⁹도 약 30번이면 끝난다(2³⁰ ≈ 10⁹).
- 전제: 확인하는 순서대로 결과가 **한쪽으로만** 바뀌어야 한다(단조성). 정렬되지 않은 배열에서 그냥 쓰면 안 된다.

## 시간복잡도 / 공간복잡도

- 시간: O(log n). 한 번 확인할 때마다 범위가 절반이 된다.
- 공간: O(1) (반복문 기준)
- 매개변수 탐색: O(log(답의 범위) × 확인 한 번의 비용). 예: 입국심사는 O(log(최대 시간) × 심사관 수).
- 정렬부터 해야 하면 정렬 O(n log n)이 더 크다. 한 번만 찾을 거라면 그냥 O(n)으로 훑는 게 낫다. 여러 번 찾을 때 정렬해두는 의미가 있다.

## 자바에서 쓰는 것

```java
int[] arr = {1, 3, 5, 7};                 // 반드시 정렬된 상태
Arrays.binarySearch(arr, 5);              // 2 (찾은 인덱스)
Arrays.binarySearch(arr, 4);              // -3  = -(넣을 자리 2) - 1
Collections.binarySearch(list, 5);        // List 버전, 반환 규칙 같음
```

- 못 찾으면 음수를 돌려준다: `-(넣을 자리) - 1`. 넣을 자리는 `-(결과) - 1`로 되돌린다.
- 같은 값이 여러 개면 **그중 어느 것**의 인덱스가 나올지 보장하지 않는다. "처음 위치"가 필요하면 직접 구현한다.
- 코테에서는 직접 구현하는 경우가 대부분이라 아래 두 틀을 외워둔다.

**틀 1. 값 찾기** (`left <= right`, 양쪽 모두 `mid ± 1`)

```java
int left = 0, right = arr.length - 1;
while (left <= right) {
    int mid = left + (right - left) / 2;      // (left + right) / 2는 넘칠 수 있다
    if (arr[mid] == target) return mid;
    else if (arr[mid] < target) left = mid + 1;   // mid는 답이 아님 → 버림
    else right = mid - 1;
}
return -1;                                    // 범위가 비면 없는 것
```

**틀 2. 조건이 처음으로 참이 되는 위치** (`left < right`, `right = mid`)

```java
int left = 0, right = n;                      // 답이 n(끝 다음)일 수도 있으면 n까지
while (left < right) {
    int mid = left + (right - left) / 2;
    if (조건(mid)) right = mid;                 // mid도 답 후보 → 남긴다
    else left = mid + 1;                      // mid는 답이 아님 → 버림
}
return left;                                  // left == right가 답
```

- Search Insert Position: `조건(mid)` = `arr[mid] >= target`
- First Bad Version: `조건(mid)` = `isBadVersion(mid)`, 범위는 `1 ~ n`
- 매개변수 탐색: `조건(x)` = "x로 가능한가?". 범위가 크면 `left`, `right`, `mid`를 `long`으로 둔다.

## 동작 과정

**틀 1**: `[-1, 0, 3, 5, 9, 12]`에서 9 찾기

| left | right | mid | arr[mid] | 판단 |
|---|---|---|---|---|
| 0 | 5 | 2 | 3 | 3 < 9 → 오른쪽 절반, `left = 3` |
| 3 | 5 | 4 | 9 | 찾음 → 4 반환 |

같은 배열에서 2 찾기 (없는 값)

| left | right | mid | arr[mid] | 판단 |
|---|---|---|---|---|
| 0 | 5 | 2 | 3 | 3 > 2 → `right = 1` |
| 0 | 1 | 0 | -1 | -1 < 2 → `left = 1` |
| 1 | 1 | 1 | 0 | 0 < 2 → `left = 2` |
| 2 | 1 | | | `left > right` → 범위가 비었음 → -1 |

**틀 2**: First Bad Version, `n = 5`, 처음 나쁜 버전이 4 (`좋음 좋음 좋음 나쁨 나쁨`)

| left | right | mid | isBad(mid) | 판단 |
|---|---|---|---|---|
| 1 | 5 | 3 | false | 3은 답 아님 → `left = 4` |
| 4 | 5 | 4 | true | 4도 후보 → `right = 4` |
| 4 | 4 | | | `left == right` → 4 반환 |

## 자주 하는 실수

- **`while` 조건과 갱신을 섞어 씀 → 무한 반복**
  - 틀 1은 `<=`와 `mid ± 1`, 틀 2는 `<`와 `right = mid` / `left = mid + 1`이 한 쌍이다.
  - `left <= right`에서 `right = mid`를 쓰면 `left == right == mid`일 때 범위가 안 줄어든다.
- **`left = mid` (+1 없이)** → `left = 0, right = 1`이면 `mid = 0`이라 `left`가 계속 0이다. 버릴 때는 반드시 `mid + 1` 또는 `mid - 1`.
- **`(left + right) / 2` 넘침** → 둘 다 10억 근처면 `int`를 넘는다. `left + (right - left) / 2`.
- **정렬 안 된 배열에 사용** → 결과가 틀린다. 먼저 정렬하거나, 단조성이 있는지 확인한다.
- **`Arrays.binarySearch`의 음수 반환을 인덱스로 씀** → 못 찾으면 음수다.
- **매개변수 탐색에서 `int` 넘침** → 입국심사의 `right = 최대 시간 × n`은 10¹⁸까지 갈 수 있다. `long`.
- **끝난 뒤 무엇을 반환할지 헷갈림** → 틀 1은 반복문 안에서 찾으면 반환하고, 밖이면 없는 것이다. 틀 2는 `left`(== `right`)다.
- **범위 양끝을 잘못 잡음** → "끝 다음 자리(n)"도 답이 될 수 있으면 `right = n`. 예: Search Insert Position에서 target이 가장 클 때.

## 직접 구현

- [ ] 백지에서 구현 (파일: `04-binary-search/`)
  - [ ] 틀 1: 값 찾기 (`BinarySearch.java`)
  - [ ] 틀 2: 처음으로 `arr[i] >= target`인 위치 (`LowerBound.java`)
- [ ] 작은 입력으로 `main`에서 검증
  - 원소 1개, 맨 앞 / 맨 뒤 값, 없는 값(가장 작은 값보다 작음, 가장 큰 값보다 큼), 같은 값이 여러 개
  - `Arrays.binarySearch` 결과와 비교

## 연습 문제

| 문제 | 링크 | 상태 |
|---|---|---|
| Binary Search | [LeetCode 704](https://leetcode.com/problems/binary-search/) | 완료 (틀 1) · [풀이 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/binary-search/BinarySearch.md) |
| Search Insert Position | [LeetCode 35](https://leetcode.com/problems/search-insert-position/) | 완료 (틀 1 + `left` 반환) · [풀이 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/binary-search/SearchInsertPosition.md) |
| First Bad Version | [LeetCode 278](https://leetcode.com/problems/first-bad-version/) | 완료 (틀 2) · [풀이 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/binary-search/FirstBadVersion.md) |
| Search in Rotated Sorted Array | [LeetCode 33](https://leetcode.com/problems/search-in-rotated-sorted-array/) | 아직 안 품 (어느 절반이 정렬됐는지 먼저 판단) |
| 입국심사 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/43238) | 아직 안 품 (매개변수 탐색, `long`) |

추천 순서는 위에서 아래로. 704, 278, 33은 Grind 75에도 있다.
