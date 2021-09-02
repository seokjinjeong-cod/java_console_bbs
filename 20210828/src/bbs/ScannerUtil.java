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
				System.out.println("���� ������ �ƴմϴ�.");
			}

		}
		return result;
	}

	// �����Է�
	public static int readInt(String prompt) {
		System.out.print(prompt);
		System.out.print(">");
		return readInt();
	}

	// ��¥�Է�
	public static String readDate() {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		while (true) {
			try {
				result = scanner.next();
				sdf.parse(result.trim());
				break;
			} catch (Exception e) {
				System.out.println("��¥ ������ �ƴմϴ�. yyyy.MM.dd");
			}

		}
		return result;
	}

	public static String readDate(String prompt) {
		System.out.print(prompt);
		System.out.print(">");
		return readDate();
	}

	// �������Է�
	public static String readMultiLine() {
		System.out.println("���� : ");
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
