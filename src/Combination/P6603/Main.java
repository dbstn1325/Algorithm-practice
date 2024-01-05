package Combination.P6603;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 주어지는 수 s
 * s 중 6개를 뽑는다.
 */
public class Main {
    static int k;
    static int[] s;
    static int[] sel;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Combination/P6603/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        while(true) {
            stk = new StringTokenizer(br.readLine());
            k = Integer.parseInt(stk.nextToken());
            if(k==0) {
                break;
            }

            s = new int[k];
            for(int i=0; i<k; i++) {
                s[i] = Integer.parseInt(stk.nextToken());
            }

            sel = new int[6];
            combi(0, 0);
            System.out.println();
        }

    }

    public static void combi(int depth, int start) {
        if(depth == 6) {
            for(int val: sel) {
                System.out.print(val + " ");
            }
            System.out.println();

            return;
        }

        for(int i=start; i<k; i++) {
            sel[depth] = s[i];
            combi(depth+1, i+1);
        }
    }
}
