# 해시

## 한 줄 정의

key로 값을 O(1)로 찾는 자료구조

## 언제 쓰나 (판별 단서)

- 문제에 이런 말이 나오면: "몇 번 나왔나", "이미 있었나", "짝이 있는가", 입력이 크고 안쪽에서 매번 찾는 상황
- 입력 크기가 이 정도일 때: 

## 시간복잡도 / 공간복잡도

- 시간: 조회, 삽입 평균 - O(1)
- 공간: O(n)

## 자바에서 쓰는 것

HashMap, HashSet, getOrDefault, containsKey, keySet/values/entrySet, Character 키

## 동작 과정

(작은 예시를 손으로 추적한 기록)

## 자주 하는 실수

- map.get(키)가 없으면 null이라 int로 쓰면 NPE -> getOrDefault
- put에서 같은 키를 덮어써서 개수가 사라짐
- Map을 for(i=0; i<map.size(); i++)로 돌 수 없음 -> keySet(), values()
- Set<Character>.remove(int)는 아무것도 지우지 못함
- Map<char, ...?는 안 되고 Character
- Map 순회 중 put/remove -> ConcurrentModificationException
- 순서가 필요한 문제에서 해시를 쓰면 안됨

## 직접 구현

- [ ] 백지에서 구현 (파일: `01-hash/`)
- [ ] 작은 입력으로 `main`에서 검증

## 연습 문제

| 문제 | 링크 | 상태 |
|---|---|---|
| 폰켓몬 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/1845) | 풀이 완료 |
| 완주하지 못한 선수 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/42576) | 풀이 완료 |
| 전화번호 목록 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/42577) | 풀이 완료 |
| 의상 (위장) | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/42578) | 풀이 완료 |
| 신고 결과 받기 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/92334) | 확인 후 기입 |
| 달리기 경주 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/178871) | 확인 후 기입 |
| 추억 점수 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/176963) | 확인 후 기입 |
| Two Sum | [LeetCode](https://leetcode.com/problems/two-sum/description/) | 풀이 완료 (재풀이 필요) |
