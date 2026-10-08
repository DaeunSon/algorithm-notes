# 정렬

## 한 줄 정의

원소를 기준에 따라 정렬

## 언제 쓰나 (판별 단서)

"정렬 후 선택", "순서만 다른지(애너그램)", "어느 순서로 놓을지 직접 정해야 함"

- "가장 큰/작은 k개", "순위", "가까운 순서" → 정렬하면 인덱스로 바로 꺼낼 수 있다
- "순서만 바꾼 것인지" → 정렬한 뒤 비교
- "두 개를 어느 순서로 놓으면 더 좋은가" → 정렬 기준(Comparator)을 직접 정의
- n이 10⁵ 이상이면 O(n²) 정렬(버블 등)은 쓸 수 없고 라이브러리 정렬을 쓴다.

## 시간복잡도 / 공간복잡도

- 시간: 라이브러리 정렬(`Arrays.sort`, `Collections.sort`)은 O(n log n)
- 공간: 구현에 따라 다르다. 기본 타입은 거의 추가 메모리 없이, 객체 정렬은 병합 계열이라 추가 메모리가 필요할 수 있다. 코테에서는 신경 쓰지 않아도 된다.

| 알고리즘 | 시간 |
|---|---|
| 버블, 선택, 삽입 | O(n²) |
| 병합, 힙 | O(n log n) |
| 퀵 | 평균 O(n log n), 최악 O(n²) |
| 계수 정렬 | O(n + k) (값의 범위 k가 작을 때) |

비교로 순서를 정하는 정렬은 최악의 경우 O(n log n)보다 빠를 수 없다고 알려져 있다.

## 자바에서 쓰는 것

Arrays.sort
Collections.sort
Comparator 규칙
compareTo

```java
Arrays.sort(arr);                              // 기본 타입 배열, 오름차순
Arrays.sort(arr, from, to);                    // from 이상 to 미만만 정렬
Collections.sort(list);                        // List
list.sort((a, b) -> b - a);                    // 람다로 기준 지정

Integer[] boxed = {3, 1, 2};
Arrays.sort(boxed, Collections.reverseOrder()); // 내림차순은 객체 배열(Integer[])만 가능

Arrays.sort(arr2D, (a, b) -> Integer.compare(a[0], b[0]));   // 2차원: 0번 열 기준

char[] c = s.toCharArray();                    // String은 정렬 못 함
Arrays.sort(c);
String sorted = new String(c);
```

- 비교 규칙은 두 원소 `a`, `b`를 받아 숫자를 돌려준다. **음수면 a가 앞, 양수면 b가 앞, 0이면 그대로.**
- 오름차순은 `a - b`, 내림차순은 `b - a`. 오버플로가 걱정되면 `Integer.compare(a, b)`.
- 문자열 비교는 `a.compareTo(b)`: 사전순이고, 한쪽이 다른 쪽의 앞부분이면 짧은 쪽이 앞이다.
- `Arrays.sort`는 반환값이 없고 배열 자체를 바꾼다.
- 객체 정렬(`Collections.sort`, `Arrays.sort(T[], cmp)`)은 같은 값끼리 원래 순서를 유지하는 안정 정렬이다.

## 동작 과정

가장 큰 수에서 비교 규칙이 쌍마다 음수/양수를 돌려주는 추적

`Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b))`: 정렬이 배열에서 두 원소를 골라 `a`, `b`로 넘기고, 규칙의 부호로 순서를 정한다. 이걸 필요한 만큼 반복해서 전체가 정렬된다.

| a | b | `b + a` | `a + b` | `(b+a).compareTo(a+b)` | 결론 |
|---|---|---|---|---|---|
| "3" | "30" | "303" | "330" | 음수 | a("3")가 앞 |
| "30" | "3" | "330" | "303" | 양수 | b("3")가 앞 |
| "34" | "3" | "334" | "343" | 음수 | a("34")가 앞 |
| "5" | "34" | "345" | "534" | 음수 | a("5")가 앞 |
| "9" | "5" | "59" | "95" | 음수 | a("9")가 앞 |

같은 쌍을 순서만 바꿔 물어도 같은 결론이 나온다. 최종: `[3, 30, 34, 5, 9]` → `[9, 5, 34, 3, 30]` → `"9534330"`.

- 이어붙인 결과가 큰 쪽이 앞에 오게 하려고 `b + a`를 앞에 쓴다 (내림차순).
- `(a + b).compareTo(b + a)`로 쓰면 작은 수가 되는 순서(오름차순)가 된다.
- 원소가 전부 0이면 `"000"`이 아니라 `"0"`을 반환해야 한다.

K번째수: `Arrays.copyOfRange(array, i - 1, j)`로 구간을 자르고 정렬한 뒤 `[k - 1]`을 꺼낸다. "몇 번째"는 1부터, 인덱스는 0부터라서 `- 1`.

## 자주 하는 실수

- Comparator : 음수면 a가 앞, 오름차순은 a-b
- Arrays.sort(s.toCharArray())는 결과를 버림 (변수에 받기)
- int[]에는 Comparator를 못 넣음 (Integer[]로 바꿔야 함)
- 값이 클 때 a-b는 오버플로 -> Integer.compare
- 정렬 기준 부호를 뒤집어서 오름/내림이 바뀜 (b+a).compareTo(a+b)
- String은 정렬 안 됨 -> char[]로 바꿔서 정렬 후 new String(arr)
- 문자열 `"3"`과 `"30"`을 그냥 `compareTo`로 비교함 (이어붙인 결과끼리 비교해야 함)
- `k번째`를 인덱스 `k`로 씀 (인덱스는 `k - 1`)
- 정렬하면 원본이 바뀐다는 걸 잊고, 원본을 따로 보관하려고 `int[] b = a;`로 대입함 (같은 배열). 복사는 `clone()`이나 `Arrays.copyOf`.
- 반복문 안에서 정렬을 계속 부름 (정렬 비용이 반복 횟수만큼 곱해짐)

## 직접 구현

- [ ] 백지에서 구현 (파일: `03-sorting/`)
- [ ] 작은 입력으로 `main`에서 검증

버블, 선택, 삽입 정렬은 지금 구현할 수 있다. 병합 정렬은 재귀를 배운 뒤([07-recursion](../07-recursion))에 구현하는 게 자연스럽다.

## 연습 문제

| 문제 | 링크 | 상태 |
|---|---|---|
| K번째수 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/42748) | 확인 후 기입 |
| 가장 큰 수 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/42746) | 확인 후 기입 |
| H-Index | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/42747) | 아직 안 품 |
