package Samsung_SW_역량테스트.Y2021_팩맨;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {

    static int m, t;
    static ArrayList<Monster> monsters = new ArrayList<>();
    static Packman packman;
    static PriorityQueue<Packman> movePackman;

    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};

    static int[] pdr = {-1, 0, 1, 0};
    static int[] pdc = {0, -1, 0, 1};

    static int turn = 1;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Samsung_SW_역량테스트/Y2021_팩맨/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        stk = new StringTokenizer(br.readLine());
        m = Integer.parseInt(stk.nextToken());
        t = Integer.parseInt(stk.nextToken());

        stk = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(stk.nextToken()) - 1;
        int c = Integer.parseInt(stk.nextToken()) - 1;
        packman = new Packman(r, c);

        for(int i=0; i<m; i++) {
            stk = new StringTokenizer(br.readLine());
            int mr = Integer.parseInt(stk.nextToken()) - 1;
            int mc = Integer.parseInt(stk.nextToken()) - 1;
            int md = Integer.parseInt(stk.nextToken()) - 1; //초기 문제 이해

            monsters.add(new Monster(mr, mc, md, false, false, true));
        }

//        System.out.println("1".compareTo("0")); true
//        System.out.println('1' > '0'); true
//        System.out.println('3' - '0'); int 3

        while(turn <= t) {
            monsterReplicate();
            moveMonster();
            packmanEat();
            deleteMonster();
            monsters.stream()
                    .filter(v-> !v.isHatch())
                    .forEach(Monster::hatch);
            turn++;
        }


        int count = (int) monsters.stream()
                .filter(v -> v.isTotalAlive())
                .count();

        System.out.println(count);
    }

    public static void deleteMonster() {
        for(Monster monster: monsters) {
            if(monster.isDeleteStart()) {
                monster.increaseDeleteCnt();
            }
            if(monster.isCompleteDelete()) {
                monster.delete();
            }
        }
    }

    private static void moveMonster() {
        for(Monster monster: monsters) {
            if(!monster.isAlive()) continue;
            if(!monster.isHatch()) continue;

            int[] next = monster.getNext();
            int nr = next[0];
            int nc = next[1];

            boolean isDie = isDie(nr, nc);
            boolean isPackman = isPackman(nr, nc);
            boolean isGo = false;
            int cnt = 0;

            if(isNotMove(nr, nc, isDie, isPackman)) {
                int moveR, moveC;
                while(true) {
                    monster.rotate();
                    moveR = monster.r + dr[monster.d];
                    moveC = monster.c + dc[monster.d];

                    if (!isNotMove(moveR, moveC, isDie(moveR, moveC), isPackman(moveR, moveC))) {
                        isGo = true;
                        break;
                    }
                    cnt++;
                    if (cnt == 8) {
                        break;
                    }
                }

                if(isGo) {
                    monster.move(moveR, moveC);
                }
                continue;
            }

//            System.out.println(nr + " " + nc);
            monster.move(nr, nc);

        }
    }

    private static void packmanEat() {
        movePackman = new PriorityQueue<>();
        dfs(packman.r, packman.c, 0, "", 0);
        Packman arrivePackman = movePackman.poll();

        packman.pushD(arrivePackman.first, arrivePackman.second, arrivePackman.third);
        packman.eat();
    }

    public static void dfs(int curR, int curC, int eat, String s, int depth) {
        if(depth == 3) {
//            System.out.println(eat + " " + s);
            movePackman.add(new Packman(curR, curC, eat, s.charAt(0), s.charAt(1), s.charAt(2)));
            return ;
        }

        for(int i=0; i<4; i++) {
            int nr = curR + pdr[i];
            int nc = curC + pdc[i];

            if(outOfBlock(nr, nc)) continue;
            boolean rr = monsters.stream()
                    .filter(v -> v.isLocation(nr, nc) && v.isWillDie >= 1)
                    .count() >= 1; //탐색 시, 방향에만 의존해야 하기 때문에

            monsters.stream()
                    .filter(v-> v.isLocation(nr, nc))
                    .forEach(v->v.willDie());
            int count = 0;
            if(!rr) {
                count = (int) monsters.stream()
                        .filter(v -> v.canEat(nr, nc))
                        .count();
            }
            dfs(nr, nc, (int) (eat + count), s + i, depth + 1);

            monsters.stream()
                    .filter(v-> v.isLocation(nr, nc))
                    .forEach(v->v.backDie());
        }
    }

    private static boolean isNotMove(int nr, int nc, boolean isDie, boolean isPackman) {
        return isDie || outOfBlock(nr, nc) || isPackman;
    }

    private static boolean isPackman(int nr, int nc) {
        return packman.isLocate(nr, nc);
    }

    private static boolean isDie(int nr, int nc) {
        return monsters.stream()
                .filter(v -> v.isDie(nr, nc))
                .count() >= 1;
    }

    static void monsterReplicate() {
        List<Monster> aliveMonsters = monsters.stream()
                .filter(v -> v.isAlive())
                .collect(Collectors.toList());

        aliveMonsters.forEach(Monster::replicate);
    }



    static class Monster {
        int r, c, d;
        boolean isDie, isDelete, isHatch;
        int isWillDie = 0; //boolean -> int
        int deleteCnt = 0;

        public Monster(int r, int c, int d, boolean isDie, boolean isDelete, boolean isHatch) {
            this.r = r;
            this.c = c;
            this.d = d;
            this.isDie = isDie;
            this.isDelete = isDelete;
            this.isHatch = isHatch;
        }

        public void willDie() {
            this.isWillDie++;
        }

        public void backDie() {
            this.isWillDie--;
        }

        public boolean isDeleteStart() {
            return this.isDie;
        }

        public void increaseDeleteCnt() {
            this.deleteCnt++;
        }

        public boolean isCompleteDelete() {
            return this.deleteCnt >= 3;
        }

        public void delete() {
            this.isDelete = true;
        }

        public boolean isTotalAlive() {
            return !isDie && !isDelete;
        }

        public void replicate() {
            monsters.add(new Monster(r, c, d, false, false, false));
        }

        public boolean isAlive() {
            return !isDie && !isDelete && isHatch; //틀린 부분 / 다음부턴 isEgg로
        }

        public boolean isAlive(int nr, int nc) {
            return r == nr && c == nc && isAlive();
        }

        public boolean isLocation(int r, int c) {
            return this.r == r && this.c == c && isAlive();
        }

        public boolean isDie(int nr, int nc) {
            return r == nr && c == nc && isDie && !isDelete; //틀린 부분
        }

        public boolean canEat(int nr, int nc) {
            return isAlive(nr, nc) && isWillDie >= 1;
        }

        public void die() {
            deleteCnt = 0;
            this.isDie = true;
        }

        public boolean isAbleEatMonster(int r, int c) {
            return this.r == r && this.c == c && isAlive();
        }

        public int[] getNext() {
            int nr = this.r + dr[this.d];
            int nc = this.c + dc[this.d];

            return new int[]{nr, nc};
        }

        public void rotate() {
            this.d = (this.d + 1) % 8;
        }

        public boolean isHatch() {
            return this.isHatch;
        }

        public void hatch() {
            isHatch = true;
        }

        public void move(int nr, int nc) {
            this.r = nr;
            this.c = nc;
        }
    }

    static class Packman implements Comparable<Packman> {
        int r, c;
        int totalEat;
        char first;
        char second;
        char third;

        public Packman(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Packman(int r, int c, int totalEat, Character first, Character second, Character third) {
            this.r = r;
            this.c = c;
            this.totalEat = totalEat;
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public void pushD(char first, char second, char third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public boolean isLocate(int nr, int nc) {
            return nr == r && nc == c;
        }

        @Override
        public int compareTo(Packman other) {
            if(this.totalEat == other.totalEat) {
                if(this.first == other.first) {
                    if(this.second == other.second) {
                        return this.third - other.third;
                    }
                    return this.second - other.second;
                }
                return this.first - other.first;
            }
            return other.totalEat - this.totalEat;
        }

        public void eat() {
            move(this.first - '0');
            move(this.second - '0');
            move(this.third - '0');
        }

        public void move(int moveD) {
            this.r = this.r + pdr[moveD];
            this.c = this.c + pdc[moveD];

            monsters.stream()
                    .filter(v-> v.isAbleEatMonster(r, c))
                    .forEach(v-> v.die());
        }
    }

    public static boolean outOfBlock(int nr, int nc) {
        return nr < 0 || nr >= 4 || nc < 0 || nc >= 4;
    }
}
