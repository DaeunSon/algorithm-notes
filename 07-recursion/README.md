# 재귀

## 한 줄 정의

(내 말로 한 줄)

## 언제 쓰나 (판별 단서)

- 문제에 이런 말이 나오면:
- 입력 크기가 이 정도일 때:

## 시간복잡도 / 공간복잡도

- 시간:
- 공간:

## 자바에서 쓰는 것

(쓰는 클래스, 메서드, 자주 쓰는 형태)

## 동작 과정

(작은 예시를 손으로 추적한 기록)

## 자주 하는 실수

- 증상 / 원인 / 규칙

## 직접 구현

- [ ] 백지에서 구현 (파일: `07-recursion/`)
  - [x] 1부터 n까지 / n부터 1까지 출력: 출력이 재귀 호출 **앞**이냐 **뒤**냐만 다름 (`PrintOrder.java`)
  - [x] 팩토리얼, 배열의 합(인덱스를 넘기는 재귀) (`Factorial.java`, `ArraySum.java`)
  - [x] 피보나치: 그냥 재귀 vs 메모이제이션, 호출 횟수 세서 비교 (`Fibonacci.java`)
  - [x] 거듭제곱: O(n) 재귀 vs 절반으로 나누는 O(log n) 재귀 (`Power.java`)
  - [ ] 하노이의 탑: 이동 순서 출력 (`Hanoi.java`)
- [ ] 작은 입력으로 `main`에서 검증
  - 반복문 버전을 같이 짜서 결과 비교
  - n = 0, 1 (종료 조건이 맞는지), 큰 n (`StackOverflowError`가 나는지)

## 연습 문제

| 문제 | 링크 | 상태 |
|---|---|---|
| Fibonacci Number | [LeetCode 509](https://leetcode.com/problems/fibonacci-number/) | 아직 안 품 (종료 조건 + 메모이제이션) |
| Reverse String | [LeetCode 344](https://leetcode.com/problems/reverse-string/) | 아직 안 품 (투 포인터를 재귀로) |
| Pow(x, n) | [LeetCode 50](https://leetcode.com/problems/powx-n/) | 아직 안 품 (절반으로 나누기, 음수 n, `Integer.MIN_VALUE`) |
| 하노이의 탑 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/12946) | 아직 안 품 (작은 문제를 믿고 맡기기) |
| Maximum Depth of Binary Tree | [LeetCode 104](https://leetcode.com/problems/maximum-depth-of-binary-tree/) | 아직 안 품 (트리 재귀 첫 문제, DFS로 넘어가는 다리) |
| Merge Two Sorted Lists | [LeetCode 21](https://leetcode.com/problems/merge-two-sorted-lists/) | 아직 안 품 (연결 리스트 재귀, Grind 75) |

추천 순서는 위에서 아래로. 104부터는 트리, 21은 연결 리스트라 `TreeNode`, `ListNode` 구조를 먼저 확인한다. 104, 21은 Grind 75에 있다.
