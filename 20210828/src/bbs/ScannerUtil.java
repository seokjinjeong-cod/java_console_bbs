package bbs;

import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ScannerUtil {

	static Scanner scanner = new Scanner(System.in);

	public static int readInt() {
		int result = 0;
		while (true) {
			try {
				String temp = scanner.next();
				result = Integer.parseInt(temp);
				break;
			} catch (Exception e) {
				System.out.println("숫자 형식이 아닙니다.");
			}

		}
		return result;
	}

	// 숫자입력
	public static int readInt(String prompt) {
		System.out.print(prompt);
		System.out.print(">");
		return readInt();
	}

	// 날짜입력
	public static String readDate() {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		while (true) {
			try {
				result = scanner.next();
				sdf.parse(result.trim());
				break;
			} catch (Exception e) {
				System.out.println("날짜 형식이 아닙니다. yyyy.MM.dd");
			}

		}
		return result;
	}

	public static String readDate(String prompt) {
		System.out.print(prompt);
		System.out.print(">");
		return readDate();
	}

	// 여러줄입력
	public static String readMultiLine() {
		System.out.println("내용 : ");
		StringBuffer result = new StringBuffer();
		try {
			String a = "";
			while (true) {
				a = scanner.next();
				if (a == null || a.trim().equals(".end")) {
					break;
				}
				result.append(a).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

}
