package Graph.P1944;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static int[][] graph;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static boolean[][] visited;
    static PriorityQueue<Vertex> pq = new PriorityQueue<>();
    static int[] parent;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Graph/P1944/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk;

        stk  = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        graph = new int[N][N];
        int index = 1;
        for(int i=0; i<N; i++) {
            String str = br.readLine();
            for(int j=0; j<N; j++) {
                char ch = str.charAt(j);

                if(ch == 'S' || ch == 'K') {
                    graph[i][j] = index;
                    index+=1;
                    continue;
                }
                if(ch == '1') {
                    graph[i][j] = -1;
                }
            }
        }

        parent = new int[index+1];
        for(int i=1; i<=index; i++) {
            parent[i] = i;
        }

        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                if(graph[i][j] <= 0) continue;

                bfs(i,j);
            }
        }



       kruskal();

    }

    static void kruskal() {
        int weight = 0;
        int cnt = 0;

        while(!pq.isEmpty()) {
            Vertex curVertex = pq.poll();

            int fromParent = find(curVertex.from);
            int toParent = find(curVertex.to);
            if(fromParent == toParent) continue;

            union(curVertex.from, curVertex.to);
            cnt++;
            weight += curVertex.weight;
        }

        int ans = cnt == (M + 1) -1 ? weight : -1; //흩어진 열쇠 정점과 시작 정점의 합이 정점 갯수이기 때문에 정점 갯수 -1
        System.out.println(ans);
    }

    static void union(int from, int to) {
        int fromP = find(from);
        int toP = find(to);

        if(fromP < toP) {
            parent[toP] = fromP;
            return ;
        }

        parent[fromP] = toP;
    }

    static int find(int t) {
        if(parent[t] == t) {
            return t;
        }

        return parent[t] = find(parent[t]);
    }



    static void bfs(int startR, int startC) {
        visited = new boolean[N][N];
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(startR, startC, 0));
        visited[startR][startC] = true;

        while(!queue.isEmpty()) {
            Node node = queue.poll();

            for(int i=0; i<4; i++) {
                int nr = node.r + dr[i];
                int nc = node.c + dc[i];

                if(nr < 0 || nr > N-1 || nc < 0 || nc > N-1) continue;
                if(visited[nr][nc] || graph[nr][nc] < 0) continue;

                if(graph[nr][nc] > 0 && graph[startR][startC] != graph[nr][nc]) {
                    pq.offer(new Vertex(graph[startR][startC], graph[nr][nc], node.cost+1));
                }

                visited[nr][nc] = true;
                queue.offer(new Node(nr, nc, node.cost + 1));
            }
        }


    }

    static class Vertex implements Comparable<Vertex> {
        int from, to, weight;

        public Vertex(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Vertex other) {
            return this.weight - other.weight;
        }
    }

    static class Node {
        int r, c, cost;

        public Node(int r, int c, int cost) {
            this.r = r;
            this.c = c;
            this.cost = cost;
        }
    }
}
