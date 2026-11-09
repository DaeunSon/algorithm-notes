public class Fibonacci {

    static long callsPlain = 0;   // fib가 불린 횟수
    static long callsMemo = 0;    // fibMemo가 불린 횟수

    public static void main(String[] args) {
        // 1) 그냥 재귀
        System.out.println(fib(10));          // 기대값: 55
        System.out.println(callsPlain);       // 몇 번 불렸을까? (손으로 센 fib(4) = 9번과 비교)

        // 2) 메모이제이션
        long[] memo = new long[51];           // memo[i] = fib(i)를 이미 계산했으면 그 값, 아니면 0
        System.out.println(fibMemo(10, memo)); // 기대값: 55
        System.out.println(callsMemo);         // 1)과 비교하기

        // 3) 생각해 볼 것: 아래 두 줄의 실행 시간을 비교해 보기
        //    (그냥 재귀로 50을 넣으면 끝나지 않으니 40까지만)
        System.out.println(fib(40));                     // 기대값: 102334155
        System.out.println(fibMemo(50, new long[51]));   // 기대값: 12586269025 (int로는 넘침)
    }

    // fib(0) = 0, fib(1) = 1, fib(n) = fib(n-1) + fib(n-2)
    public static long fib(int n) {
        callsPlain++;
        // TODO: 종료 조건
        if (n==2) return 1;
        if (n==1) return 1;
        if (n==0) return 0;

        // TODO: 한 단계 작은 문제 두 개를 믿고 맡기기
        return fib(n-1) + fib(n-2);
    }

    // fib와 같은데, 한 번 계산한 값은 memo에 저장해 두고 다시 쓰기 - 가지치기
    public static long fibMemo(int n, long[] memo) {
        callsMemo++;
        // TODO: 종료 조건
        if (n==2) return 1;
        if (n==1) return 1;
        if (n==0) return 0;

        // TODO: 이미 계산한 값이면 바로 돌려주기
        if (memo[n]!=0) return memo[n];
        // TODO: 계산해서 memo에 저장하고 돌려주기
        memo[n] = fibMemo(n-1, memo) + fibMemo(n-2, memo);
        return memo[n];
    }
}
