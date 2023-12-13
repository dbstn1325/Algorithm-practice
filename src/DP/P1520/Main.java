package DP.P1520;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static int N, M;
    public static int[][] graph;
    public static int[][] count;
    public static int[] dr = {-1, 1, 0, 0};
    public static int[] dc = {0, 0, -1, 1};
    public static boolean[][] visited;

    /**
     * 테스트 - 기존 dfs
     */
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P1520/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk;
        stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        graph = new int[N+1][M+1];
        count = new int[N+1][M+1];
        visited = new boolean[N+1][M+1];
        for(int i=0; i<N; i++){
            stk = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++){
                graph[i][j] = Integer.parseInt(stk.nextToken());
            }
        }

        dfs(0, 0);
        System.out.println(count[N-1][M-1]);
    }

    public static void dfs(int startR, int startC) {
        if(startR == M-1 && startC == N-1) {
            return;
        }

        for(int i=0; i<4; i++) {
            int nr = startR + dr[i];
            int nc = startC + dc[i];

            if(nr < 0 || nr >= N || nc < 0 || nc >= M) continue;

            if(graph[startR][startC] > graph[nr][nc] && !visited[nr][nc]) {
                count[nr][nc] += 1;
                visited[startR][startC] = true;
                dfs(nr, nc);
                visited[startR][startC] = false;
            }
        }
    }
}
