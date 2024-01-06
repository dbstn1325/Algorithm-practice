package Combination.P2529;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Way {
    static int k;
    static char[] ch;
    static int[] sel;
    static boolean[] visited;
    static ArrayList<String> list = new ArrayList<>();

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
        combination(0, "");
        System.out.println(list.get(list.size()-1)); //리스트의 가장 끝(최대)
        System.out.println(list.get(0));             //리스트의 가장 처음(최소)
    }

    static void combination(int depth, String str) { // 재귀적으로 현재 숫자를 문자열로 담아둔다.
        if(depth == k+1) {
            list.add(str); // 쌓인 문자열을 모두 리스트에 넣는다.
            return;
        }

        for(int i=0; i<=9; i++) {
            if (depth >= 1 && check(depth-1, i)) continue;

            if(visited[i]) continue;

            visited[i] = true;
            sel[depth] = i;
            combination(depth+1, str + i);
            visited[i] = false;
        }
    }

    static boolean check(int prevDepth, int curNum) {
        if (ch[prevDepth] == '<' && sel[prevDepth] >= curNum) return true;
        if (ch[prevDepth] == '>' && sel[prevDepth] <= curNum) return true;

        return false;
    }
}
