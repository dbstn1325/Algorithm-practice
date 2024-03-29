package Samsung_SW_역량테스트.Y2023_포탑부수기;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int N, M, K;
    static Turret[][] turrets;
    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0};
    static boolean isArrive = false;

    static int depthCnt = 0;

    static Turret startTurret;
    static Turret targetTurret;
    static String routes;
    static int cnt = 0;

    static int[] ddr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] ddc = {0, -1, -1, -1, 0, 1, 1, 1};

    static PriorityQueue<Turret> pq = new PriorityQueue<Turret>();
    static PriorityQueue<Turret> reverseOrderQueue;
    static int turn;


    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Samsung_SW_역량테스트/Y2023_포탑부수기/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());
        K = Integer.parseInt(stk.nextToken());

        turrets = new Turret[N][M];
        for (int i = 0; i < N; i++) {
            stk = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int attack = Integer.parseInt(stk.nextToken());

                if(attack <= 0) {
                    turrets[i][j] = new Turret(i, j, 0, true, attack);
                    continue;
                }

                turrets[i][j] = new Turret(i, j, 0, false, attack);
            }
        }

        init();
        cnt = 2;
        turn = 1;
        for(int i=0; i<K; i++) {
            if(cnt <= 1) break;
            isArrive = false;
            depthCnt = 0;
            cnt = 0;

            findStart();
            findTarget();
            startTurret.start(turn);

            isArrive = bfs();
            if (isArrive) {
                attackTurret(startTurret.r, startTurret.c, 0, routes);
                targetAttack();
            }

            if (!isArrive) {
                bomb(targetTurret.r, targetTurret.c);
            }

            finalTurret();
            increaseAttack();

            turn+=1;
        }

        int max = getMax();
        System.out.print(max);
    }

    private static void init() {
        Comparator<Turret> reverseOrderComparator = new Comparator<Turret>() {
            @Override
            public int compare(Turret o1, Turret o2) {
                if(o1.attack == o2.attack) {
                    if (o1.isAttack == o2.isAttack) {
                        if ((o1.r + o1.c) == (o2.r + o2.c)) {
                            return o1.c - o2.c;
                        }
                        return (o1.r + o1.c) - (o2.r + o2.c);
                    }
                    return o1.isAttack - o2.isAttack;
                }
                return o2.attack - o1.attack;
            }
        };
        reverseOrderQueue = new PriorityQueue<>(reverseOrderComparator);
    }

    private static void finalTurret() {
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++) {
                Turret turret = turrets[i][j];
                if(turret.isHurt && turret.attack <= 0) {
                    turret.attack = 0;
                    turret.isBroke = true;
                }
            }
        }
    }

    private static void increaseAttack() {
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++) {
                if(turrets[i][j].isBroke) continue;

                if(!turrets[i][j].isHurt) {
                    turrets[i][j].attack += 1;
                }

                cnt +=1;
                turrets[i][j].isHurt = false;
            }
        }
    }

    private static int getMax() {
        int max = Integer.MIN_VALUE;
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++) {
                if(turrets[i][j].attack > 0) {
                    max = Math.max(max, turrets[i][j].attack);
                }
            }
        }
        return max;
    }

    static void bomb(int r, int c) {
        for(int i=0; i<8; i++) {
            int nr = r + ddr[i];
            int nc = c + ddc[i];

            int[] over = overOfBlock(nr, nc);

            if(over[0] == startTurret.r && over[1] == startTurret.c) continue;
            aroundAttack(over);
        }
        targetAttack();
    }

    private static void targetAttack() {
        turrets[targetTurret.r][targetTurret.c].pushAttack(startTurret.attack);
    }

    static void attackTurret(int r, int c, int depth, String move) {
        if(depth == depthCnt - 1) {
            return ;
        }

        int nr = r + dr[move.charAt(depth) - '0'];
        int nc = c + dc[move.charAt(depth) - '0'];

        int[] over = overOfBlock(nr, nc);

        aroundAttack(over);
        attackTurret(over[0], over[1], depth+1, move);
    }

    private static void aroundAttack(int[] over) {
        turrets[over[0]][over[1]].pushAttack(startTurret.attack/2);
    }

    static boolean bfs() {
        boolean[][] visited = new boolean[N][M];
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(startTurret.r, startTurret.c, 0, ""));
        visited[startTurret.r][startTurret.c] = true;

        while(!queue.isEmpty()) {
            Node cur = queue.poll();

            for(int i=0; i<4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];
                int[] over = overOfBlock(nr, nc);

                if(turrets[over[0]][over[1]].isBroke) continue;
                if(visited[over[0]][over[1]]) continue;

                if(over[0] == targetTurret.r && over[1] == targetTurret.c) {
                    depthCnt = cur.depth + 1;
                    routes = cur.routes;
                    return true;
                }

                visited[over[0]][over[1]] = true;
                if(turrets[over[0]][over[1]].isBroke) continue;
                queue.add(new Node(over[0], over[1], cur.depth + 1, cur.routes + i));
            }
        }

        return false;

    }

    static class Node {
        int r, c, depth;
        String routes;

        public Node(int r, int c, int depth, String routes) {
            this.r = r;
            this.c = c;
            this.depth = depth;
            this.routes = routes;
        }
    }

    public static int[] overOfBlock(int nr, int nc) {
        nr = nr < 0 ? nr + N : nr;
        nr = nr >= N ? nr % N : nr;

        nc = nc < 0 ? nc + M : nc;
        nc = nc >= M ? nc % M : nc;

        return new int[]{nr, nc};
    }

    static void findStart() {
        pq.clear();
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if(!turrets[i][j].isBroke) {
                    pq.add(turrets[i][j]);
                }
            }
        }

        startTurret = pq.poll();
    }

    private static void findTarget() {
        reverseOrderQueue.clear();
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if(!turrets[i][j].isBroke) {
                    reverseOrderQueue.add(turrets[i][j]);
                }
            }
        }
        targetTurret = reverseOrderQueue.poll();
    }

    static class Turret implements Comparable<Turret> {
        int r, c, isAttack;
        boolean isBroke;
        boolean isHurt = false;
        int attack;

        public Turret(int r, int c, int isAttack, boolean isBroke, int attack) {
            this.r = r;
            this.c = c;
            this.isAttack = isAttack;
            this.isBroke = isBroke;
            this.attack = attack;
        }

        void start(int turn) {
            this.isAttack = turn;
            this.isHurt = true;
            this.attack += (N + M);
        }

        public void pushAttack(int at) {
            this.isHurt = true;
            this.attack -= at;
        }

        @Override
        public int compareTo(Turret other) {
            if(this.attack == other.attack) {
                if (this.isAttack == other.isAttack) {
                    if ((this.r + this.c) == (other.r + other.c)) {
                        return other.c - this.c;
                    }
                    return (other.r + other.c) - (this.r + this.c);
                }

                return other.isAttack - this.isAttack;
            }
            return this.attack - other.attack;
        }
    }

}
