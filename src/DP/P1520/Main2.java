package DP.P1520;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main2 {
    public static int N, M;
    public static int[][] graph;
    public static int[][] count;
    public static int[] dr = {-1, 0};
    public static int[] dc = {0, -1};

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P1520/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk;
        stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        graph = new int[N+1][M+1];
        for(int i=0; i<=N; i++){
            Arrays.fill(graph[i], 10001);
        }
        count = new int[N+1][M+1];
        for(int i=1; i<=N; i++){
            stk = new StringTokenizer(br.readLine());
            for(int j=1; j<=M; j++){
                graph[i][j] = Integer.parseInt(stk.nextToken());
            }
        }

        if(N==1) {
            if(M==1){
                System.out.println(1);
                return;
            }
            if(M==2) {
                int ans = graph[1][1] > graph[1][2] ? 1 : 0;
                System.out.println(ans);
                return;
            }
        }
        if(N==2 && M==1) {
            int ans = graph[1][1] > graph[2][1] ? 1 : 0;
            System.out.println(ans);
            return;
        }

        count[1][2] = graph[1][1] > graph[1][2] ? 1 : 0;
        count[2][1] = graph[1][1] > graph[2][1] ? 1 : 0;
        for(int i=1; i<=N; i++){
            for(int j=1; j<=M; j++){
                if(i==1&&j==1) continue;

                for(int k=0; k<2; k++){
                    int nr = i + dr[k];
                    int nc = j + dc[k];

                    if(graph[nr][nc] == 10001) {
                        continue;
                    }

                    if(count[nr][nc] != 0 && graph[nr][nc] > graph[i][j]) {
                        count[i][j] += count[nr][nc];
                        continue;
                    }

                    if(count[nr][nc] != 0 && graph[nr][nc] > graph[i][j] && count[i][j] == 0) {
                        count[i][j] = 1;
                        continue;
                    }

                    if(count[nr][nc] != 0 && graph[nr][nc] <= graph[i][j] && count[i][j] != 0) {
                        count[nr][nc]+=1;
                    }
                }
            }
        }

        System.out.println(count[N][M]);


    }
}
