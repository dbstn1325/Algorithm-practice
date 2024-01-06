package Combination.P2529;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int k;
    static char[] ch;
    static int[] sel;
    static boolean[] visited;
    static long max = Long.MIN_VALUE, min = Long.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Combination/P2529/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        k = Integer.parseInt(br.readLine());

        stk = new StringTokenizer(br.readLine());
        ch = new char[k];
        for(int i=0; i<k; i++) {
            ch[i] = stk.nextToken().charAt(0);
        }

        sel = new int[k+1];
        visited = new boolean[10];
        combination(0);
        System.out.println(max);
        System.out.println(min < 100 || !String.valueOf(min).contains("0") ? "0" + min : min);
    }

    static void combination(int depth) {
        if(depth == k+1) {
            String s = "";
            for(int val: sel) {
                s+=val;
            }
            long num = Long.parseLong(s);
            max = Math.max(num, max);
            min = Math.min(num, min);
            return;
        }

        for(int i=0; i<=9; i++) {
            if (depth >= 1 && check(depth-1, i)) continue;

            if(visited[i]) continue;

            visited[i] = true;
            sel[depth] = i;
            combination(depth+1);
            visited[i] = false;
        }
    }

    static boolean check(int prevDepth, int curNum) {
        if (ch[prevDepth] == '<' && sel[prevDepth] >= curNum) return true;
        if (ch[prevDepth] == '>' && sel[prevDepth] <= curNum) return true;

        return false;
    }
}
