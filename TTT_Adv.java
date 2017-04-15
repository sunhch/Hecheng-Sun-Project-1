/**
 * Hecheng Sun
 * CSC 242
 * Project 1
 */
import java.util.Scanner;
import java.util.ArrayList;
public class TTT_Adv {
	int[][][] state = new int[9+1][3][3];
	int human,ai,turn = 1,location = -1;
	boolean endGame = false;
	ArrayList<Integer> applicable = new ArrayList<Integer>();
	int Inf = Integer.MAX_VALUE;
	int NegInf = Integer.MIN_VALUE;
	int chosenBoard = location,solution;
	ArrayList<Integer> remainBoard = new ArrayList<Integer>();

	public void draw(){
		System.err.println(" "+squareConvert(state,1,0,0)+" | "+squareConvert(state,1,0,1)+" | "+squareConvert(state,1,0,2)+"   "+squareConvert(state,2,0,0)+" | "+squareConvert(state,2,0,1)+" | "+squareConvert(state,2,0,2)+"   "+squareConvert(state,3,0,0)+" | "+squareConvert(state,3,0,1)+" | "+squareConvert(state,3,0,2));
		System.err.println("-----------"+" "+"-----------"+" "+"-----------");
		System.err.println(" "+squareConvert(state,1,1,0)+" | "+squareConvert(state,1,1,1)+" | "+squareConvert(state,1,1,2)+"   "+squareConvert(state,2,1,0)+" | "+squareConvert(state,2,1,1)+" | "+squareConvert(state,2,1,2)+"   "+squareConvert(state,3,1,0)+" | "+squareConvert(state,3,1,1)+" | "+squareConvert(state,3,1,2));
		System.err.println("-----------"+" "+"-----------"+" "+"-----------");
		System.err.println(" "+squareConvert(state,1,2,0)+" | "+squareConvert(state,1,2,1)+" | "+squareConvert(state,1,2,2)+"   "+squareConvert(state,2,2,0)+" | "+squareConvert(state,2,2,1)+" | "+squareConvert(state,2,2,2)+"   "+squareConvert(state,3,2,0)+" | "+squareConvert(state,3,2,1)+" | "+squareConvert(state,3,2,2));
		System.err.println();
		System.err.println(" "+squareConvert(state,4,0,0)+" | "+squareConvert(state,4,0,1)+" | "+squareConvert(state,4,0,2)+"   "+squareConvert(state,5,0,0)+" | "+squareConvert(state,5,0,1)+" | "+squareConvert(state,5,0,2)+"   "+squareConvert(state,6,0,0)+" | "+squareConvert(state,6,0,1)+" | "+squareConvert(state,6,0,2));
		System.err.println("-----------"+" "+"-----------"+" "+"-----------");
		System.err.println(" "+squareConvert(state,4,1,0)+" | "+squareConvert(state,4,1,1)+" | "+squareConvert(state,4,1,2)+"   "+squareConvert(state,5,1,0)+" | "+squareConvert(state,5,1,1)+" | "+squareConvert(state,5,1,2)+"   "+squareConvert(state,6,1,0)+" | "+squareConvert(state,6,1,1)+" | "+squareConvert(state,6,1,2));
		System.err.println("-----------"+" "+"-----------"+" "+"-----------");
		System.err.println(" "+squareConvert(state,4,2,0)+" | "+squareConvert(state,4,2,1)+" | "+squareConvert(state,4,2,2)+"   "+squareConvert(state,5,2,0)+" | "+squareConvert(state,5,2,1)+" | "+squareConvert(state,5,2,2)+"   "+squareConvert(state,6,2,0)+" | "+squareConvert(state,6,2,1)+" | "+squareConvert(state,6,2,2));
		System.err.println();
		System.err.println(" "+squareConvert(state,7,0,0)+" | "+squareConvert(state,7,0,1)+" | "+squareConvert(state,7,0,2)+"   "+squareConvert(state,8,0,0)+" | "+squareConvert(state,8,0,1)+" | "+squareConvert(state,8,0,2)+"   "+squareConvert(state,9,0,0)+" | "+squareConvert(state,9,0,1)+" | "+squareConvert(state,9,0,2));
		System.err.println("-----------"+" "+"-----------"+" "+"-----------");
		System.err.println(" "+squareConvert(state,7,1,0)+" | "+squareConvert(state,7,1,1)+" | "+squareConvert(state,7,1,2)+"   "+squareConvert(state,8,1,0)+" | "+squareConvert(state,8,1,1)+" | "+squareConvert(state,8,1,2)+"   "+squareConvert(state,9,1,0)+" | "+squareConvert(state,9,1,1)+" | "+squareConvert(state,9,1,2));
		System.err.println("-----------"+" "+"-----------"+" "+"-----------");
		System.err.println(" "+squareConvert(state,7,2,0)+" | "+squareConvert(state,7,2,1)+" | "+squareConvert(state,7,2,2)+"   "+squareConvert(state,8,2,0)+" | "+squareConvert(state,8,2,1)+" | "+squareConvert(state,8,2,2)+"   "+squareConvert(state,9,2,0)+" | "+squareConvert(state,9,2,1)+" | "+squareConvert(state,9,2,2));
	}

	public String squareConvert(int[][][] in,int a,int b,int c){
		if(in[a][b][c] == 1){
			return "X";
		}else if(in[a][b][c] == -1){
			return "O";
		}else{
			return String.valueOf(3*b+c+1);
		}
	}

	public static void main(String[] args) {
		while(true){
			new TTT_Adv();
		}
	}

	public TTT_Adv(){
		ttt_Adv();
	}

	@SuppressWarnings("resource")
	public void ttt_Adv(){
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

		for(;!(actions(location).size() == 0 || endGame);terminalTest()){
			if(human == turn) {
				if(location == -1) {
					System.err.print("Location Number (1-9): ");
					String temp = in.nextLine();
					if(temp.equals("1") || temp.equals("2") || temp.equals("3") || temp.equals("4") || temp.equals("5") || temp.equals("6") || temp.equals("7") || temp.equals("8") || temp.equals("9")){
						location = Integer.parseInt(temp);
					}else{
						System.err.println("illegal input");
						continue;
					}
				}
				System.err.print("It's your turn at "+ location+": ");

				String temp = in.nextLine();
				int newMove = 0 ;
				if(temp.equals("1") || temp.equals("2") || temp.equals("3") || temp.equals("4") || temp.equals("5") || temp.equals("6") || temp.equals("7") || temp.equals("8") || temp.equals("9")){
					newMove = Integer.parseInt(temp);
				}else{
					System.err.println("illegal input");
					continue;
				}
				actions(location);
				if(!applicable.contains(newMove)){
					swap();
					System.err.println("illegal input");
				}
				else
					transition(newMove,location,human);
			}
			else{
				System.err.println("It's AI's turn");
				System.err.println();
				int[] aiMove = alphaBetaSearch();
				int aiBoard = aiMove[0];
				int aiSquare = aiMove[1];
				transition(aiSquare,aiBoard,ai);
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
		for(int boards = 1; boards <= 9; boards++){
			for(int k = 0;k <= 2;k++){
				if(state[boards][k][0] == human && state[boards][k][1] == human && state[boards][k][2] == human){
					endGame=true;
					return human;
				}
				if(state[boards][k][0] == ai && state[boards][k][1] == ai && state[boards][k][2] == ai){
					endGame=true;
					return ai;
				}
			}

			for(int k = 0; k <= 2; k++){
				if(state[boards][0][k] == human && state[boards][1][k] == human && state[boards][2][k] == human){
					endGame=true;
					return human;
				}
				if(state[boards][0][k] == ai && state[boards][1][k] == ai && state[boards][2][k] == ai){
					endGame=true;
					return ai;

				}
			}

			if(state[boards][0][0] == human && state[boards][1][1] == human && state[boards][2][2] == human){
				endGame=true;
				return human;
			}
			if(state[boards][0][0] == ai && state[boards][1][1] == ai && state[boards][2][2] == ai){
				endGame=true;
				return ai;
			}

			if(state[boards][2][0] == human && state[boards][1][1] == human && state[boards][0][2] == human){
				endGame=true;
				return human;
			}
			if(state[boards][2][0] == ai && state[boards][1][1] == ai && state[boards][0][2] == ai){
				endGame=true;
				return ai;
			}
		}
		return -2;
	}

	public int[] alphaBetaSearch(){
		return howToChoose(state,NegInf,Inf,(int)(Math.pow(2, 8)),ai);
	}
	
	public int[] howToChoose(int[][][] state,int max,int min,int depth,int who){//decide the move by minimax algorithm with alpha beta pruning
		minimaxHelper();
		
		if(!(actions(location).size()==0) && terminalTest() == -2 && depth > 1) {
			for(int boards : remainBoard){
				for(int i = 0 ; i < actions(boards).size() && (max < min);i++){
					int k = 0;
					int move = actions(boards).get(i);
					transition(move,boards,who);//Tracking down through the tree
					if(who == human){
						min = minValue(solution,max,depth,min,move,chosenBoard,boards);
					}else if(who == ai){
						max = maxValue(solution,max,depth,min,move,chosenBoard,boards);
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
					state[boards][k][--move] = 0;
				}
			}
			if(who != 0){
				if(who == human){
					int[] out = {chosenBoard,solution,min};
					return out;

				}else if (who == ai){
					int[] out = {chosenBoard,solution,max};
					return out;
				}
			}else{
				return null;
			}
		}else{
			int temp = terminalTest();
			endGame = false;
			if(temp == 1){
				int[] out = {location,solution,ai*Inf};
				return out;
			}else if(temp == -1){
				int[] out = {location,solution,human*Inf};
				return out;
			}else {
				if(depth > 1){
					int[] out = {location,solution,0};
					return out;
				}else{
					if(who == ai){
						int [] out = {location,solution,heuHelper()};
						return out;
					}else{
						int [] out = {location,solution,-heuHelper()};
						return out;
					}
				}

			}
		}
		return null;
	}

	public int maxValue(int solution,int a, int depth, int b,int move,int chosenBoard,int boards){
		int temp[] = howToChoose(state,a, b, depth/2, -ai);
		int u = temp[2];
		if(a < u){
			this.solution = move;
			this.chosenBoard = boards;
		}else{
			this.solution = solution;
			this.chosenBoard = chosenBoard;
		}
		a = Math.max(u, a);
		return a;
	}

	public int minValue(int solution,int a, int depth, int b,int move,int chosenBoard,int boards){
		int[] temp = howToChoose(state,a, b, depth/2, -human);
		int u = temp[2];
		if(b > u){
			this.solution = move;
			this.chosenBoard = boards;
		}else{
			this.solution = solution;
			this.chosenBoard = chosenBoard;
		}
		b = Math.min(u, b);
		return b;
	}

	public void transition(int newMove,int newBoard,int who){
		location = newMove;
		int r = 0;
		while(true){
			if(newMove >= 4){
				newMove = newMove - 3;
				r++;
			}
			if(newMove < 4){
				break;
			}
		}
		state[newBoard][r][--newMove] = who;
		if(actions(location).size() == 0)
			location = -1;
	}

	public ArrayList<Integer> actions(int chosenBoard){
		applicable.clear();
		if(chosenBoard == -1)
			chosenBoard = 0;
		int counter = 0;
		while(counter <= 2){
			int i = counter;
			counter = 0;
			for(;counter <= 2;counter++){
				if(state[chosenBoard][i][counter] == 0){
					applicable.add(3*i+counter+1);
				}
			}
			counter = i+1;
		}
		return applicable;
	}

	public void swap(){
		turn = -turn;
	}

	public void minimaxHelper(){
		remainBoard.clear();
		if(location != -1)
			remainBoard.add(location);
		else{
			for(int boards = 0;boards <= 8;boards++){
				if(!(actions(boards+1).size() == 0)){
					remainBoard.add(boards+1);
				}
			}
		}
	}

	public int heuHelper(){
		int out = heuristic(ai) - heuristic(human);
		return out;
	}

	public int heuristic(int who){
		int out = 0;
		for(int boards = 1; boards <= 9; boards++){
			//diagonal 
			int score1 = 0;
			boolean worth1 = true;
			for(int i=0; i <= 2;i++){
				if(state[boards][i][i] != who && state[boards][i][i] != 0){
					worth1 = false;
				}
				if(state[boards][i][i] == who){
					score1++;
				}
			}
			if(worth1)
				out= (int) (out + Math.pow(10, score1));

			//reverse-diagonal
			int score2 = 0;
			boolean worth2 = true;
			for(int i=0; i <= 2;i++){
				if(state[boards][i][2-i] != who && state[boards][i][2-i] != 0){
					worth2 = false;
				}

				if(state[boards][i][2-i]==who){
					score2++;
				}
			}
			if(worth2)
				out = (int) (out + Math.pow(10, score2));
			
			for(int r = 0; r <= 2; r++){//all three rows
				boolean worth3 = true;
				int score = 0;
				for(int j = 0; j <= 2; j++){
					if(state[boards][r][j] != who && state[boards][r][j] != 0){
						worth3 = false;
					}
					if(state[boards][r][j] == who){
						score++;
					}
				}if(worth3)
					out = (int) (out + Math.pow(10, score));
			}

			for(int c = 0; c <= 2; c++){//All three column
				boolean worth4 = true;
				int score = 0;
				for(int i=0; i <= 2;i++){
					if(state[boards][i][c] != who && state[boards][i][c] != 0){
						worth4 = false;
					}
					if(state[boards][i][c] == who){
						score++;
					}
				}
				if(worth4)
					out = (int) (out + Math.pow(10, score));
			}
		}
		return out;
	}	
}