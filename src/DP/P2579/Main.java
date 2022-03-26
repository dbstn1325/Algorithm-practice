import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		
		int[] a = new int[n+1];
		for(int i=1; i<=n; i++) {
			a[i] = sc.nextInt();
		}
		
		int[] d = new int[n+1];
		
		d[0] = 0;
		d[1] = a[1];
  //	d[2] = a[1]+a[2]; 그럼 앞의 수를(a[1]을) 또 다르게 표현할 방법은 없을까(최대한 d형식으로) ==> a[1]=d[1]
		
//		if(n>=2) {
//			d[2] = a[0]+a[2];
//		}
		
		if(n>=2) {//2부터 (n=1 쳤을때 런타임에러)
			d[2] = a[1] + a[2]; //d[2]은 무조건 얘.
		}
		for(int i=3; i<=n; i++) {
			d[i] = Math.max(d[i-2], d[i-3]+a[i-1]) + a[i]; //고정박고
		}
		
		System.out.println(d[n]);
		
	}
}