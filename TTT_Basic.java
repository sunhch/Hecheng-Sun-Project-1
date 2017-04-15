/**
 * Hecheng Sun
 * CSC 242
 * Project 1
 */
import java.util.Scanner;
import java.util.ArrayList;

public class TTT_Basic {
	int[][] state = new int[3][3];
	int human,ai,turn = 1;
	boolean endGame =false;
	ArrayList<Integer> applicable =new ArrayList<Integer>();
	int Inf = Integer.MAX_VALUE;
	int NegInf = Integer.MIN_VALUE;
	int solution;


	public void draw(){
		System.err.println(" "+squareConvert(state,0,0) + " | "+squareConvert(state,0,1)+" | "+squareConvert(state,0,2));
		System.err.println("-----------");
		System.err.println(" "+squareConvert(state,1,0) + " | "+squareConvert(state,1,1)+" | "+squareConvert(state,1,2));
		System.err.println("-----------");
		System.err.println(" "+squareConvert(state,2,0) + " | "+squareConvert(state,2,1)+" | "+squareConvert(state,2,2));
	}

	public String squareConvert(int[][] in,int a,int b){
		if(in[a][b] == 1){
			return "X";
		}else if(in[a][b] == -1){
			return "O";
		}else{
			return String.valueOf(3*a+b+1);
		}
	}

	public static void main(String[] args) {
		while(true){
			new TTT_Basic();
		}
	}

	public TTT_Basic(){
		ttt();
	}

	@SuppressWarnings("resource")
	public void ttt(){
		System.err.println("X or O?");
		Scanner in = new Scanner(System.in);
		String choose;

		for(choose = in.nextLine();!choose.equals("O") && !choose.equals("X");){
			if(choose.equals("x")){
				choose = "X";
			}
			if(choose.equals("o")){
				choose = "O";
			}
			if(!choose.equals("X") && !choose.equals("O")){
				System.err.println("illegal input");
				choose = in.nextLine();
			}
		}

		if(choose.equals("X"))
			human = 1;
		else
			human = -1;

		ai = -human;

		draw();

		for(;!(actions().size() == 0 || endGame);terminalTest()){
			if(human == turn){
				System.err.print("It's your turn: ");
				String temp = in.nextLine();
				int newMove = 0;
				if(temp.equals("1") || temp.equals("2") || temp.equals("3") || temp.equals("4") || temp.equals("5") || temp.equals("6") || temp.equals("7") || temp.equals("8") || temp.equals("9")){
					newMove = Integer.parseInt(temp);
				}else{
					System.err.println("illegal input");
					continue;
				}
				actions();
				if(!applicable.contains(newMove)){
					swap();
					System.err.println("illegal input");
				}	
				else
					transition(human,newMove);
			}else{
				System.err.println("It's AI's turn");
				System.err.println();
				transition(ai, alphaBetaSearch());
				endGame = false;
				draw();
			}
			swap();
		}

		if(terminalTest() == -2) 
			System.err.println("We Draw");
		else{
			if(terminalTest() == human){
				System.err.println("You won! This shouldn't happen...");
			}else{
				System.err.println("I won! Resistance is futile.");
			}
		}
		System.err.println();
	}

	public int terminalTest(){
		if(state[0][0] == human && state[1][1] == human && state[2][2] == human){
			endGame=true;
			return human;
		}
		if(state[0][0] == ai && state[1][1] == ai && state[2][2] == ai){
			endGame=true;
			return ai;
		}

		if(state[2][0] == human && state[1][1] == human && state[0][2] == human){
			endGame=true;
			return human;
		}
		if(state[2][0] == ai && state[1][1] == ai && state[0][2] == ai){
			endGame=true;
			return ai;
		}

		for(int k = 0; k <= 2; k++){
			if(state[k][0] == human && state[k][1] == human && state[k][2] == human){
				endGame=true;
				return human;
			}
			if(state[k][0] == ai && state[k][1] == ai && state[k][2] == ai){
				endGame=true;
				return ai;
			}
		}

		for(int k = 0; k <= 2; k++){
			if(state[0][k] == human && state[1][k] == human && state[2][k] == human){
				endGame=true;
				return human;
			}
			if(state[0][k] == ai && state[1][k] == ai && state[2][k] == ai){
				endGame=true;
				return ai;
			}
		}
		return -2;
	}

	public int alphaBetaSearch(){
		return howToChoose(state,NegInf,Inf,ai)[0];
	}
	
	public int[] howToChoose(int[][] state,int max,int min,int who){//decide the move by minimax algorithm with alpha beta pruning
		if(!(actions().size()==0) && terminalTest() == -2){
			for(int i = 0 ; i < actions().size() && (max < min);i++){
				int k = 0;
				int move = actions().get(i);
				transition(who,move);//Tracking down through the tree
				if(who == human){
					min = minValue(solution,max,min,move);
				}else if(who == ai){
					max = maxValue(solution,max,min,move);
				}

				//decrementing
				if(move >= 4){
					k = 1;
					move = move - 3;
					if(move >= 4){
						k =2;
						move = move -3;
					}
				}
				state[k][--move] = 0;
			}

			if(who != 0){
				if(who == human){
					int[] out = {solution,min};
					return out;

				}else if (who == ai){
					int[] out = {solution,max};
					return out;
				}
			}else{
				return null;
			}
		}else{
			int temp = terminalTest();
			if(temp == 1){
				int[] out = {solution,ai};
				return out;
			}else if(temp == -1){
				int[] out = {solution,human};
				return out;
			}else {
				int[] out = {solution,0};
				return out;
			}
		}
		return null;
	}

	public int maxValue(int solution,int a,int b,int move){
		int [] temp = howToChoose(state,a, b, -ai);
		int u = temp[1];
		if(a < u){
			this.solution = move;
		}else{
			this.solution = solution;
		}
		a = Math.max(u, a);
		
		return a;
	}

	public int minValue(int solution,int a,int b,int move){
		int[] temp = howToChoose(state,a, b, -human);
		int u = temp[1];
		if(b > u){
			this.solution = move;
		}else{
			this.solution = solution;
		}
		b = Math.min(u, b);
		return b;
	}

	public void transition(int who,int newMove){
		int r = 0;
		while(true){
			if(newMove >= 4){
				newMove = newMove - 3;
				r++;
			}
			if(newMove<4){
				break;
			}
		}
		state[r][--newMove] = who;
	}

	public ArrayList<Integer> actions(){
		applicable.clear();

		if(state[0][0]!=1 && state[0][0]!=-1){
			applicable.add(1);
		}
		if(state[0][1]!=1 && state[0][1]!=-1){
			applicable.add(2);
		}
		if(state[0][2]!=1 && state[0][2]!=-1){
			applicable.add(3);
		}
		if(state[1][0]!=1 && state[1][0]!=-1){
			applicable.add(4);
		}
		if(state[1][1]!=1 && state[1][1]!=-1){
			applicable.add(5);
		}
		if(state[1][2]!=1 && state[1][2]!=-1){
			applicable.add(6);
		}
		if(state[2][0]!=1 && state[2][0]!=-1){
			applicable.add(7);
		}
		if(state[2][1]!=1 && state[2][1]!=-1){
			applicable.add(8);
		}
		if(state[2][2]!=1 && state[2][2]!=-1){
			applicable.add(9);
		}
		return applicable;
	}

	public void swap(){
		turn = -turn;
	}
}