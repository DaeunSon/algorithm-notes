public class Power {

    static long callsSlow = 0;
    static long callsFast = 0;

    public static void main(String[] args) {
        System.out.println(powSlow(2, 10));   // 기대값: 1024
        System.out.println(callsSlow);        // 몇 번 불렸을까?

        System.out.println(powFast(2, 10));   // 기대값: 1024
        System.out.println(callsFast);        // powSlow와 비교하기

        System.out.println(powFast(3, 0));    // 기대값: 1
        System.out.println(powFast(2, 62));   // 기대값: 4611686018427387904
        System.out.println(powFast(3, 13));   // 기대값: 1594323 (홀수 지수)

        // 생각해 볼 것: powSlow(2, 100000)을 부르면 무슨 일이 생길까? (값은 넘치지만, 그 전에 다른 에러가 남)
        System.out.println(powFast(2, 100000));
    }

    // x^n = x × x^(n-1)  →  한 번에 지수를 1씩 줄이기, O(n)
    public static long powSlow(long x, int n) {
        callsSlow++;
        // TODO: 종료 조건
        if (n==0) return 1;

        // TODO: 한 단계 작은 문제를 믿고 맡기기
        return x * powSlow(x, n-1);
    }

    // x^n = x^(n/2) × x^(n/2)  →  한 번에 지수를 절반으로 줄이기, O(log n)
    // 주의 1: x^(n/2)를 두 번 부르면 다시 O(n)이 된다. 한 번만 계산해서 변수에 담기
    // 주의 2: n이 홀수면 n/2를 두 번 곱해도 x가 하나 모자란다
    public static long powFast(long x, int n) {
        callsFast++;
        // TODO: 종료 조건
        if (n==0) return 1;
        // TODO: 절반 문제를 한 번만 맡기기
        long half = powFast(x, n/2);
        // TODO: n이 짝수일 때 / 홀수일 때
        return (n%2==0) ? half*half : half*half*x;
    }
}
