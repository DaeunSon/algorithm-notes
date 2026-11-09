public class Factorial {

    public static void main(String[] args) {
        System.out.println(factorial(0));   // 기대값: 1
        System.out.println(factorial(1));   // 기대값: 1
        System.out.println(factorial(5));   // 기대값: 120

        // 생각해 볼 것: 아래 두 값을 출력해 보고 무슨 일이 생기는지 확인하기
        System.out.println(factorial(20));
        System.out.println(factorial(21));
    }

    // n! = n × (n-1) × ... × 1, 0! = 1
    public static long factorial(int n) {
        // TODO: 종료 조건
        if (n<=1) return 1;
        // TODO: 한 단계 작은 문제를 믿고 맡기기
        return n*factorial(n-1);
    }
}
