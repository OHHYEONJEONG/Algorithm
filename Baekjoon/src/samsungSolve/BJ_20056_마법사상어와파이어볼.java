package samsungSolve;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BJ_20056_마법사상어와파이어볼 {
	static int N,M,K;
	static int answer;
	static ArrayList<FireBall>[][] map;
	static Queue<FireBall> que;
	static int[] dr = {-1,-1,0,1,1,1,0,-1};
	static int[] dc = {0,1,1,1,0,-1,-1,-1};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		map = new ArrayList[N][N];
		map[0][0] = new ArrayList();
		que = new LinkedList<>();
		answer = 0;
		
		// map 초기화
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				map[i][j] = new ArrayList();
			}
		}
		
		// 파이어볼 개수만큼 que에 넣기
		for(int i=0;i<M;i++) {
			
			int r = sc.nextInt();
			int c = sc.nextInt();
			int m = sc.nextInt();
			int d = sc.nextInt();
			int s = sc.nextInt();
			
			FireBall ball = new FireBall(r-1,c-1,m,d,s);
			que.add(ball);
		}
		
		// 파이어볼들 que에서 꺼내서 이동 
		for(int i=0;i<K;i++) {
			go();
		}
		System.out.println(answer);
	}
	private static void go() {
		answer = 0;
		int que_size = que.size();
		for(int i=0;i<que_size;i++) {
			FireBall f = que.poll();
			// 파이어볼 현재 위치와 방향, 속력, 질량
			int r = f.r;
			int c = f.c;
			int d = f.d;
			int s = f.s;
			int m = f.m;
			
			// 파이어볼 다음 위치 계산
			int nr = r+(dr[d]*s);
			int nc = c+(dc[d]*s);
			
			if(nr>=0) nr = nr % N;
			else if(nr<0) nr = N - (Math.abs(nr)%N);
			if(nc>=0) nc = nc % N;
			else if(nc<0) nc = N - (Math.abs(nc)%N);
//			System.out.println("현재위치 (r,c): ("+r+","+c+"), 다음위치 (nr,nc): ("+nr+", "+nc+"), d,s,m:"+d+", "+s+", "+m);
			map[r][c].clear();	// 이동하니까 원래 있던자리 clear;
			map[nr][nc].add(new FireBall(nr,nc,m,s,d));	// 다음 자리로 이동
		}
		// 합칠 파이어볼 있으면 합치기.
		addFireBall();
	}
	private static void addFireBall() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				int size = map[i][j].size();
				if(size>1) {
					int sumM = 0; // 합칠 질량
					int sumS = 0; // 합칠 속력
					int evenD = 0; // 방향이 짝수 인거 개수
					int oddD = 0; // 방향이 홀수 인거 개수
					for(int k=0;k<size;k++) {
						FireBall f = map[i][j].get(k);
						sumM += f.m;
						sumS += f.s;
						if(f.d%2==0) evenD++;
						else oddD++;
					}
					sumM = (int) Math.floor(sumM/5);
					if(sumM==0) continue;	// 소멸되어 없어짐
					sumS = (int) Math.floor(sumS/size);
					answer+=sumM*4;
					if(evenD == size || oddD == size) { // 방향이 모두 짝수거나 홀수이면 0,2,4,6
						que.add(new FireBall(i,j,sumM,sumS,0));
						que.add(new FireBall(i,j,sumM,sumS,2));
						que.add(new FireBall(i,j,sumM,sumS,4));
						que.add(new FireBall(i,j,sumM,sumS,6));
					}else {
						que.add(new FireBall(i,j,sumM,sumS,1));
						que.add(new FireBall(i,j,sumM,sumS,3));
						que.add(new FireBall(i,j,sumM,sumS,5));
						que.add(new FireBall(i,j,sumM,sumS,7));
					}
				}
				else if(size==1) {
					FireBall fireball = map[i][j].get(0);
					que.add(new FireBall(fireball.r,fireball.c,fireball.m,fireball.s,fireball.d));
					answer+=fireball.m;
				}
			}
		}
	}
}

class FireBall{
	int r,c,m,s,d;

	public FireBall(int r, int c, int m, int s, int d) {
		super();
		this.r = r;
		this.c = c;
		this.m = m;
		this.s = s;
		this.d = d;
	}
	
}
