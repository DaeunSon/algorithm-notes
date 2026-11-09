public class PrintOrder {

    public static void main(String[] args) {
        printDown(5);
        System.out.println();
        printUp(5);
    }

    public static void printDown(int n) {
        if (n == 0) return;
        System.out.print(n+ " ");
        printDown(n - 1);
    }

    public static void printUp(int n) {
        if (n == 0) return;
        printUp(n - 1);
        System.out.print(n+ " ");
    }
}