package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MsgTreeMain {
	
	/**
	 * Takes an arch file as an input and breaks it up between the string and the binary
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scn = new Scanner(System.in);
		String fileName = "";
		String fileString = "";
		String characters = "";
		String encoding = "";
		int lines = 0;
		
		System.out.println("Please enter filename to decode: ");
		fileName = scn.next();
		File file = new File(fileName);
		scn.close();
		Scanner scnFile = new Scanner(file);
		
		while (scnFile.hasNextLine()) {		// Takes the text from the file and adds it to fileString
			lines++;
			if (lines == 1) {		// Checks if more than 1 line is detected and if so adds \n
				fileString += scnFile.nextLine();
			} else {
				fileString += "\n";
				fileString += scnFile.nextLine();
			}
		}
		
		char[] encodedChar = fileString.toCharArray();
		int length = encodedChar.length;
		
		for (int i = 0; i < length; i++) {		// Splits the fileString into the string and the binary so a tree can be made with the string
			if (i == length-1) {
				encoding += encodedChar[i]; 
				break;
			}
			if ((encodedChar[i] == '1' && (encodedChar[i+1] == '1' || encodedChar[i+1] == '0')) || (encodedChar[i] == '0' && (encodedChar[i+1] == '1' || encodedChar[i+1] == '0'))) {
				encoding += encodedChar[i];
			} else {
				characters += encodedChar[i];  
			}
		} 

		System.out.println("character" + "\t" + "code"); 
		System.out.println("---------------------------");
		MsgTree tree = new MsgTree(characters);		// Calls to create a MsgTree given the string of characters
		//System.out.println(tree.root.payloadChar);
		MsgTree.printCodes(tree.root, "");
		System.out.println("MESSAGE:");
		tree.decode(tree.root, encoding);		// Uses the created tree and the encoding binary and decrypts the message
		scnFile.close();
    }
}
