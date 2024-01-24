package Samsung_SW_역량테스트.Y2023_포탑부수기;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class MLE {

    static int N, M, K;
    static List<Turret> turrets;
    static boolean broke[][];
    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0};
    static int totalCnt = 0;
    static int minCnt = Integer.MAX_VALUE;

    static int depthCnt = 0;

    static Turret startTurret;
    static Turret targetTurret;
    static boolean isComplete = false;
    static String routes;

    static int[] ddr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] ddc = {0, -1, -1, -1, 0, 1, 1, 1};


    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Samsung_SW_역량테스트/Y2023_포탑부수기/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());
        K = Integer.parseInt(stk.nextToken());

        turrets = new ArrayList<>();
        broke = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            stk = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int attack = Integer.parseInt(stk.nextToken());
                if (attack > 0) {
                    turrets.add(new Turret(i, j, 0, false, attack));
                    continue;
                }
                broke[i][j] = true;
            }
        }


        while (K-- > 0) {
            if(turrets.size() <= 1) break;
            totalCnt = 0;
            minCnt = Integer.MAX_VALUE;
            depthCnt = 0;
            isComplete = false;

            sortAttacker();
            startTurret = turrets.get(0);
            targetTurret = turrets.get(turrets.size() - 1);
            startTurret.start();

            bfs();
            if (totalCnt >= 1) {
                depthCnt = minCnt;
                dfs(0, startTurret.r, startTurret.c, "");
                attackTurret(startTurret.r, startTurret.c, 0, routes);
            }

            if (totalCnt < 1) {
                bomb(targetTurret.r, targetTurret.c);
            }

            turrets.stream()
                    .filter(t-> !t.isHurt)
                    .forEach(tr -> tr.attack += 1);

            for(Turret turret: turrets) {
                if(turret.attack <= 0) {
                    turret.isBroke = true;
                    broke[turret.r][turret.c] = true;
                }
            }
            replace();
        }

        Collections.sort(turrets, new Comparator<Turret>() {
            @Override
            public int compare(Turret o1, Turret o2) {
                return o2.attack - o1.attack;
            }
        });
        System.out.println(turrets.get(0).attack);
    }

    private static void replace() {
        List<Turret> collect = turrets.stream()
                .filter(v -> v.isBroke == false).collect(Collectors.toList());
        turrets = collect;
        turrets.stream()
                .forEach(v -> v.isHurt = false);
    }

    static void bomb(int r, int c) {
        for(int i=0; i<8; i++) {
            int nr = r + ddr[i];
            int nc = c + ddc[i];

            int[] over = overOfBlock(nr, nc);

            if(over[0] == startTurret.r && over[1] == startTurret.c) continue;
            aroundAttack(over);
        }
        targetAttack(new int[]{r, c}, true);
    }

    static void attackTurret(int r, int c, int depth, String move) {
        if(depth == depthCnt) {
            return ;
        }

        int nr = r + dr[move.charAt(depth) - '0'];
        int nc = c + dc[move.charAt(depth) - '0'];

        int[] over = overOfBlock(nr, nc);
        aroundAttack(over);
        targetAttack(over, false);
        attackTurret(over[0], over[1], depth+1, move);
    }

    private static void aroundAttack(int[] over) {
        turrets.stream()
                .filter(t -> t.r == over[0] && t.c == over[1])
                .forEach(tr -> tr.pushAttack(startTurret.attack/2));
    }

    private static void targetAttack(int[] over, boolean isBomb) {
        int attack = getAttack(isBomb);

        if(over[0] == targetTurret.r && over[1] == targetTurret.c) {
            turrets.stream()
                    .filter(t -> t.r == targetTurret.r && t.c == targetTurret.c)
                    .forEach(tr -> tr.pushAttack(attack));
        }
    }

    private static int getAttack(boolean isBomb) {
        if(isBomb) return startTurret.attack;

        return startTurret.attack %2 == 0 ? startTurret.attack /2 : startTurret.attack /2+1;
    }

    static void dfs(int depth, int r, int c, String route) {
        if(depth > depthCnt) {
            return ;
        }

        if(depth == depthCnt && r == targetTurret.r && c == targetTurret.c && !isComplete) {
            routes = route;
            isComplete = true;
            return ;
        }

        for(int i=0; i<4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            int[] over = overOfBlock(nr, nc);
            dfs(depth + 1, over[0], over[1], route + i);
        }
    }

    static void bfs() {
        boolean[][] visited = new boolean[N][M];
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(startTurret.r, startTurret.c, 0));
        visited[startTurret.r][startTurret.c] = true;

        while(!queue.isEmpty()) {
            Node cur = queue.poll();

            for(int i=0; i<4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];
                int[] over = overOfBlock(nr, nc);

                if(broke[over[0]][over[1]]) continue;
                if(visited[over[0]][over[1]]) continue;
                if(minCnt != Integer.MAX_VALUE && cur.cnt > minCnt) continue;

                if(over[0] == targetTurret.r && over[1] == targetTurret.c) {
                    minCnt = Math.min(minCnt, cur.cnt + 1);
                    if(minCnt == cur.cnt+1) {
                        totalCnt += 1;
                    }
                    continue;
                }

                visited[over[0]][over[1]] = true;
                queue.add(new Node(over[0], over[1], cur.cnt + 1));
            }
        }

    }

    static class Node {
        int r, c, cnt;

        public Node(int r, int c, int cnt) {
            this.r = r;
            this.c = c;
            this.cnt = cnt;
        }
    }

    public static int[] overOfBlock(int nr, int nc) {
        nr = nr < 0 ? nr + N : nr;
        nr = nr >= N ? nr % N : nr;

        nc = nc < 0 ? nc + M : nc;
        nc = nc >= M ? nc % M : nc;

        return new int[]{nr, nc};
    }

    static void sortAttacker() {
        Collections.sort(turrets, new Comparator<Turret>() {
            @Override
            public int compare(Turret o1, Turret o2) {
                if(o1.attack == o2.attack) {
                    if (o1.isAttack == o2.isAttack) {
                        if ((o1.r + o1.c) == (o2.r + o2.c)) {
                            return o2.c - o1.c;
                        }
                        return (o2.r + o2.c) - (o1.r + o1.c);
                    }

                    return o2.isAttack - o1.isAttack;
                }
                return o1.attack - o2.attack;
            }
        });
    }


    static class Turret {
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

        void start() {
            this.isAttack += 1;
            this.isHurt = true;
            this.attack += (N + M);
        }

        public void pushAttack(int at) {
            this.isHurt = true;
            this.attack -= at;
        }
    }

}
