package Graph.P1717;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static int[] parent;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Graph/P1717/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        parent = new int[N+1];
        for(int i=1; i<=N; i++) {
            parent[i] = i;
        }

        for(int i=0; i<M; i++) {
            stk = new StringTokenizer(br.readLine());
            int signal = Integer.parseInt(stk.nextToken());
            int from = Integer.parseInt(stk.nextToken());
            int to = Integer.parseInt(stk.nextToken());

            if(signal == 0) {
                int fromParent = find(from);
                int toParent = find(to);

                if(fromParent == toParent) continue;

                union(from, to);
                continue;
            }

            //부모만 바뀌고, 자식은 안바뀌는 경우 즉, 부모가 다른 대상을 공통 부모로 가지는 경우를 대비하기 위해 parent(x) find(o)
            String ans = find(from) == find(to) ? "YES" : "NO";
            System.out.println(ans);
        }

    }


    static int find(int targetNode) {
        if(parent[targetNode] == targetNode) {
            return targetNode;
        }

        parent[targetNode] = find(parent[targetNode]); //자식이 바뀌는 경우, 부모도 이어서 바꿔주기 위해
        return parent[targetNode];
    }

    static void union(int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if(p1 < p2) {
            parent[p2] = p1;
            return ;
        }

        parent[p1] = p2;
    }
}
