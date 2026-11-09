public class Hanoi {

    static int moves = 0;   // 옮긴 횟수

    public static void main(String[] args) {
        hanoi(2, 1, 2, 3);
        System.out.println("이동 횟수: " + moves);   // 기대값: 3
        // 기대 출력:
        // 1 -> 2
        // 1 -> 3
        // 2 -> 3

        System.out.println();
        moves = 0;
        hanoi(3, 1, 2, 3);
        System.out.println("이동 횟수: " + moves);   // 기대값: 7
        // 기대 출력:
        // 1 -> 3
        // 1 -> 2
        // 3 -> 2
        // 1 -> 3
        // 2 -> 1
        // 2 -> 3
        // 1 -> 3

        // 생각해 볼 것: n = 1, 2, 3, 4, 5일 때 이동 횟수는? 규칙을 식으로 써 보기
    }

    // 원판 n개를 from 기둥에서 to 기둥으로 옮긴다. via는 잠깐 거쳐 가는 기둥
    // 규칙: 한 번에 한 개씩, 큰 원판을 작은 원판 위에 올릴 수 없다
    public static void hanoi(int n, int from, int via, int to) {
        // TODO: 종료 조건 (옮길 원판이 없으면?)

        // TODO: 1단계 - 위의 n-1개를 어디로 치워 둘까? (믿고 맡기기)

        // TODO: 2단계 - 가장 큰 원판 하나를 옮기기 (출력하고 moves++)

        // TODO: 3단계 - 치워 둔 n-1개를 어디로 옮길까? (믿고 맡기기)
    }
}
