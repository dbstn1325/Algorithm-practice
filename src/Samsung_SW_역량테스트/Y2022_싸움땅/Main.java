package Samsung_SW_역량테스트.Y2022_싸움땅;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {
    static int n, m, k;
    static Guns[][] guns;
    static List<Player> players = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        System.setIn(new FileInputStream("src/Samsung_SW_역량테스트/Y2022_싸움땅/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        stk = new StringTokenizer(br.readLine());
        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());
        k = Integer.parseInt(stk.nextToken());

        guns = new Guns[n][n];
        for(int i=0; i<n; i++) {
            stk = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++) {
                int attack = Integer.parseInt(stk.nextToken());
                Guns gun = new Guns();
                gun.addGun(attack);

                guns[i][j] = gun;
            }
        }

        for(int i=0; i<m; i++) {
            stk = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(stk.nextToken()) - 1;
            int y = Integer.parseInt(stk.nextToken()) - 1;
            int d = Integer.parseInt(stk.nextToken());
            int s = Integer.parseInt(stk.nextToken());

            players.add(new Player(x, y, d, s));
        }


        while(k-- > 0) {
            for (Player player : players) {
                player.moveAhead();
                int curR = player.getR();
                int curC = player.getC();

                List<Player> findPlayer = players.stream()
                        .filter(v -> v.isPlayer(curR, curC))
                        .collect(Collectors.toList());

                boolean isMatch = findPlayer.stream()
                        .count() >= 2;
                if (!isMatch) {
                    processMatch(player, curR, curC);
                }

                if (isMatch) {
                    Player[] result = processWinner(findPlayer);
                    Player winner = result[0];
                    Player loser = result[1];

                    //패자
                    putDownGun(curR, curC, loser);
                    while (true) {
                        int[] next = loser.getNext();
                        boolean isPossibleMove = loser.loserCanMoveAndMove(next[0], next[1]);

                        if(isPossibleMove) {
                            break;
                        }
                    }
                    if (guns[loser.getR()][loser.getC()].getMax() != 0) {
                        changeGun(loser, loser.getR(), loser.getC());
                    }

                    //승자
                    changeGun(winner, winner.getR(), winner.getC());
                }

            }
        }

        for(Player player: players) {
            System.out.print(player.getPoint() + " ");
        }


    }

    private static void putDownGun(int curR, int curC, Player loser) {
        guns[curR][curC].addGun(loser.getAttack());
        loser.clearGun();
    }

    private static Player[] processWinner(List<Player> findPlayer) {
        Player player1 = findPlayer.get(0);
        Player player2 = findPlayer.get(1);

        int player1Point = player1.getTotal();
        int player2Point = player2.getTotal();

        if(player1Point > player2Point) {
            player1.increasePoint(player1Point - player2Point);
            return new Player[]{player1, player2};
        }

        if(player1Point == player2Point) {
            if(player1.getS() > player2.getS()) {
                player1.increasePoint(player1Point - player2Point);
                return new Player[]{player1, player2};
            }

            player2.increasePoint(player2Point - player1Point);
            return new Player[]{player2, player1};
        }

        player2.increasePoint(player2Point - player1Point);
        return new Player[]{player2, player1};
    }

    public static void processMatch(Player player, int curR, int curC) {
        int curMax = guns[curR][curC].getMax();

        if(player.isGetAttack()) {
            changeGun(player, curR, curC);
            return ;
        };

        player.setAttack(curMax);
        guns[curR][curC].except(curMax);
    }

    private static void changeGun(Player player, int curR, int curC) {
        int curGun = player.getAttack();
        int maxGun = guns[curR][curC].getMax();
        if(curGun > maxGun) {
            return ;
        }

        player.setAttack(maxGun);
        guns[curR][curC].except(maxGun, curGun);
    }

    static class Guns {
        ArrayList<Integer> guns;

        public Guns() {
            guns = new ArrayList<>();
        }

        public void addGun(int attack) {
            guns.add(attack);
        }

        public void except(int takeOverAttack) {
            if(guns.indexOf(takeOverAttack) >= 0) {
                guns.remove(guns.indexOf(takeOverAttack));
            }
        }

        public void except(int beforeAttack, int newAttack) {
            if(guns.indexOf(beforeAttack) >= 0) {
                guns.remove(guns.indexOf(beforeAttack));
                guns.add(newAttack);
            }
        }

        public int getMax() {
            return guns.stream()
                    .max(Integer::compareTo)
                    .orElse(0);
        }
    }

    static class Player {
        int r;
        int c;
        int d;
        int s;
        int attack = 0;
        int point = 0;

        public Player(int r, int c, int d, int s) {
            this.r = r;
            this.c = c;
            this.d = d;
            this.s = s;
        }

        public int getPoint() {
            return point;
        }

        public int[] getNext() {
            int nr = this.r;
            int nc = this.c;

            if(d==0) {
                nr--;
            }else if (d==1) {
                nc++;
            }else if (d==2) {
                nr++;
            }else if (d==3) {
                nc--;
            }

            return new int[]{nr, nc};
        }

        public boolean loserCanMoveAndMove(int nr, int nc) {
            boolean isMatch = players.stream()
                    .filter(v-> v.isPlayer(nr, nc))
                    .count() >= 1;

            if(d==0) {
                if(nr < 0 || isMatch) {
                    changeDRotateRight();
                    return false;
                }

                moveUp();
            }
            if(d==1) {
                if(nc >= n || isMatch) {
                    changeDRotateRight();
                    return false;
                }

                moveRight();
            }
            if(d==2) {
                if(nr >= n || isMatch) {
                    changeDRotateRight();
                    return false;
                }
                moveDown();
            }
            if(d==3) {
                if(nc < 0 || isMatch) {
                    changeDRotateRight();
                    return false;
                }

                moveLeft();
            }

            return true;
        }

        public void clearGun() {
            this.attack = 0;
        }

        public void increasePoint(int point) {
            this.point += point;
        }

        public int getS() {
            return s;
        }

        public int getTotal() {
            return this.s + this.attack;
        }

        public int getAttack() {
            return attack;
        }

        public boolean isGetAttack() {
            return this.attack != 0;
        }

        public void setAttack(int attack) {
            this.attack = attack;
        }

        public boolean isPlayer(int targetR, int targetC) {
            return r == targetR && c == targetC;
        }

        public void moveAhead() {
            if(d==0) {
                if(r-1<0) {
                    changeDReverse();
                    moveDown();
                    return ;
                }
                moveUp();
            }
            if(d==1) {
                if(c+1>=n) {
                    changeDReverse();
                    moveLeft();
                    return ;
                }

                moveRight();
            }
            if(d==2) {
                if(r+1>=n) {
                    changeDReverse();
                    moveUp();
                    return ;
                }
                moveDown();
            }
            if(d==3) {
                if(c-1<0) {
                    changeDReverse();
                    moveRight();
                    return ;
                }
                moveLeft();
            }
        }

        private void changeDRotateRight() {
            d=(d+1) %4;
        }

        private void changeDReverse() {
            d=(d+2) % 4;
        }

        private void moveUp() {
            r--;
        }

        private void moveRight() {
            c++;
        }

        private void moveDown() {
            r++;
        }

        private void moveLeft() {
            c--;
        }

        public int getR() {
            return r;
        }

        public int getC() {
            return c;
        }
    }
}
