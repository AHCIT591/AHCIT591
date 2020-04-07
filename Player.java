import java.util.HashMap;
import java.util.Scanner;

/**
 * Class the represents a human player
 * 
 * @author Jacob
 *
 */
public class Player {

	String name;
	Scanner in = new Scanner(System.in); // using the same scanner in several different methods, cause trouble?

	Player(String name) {
		this.name = name;
	}

	/**
	 * Pick the first tile from bag, if it is closest to A among players, the player
	 * will be the first to play his or her turn.
	 */
	Tile pickATile(BagOfTile bagOfTile) {
		Tile firstTile;
		// pick a random tile from the bag of tiles
		firstTile = bagOfTile.pickTile(); // removes the picked tile from bag, is that okay? "pickTile" already in the
											// bagOfTile
		// class, where is better?
		return firstTile;

	}

	/**
	 * Draw the first 7 tiles to make a rack from bag
	 */
	Rack getRack(BagOfTile bagOfTile) {
		Rack myRack = new Rack();
		for (int i = 0; i < 7; i++) {
			Tile pickedTile = bagOfTile.pickTile();
			myRack.addTile(pickedTile);
		}
		return myRack; // already done in the rack class? where is better?
	}

	/**
	 * Place one tile from rack on the board till creating a word
	 * 
	 * @param locationInput
	 */
	void placeTile(Rack myRack, Board myBoard) {
		HashMap<String, Integer> letterToValue = new HashMap<String, Integer>();
		for (Tile tile : myRack.getRack()) {
			letterToValue.put(tile.getLetter(), tile.getValue());
		}

		// need to add exceptions for invalid inputLetter or location
		System.out.println("Please input a letter from your rack that you want to place on the board: ");
		String inputLetter = in.next();
		int value = letterToValue.get(inputLetter);
		Tile tile = new Tile(inputLetter, value);
		// System.out.println(tile.getLetter()+tile.getValue()); checking the tile has
		// the correct information
		System.out.println("Plese input the location(row,column) you want to place it: "); // how do we make sure the
																							// input format

		// Below is better to be done at board class? // format
		String location = in.next();
		myBoard.updateBoard(location, tile);
		myBoard.printBoard();
		// after this, need to put back one additional tile on rack, call addTile method
	}

	/**
	 * If the player doesn't have a word, exchange one tile on the rack with one
	 * from bag of Tiles
	 */
	void exchangeTile(Rack myRack, BagOfTile bagOfTile) {
		// need to add exception for invalid input letter
		System.out.println("Please indicate the letter you want to exchange: ");
		String exchangeLetter = in.next();
		HashMap<String, Integer> letterToValue = new HashMap<String, Integer>();
		for (Tile tile : myRack.getRack()) {
			letterToValue.put(tile.getLetter(), tile.getValue());
		}
		int value = letterToValue.get(exchangeLetter);
		Tile tile = new Tile(exchangeLetter, value);
		// System.out.println(tile.getLetter()+tile.getValue());
		myRack.removeTile(tile); // why removing is not happening?
		// myRack.rackDisplay();
		Tile additionalTile = bagOfTile.pickTile();
		myRack.addTile(additionalTile);
		myRack.rackDisplay();

	}

	/**
	 * challenge another player about whether the word is valid. If it is in the
	 * dictionary, challenger lose the turn? if not, the player placed the word lose
	 * the turn? prompt "Do you want to challenge previous player" in the game
	 * class?
	 * 
	 * @param dictionary
	 * @param word
	 * @return
	 */
	boolean findInDictionary(Dictionary dictionary, String word) {
		if (dictionary.getDictionaryArray().contains(word)) {
			System.out.println("In the dictionary!");
			return false;
			// how to make the player lose this turn? just pass to the next player in the
			// game class?
		} else {
			System.out.println("NOT in the dictionary! Chalenger Won!");
			return true;
		}
	}

}
