package Combination.P11050;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Factorial {

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Combination/P11050/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(stk.nextToken());
        int k = Integer.parseInt(stk.nextToken());

        System.out.println(fact(n) / (fact(k) * fact(n-k)));
    }

    //5C2 = n! / r!(n-r)!

    public static int fact(int n) {
        if(n==0) {
            return 1;
        }

        return n * fact(n-1);
    }
}
