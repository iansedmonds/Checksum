import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Integer;
import java.lang.NullPointerException;

public class checksum {


	public static void main(String args[]) throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		
		/**
		 * args[0] = text file
		 * args[1] = checkSumSize size
		 */
		
		//==error handling==
		if(args.length == 0) {
			System.err.println("Not enough arguments passed");
			return;
		}
		
		//checks if there are 2 and only 2 arguments passed
		if((args.length != 2))
		{
			int arguments = args.length - 2;
			
			if(arguments < 0)
			{
				System.err.println("Not enough arguments passed");
			}
			
			else {
			System.err.println(arguments + " too many arguments passed. "
					+ "Please only pass a file name and an integer for the size of bits.");
			}
			
			return;
		}
		
		//switch statement which passes corresponding checksum size
		switch(args[1])
		{
			case "8":
				checkSumSize(args);
				break;
			
			case "16":
				checkSumSize(args);
				break;
		
			case "32":
				checkSumSize(args);
				break;
				
			//error message if invalid checksum size is entered
			default:
				System.err.println("Valid checkSumSize sizes are 8, 16 or 32");
				return;
				
		}
	}

	public static void checkSumSize(String[] args) throws FileNotFoundException, NullPointerException {
	
		File fileName = new File(args[0]);
		
		int checkSumSize = 0;
		
		switch(args[1])
		{
			case "8":
				checkSumSize = 8;
				break;
			
			case "16":
				checkSumSize = 16;
				break;
		
			case "32":
				checkSumSize = 32;
				break;	
		}
		
		//gets file path
		String filePath = fileName.getAbsolutePath();
		File file = new File(filePath);
		Scanner scan = new Scanner(file);
		String contents = new String();
	
		while(scan.hasNextLine())
		{
			contents = contents.concat(scan.nextLine());
		}
		
		scan.close();
		
		//adds new line character since 
			//file is only read to but not including newline character
		contents = contents + '\n';
		
		int charCount = contents.length();
		
		//adds 'X' hen appropriate
		while(charCount % (checkSumSize / 8) != 0)
		{
			contents = contents.concat("X");
			charCount = contents.length();
		}
		
		System.out.println();
		
		//prints out only 80 characters per line
		char[] contentsArr = contents.toCharArray();
		for(int i = 0; i < contentsArr.length; i++)
		{
			System.out.print(contentsArr[i]);
			if((i + 1) == 80)
			{
				System.out.println();
			}
		}
		
		//characters per block
		int blockSize = (checkSumSize / 8);
				
		//total amount of all blocks
		int blockCount = (charCount / (checkSumSize / 8));
		
		
		//8 bit algorithm
		if(checkSumSize == 8)
		{
			int[] sumArr = new int[blockCount];
			
			//puts corresponding ascii value into array
			for(int i = 0; i < charCount; i++)
			{
				sumArr[i] = contents.charAt(i);
			}
			
			//finds sum of all ascii values in array
			int sum = 0;
			for(int i = 0; i < sumArr.length; i++)
			{
				sum = sum + sumArr[i];
			}
	
			//changes 'sum' into hexadecimal string
			String hexFinal = Integer.toHexString(sum);
			System.out.println();
			
			int size = hexFinal.length();
			
			//ensures only 2 hexadecimal characters are printed
			while(size > 2)
			{
				hexFinal = hexFinal.replace(hexFinal.charAt(0), ' ');
				hexFinal = hexFinal.stripLeading();
				size--;
				
			}
			
			System.out.println();
			System.out.println(checkSumSize + " bit checkSumSize is\t" + hexFinal + " for all    " + charCount + " chars");
		}
		
		//16 bit algorithm
		if(checkSumSize == 16)
		{
			 
			String[] dblArray = new String[contents.length()];
			char[] characters = contents.toCharArray();
			
			//creates string of two grouped characters
				//and then stores it in separate array
			for(int i = 0; i < contents.length(); i++)
			{
				String temp = Integer.toBinaryString(characters[i]);
				
				while(temp.length() < (checkSumSize / 2))
				{
					temp = "0".concat(temp);
				}
				
				String temp2 = Integer.toBinaryString(characters[i + 1]);
				
				while(temp2.length() < (checkSumSize / 2))
				{
					temp2 = "0".concat(temp2);
				}
		
				dblArray[i] = temp + temp2;
				i++;
			}
			
			//for any null characters changes it to zero 
				//to avoid any addition conflict
			for(int i = 0; i < dblArray.length; i++)
			{
				if(dblArray[i] == null)
				{
					dblArray[i] = "0000000000000000";
				}
			}
			
			//finds sum of values in base 2
			int sum = 0;
			for(int i = 0; i < dblArray.length; i++)
			{
				sum = sum + Integer.parseInt(dblArray[i], 2);
			}
			
			//changes 'sum' into hexadecimal string
			String hexFinal = Integer.toHexString(sum);
			int size = hexFinal.length();
			
			//ensures hex string is only 4 characters
			while(size > 4)
			{
				hexFinal = hexFinal.substring(1);
				size = hexFinal.length();
			}
			
			System.out.println();
			System.out.println(checkSumSize + " bit checkSumSize is\t" + hexFinal + " for all    " + charCount + " chars");
			
		}
		
		//32 bit algorithm
		if(checkSumSize == 32)
		{
			String[] quadArray = new String[contents.length()];
			char[] characters = contents.toCharArray();
			
			//creates individual strings for each character's
			//corresponding binary value and concatenates to one string
			//and sets that to another array
			for(int i = 0; i < contents.length() - 2; i++)
			{
				String temp = Integer.toBinaryString(characters[i]);
				
				while(temp.length() < (checkSumSize / 4))
				{
					temp = "0".concat(temp);
				}
				
				String temp2 = Integer.toBinaryString(characters[i + 1]);
				
				while(temp2.length() < (checkSumSize / blockSize))
				{
					temp2 = "0".concat(temp2);
				}
				
				String temp3 = Integer.toBinaryString(characters[i + 2]);
				
				while(temp3.length() < (checkSumSize / blockSize))
				{
					temp3 = "0".concat(temp3);
				}
				
				String temp4 = Integer.toBinaryString(characters[i + 3]);
				
				while(temp4.length() < (checkSumSize / blockSize))
				{
					temp4 = "0".concat(temp4);
				}
				
				quadArray[i] = temp + temp2 + temp3 + temp4;
				
				i +=3;
				
			}
			System.out.println();
			
			//sets any null elements to 0 to avoid addition conflict
			for(int i = 0; i < quadArray.length; i++)
			{
				if(quadArray[i] == null)
				{
					quadArray[i] = "00000000000000000000000000000000";
				}
			}
			
			//finds corresponding value of binary number and adds to rest
			//of the binary numbers
			int sum = 0;
			for(int i = 0; i < quadArray.length; i++)
			{
				sum = sum + Integer.parseInt(quadArray[i], 2);
			}
			
			//sets sum to corresponding hex string
			//doesn't need to check if correct amount of digits
			//because an 'int' can only handle 8 hex characters
			String hexFinal = Integer.toHexString(sum);
			
			System.out.println();
			System.out.println(checkSumSize + " bit checkSumSize is\t" + hexFinal + " for all    " + charCount + " chars");
			
		}	
	}	
}
