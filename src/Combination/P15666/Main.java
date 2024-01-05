package Combination.P15666;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static int N, M;
    public static boolean[] visited;
    public static int[] num;
    public static int[] arr;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Combination/P15666/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        stk = new StringTokenizer(br.readLine());
        num = new int[N];
        for(int i=0; i<N; i++){
            num[i] = Integer.parseInt(stk.nextToken());
        }
        Arrays.sort(num);

        arr = new int[M];
        visited = new boolean[N];
        combination(0, 0);
    }

    public static void combination(int depth, int startIdx) {
        if(depth == M) {
            for(int a: arr) {
                System.out.print(a + " ");
            }
            System.out.println();
            return ;
        }

        for(int i=startIdx; i<N; i++) {
            if(i != 0 && num[i-1] == num[i]) { //같은 depth 내에서 또 다른 재귀를 들어가기 전 기억
                continue;
            }

            arr[depth] = num[i];
            combination(depth + 1, i);
        }

    }
}
