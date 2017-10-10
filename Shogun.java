import java.util.Scanner;
import java.lang.StringBuilder;

public class Shogun
{
	private static final int BOARD_LENGTH=8;
	private static final int BOARD_WIDTH=7;

	public static void main(String[] args)
	{
		playGame();
	}

	public static void playGame()
	{
		Scanner kb = new Scanner(System.in);
		boolean play = true;
		boolean compTurn = false;
		boolean legal = false;
		int counter =0;
		System.out.println("Setting up the board...");
		int[][] board = initBoard();
		System.out.print("Do you want to go first (y/n)? ");
		compTurn = kb.next().equalsIgnoreCase("y")?false:true;
		System.out.println();
		getMoves(board, compTurn);
		System.out.println("Showing the board...");
		displayBoard(board);
		/*while (false)
		{
			System.out.println("Getting legal moves...");
			if (compTurn)
			{	
				System.out.println("Comp making a turn...");
			}else
			{
				legal ^= false;
				while(!legal)
				{
					System.out.println("Player making a turn...");
					legal ^= true;
				}
				
			}
			System.out.println("Updating board state");
			System.out.println("Showing the board...");
			System.out.println("Checking if the game is over...");
			counter++;
		}*/
	}


	// Piece Definitions:
	//						King = K = +/-5
	//						Ninja = J = +/-4
	//						Samurai = S = +/-3
	//						Mini-Ninja = j = +/-2
	//						Mini-Samurai = s = +/-1
	public static int[][] initBoard()
	{
		int[][] board = {{0, 0, 0, -5, 0, 0, 0},
						 {-3, -3, -3, 0, -4, -4, -4},
						 {-2, -2, -2, 0, -1, -1, -1},
						 {0, 0, 0, 0, 0, 0, 0},
						 {0, 0, 0, 0, 0, 0, 0},
						 {1, 1, 1, 0, 2, 2, 2},
						 {4, 4, 4, 0, 3, 3, 3},
						 {0, 0, 0, 5, 0, 0, 0}};
		return board;
	}

	public static void displayBoard(int[][] board)
	{
		for (int length = BOARD_LENGTH-1; length>=0; length--)
		{
			System.out.print(length + 1);
			for (int width = 0; width<BOARD_WIDTH; width++)
			{
				System.out.printf("%3d", board[length][width]);
			}
			System.out.println();
		}
		System.out.println("  A  B  C  D  E  F  G");
	}

	// Piece Definitions:
	//						King = K = +/-5
	//						Ninja = J = +/-4
	//						Samurai = S = +/-3
	//						Mini-Ninja = j = +/-2
	//						Mini-Samurai = s = +/-1
	// 
	// Note that all length locations on printed board appear as length+1
	// As such, when referring to the current piece's location, the length number will be length+1
	public static void getMoves(int[][] board, boolean compTurn)
	{
		StringBuilder moves = new StringBuilder();
		// If it's the computer's turn, find possible moves for the computer
		// All moves described from the computer's POV
		// Computer moves from board[7][] toward board[0][]
		// Left for the computer is board[][0] and right is board[][7]
		if (compTurn)
		{
			for (int length = 0; length < BOARD_LENGTH; length++)
			{
				for (int width = 0; width < BOARD_WIDTH; width++)
				{
					// Mini-Samurai
					if (board[length][width]==1)
					{
						// If there is no piece in front (board[length-1][]) of the Mini-Samurai, it's possible to move forward one space
						// Mini-Samurai must also not be in board[0][] row
						if (length>0 && board[length-1][width]==0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width+'A')).append(length+" ");
						// If there is an enemy piece to the left (board[][width-1]) AND forward (board[length-1][]) 
						// of the Mini-Samurai AND there is no piece directly to the left (board[length][width-1])...
						// Mini-Samurai must not be in board[][0]
						if (width>0 && board[length][width-1]==0 && board[length-1][width-1]<0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width-1+'A')).append(length+1+" ");
						// If there is an enemy piece to the right (board[][width+1]) AND forward (board[length-1][]) 
						// of the Mini-Samurai AND there is no piece directly to the right(board[length][width+1])...
						// Mini-Samurai must not be in board[][BOARD_WIDTH-1]
						if (width<BOARD_WIDTH-1 && board[length][width+1]==0 && board[length-1][width+1]<0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width+1+'A')).append(length+1+" ");
					}
					// Mini-Ninja
					else if (board[length][width]==2)
					{
						// If there is no piece diagonally forward to the right (board[length-1][width+1]) of Mini-Ninja...
						// Mini-Ninja must not be board[0][] or board[][BOARD_WIDTH-1]
						if (length>0 && width<BOARD_WIDTH-1 && board[length-1][width+1]==0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width+1+'A')).append(length+" ");
						// If there is no piece diagonally forward to the left (board[length-1][width-1]) of Mini-Ninja...
						// Mini-Ninja must not be board[0][] or board[][0]
						if (length>0 && width>0 && board[length-1][width-1]==0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width-1+'A')).append(length+" ");
						// If there is an enemy piece to the right (board[][width+1]) with no piece behind it(board[length+1][width+1]), 
						// Mini-Ninja can move there to attack
						// Mini-Ninja must not be board[BOARD_LENGTH-1][] or board[][BOARD_WIDTH-1]
						if (length<BOARD_LENGTH-1 && width<BOARD_WIDTH-1 && board[length][width+1]<0 && board[length+1][width+1]==0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width+1+'A')).append(length+2+" ");
						// If there is an enemy piece to the left (board[][width-1]) with no piece behind it (board[length+1][width-1]), 
						// Mini-Ninja can move there to attack
						// Mini-Ninja must not be board[BOARD_LENGTH-1][] or board[][0]
						if (length<BOARD_LENGTH-1 && width>0 && board[length][width-1]<0 && board[length+1][width+1]==0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width-1+'A')).append(length+2+" ");
					}
					// Samurai
					else if (length>0 && board[length][width]==3)
					{
						// Use a for loop to search for possible moves
						// Samurai can move directly forward until it runs into another piece or the end of the board. 
						// Can only move horizontally to attack
						for (int forward = length-1; forward>=0; forward--)
						{
							if (board[forward][width]==0)
							{
								moves.append((char)(width+'A')).append(length+1).append((char)(width+'A')).append(forward+1+" ");
							}else forward = -1;
						}
						// Now checking for horizontal moves to the left
						// Note that Samurai must not be in board[][0]
						if (width>0)
						{
							for (int left = width-1; left>=0; left--)
							{
								// If there is nothing in the square to the left of the ninja, we can then check the square in front of it
								if (board[length][left]==0)
								{
									// If there is an enemy in the square in front of the open square, then the open square becomes a possible move
									if (board[length+1][left]<0)
									{
										moves.append((char)(width+'A')).append(length+1).append((char)(left+'A')).append(length+1+" ");
									}
								}else left = -1; // If the square to the left is blocked, there is no need to check further
							}
						}
						// Now checking for horizontal moves to the right
						// Note that Samurai must not be in board[][BOARD_WIDTH-1]
						if (width<BOARD_WIDTH-1)
						{
							for (int right = width + 1; width <BOARD_WIDTH; width++)
							{
								// If there is nothing in the square to the right of the ninja we can then check the square in front of it
								if (board[length][right]==0)
								{
									// If there is an enemy in the square in front of the open square, the open square becomes a possible move
									if (board[length+1][right]<0)
									{
										moves.append((char)(width+'A')).append(length+1).append((char)(right+'A')).append(length+1+" ");
									}
								}else right = -1; // If the square to the right is blocked, there is no need to check further
							}
						}
					}
					// Ninja
					{
						
					}
				}
			}
		}
		// Finding the possible moves for the human player
		// All moves described from the computer's POV
		// Player moves from board[0][] to board[7][]
		// Plyer's left is board[][0] and right is board[][7]
		else
		{
			for (int length = 0; length < BOARD_LENGTH; length++)
			{
				for (int width = 0; width < BOARD_WIDTH; width++)
				{
					// Mini-Samurai
					if (board[length][width]==-1)
					{
						// If there is no piece in front (board[length+1][]) of the Mini-Samurai, it's possible to move forward one space
						// Mini-Samurai must also not be in (board[BOARD_LENGTH-1][])
						if (length<BOARD_LENGTH-1 && board[length+1][width]==0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width+'A')).append(length+2+" ");
						// If there is an enemy piece to the left (board[][width-1]) AND forward (board[length+1][]) of the Mini-Samurai 
						// AND there is no piece directly to the left (board[length][width-1])...
						// Mini-Samurai must not be in board[][0]
						if (width>0 && board[length][width-1]==0 && board[length+1][width-1]>0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width-1+'A')).append(length+1+" ");
						// If there is an enemy piece to the right AND forward (board[length+1][width+1]) of the Mini-Samurai 
						// AND there is no piece directly to the right (board[length][width+1])...
						// Mini-Samurai must not be in board[][BOARD_WIDTH-1]
						if (width<BOARD_WIDTH-1 && board[length][width+1]==0 && board[length+1][width+1]>0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width+1+'A')).append(length+1+" ");
					}
					// Mini-Ninja
					else if (board[length][width]==-2)
					{
						// If there is no piece diagonally forward to the right (board[length+1][width+1]) of Mini-Ninja...
						// Mini-Ninja must not be board[BOARD_LENGTH-1][] or board[][BOARD_WIDTH-1]
						if (length<BOARD_LENGTH-1 && width<BOARD_WIDTH-1 && board[length+1][width+1]==0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width+1+'A')).append(length+2+" ");
						// If there is no piece diagonally forward to the left (board[length+1][width-1]) of Mini-Ninja...
						// Mini-Ninja must not be board[BOARD_LENGTH-1][] or board[][0]
						if (length<BOARD_LENGTH-1 && width>0 && board[length+1][width-1]==0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width-1+'A')).append(length+2+" ");
						// If there is an enemy piece to the right (board[length][width+1]) with no piece behind it (board[length-1][width+1]), 
						// Mini-Ninja can move there to attack
						// Mini-Ninja must not be board[0][] or board[][BOARD_WIDTH-1]
						if (length>0 && width<BOARD_WIDTH-1 && board[length][width+1]<0 && board[length-1][width+1]==0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width+1+'A')).append(length+" ");
						// If there is an enemy piece to the left (board[length][width-1]) with no piece behind (board[length-1][width-1]) it, 
						// Mini-Ninja can move there to attack
						// Mini-Ninja must not be board[0][] or board[][0]
						if (length>0 && width>0 && board[length][width-1]<0 && board[length+1][width+1]==0)
							moves.append((char)(width+'A')).append(length+1).append((char)(width-1+'A')).append(length+2+" ");
					}
				}
			}
		}
		System.out.println(moves);
	}
}