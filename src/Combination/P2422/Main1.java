package Combination.P2422;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main1 {
    static int n, m;
    static int[] sel;
    static int[][] notMatch;
    static int cnt = 0;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Combination/P2422/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        stk = new StringTokenizer(br.readLine());
        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());

        notMatch = new int[m][2];
        for(int i=0; i<m; i++) {
            stk = new StringTokenizer(br.readLine());
            notMatch[i][0] = Integer.parseInt(stk.nextToken());
            notMatch[i][1] = Integer.parseInt(stk.nextToken());
        }

        sel = new int[3];
        combi(0, 0);
        System.out.println(cnt);
    }

    static void combi(int depth, int start) {
        if(depth == 3) {
            if(!checkNotMatch(depth)) return;
            cnt++;
            return;
        }

        for(int i=start; i<n; i++) {
            sel[depth] = i+1;
            combi(depth+1, i+1);
        }
    }

    static boolean checkNotMatch(int depth) {
        List<Integer> select = Arrays.stream(sel)
                .boxed()
                .collect(Collectors.toList());

        for(int[] n: notMatch) {
            boolean isFlag1 = select.contains(n[0]);
            boolean isFlag2 = select.contains(n[1]);

            if(isFlag1 && isFlag2) {
                return false;
            }
        }

        return true;
    }
}
