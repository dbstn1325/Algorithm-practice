package Combination.P2422;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] sel;
    static boolean[][] notMatch;
    static int cnt = 0;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Combination/P2422/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        stk = new StringTokenizer(br.readLine());
        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        notMatch = new boolean[n+1][n+1];
        for(int i=0; i<m; i++) {
            stk = new StringTokenizer(br.readLine());
            int p1 = Integer.parseInt(stk.nextToken());
            int p2 = Integer.parseInt(stk.nextToken());
            notMatch[p1][p2] = true;
            notMatch[p2][p1] = true;
        }

        sel = new int[3];
        combi(0, 0);
        System.out.println(cnt);
    }

    static void combi(int depth, int start) {
        if(depth == 3) {
            cnt++;
            return;
        }

        for(int i=start; i<n; i++) {
            sel[depth] = i+1;
            if(depth >= 1 && !checkNotMatch(i+1, depth)) continue;
            combi(depth+1, i+1);
        }
    }

    static boolean checkNotMatch(int num, int depth) {
        for(int i=0; i<notMatch[num].length; i++) {
            boolean[] targetMatch = notMatch[num];

            for(int j=0; j<=depth; j++) {
                if(targetMatch[sel[j]]) {
                    return false;
                }
            }
        }

        return true;
    }
}
