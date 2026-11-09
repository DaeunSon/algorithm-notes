public class ArraySum {

    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 1, 5};
        System.out.println(sum(arr, /* TODO: 처음 idx */ 0));   // 기대값: 14

        int[] empty = {};
        System.out.println(sum(empty, /* TODO: 처음 idx */ 0)); // 기대값: 0
    }

    // arr[idx]부터 끝까지의 합
    public static int sum(int[] arr, int idx) {
        // TODO: 종료 조건
        if (idx==arr.length) return 0;
        // TODO: 한 단계 작은 문제를 믿고 맡기기
        return arr[idx] + sum(arr, idx+1);
    }
}
