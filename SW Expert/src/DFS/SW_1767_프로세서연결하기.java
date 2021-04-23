package DFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SW_1767_프로세서연결하기 {
	static int T, N;
	static int[][] map;
	static boolean[][] visited;
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1}; // 상하좌우
	static int maxCore; // 코어 세기
	static int minLength; // 최소 전선길이
	static List<Core> coreList;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		T = sc.nextInt();
		for(int t=1;t<=T;t++) {
			N = sc.nextInt();
			map = new int[N][N];
			visited = new boolean[N][N];
			maxCore = 0;
			minLength = Integer.MAX_VALUE;
			coreList = new ArrayList<>();
			
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					map[i][j] = sc.nextInt();
					if(map[i][j]==1&&i>0&&i<N-1&&j>0&&j<N-1) { // 가장자리 코어 제외한 코어들 List에 넣기 
						coreList.add(new Core(i,j));
					}
				}
			}
			
			dfs(0,0,0);
			System.out.println("#"+t+" "+minLength);
		}
	}
	
	public static void dfs(int idx, int cnt, int length) { // 코어 리스트번호, 골라진 코어수, 전선길이
		if(idx==coreList.size()) {
			if(maxCore<cnt) {
				maxCore = cnt;
				minLength = length;
			}else if(maxCore==cnt&&minLength>length) {
				minLength = length;
			}
			return;
		}
		
		for(int d=0;d<4;d++) {
			int addLength = putLine(idx, d);	// 전선 놓기
			if(addLength>0) {
				dfs(idx+1, cnt+1, length+addLength);
				// 전선 놓은거 쭉 되돌리기
				cleanLine(idx,d);
			}else {
				dfs(idx+1, cnt, length);
			}
		}
	}

	
	private static int putLine(int idx, int d) {
		int length = 0;
		boolean isGo = false; // 전선이 가장자리에 닿았는지 확인.
		Core next_core = coreList.get(idx);
		int nr = next_core.r;
		int nc = next_core.c;
		for(int i=1;i<N-1;i++) { // 전선 놓을 수 있는지 없는지 확인
			nr+=dr[d];
			nc+=dc[d];
			if(nr<0 || nr>N-1 || nc<0 || nc>N-1) {
				isGo = true;
				break;
			}
			if(map[nr][nc]==1) {
				return 0;
			}
		}
		
		nr = next_core.r;
		nc = next_core.c;
		if(isGo) { // 전선 놓을 수 있다면 전선 놓기
			for(int i=1;i<N-1;i++) {
				nr += dr[d];
				nc += dc[d];
				if(nr<0 || nr>N-1 || nc<0 || nc>N-1) break;
				if(map[nr][nc]==0) {
					map[nr][nc]=1;
					length++;
				}
			}
		}
		return length;
	}
	
	private static void cleanLine(int idx, int d) {
		Core next_core = coreList.get(idx);
		int nr = next_core.r;
		int nc = next_core.c;
		for(int i=1;i<N-1;i++) {
			nr += dr[d];
			nc += dc[d];
			if(nr<0 || nr>N-1 || nc<0 || nc>N-1) break;
			if(map[nr][nc]==1) {
				map[nr][nc]=0;
			}
		}
	}

}

class Core {
	int r, c;

	public Core(int r, int c) {
		super();
		this.r = r;
		this.c = c;
	}
	
}
