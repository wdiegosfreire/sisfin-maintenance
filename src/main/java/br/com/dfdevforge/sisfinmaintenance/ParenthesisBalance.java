package br.com.dfdevforge.sisfinmaintenance;

import java.util.Scanner;
import java.util.Stack;

public class ParenthesisBalance {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String[] expressionList = new String[5];
		expressionList[0] = scanner.nextLine();
		expressionList[1] = scanner.nextLine();
		expressionList[2] = scanner.nextLine();
		expressionList[3] = scanner.nextLine();
		expressionList[4] = scanner.nextLine();

		for (int x = 0; x < expressionList.length; x++) {
			String expression = expressionList[x];

			Stack<String> op = new Stack<String>();
			Stack<String> cp = new Stack<String>();

			String result = "correct";

			for (int i = 0; i < expression.length(); i++) {
				if (expression.charAt(0) == ')') {
					result = "incorrect";
					break;
				}

				if (expression.charAt(i) == '(')
					op.push(expression.charAt(i) + "");
				else if (expression.charAt(i) == ')')
					cp.push(expression.charAt(i) + "");
			}

			if (op.size() != cp.size())
				result = "incorrect";

			System.out.println(result);
		}

		scanner.close();
	}
}