package tictactoeAI;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToeAI {

	// 0 means blank
	// 1 means X (AI)
	// 2 means O (human)
	
	static String[] board;
	static String move;
	static String AI  =  "X";
	static String Human = "O";
	
	static class Move
	{
		int row, column;
	}
	
	//Checks if there are remaining moves on the board, which returns true
	// If false, no moves remains
	
	static Boolean remainingMoves(String board[])
	{
		for(int i = 0; i < 3; i++) 
		{
			for(int j = 0; j < 3; j++) 
			{
				if(board[i] == move && move.isBlank()) 
				{
					if(board[j] == move && move.isBlank()) 
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	static int evaluate(String[] board) 
	{
		for(int row = 0; row < 3; row++) // Checks rows for X or O victory
		{
			if(board[0] == board[1] && board[1] == board[2]) 
			{
				if (board[0] == AI) 
				{
					return +10;
				}
				else if(board[0] == Human) 
				{
					return -10;
				}
			}
			
			if(board[3] == board[4] && board[4] == board[5]) 
			{
				if (board[3] == AI) 
				{
					return +10;
				}
				else if(board[3] == Human) 
				{
					return -10;
				}
			}
			
			if(board[6] == board[7] && board[7] == board[8]) 
			{
				if (board[6] == AI) 
				{
					return +10;
				}
				else if(board[6] == Human) 
				{
					return -10;
				}
			}
		}
		
		
		for(int column = 0; column < 3; column++) // Checks columns for X or O win
		{
			if(board[0] == board[3] && board[3] == board[6]) 
			{
				if(board[0] ==  AI) 
				{
					return +10;
				}
				else if(board[0] == Human) 
				{
					return -10;
				}
			}
			
			if(board[1] == board[4] && board[4] == board[7]) 
			{

				if(board[1] ==  AI) 
				{
					return +10;
				}
				else if(board[1] == Human) 
				{
					return -10;
				}
			}
			
			if(board[2] == board[5] && board[5] == board[8]) 
			{
				if(board[2] ==  AI) 
				{
					return +10;
				}
				else if(board[2] == Human) 
				{
					return -10;
				}
			}
		}
		
		
		// Checks diagonals on board for X or O win
		if(board[0] == board[4] && board[4] == board[8]) 
		{
			if(board[0] ==  AI) 
			{
				return +10;
			}
			else if(board[0] == Human) 
			{
				return -10;
			}
		}
		
		if(board[2] == board[4] && board[4] == board[6]) 
		{
			if(board[2] ==  AI) 
			{
				return +10;
			}
			else if(board[2] == Human) 
			{
				return -10;
			}
		}
		
		return 0; //if the end result ends in a draw, return 0
	}
	
	
	// Minimax function considers possible ways the game can go
	// Returns value of board
	
	static int minimax(String[] board, int depth, Boolean isMax)
	{
		int score = evaluate(board);
		
		if(score == 10) // If board is evaluated and AI won, return score for AI
		{
			return score;
		}
		
		if(score == -10) // If board is evaluated and AI lost, return score for AI
		{
			return score;
		}
		
		if (remainingMoves(board) == false) // if there are no moves left and no winner, it is a draw
		{
			return 0;
		}
		
		//if move is maximizer
		if(isMax) 
		{
			int best = -100;
			
			for(int i = 0; i < 3; i++) // Traverse every node
			{
				for(int j = 0; j < 3; j++) 
				{
					if(board[i].isBlank()) // Check if board[i] is empty
					{
						if(board[j].isBlank()) // Check if board[j] is empty
						{
							move = AI;
							best = Math.max(best, minimax(board, depth + 1, !isMax));
							
							board[i] = "";
							board[j] = "";
						}
					}
				}
			}
			
			return best;
		}
		
		else // if it is minimizer's move
		{
			int best = 100;
			for(int i = 0; i < 3; i++) 
			{
				for(int j = 0; j < 3; j++) 
				{
					if(board[i].isBlank()) 
					{
						if(board[j].isBlank()) 
						{
							move = AI; // make AI move
							
							//call minimax recursively and choose min value
							best = Math.min(best, minimax(board, depth + 1, !isMax));
							
							board[i] = ""; // undo move
							board[j] = ""; // undo move
						}
					}
				}
			}
			
			return best;
		}
	}
	
	//returns best possible move for player
	static Move bestMove(String[] board) 
	{
		int bestVal = -100;
		Move bestMove = new Move();
		bestMove.row  = -1;
		bestMove.column = -1;
		
		for(int i = 0; i < 3; i++) 
		{
			for(int j = 0; j < 3; j++) 
			{
				if(board[i].isBlank()) 
				{						//check if board[i] & board[j] is empty
					if(board[j].isBlank()) 
					{
						move = AI;
						
						//evaluation function for move
						int moveValue = minimax(board, 0, false);
						board[i] = ""; // undi move
						board[j] = ""; // undo move
						
						// if value of current move is greater than best value, update best value
						if(moveValue > bestVal) 
						{
							bestMove.row = i;
							bestMove.column = j;
							bestVal = moveValue;
						}
					}
				}
			}
		}
	
		System.out.println("Value of best move is: " + bestVal);
		return bestMove;
	}
	
	//This function checks if either there are 3 O's or 3 X's on the board for AI or Human
	static String checkWinner()
	{
		for(int i = 0; i < 8; i++) 
		{
			String line = null;
			
			switch(i) 
			{
				case 0:
					line = board[0] + board[1] + board[2];
					break;
				
				case 1:
					line = board[3] + board[4] + board[5];
					break;
				
				case 2:
					line = board[6] + board[7] + board[8];
					break;
				
				case 3:
					line = board[0] + board[3] + board[6];
					break;
				
				case 4:
					line = board[1] + board[4] + board[7];
					break;
				
				case 5:
					line = board[2] + board[5] + board[8];
					break;
				
				case 6:
					line = board[0] + board[4] + board[8];
					break;
				
				case 7:
					line = board[2] + board[4] + board[6];
					break;
			}
			
			// If the winner is X (AI)
			if(line.equals("XXX")) 
			{
				return "X";
			}
			
			// If the winner is O (Human)
			else if(line.equals("OOO")) 
			{
				return "O";
			}
		}
		
		for(int i = 0; i < 9; i++) 
		{
			if(Arrays.asList(board).contains(String.valueOf(i + 1))) 
			{
				break;
			}
			else if (i == 8) 
			{
				return "Draw";
			}
		}
		
		System.out.println("It is " + move + "'s turn.");
		System.out.println("Enter a block number to place " + move + " in:");
		return null;
	}
	
	static void printBoard() 
	{
		System.out.println("|---|---|---|");
		System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " |");
		System.out.println("| " + board[3] + " | " + board[4] + " | " + board[5] + " |");
		System.out.println("| " + board[6] + " | " + board[7] + " | " + board[8] + " |");
		System.out.println("|---|---|---|");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		@SuppressWarnings("resource")
		Scanner userNumber = new Scanner(System.in);
		
		board = new String[9];
		

		int min = 1, max = 5;
		int AI_Number = (int)Math.floor(Math.random() * (max - min + 1) + min); // the AI will pick a random number from 1 to 5
		int AI_randomMove_Min = 1, AI_randomMove_Max = 9;
		int AI_randomMove = (int)Math.floor(Math.random() * (AI_randomMove_Max - AI_randomMove_Min) + AI_randomMove_Min);
		int placeAIinput = AI_randomMove;
		
	
		
		String Winner = null;
		
		int slotInput;
		
		
		for(int i = 0; i < 9; i++) 
		{
			board[i] = String.valueOf(i + 1);
		}
		
		System.out.println("Welcome to Tic Tac Toe.");
		printBoard();
		
		System.out.println("The AI will have the letter X and the Human player will have the letter O.");
		
		System.out.println("The Human and the AI will pick a random number from 1 to 5");
		System.out.println("Whoever has the bigger number goes first.");
		
		System.out.println("Human player, enter a number from 1 to 5.");
		int HumanNumber = userNumber.nextInt();
		
		System.out.println("The Human player " + Human + " 's number is: " + HumanNumber);
		System.out.println("The AI's number is: " + AI_Number);
		
		if(HumanNumber > AI_Number) 
		{
			System.out.println("Human Player (letter O) will go first.");
			move = Human;
		}
		if(AI_Number > HumanNumber)
		{
			System.out.println("AI Player (letter X will go first. )");
			move = AI;
			//slotInput = AI_randomMove;
		}
		
		if(HumanNumber > 5 || HumanNumber < 1) // if human player's number is outside of the range, human player must pick a different number
		{
			System.out.println("Human, the number you entered is not in the range of 1 to 5.");
			System.out.println("Enter a number from 1 to 5");
			HumanNumber = userNumber.nextInt();
		}
		if(HumanNumber == AI_Number) 
		{
			System.out.println("Human Player and AI have the same number.");
			System.out.println("Both players must pick different numbers from 1 to 5.");
			AI_Number = (int)Math.floor(Math.random() * (max - min + 1) + min);
			System.out.println("Enter a number from 1 to 5");
			HumanNumber = userNumber.nextInt();
		}
		
		Move findbestMove = bestMove(board);
		
		System.out.println("THe Optimal move is: ");
		System.out.println("Row: " + findbestMove.row
							+ " Col: " + findbestMove.column);
		
		
		while(Winner == null) 
		{
			
			
			//Exception handeling
			//slotInput will take input from 1 to 9
			// If the user enters a number outside range of 1 to 9
			// it will print out invalid input and will ask human player to enter a slot from 1 to 9
			try 
			{
				slotInput = in.nextInt();
				//slotInput2 = placeAIinput;
				
				
				if(!(slotInput > 0 && slotInput <= 9)) 
				{
					System.out.println("Invalid input; Retype a slot number from 1 to 9");
					continue;
				}
			}
			catch(InputMismatchException e) 
			{
				System.out.println("Invalid input; Retype a slot number from 1 to 9");
				continue;
			}
			
			// This if statement decides which player 
			if(board[slotInput - 1].equals(String.valueOf(slotInput))) 
			{
				board[slotInput - 1] = move;
				
				if(move.equals("X")) 
				{
					move = Human;
				}
				else 
				{
					slotInput = placeAIinput;
					move = AI;
				}
				
								
				printBoard();
				Winner = checkWinner();
			}
			
			else 
			{
				System.out.println("Slot has been taken. Enter another slot number: ");
			}
		
		}
		
		if(Winner.equalsIgnoreCase("Draw")) 
		{
			System.out.println("It is a Draw! Thank you for playing.");
		}
		else 
		{
			System.out.println("Congrats! Player " + Winner + " has won! Thank you for playing.");
		}
		
		in.close();
	}
}
