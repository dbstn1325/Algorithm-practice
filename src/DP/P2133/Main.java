package DP.P2133;
import java.util.*;

public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long[] d = new long[n+1];
        
        
        
        d[0] = 1;	
        d[1] = 0;	//n이 1인 경우, 3*n 타일을 2*1 과 1*2로 표현할 수 없기 때문에
        
        if(n>1) {
        	d[2] = 3;
        }
        
        
        //d[4] = d[2]*d[2] //맨끝을 3*2로고정시켜놓는것,  3가지가3번=3곱하기3
        //d[4] = d[0]*'특별한경우';  //'특별한경우':=2
        
        for(int i=4; i<=n; i+=2) { //n이홀수인경우는 2*1과1*2로는존재하지않기때문에 +=2씩
        	d[i] = d[i-2]*d[2];
        	for(int j=i-4; j>=0; j-=2) { //젤i가큰경우마다의 '특별한경우'까지 세주기위해서
        		d[i] += d[j]*2;
        	}
        }
        System.out.println(d[n]);
    }
}