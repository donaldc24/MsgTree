package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * @author Donald Calhoun
 * Class to Build a Binary Search Tree
 */
public class MsgTree {
	
	public char payloadChar;	
	public MsgTree left;
	public MsgTree right;
	public MsgTree root;
	public static Stack<MsgTree> stack;
	
	/**
	 * Constructor building the tree from a string
	 * @param encodingString  Takes input of String in file
	 */
	public MsgTree (String encodingString) {
		char[] cArray = encodingString.toCharArray();
		stack = new Stack<MsgTree>();
		MsgTree t = new MsgTree(cArray[0]);

		for (int i = cArray.length-1; i > 0; i--) {		// Loops through the encodingString and builds tree
			MsgTree temp = new MsgTree(cArray[i]);
			stack.push(temp);
			
			if (cArray[i] == '^') {
				temp = stack.pop(); 
				temp.left = stack.pop();
				temp.right = stack.pop();
				stack.push(temp);
			}
		}
		
		t.left = stack.pop();
		t.right = stack.pop();
		root = t;
	}
	 
	/**
	 * Constructor for a single node with null children
	 * @param payloadChar
	 */
	public MsgTree (char payloadChar) {
		left = null; 
		right = null;
		this.payloadChar = payloadChar; 
	}
	
	/**
	 * Method to print characters and their binary codes
	 * @param root	Takes the created tree as input
	 * @param code	Keeps Track of the binary value of each letter while traversing tree in preorder way
	 */
	public static void printCodes (MsgTree root, String code) {
		
        if (root == null) {
            return;
        }
            
        if (root.payloadChar != '^') {		// Checks if the given node in the tree is ^ and prints the result and resets the binary code 
        	if (root.payloadChar == '\n') {
        		System.out.println("   " + "\\n" + "\t\t" + code);
        	} else if (root.payloadChar == ' ') {
        		System.out.println("   " + "\\s" + "\t\t" + code);
        	} else {
        		System.out.println("   " + root.payloadChar + "\t\t" + code);
        	}
            code = code.substring(0, code.length() - code.length());
        }
  
        code += "0";	// Adds a 0 to the code
        printCodes(root.left, code);

        code = code.substring(0, code.length() - 1);	// Removes the 0 if left was null or did not run
        code += "1";	// Adds 1 since the traversle has gone right
        printCodes(root.right, code);
	}
	 
	/**
	 * Decodes the binary by traversing the binary tree in preorder fashion for each char
	 * @param codes  Takes the tree as an input
	 * @param msg	Takes the String of the binary as input
	 */
	public void decode (MsgTree codes, String msg) {
		char[] encodedMsg = msg.toCharArray();
		String output = "";
		
		for (int j = 0; j < encodedMsg.length; j++) {	// Iterates thru the binary string and decrpts the message char by char
			if (encodedMsg[j] == '0') {
				codes = codes.left;
				if (codes.payloadChar != '^') {
					output += codes.payloadChar;
					codes = root;
				}
			} else {
				codes = codes.right;
				if (codes.payloadChar != '^') {
					output += codes.payloadChar;
					codes = root;
				}
			}
		}
		System.out.println(output);
	} 
}