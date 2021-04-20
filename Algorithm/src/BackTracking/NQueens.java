package BackTracking;

import java.util.Scanner;

public class NQueens {
	
	static int N;
	static int[] col;	// �� ���� ���� ��ġ�� ����
	static int answer;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		col = new int[N+1];
	}
	
	// ���� ����(rowNo) �࿡ ����
	public static void setQueens(int rowNo) {
		if(rowNo > N) {
			// ������ ��츸 ����ؼ� ���� �����Ƿ� �ذ� ��.
			answer++;
			return;
		}
		
		// ������ ������ (1�� ~ N��)
		for(int j=0;j<=N;j++) {
			col[rowNo] = j;
			if(checking(rowNo)) { // ���� ���� ����ġ�� �����ϴٸ� ���� ������
				setQueens(rowNo+1);
			}
			// �������迭�� ���� ��ġ�� �����ϹǷ� �õ��ߴ� ������ �� ���� �ǵ��� �ʿ䰡 ����.
		}
	}
	
	// rowNo���� ���� ���°� �������� üũ : ������ �ִٸ� true, ���� �� ���ٸ� false
	private static boolean checking(int rowNo) {
		for(int i=1;i<rowNo;i++) {
			if((col[rowNo]==col[i]) || Math.abs(col[rowNo]-col[i]) == rowNo-i) return false;
		}
		return true;
	}

}
