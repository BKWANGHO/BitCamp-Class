import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long x, y, w, h, rex,rey = 0L;
        x = sc.nextLong();
        y = sc.nextLong();
        w = sc.nextLong();
        h = sc.nextLong();

        if (x < (w - x)) {
            rex = x;
        } else {
            rex = w - x;
        }

        if (y < (h - y)) {
            rey =y;
        }else {
            rey = h-y;
        }

        System.out.println(Math.min(rex, rey));


    }
}