/*
 Your name here: Carlos Alvarado
 Your McGill ID here: 260551435
*/

package assignment2;

public class SolitaireCipher {
	public Deck key;

	public SolitaireCipher (Deck key) {
		this.key = new Deck(key); // deep copy of the deck
	}

	/* 
	 * TODO: Generates a keystream of the given size
	 */
	public int[] getKeystream(int size) {
		
		int[] keyStream = new int[size];
		int i = 0;
		while(i < size) {
			keyStream[i] = key.generateNextKeystreamValue();
			i++;
		} 
		
		return keyStream;
	}

	/* 
	 * TODO: Encodes the input message using the algorithm described in the pdf.
	 */
	public String encode(String msg) {
		
		int i = 0;
		String newMsg = msg.toUpperCase().replaceAll("[^A-Z]", "");
		int [] keyStream = this.getKeystream(newMsg.length());
		char[] encodedMsg = new char[newMsg.length()];
		
		while(i < newMsg.length()) {
			int numShift = keyStream[i];
			char letter = newMsg.charAt(i);
			char newLetter = (char) ((letter - 'A' + numShift) % 26 + 'A');
			encodedMsg[i] = newLetter;
			i++;
		}
		
		String encodedMsgStr = new String(encodedMsg);
		
		return encodedMsgStr;
	}

	/* 
	 * TODO: Decodes the input message using the algorithm described in the pdf.
	 */
	public String decode(String msg) {
		
		int i = 0;
		int[] keyStream = getKeystream(msg.length());
		char[] decodedMsg = new char[msg.length()];
		
		while(i < msg.length()) {
			int numShift = keyStream[i];
			char letter = msg.charAt(i);
			char newLetter = (char) ((letter - 'A' - numShift + 26) % 26 + 'A');
			decodedMsg[i] = newLetter;
			i++;
		}
		
		String decodedMsgStr = new String(decodedMsg);
		
		return decodedMsgStr;
		
	}

}
