package Permutation.P15649;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static int N, M;
    public static boolean[] visited;
    public static int[] arr;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Permutation/P15649/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        visited = new boolean[N+1];
        arr = new int[M];
        permutation(0);
    }

    public static void permutation(int depth) {
        if(depth == M) {
            for(int a: arr) {
                System.out.print(a + " ");
            }
            System.out.println();
            return ;
        }

        for(int i=1; i<=N; i++) {
            if(!visited[i]) { //중복인 경우의 수를 방지하기 위해 visited 사용
                visited[i] = true;
                arr[depth]=i;
                permutation(depth+1);
                visited[i] = false;
            }
        }
    }
}
