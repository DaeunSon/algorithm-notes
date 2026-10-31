# 투 포인터와 슬라이딩 윈도우

## 한 줄 정의

인덱스 두 개를 규칙에 따라 **한 방향으로만** 움직여서, 모든 쌍·모든 구간을 다 보는 O(n²)을 O(n)으로 줄인다.

## 언제 쓰나 (판별 단서)

- "**정렬된** 배열에서 합이 target인 두 수", "양 끝에서" → 양 끝에서 안쪽으로 (모양 1)
- "제자리에서(in-place) 지우기/옮기기", "추가 배열 없이" → 같은 방향, 느린·빠른 포인터 (모양 2)
- "**연속된** 부분 배열 / 부분 문자열"
  - "길이가 k인" → 고정 길이 윈도우 (모양 3)
  - "조건을 만족하는 가장 긴 / 가장 짧은" → 가변 길이 윈도우 (모양 4)
- 입력 크기: n이 10⁵ 이상인데 쌍이나 구간을 다 봐야 할 것 같을 때. O(n²)은 안 되므로 이 방법을 떠올린다.
- 전제: 포인터를 한 방향으로만 움직여도 답을 놓치지 않아야 한다.
  - 모양 1은 **정렬**되어 있어야 한다.
  - 모양 4에서 합을 다룰 때는 값이 **음수가 아니어야** 한다(창을 늘리면 합이 커지기만 해야 함).
- 헷갈리는 것: "연속"이 아니라 "아무 원소나 골라서"(부분 수열)면 이 방법이 아니다.

## 시간복잡도 / 공간복잡도

- 시간: O(n). 두 포인터가 각각 최대 n번만 움직인다.
  - **`for` 안에 `while`이 있어도 O(n²)이 아니다.** 안쪽 `while`이 `left`를 움직이는 횟수는 전부 합쳐 n번을 넘지 않는다.
- 공간: O(1). 윈도우 안의 글자를 세려면 해시나 `int[26]`이 더 필요하다.
- 모양 1을 쓰려고 정렬부터 하면 O(n log n)이다.

## 자바에서 쓰는 것

**모양 1. 양 끝에서 안쪽으로** (정렬된 배열의 두 수 합, 팰린드롬, 물 담기)

```java
int left = 0, right = arr.length - 1;
while (left < right) {                    // 같은 원소를 두 번 쓰지 않으려면 <
    int sum = arr[left] + arr[right];
    if (sum == target) return new int[]{left, right};
    else if (sum < target) left++;        // 더 커져야 함 → 작은 쪽을 버림
    else right--;                         // 더 작아져야 함 → 큰 쪽을 버림
}
```

**모양 2. 같은 방향, 느린·빠른 포인터** (제자리에서 남길 것만 앞으로 모으기)

```java
int slow = 0;                             // 다음에 남길 값을 쓸 자리
for (int fast = 0; fast < nums.length; fast++) {
    if (nums[fast] != 0) {                // 남길 원소면
        nums[slow] = nums[fast];
        slow++;
    }
}
// 0 ~ slow-1 까지가 남긴 원소
```

**모양 3. 고정 길이 윈도우** (길이 k인 구간의 최대 합)

```java
int sum = 0;
for (int i = 0; i < k; i++) sum += arr[i];        // 첫 창
int best = sum;
for (int right = k; right < arr.length; right++) {
    sum += arr[right] - arr[right - k];           // 새로 들어온 것 더하고, 나간 것 빼기
    best = Math.max(best, sum);
}
```

**모양 4. 가변 길이 윈도우** (조건을 지키는 가장 긴 구간)

```java
int left = 0, best = 0;
for (int right = 0; right < n; right++) {
    // ① right 원소를 창에 넣는다
    while (/* 창이 조건을 어김 */) {
        // ② left 원소를 창에서 뺀다
        left++;
    }
    best = Math.max(best, right - left + 1);      // ③ 지금 창은 조건을 지킴 → 길이 갱신
}
```

- 창의 길이는 `right - left + 1`이다(양 끝 포함).
- "가장 짧은" 구간을 찾을 때는 ③을 `while` **안**으로 옮긴다. 조건을 만족하는 동안 줄이면서 길이를 기록한다.
- 창 안의 내용은 `HashSet`, `HashMap`, 알파벳이면 `int[26]`으로 관리한다.

## 동작 과정

> 모양 1~4를 한 단계씩 넘겨보는 페이지: [투 포인터 따라가기](https://claude.ai/artifact/B35pUH4wkQ3Kf7PGjzSeeS) (아래 두 예시와 같은 입력, 직접 입력도 가능)

**모양 1**: `[1, 3, 4, 6, 8, 11]`에서 합이 10인 두 수

| left | right | 합 | 판단 |
|---|---|---|---|
| 0 (1) | 5 (11) | 12 | 크다 → `right--` |
| 0 (1) | 4 (8) | 9 | 작다 → `left++` |
| 1 (3) | 4 (8) | 11 | 크다 → `right--` |
| 1 (3) | 3 (6) | 9 | 작다 → `left++` |
| 2 (4) | 3 (6) | 10 | 찾음 → `[2, 3]` |

왜 버려도 되나: 첫 줄에서 1 + 11 = 12로 크다. 11은 이미 가장 작은 1과 더해도 크니까, 11과 짝이 되는 수는 없다. 그래서 11을 버린다.

**모양 4**: `"pwwkew"`에서 중복 없는 가장 긴 부분 문자열 (`HashSet`으로 창 관리)

| right | 넣는 글자 | 중복? | 빼는 것 | 창 | 길이 | best |
|---|---|---|---|---|---|---|
| 0 | p | | | p | 1 | 1 |
| 1 | w | | | pw | 2 | 2 |
| 2 | w | w 중복 | p, w (left = 2) | w | 1 | 2 |
| 3 | k | | | wk | 2 | 2 |
| 4 | e | | | wke | 3 | 3 |
| 5 | w | w 중복 | w (left = 3) | kew | 3 | 3 |

`left`는 0 → 2 → 3으로 **뒤로 가지 않고** 앞으로만 간다. 그래서 O(n)이다.

## 자주 하는 실수

- **창을 줄일 때 `if`를 씀** → 한 칸만 줄어서 여전히 조건을 어긴다. 조건을 지킬 때까지 `while`.
- **`left++` 전에 `left` 원소를 창에서 빼지 않음** → 창의 내용과 실제 범위가 어긋난다. 빼고 나서 움직인다.
- **창 길이를 `right - left`로 씀** → 양 끝 포함이라 `+ 1`.
- **정렬 안 된 배열에 모양 1** → 버리면 안 되는 쪽을 버린다. 정렬이 필요한데, 원래 인덱스를 답해야 하면(Two Sum) 정렬하는 순간 인덱스를 잃는다 → 해시를 쓴다.
- **`while`의 범위 확인이 없거나 순서가 뒤** → 안쪽에서 포인터를 움직이는 `while`에는 `left < right &&`를 **맨 앞**에(Valid Palindrome, Insert Interval과 같은 이유).
- **같은 원소를 두 번 씀** → 모양 1에서 `left <= right`로 쓰면 `left == right`일 때 같은 칸을 두 번 더한다.
- **음수가 있는데 합으로 가변 윈도우** → 창을 늘려도 합이 줄 수 있어서 "줄일지 늘릴지" 판단이 틀린다. 누적 합([12-prefix-sum](../12-prefix-sum))을 쓴다.
- **3Sum에서 중복 답** → 정렬한 뒤 같은 값은 건너뛴다(`while (left < right && nums[left] == nums[left + 1]) left++;`).

## 직접 구현

- [ ] 백지에서 구현 (파일: `05-two-pointers/`)
  - [ ] 모양 1: 정렬된 배열에서 합이 target인 두 인덱스 (`TwoSumSorted.java`)
  - [ ] 모양 2: 0을 뒤로 보내기 (`MoveZeroes.java`)
  - [ ] 모양 3: 길이 k 구간의 최대 합 (`MaxSumK.java`)
  - [ ] 모양 4: 중복 없는 가장 긴 부분 문자열 (`LongestNoRepeat.java`)
- [ ] 작은 입력으로 `main`에서 검증
  - 이중 `for`문으로 모든 쌍·구간을 보는 풀이를 같이 짜서 결과를 비교한다.
  - 빈 배열, 원소 1개, 전부 같은 값, 답이 없는 경우

## 연습 문제

| 문제 | 링크 | 상태 |
|---|---|---|
| Valid Palindrome | [LeetCode 125](https://leetcode.com/problems/valid-palindrome/) | 완료 (모양 1) · [풀이 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/two-pointers/ValidPalindrome.md) |
| Longest Substring Without Repeating Characters | [LeetCode 3](https://leetcode.com/problems/longest-substring-without-repeating-characters/) | 완료 (모양 4) · [풀이 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/string/LongestSubstringWithoutRepeatingCharacters.md) |
| Two Sum II - Input Array Is Sorted | [LeetCode 167](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) | 완료 (모양 1, 3Sum의 기초) · [풀이 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/two-pointers/TwoSumII.md) |
| Move Zeroes | [LeetCode 283](https://leetcode.com/problems/move-zeroes/) | 완료 (모양 2) · [풀이 메모](https://github.com/DaeunSon/leetcode-grind75/blob/main/two-pointers/MoveZeroes.md) |
| Maximum Average Subarray I | [LeetCode 643](https://leetcode.com/problems/maximum-average-subarray-i/) | 아직 안 품 (모양 3) |
| Container With Most Water | [LeetCode 11](https://leetcode.com/problems/container-with-most-water/) | 아직 안 품 (모양 1, 어느 쪽을 버릴지 이유 대기) |
| 3Sum | [LeetCode 15](https://leetcode.com/problems/3sum/) | 아직 안 품 (정렬 + 하나 고정 + 모양 1, 중복 건너뛰기) |
| Find All Anagrams in a String | [LeetCode 438](https://leetcode.com/problems/find-all-anagrams-in-a-string/) | 아직 안 품 (모양 3 + `int[26]`) |
| 연속된 부분 수열의 합 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/178870) | 아직 안 품 (모양 4, n이 10⁶) |
| Minimum Window Substring | [LeetCode 76](https://leetcode.com/problems/minimum-window-substring/) | 아직 안 품 (모양 4 "가장 짧은", 어려움) |
| Trapping Rain Water | [LeetCode 42](https://leetcode.com/problems/trapping-rain-water/) | 아직 안 품 (모양 1 응용, 어려움) |

추천 순서는 위에서 아래로. 167, 283, 643으로 모양을 하나씩 익힌 뒤 11번부터 Grind 75 문제로 넘어간다. 11, 15, 438, 76, 42는 Grind 75에 있다.
