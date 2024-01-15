package Samsung_SW_역량테스트.Y2021_팩맨;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int m, t;

    static Monsters[][] eggs;
    static Monsters[][] monsters;
    static Monsters[][] moveMonsters;
    static Monsters[][] die;
    static Packman packman;
    static PriorityQueue<Packman> movePackman;

    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};

    static int[] pdr = {-1, 0, 1, 0};
    static int[] pdc = {0, -1, 0, 1};

    static int turn = 1;
    static int answer = 0;

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

        monsters = new Monsters[4][4];
        eggs = new Monsters[4][4];
        die = new Monsters[4][4];
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                monsters[i][j] = new Monsters();
                eggs[i][j] = new Monsters();
                die[i][j] = new Monsters();
            }
        }

        for(int i=0; i<m; i++) {
            stk = new StringTokenizer(br.readLine());
            int mr = Integer.parseInt(stk.nextToken()) - 1;
            int mc = Integer.parseInt(stk.nextToken()) - 1;
            int md = Integer.parseInt(stk.nextToken()) - 1; //초기 문제 이해

            monsters[mr][mc].addMonster(new Monster(md, false, false));
        }

        while(turn <= t) {          //새로 할때는 monsters[2][1]에 데이터 있음
            monsterReplicate(turn); //moveMonster를 monsters로 복사, monsters 기준 살아있는 애들 알낳기(eggs)     최초: 입력알담기 / moveMonster 중 삭제된 애들까지 포함해서, mosters로 복사
            moveMonster();          //mosters 기준, moveMonsters 갱신 - 이동 완료
            packmanEat();           //moveMonsters 중 죽임 -  이동한 애 중 먹음, die
            deleteMonster();        //moveMonsters 기준 삭제 카운트 - 이동한 애 삭제
            hatchMonster();         //최초 알 낳은 장소 기준 moveMonsters에 낳기 - 알이였던 애들 부활
            turn++;
        }

        calculateAnswer();
        System.out.println(answer);
    }

    static void monsterReplicate(int turn) {
        if(turn >= 2) {
            monsters = moveMonsters;
        }
//        System.out.println(die[2][2].getMonsters().size());
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                ArrayList<Monster> monsterList = monsters[i][j].getMonsters();
                for(Monster monster: monsterList) {
                    //나중에 부화해야하기 때문에
                    eggs[i][j].addMonster(new Monster(monster.d, false, false));
                }
            }
        }
    }

    public static void hatchMonster() {
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                ArrayList<Monster> monsterList = eggs[i][j].getMonsters();
                for(Monster monster: monsterList) {
                    moveMonsters[i][j].addMonster(new Monster(monster.d, false, false));
                }
            }
        }
        eggs = new Monsters[4][4];
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                eggs[i][j] = new Monsters();
            }
        }
    }

    private static void moveMonster() {
        init();
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                List<Monster> curMonsters = monsters[i][j].getMonsters();

                for(Monster monster: curMonsters) {
                    int[] next = monster.getNext(i, j);
                    int nr = next[0];
                    int nc = next[1];

                    if(outOfBlock(nr, nc)) {
                        moveByRotate(monster, i, j);
                        continue;
                    };

                    boolean isDie = die[nr][nc].isDie();
                    boolean isPackman = packman.isLocate(nr, nc);
                    if(isNotMove(isDie, isPackman)) {
                        moveByRotate(monster, i, j);
                        continue;
                    }

//                    monsters[i][j].remove(monster);
//                    monsters[nr][nc].addMonster(new Monster(monster.d, false, false, true));
                    moveMonsters[nr][nc].addMonster(monster);
                }

            }
        }
    }

    private static void moveByRotate(Monster monster, int curR, int curC) {
        int moveR, moveC;
        int cnt = 0;
        boolean isGo = false;

        while(true) {
            monster.rotate();
            cnt++;
            moveR = curR + dr[monster.d];
            moveC = curC + dc[monster.d];

            if(outOfBlock(moveR, moveC)) {
               continue;
            }

            if (!isNotMove(die[moveR][moveC].isDie(), packman.isLocate(moveR, moveC))) {
                isGo = true;
                break;
            }
            if (cnt > 7) {
                break;
            }
        }

        if(isGo) {
            moveMonsters[moveR][moveC].addMonster(monster);
            return ;
        }

        moveMonsters[curR][curC].addMonster(monster);
    }

    private static void packmanEat() {
        movePackman = new PriorityQueue<>();
        dfs(packman.r, packman.c, 0, "", 0);
        Packman arrivePackman = movePackman.poll();
//        System.out.println(arrivePackman.totalEat + " " + arrivePackman.first + arrivePackman.second + arrivePackman.third);

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
            boolean visited = moveMonsters[nr][nc].isVisited();

            moveMonsters[nr][nc].visited();
            int count = 0;
            if(!visited) {
                count = moveMonsters[nr][nc].countEat();
            }
            dfs(nr, nc, eat + count, s + i, depth + 1);
            moveMonsters[nr][nc].notVisited();
        }
    }

    public static void init() {
        moveMonsters = new Monsters[4][4];
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                moveMonsters[i][j] = new Monsters();
            }
        }
    }

    public static void deleteMonster() {
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                Monsters curMonsters = die[i][j];
                curMonsters.increaseDeleteCnt();
                curMonsters.delete();
            }
        }
    }

    public static void calculateAnswer() {
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                List<Monster> monsterList = moveMonsters[i][j].getMonsters();
                for(Monster monster: monsterList) {
                    answer++;
                }
            }
        }
    }


    private static boolean isNotMove(boolean isDie, boolean isPackman) {
        return isDie || isPackman;
    }

    static class Monsters {
        ArrayList<Monster> monsters;

        public Monsters() {
            this.monsters = new ArrayList<>();
        }

        public void addMonster(Monster monster) {
            this.monsters.add(monster);
        }

        public ArrayList<Monster> getMonsters() {
            return monsters;
        }

        public boolean isVisited() {
            return monsters.stream()
                    .filter(v -> v.isWillDie >= 1)
                    .count() >= 1;
        }

        public void visited() {
            monsters.stream()
                    .forEach(v->v.willDie());
        }

        public void notVisited() {
            monsters.stream()
                    .forEach(v->v.backDie());
        }



        public void die() {
            monsters.forEach(v-> v.die());
        }

        public void increaseDeleteCnt() {
            monsters.stream()
                    .filter(v-> v.isDeleteStart())
                    .forEach(Monster::increaseDeleteCnt);
        }

        public void delete() {
            monsters.stream()
                    .filter(v-> v.isCompleteDelete())
                    .forEach(Monster::delete);
        }

        public boolean isDie() {
            return monsters.stream()
                    .filter(Monster::isDie)
                    .count() >= 1;
        }

        public void removeAll() {
            monsters.clear();
        }

        public void add(List<Monster> monsters) {
            monsters.stream()
                    .forEach(v-> this.monsters.add(v));
        }

        public int countEat() {
            return (int) monsters.stream()
                    .filter(v -> v.canEat())
                    .count();
        }
    }


    static class Monster {
        int d;
        boolean isDie, isDelete;
        int isWillDie = 0; //boolean -> int
        int deleteCnt = 0;

        public Monster(int d, boolean isDie, boolean isDelete) {
            this.d = d;
            this.isDie = isDie;
            this.isDelete = isDelete;
        }

        public void willDie() {
            this.isWillDie++;
        }

        public void backDie() {
            this.isWillDie--;
        }

        public boolean isDeleteStart() {
            return isDie && !isDelete;
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

        public boolean isDie() {
            return isDie && !isDelete; //틀린 부분
        }

        public boolean canEat() {
            return isWillDie >= 1;
        }

        public void die() {
            this.isDie = true;
        }

        public int[] getNext(int curR, int curC) {
            int nr = curR + dr[this.d];
            int nc = curC + dc[this.d];

            return new int[]{nr, nc};
        }

        public void rotate() {
            this.d = (this.d + 1) % 8;
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
            ArrayList<Monster> monsters1 = moveMonsters[this.r][this.c].getMonsters();
            if(monsters1.size() > 0) {
                die[this.r][this.c].getMonsters().add(monsters1.get(0));
                die[this.r][this.c].die();
                moveMonsters[this.r][this.c].removeAll();
            }
        }
    }

    public static boolean outOfBlock(int nr, int nc) {
        return nr < 0 || nr >= 4 || nc < 0 || nc >= 4;
    }
}
