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

```java
Map<Integer, Integer> map = new HashMap<>();
map.put(k, v);                          // 넣기 (같은 키면 덮어씀)
map.containsKey(k);                     // 키가 있나? → true / false
map.get(k);                             // 키의 값 (없으면 null)
map.getOrDefault(k, 0);                 // 없으면 0
map.put(k, map.getOrDefault(k, 0) + 1); // 개수 세기
map.remove(k);                          // 지우기
for (int key : map.keySet()) { ... }    // 키 전부 돌기
for (Map.Entry<Integer, Integer> e : map.entrySet()) { e.getKey(); e.getValue(); }

Set<Integer> set = new HashSet<>();
set.add(x);                             // 넣기 (이미 있으면 false)
set.contains(x);                        // 있나?
```

**짝 찾기 패턴** (Two Sum): 이중 `for`문의 안쪽이 "짝이 있나?"를 찾고 있으면 해시로 바꾼다.
`need = target - nums[i]`를 `containsKey`로 찾고, 없으면 나를 `put`한다. **찾기 → 넣기** 순서라야 나 자신을 짝으로 찾지 않는다.

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
| 전화번호 목록 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/42577) | 풀이 완료. 11-08 재풀이 △: 왜 해시인지 설명 듣고 정렬 풀이로 작성해서 통과. 해시: 번호마다 앞부분(길이 1 ~ 자기 길이 − 1)을 잘라 `HashSet`에 있는지 확인 → "전부와 비교" O(n²) 대신 "있나?" O(1). 정렬: 문자열 정렬하면 접두어 관계인 번호가 바로 옆에 붙어서 이웃끼리만 `startsWith` 비교 |
| 의상 (위장) | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/42578) | 풀이 완료. 11-08 재풀이 ✗: 종류별 개수 세기는 혼자 했지만 조합 계산(`answer *= (v + 1)`)을 못 떠올려서 보고 작성. 종류마다 "이 중 하나 입기 + 안 입기" = `v + 1`가지, 종류끼리는 독립이라 곱하기, 마지막 `- 1`은 전부 안 입는 경우(최소 하나는 입어야 함) 빼기 → 재풀이 필요 |
| 신고 결과 받기 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/92334) | 확인 후 기입 |
| 달리기 경주 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/178871) | 확인 후 기입 |
| 추억 점수 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/176963) | 확인 후 기입 |
| Two Sum | [LeetCode](https://leetcode.com/problems/two-sum/description/) | 풀이 완료 (재풀이 필요) |
