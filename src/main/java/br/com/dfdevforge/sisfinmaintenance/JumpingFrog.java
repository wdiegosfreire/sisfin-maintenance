package br.com.dfdevforge.sisfinmaintenance;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JumpingFrog {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String line = scanner.nextLine();

		int jumpHeight = Integer.parseInt(line.split(" ")[0]);

		line = scanner.nextLine();

		String[] pipeString = line.split(" ");

		List<Integer> pipeList = new ArrayList<>();
		for (int i = 0; i < pipeString.length; i++) {
			pipeList.add(Integer.parseInt(pipeString[i]));
		}

		boolean youWin = Boolean.TRUE;

		int actualPipe = 0;
		int nextPipe = 0;
		for (int j = 0; j < pipeList.size(); j++) {
			if (j == pipeList.size() - 1)
				break;
			
			actualPipe = pipeList.get(j);
			nextPipe = pipeList.get(j + 1);

			int jump = 0;
			if (actualPipe < nextPipe)
				jump = nextPipe - actualPipe;
			else if (actualPipe > nextPipe)
				jump = actualPipe - nextPipe;

			if (jump > jumpHeight) {
				youWin = Boolean.FALSE;
				break;
			}
		}

		System.out.println(youWin ? "YOU WIN" : "GAEM OVER");

		scanner.close();
	}
}