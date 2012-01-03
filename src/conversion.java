import java.util.ArrayList;

public class conversion {

	private String convertedBinary = "";
	private String[] ASCIICharacters = { "NUL (NUll)",
			"SOH (The start of a heading)", "STX (Start of text)",
			"ETX(End of text)", "EOT(End of transmision)", "ENQ (Enquiry)",
			"ACK (Acknolodgement)", "BEL (Bell)", "BS (Back space)",
			"HT (Horizontal tab)", "LF (Line field)", "VT (Vertical tab)",
			"FF (Form field)", "CR Carriage return", "SO Shift out",
			"SI (Shift in)", "DLE (Data Line Escape)",
			"DC1 (Device control 1)", "DC2 (Device control 2)",
			"DC3 (Device control 3)", "DC4 (Device control 4)",
			"NAK (Negative acknowledgement)", "SYN (Synchronous Idle)",
			"ETB (End of Transmit Block)", "CAN (Cancel)",
			"EM (End of Medium)", "SUB (Substitute)", "ESC (Escape)",
			"FS (File Separator)", "GS (File Separator)",
			"RS (Record Separator)", "US (Unit Separator)", " ", "!", "\"",
			"#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/",
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ":", ";", "<",
			"=", ">", "?", "@", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "]", "\\", "]", "^", "_", "`", "a", "b", "c",
			"d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
			"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "{", "|", "}",
			"~", "(DELETE)" };
	private ArrayList<Integer> decNum = new ArrayList<Integer>();
	private boolean error = false;
	private String returnString;
	private String[] enteredTextArray;
	private int[] decimal;
	private String convertedASCII = "";
	private static boolean spaces = false;

	private void convertToBinary(char[] charTextArray) {

		for (char ch : charTextArray) {
			String stringCh = String.valueOf(ch);

			for (int i = 0; i <= ASCIICharacters.length; i++) {

				if (stringCh.equals(ASCIICharacters[i])) {
					decNum.add(i);
					break;
				}
			}
		}
		for (int i = 0; i < decNum.size(); i++) {
			String str = decNum.get(i).toString();
			int inte = Integer.parseInt(str);
			String binaryStr = Integer.toBinaryString(inte);
			switch (binaryStr.length()) {
			case 1:
				binaryStr = "0000000" + binaryStr;
				break;
			case 2:
				binaryStr = "000000" + binaryStr;
				break;
			case 3:
				binaryStr = "00000" + binaryStr;
				break;
			case 4:
				binaryStr = "0000" + binaryStr;
				break;
			case 5:
				binaryStr = "000" + binaryStr;
				break;
			case 6:
				binaryStr = "00" + binaryStr;
				break;
			case 7:
				binaryStr = "0" + binaryStr;
				break;
			}
			if (getSpaces()) {
				if (convertedBinary.equals("")) {
					convertedBinary = convertedBinary + binaryStr;

				} else {
					convertedBinary = convertedBinary + " " + binaryStr;
				}
			} else {
				convertedBinary = convertedBinary + binaryStr;
			}
		}
		returnString = convertedBinary;

	}

	public void stringProcess(String enteredText, int action) {
		boolean ASCII = true;
		enteredText = enteredText.trim();

		if (enteredText.isEmpty() == false) {
			char[] enteredCharArray = enteredText.toCharArray();
			if (action == 1) {
				convertToBinary(enteredCharArray);

			} else {

				for (char ch : enteredCharArray) {
					String stringCh = String.valueOf(ch);
					if (stringCh.equals("1") == false
							&& stringCh.equals("0") == false
							&& stringCh.equals(" ") == false) {
						if (action == 2) {
							returnString = "Only \" \",\"0\",&\"1\" allowed";
							error = true;
							ASCII = false;
							break;
						} else {
							convertToBinary(enteredCharArray);
							ASCII = false;
							break;
						}

					}

				}
				if (ASCII == true) {

					String replacedText = enteredText.replace(" ", "");
					char[] replacedCharArray = replacedText.toCharArray();
					if (replacedText.length() % 8 != 0) {
						if (action == 3) {
							convertToBinary(enteredCharArray);
							error = true;
						} else {
							returnString = "Incorrect Binary";
							error = true;

						}
					} else {
						int i = 0;
						enteredTextArray = new String[replacedCharArray.length / 8];
						for (int c = 0; c < enteredTextArray.length; c++) {
							enteredTextArray[c] = "";
						}
						for (char ch : replacedCharArray) {
							String stringCh = String.valueOf(ch);
							if (enteredTextArray[i].length() != 8) {
								enteredTextArray[i] = enteredTextArray[i]
										+ stringCh;
							} else {
								i++;
								enteredTextArray[i] = enteredTextArray[i]
										+ stringCh;

							}

						}
					}
					if (error == false) {
						convertToASCII(enteredTextArray);
					}
				}

			}

		} else {

			returnString = "NO ENTERED TEXT";

		}

	}

	public String getReturn() {
		return returnString;
	}

	private void convertToASCII(String[] binaryArray) {
		decimal = new int[binaryArray.length];
		int decimalIndex = 0;
		for (String x : binaryArray) {
			decimal[decimalIndex] = 0;
			char[] enteredCharArray = x.toCharArray();
			for (int i = 0; i != enteredCharArray.length; i++) {
				int integer = charToInt(enteredCharArray[i]);
				switch (i) {
				case 0:
					decimal[decimalIndex] = decimal[decimalIndex]
							+ (integer * 128);
					break;
				case 1:
					decimal[decimalIndex] = decimal[decimalIndex]
							+ (integer * 64);
					break;
				case 2:
					decimal[decimalIndex] = decimal[decimalIndex]
							+ (integer * 32);
					break;
				case 3:
					decimal[decimalIndex] = decimal[decimalIndex]
							+ (integer * 16);
					break;
				case 4:
					decimal[decimalIndex] = decimal[decimalIndex]
							+ (integer * 8);
					break;
				case 5:
					decimal[decimalIndex] = decimal[decimalIndex]
							+ (integer * 4);
					break;
				case 6:
					decimal[decimalIndex] = decimal[decimalIndex]
							+ (integer * 2);
					break;
				case 7:
					decimal[decimalIndex] = decimal[decimalIndex] + integer;
					break;
				}
			}
			decimalIndex++;
		}
		for (int i : decimal) {
			convertedASCII = convertedASCII + ASCIICharacters[i];
		}
		returnString = convertedASCII;
	}

	public static boolean getSpaces() {
		return spaces;
	}

	public static void setSpaces(boolean spaces) {
		conversion.spaces = spaces;
	}

	private int charToInt(char c) {
		String s = String.valueOf(c);
		int i = Integer.parseInt(s);
		return i;
	}
}
