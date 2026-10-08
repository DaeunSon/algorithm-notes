# 스택/큐

## 한 줄 정의

- 스택은 후입선출(LIFO): 가장 나중에 넣은 것을 먼저 꺼낸다 (접시 쌓기)
- 큐는 선입선출(FIFO): 가장 먼저 넣은 것을 먼저 꺼낸다 (줄서기)

## 언제 쓰나 (판별 단서)

"다음에 처리할 대상이 가장 먼저 들어온 것인가, 가장 최근인가?"
빼내면 양옆이 이어져 새 조건이 생기면 스택, 앞이 뒤를 막으면 큐

| 상황 | 선택 | 예 |
|---|---|---|
| 최근 것과 짝을 맞추거나 패턴을 확인하고 빼낸다 | 스택 | 햄버거 만들기, 올바른 괄호, 같은 글자 연속 제거 |
| 먼저 온 순서대로 처리, 앞이 끝나야 뒤가 진행 | 큐 | 기능개발, 프로세스, BFS |
| 가장 작은/큰 값을 계속 꺼낸다 | 우선순위 큐 | [13-priority-queue](../13-priority-queue) |
| 그냥 앞에서부터 하나씩 대응 | 자료구조 불필요 (포인터) | 카드 뭉치 |

순서를 지켜야 하는 문제에서는 해시를 쓰면 안 된다.

## 시간복잡도 / 공간복잡도

- 시간: `push`, `pop`, `peek`, `add`, `poll`, `isEmpty` 모두 O(1)
- 공간: O(n)
- 주의: `ArrayList`의 맨 앞 삭제 `remove(0)`는 O(n)이라 큐로 쓰면 안 된다.

## 자바에서 쓰는 것

배열 + top, ArrayDeque, Stack, Queue<int[]>, LinkedList,
add/poll/peek/isEmpty

stack : push(x), pop(), peek()
queue : add(x) / offer(x), poll(), peek()

```java
Deque<Integer> stack = new ArrayDeque<>();   // 스택으로 쓸 것
stack.push(1);  stack.push(2);
stack.peek();   // 2 (꺼내지 않고 확인)
stack.pop();    // 2

Queue<Integer> queue = new ArrayDeque<>();   // 큐로 쓸 것
queue.add(1);   queue.add(2);
queue.peek();   // 1
queue.poll();   // 1
```

- `ArrayDeque`는 양쪽 끝을 다룰 수 있어서 스택과 큐 둘 다로 쓴다. 선언 타입(`Deque`, `Queue`)으로 의도를 드러낸다.
- 배열 + `top`: `top`이 쌓인 개수다. 값을 지우지 않고 `top`만 줄이면 제거한 것과 같다 (다음에 쌓을 때 덮어쓴다).
- `ArrayDeque`는 `null`을 저장할 수 없다.
- 비어 있을 때: `pop()`은 `NoSuchElementException`, `peek()`와 `poll()`은 `null`을 돌려준다. `null`을 `int`로 받으면 NPE가 난다. `Stack` 클래스의 `pop()`은 `EmptyStackException`이다.
- `for (int x : queue)`처럼 꺼내지 않고 훑어볼 수 있다 (프로세스 문제에서 남은 것 중 더 높은 우선순위 확인).

## 동작 과정

햄버거 만들기: `[2, 1, 1, 2, 3, 1, 2, 3, 1]`. 쌓다가 맨 위 4개가 `1, 2, 3, 1`이면 `top -= 4`.

| 넣은 재료 | 쌓인 상태 | 맨 위 4개 | 처리 | 완성 |
|---|---|---|---|---|
| 2 | [2] | | | 0 |
| 1 | [2, 1] | | | 0 |
| 1 | [2, 1, 1] | | | 0 |
| 2 | [2, 1, 1, 2] | 1,1,2 아님 | | 0 |
| 3 | [2, 1, 1, 2, 3] | | | 0 |
| 1 | [2, 1, 1, 2, 3, 1] | 1, 2, 3, 1 | 제거 → [2, 1] | 1 |
| 2 | [2, 1, 2] | | | 1 |
| 3 | [2, 1, 2, 3] | | | 1 |
| 1 | [2, 1, 2, 3, 1] | 1, 2, 3, 1 | 제거 → [2] | **2** |

첫 번째 햄버거를 빼내면 남은 `[2, 1]`이 뒤 재료와 이어져서 두 번째 햄버거가 만들어진다. 이게 "빼내면 양옆이 이어진다"는 뜻이고, 그래서 스택이다.

올바른 괄호: 종류가 하나면 스택 대신 카운터로 충분하다.

```
"()(()"  : ( → 1,  ) → 0,  ( → 1,  ( → 2,  ) → 1   끝에 1 → false
")("     : ) → -1 → 중간에 음수 → false
```

## 자주 하는 실수

- 스택과 큐를 헷갈림 : 스택은 위에서, 큐는 앞에서 꺼냄
- Stack 클래스는 구식, ArrayDeque를 권장
- 매 재료마다 해야 하는 `if` 검사를 `for` 밖에 둠 (햄버거: 쌓기와 검사는 같은 반복 안)
- `top >= 4`를 먼저 확인하지 않고 `stack[top-4]`를 읽음 (음수 인덱스)
- 괄호 문제에서 개수만 비교함 (`")("`는 개수가 같아도 틀림). 중간에 음수가 되는지 확인해야 한다.
- 빈 스택/큐에서 꺼냄: `pop()`은 예외, `poll()`/`peek()`는 `null` → 언박싱 NPE
- 반복문 안에서 `ArrayList.remove(0)`으로 큐처럼 씀 → O(n²)

## 직접 구현

- [ ] 백지에서 구현 (파일: `02-stack-queue/`)
- [ ] 작은 입력으로 `main`에서 검증

## 메모
스택/큐는 개념, Queue·Deque는 인터페이스, LinkedList·ArrayDeque는 구현 클래스.
권장: Deque<T> stack = new ArrayDeque<>();  Queue<T> q = new ArrayDeque<>();

```
Collection
├─ List (인터페이스)
│   ├─ ArrayList
│   ├─ LinkedList  ──┐
│   └─ Vector        │
│       └─ Stack     │   (구식 스택 클래스)
│                    │
└─ Queue (인터페이스) │
    ├─ PriorityQueue │
    └─ Deque (인터페이스)
        ├─ ArrayDeque
        └─ LinkedList ◀┘  (List와 Deque를 둘 다 구현)
```

LinkedList는 리스트에 큐 기능이 붙은 것, ArrayDeque는 스택·큐 전용으로 만든 것

| | ArrayList | LinkedList | ArrayDeque |
|---|---|---|---|
| 구현하는 인터페이스 | List | List, Deque | Deque |
| 내부 구조 | 배열 | 노드 연결 (이중 연결 리스트) | 원형 배열 |
| `get(i)` | O(1) | O(n) | 없음 |
| 끝에 추가 | O(1) (평균) | O(1) | O(1) |
| 맨 앞에 추가/삭제 | O(n) | O(1) | O(1) |
| `null` 저장 | 가능 | 가능 | 불가능 |
| 주 용도 | 목록 (인덱스 접근) | 코테에서는 거의 안 씀 | 스택, 큐 |

- 이중 연결 리스트: 노드마다 값과 앞뒤 노드 주소를 가진다. 중간 노드를 이미 알면 삽입/삭제가 O(1)이다.
- 원형 배열: 배열 하나에 `head`, `tail` 인덱스만 기억하고, 끝에 닿으면 반대편으로 돌아간다. 메모리가 연속이라 빠르다.
- 스택/큐는 양쪽 끝만 쓰므로 `ArrayDeque`를 쓴다.

## 연습 문제

| 문제 | 링크 | 상태 |
|---|---|---|
| 같은 숫자는 싫어 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/12906) | 확인 후 기입 |
| 올바른 괄호 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/12909) | 확인 후 기입 |
| 기능개발 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/42586) | 확인 후 기입 |
| 프로세스 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/42587) | 확인 후 기입 |
| 햄버거 만들기 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/133502) | 확인 후 기입 |
| 주식가격 | [프로그래머스](https://school.programmers.co.kr/learn/courses/30/lessons/42584) | 아직 안 품 |
