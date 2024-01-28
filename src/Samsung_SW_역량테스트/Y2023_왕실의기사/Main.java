package Samsung_SW_역량테스트.Y2023_왕실의기사;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int L, N, Q;
    static Soldier[][] soldier;
    static int[][] option;
    static ArrayList<int[]> orders;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static Soldier[][] tmpSoldier;
    static ArrayList<Soldier> list;
    static int[] dHp;
    static int[] totalAttack;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Samsung_SW_역량테스트/Y2023_왕실의기사/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        stk = new StringTokenizer(br.readLine());
        L = Integer.parseInt(stk.nextToken());
        N = Integer.parseInt(stk.nextToken());
        Q = Integer.parseInt(stk.nextToken());

        option = new int[L][L];
        for(int i=0; i<L; i++) {
            stk = new StringTokenizer(br.readLine());
            for(int j=0; j<L; j++) {
                option[i][j] = Integer.parseInt(stk.nextToken()); // 0 1함정 2벽
            }
        }

        soldier = new Soldier[L][L];

        for(int i=1; i<=N; i++) {
            stk = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(stk.nextToken()) - 1;
            int c = Integer.parseInt(stk.nextToken()) - 1;
            int h = Integer.parseInt(stk.nextToken());
            int w = Integer.parseInt(stk.nextToken());
            int k = Integer.parseInt(stk.nextToken());

            for(int ni=r; ni<r+h; ni++) {
                for(int nj=c; nj<c+w; nj++) {
                    soldier[ni][nj] = new Soldier(i, k, false);
                }
            }
        }

        orders = new ArrayList<>();
        for(int i=0;i<Q; i++) {
            stk = new StringTokenizer(br.readLine());
            int solNum = Integer.parseInt(stk.nextToken());
            int solDir = Integer.parseInt(stk.nextToken());

            orders.add(new int[]{solNum, solDir});
        }

        totalAttack = new int[N+1];
        for(int[] order: orders) {
            list = new ArrayList<>();
            boolean bfs = bfs(order[0], order[1]);
            if (bfs) {
                for (Soldier soldier1 : list) {
                    soldier[soldier1.nr][soldier1.nc] = null;
                }

                for (Soldier soldier1 : list) {
                    int nr = soldier1.nr + dr[order[1]];
                    int nc = soldier1.nc + dc[order[1]];

                    soldier[nr][nc] = soldier1;
                    soldier[nr][nc].isMove = true;
                }

                attackByOther(order[0]);
                processDead();
            }
        }

        int totalDamage = getTotalDamage();
        System.out.print(totalDamage);
    }

    private static void attackByOther(int attackNum) {
        dHp = new int[N+1];

        for(int i=0; i<L; i++) {
            for(int j=0; j<L; j++) {
                if(soldier[i][j] == null) continue;
                if(soldier[i][j].isOver) continue;
                if(soldier[i][j].num == attackNum) {
                    soldier[i][j].isMove = false;
                    continue;
                }

                if(option[i][j] == 1 && soldier[i][j].isMove) {
                    dHp[soldier[i][j].num] += 1;
                    totalAttack[soldier[i][j].num] += 1;
                }

                soldier[i][j].isMove = false;
            }
        }

        for(int i=0; i<L; i++) {
            for(int j=0; j<L; j++) {
                if(soldier[i][j] == null) continue;
                if(soldier[i][j].isOver) continue;

                for(int k=1; k<=N; k++) {
                    soldier[i][j].decrease(k, dHp[k]);
                }
            }
        }
    }

    private static void processDead() {
        for(int i=0; i<L; i++) {
            for(int j=0; j<L; j++) {
                if(soldier[i][j] == null) continue;
                if(soldier[i][j].isOver) continue;

                if(soldier[i][j].hp <= 0) soldier[i][j].isOver = true;
            }
        }
    }


    private static int getTotalDamage() {
        int totalDamage = 0;
        boolean[] visited = new boolean[N+1];
        for(int i=0; i<L; i++) {
            for(int j=0; j<L; j++) {
                if(soldier[i][j] == null) continue;
                if(visited[soldier[i][j].num]) continue;

                if(!soldier[i][j].isOver) {
                    visited[soldier[i][j].num] = true;
                    totalDamage += totalAttack[soldier[i][j].num];
                }
            }
        }

        return totalDamage;
    }

    static boolean bfs(int solNum, int solDir) {
        boolean[][] visited = new boolean[L][L];
        Queue<Node> queue = new LinkedList<>();
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (soldier[i][j] == null) continue;

                if (soldier[i][j].num == solNum) {
                    if (soldier[i][j].isOver) return false;

                    queue.add(new Node(i, j, 0, false, i, j));
                    soldier[i][j].pushNrNc(i, j);
                    list.add(soldier[i][j]);
                    visited[i][j] = true;
                }
            }
        }

        while (!queue.isEmpty()) {
            Node curNode = queue.poll();

            for(int idx=solDir; idx<solDir+4; idx++) {
                int i = idx %4;

                if(curNode.depth == 0 && i != solDir) continue;

                int nr = curNode.r + dr[i];
                int nc = curNode.c + dc[i];

                if(i == solDir) {
                    if(outOfBlock(nr, nc)) return false;
                    if(option[nr][nc] == 2) return false;
                }

                if(outOfBlock(nr, nc)) continue;
                if (visited[nr][nc]) continue;

                visited[nr][nc] = true;
                if(soldier[nr][nc] != null) {
                    if(soldier[nr][nc].isOver) continue;

                    if(( solDir == 2 || solDir == 0) && i == solDir && curNode.startC == nc) {
                        queue.add(new Node(nr, nc, curNode.depth + 1, true, curNode.startR, curNode.startC));
                        soldier[nr][nc].pushNrNc(nr, nc);
                        list.add(soldier[nr][nc]);
                        continue;
                    }

                    if((solDir == 1 || solDir == 3) && i == solDir && curNode.startR == nr) {
                        queue.add(new Node(nr, nc, curNode.depth + 1, true, curNode.startR, curNode.startC));
                        soldier[nr][nc].pushNrNc(nr, nc);
                        list.add(soldier[nr][nc]);
                        continue;
                    }


                    if(curNode.isLine && soldier[curNode.r][curNode.c].num == soldier[nr][nc].num) {
                        queue.add(new Node(nr, nc, curNode.depth + 1, true, curNode.startR, curNode.startC));
                        soldier[nr][nc].pushNrNc(nr, nc);
                        list.add(soldier[nr][nc]);
                    }
                }
            }
        }
        return true;
    }

    static class Node {
        int r, c, depth;
        boolean isLine;
        int startR, startC;

        public Node(int r, int c, int depth, boolean isLine, int startR, int startC) {
            this.r = r;
            this.c = c;
            this.depth = depth;
            this.isLine = isLine;
            this.startR = startR;
            this.startC = startC;
        }
    }

    static class Soldier {
        int num;
        int hp;
        boolean isOver;
        int nr, nc = 0;
        int totalAttack = 0;
        boolean isMove = false;

        public Soldier(int num, int hp, boolean isOver) {
            this.num = num;
            this.hp = hp;
            this.isOver = isOver;
        }

        public void pushNrNc(int nr, int nc) {
            this.nr = nr;
            this.nc = nc;
        }

        public void decrease(int k, int dHp) {
            if(num == k) {
                hp -= dHp;
            }
        }

    }

    static boolean outOfBlock(int r, int c) {
        return r < 0 || r >= L || c < 0 || c >= L;
    }
}
