package br.com.dfdevforge.sisfinmaintenance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Diving {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int diversNumber = Integer.parseInt(scanner.nextLine());

		String[] diverName = new String[diversNumber];
		double[] diverDifficulty = new double[diversNumber];
		String[] diverScoreList = new String[diversNumber];

		Map<String, List<Double>> scoreMap = new HashMap<>();

		for (int i = 0; i < diversNumber; i++) {
			diverName[i] = scanner.nextLine();
			diverDifficulty[i] = Double.parseDouble(scanner.nextLine());
			diverScoreList[i] = scanner.nextLine();

			String[] diverScoreSpplited = diverScoreList[i].split(" ");
			
			List<Double> scoreList = new ArrayList<>();
			for (int j = 0; j < diverScoreSpplited.length; j++) {
				scoreList.add(Double.parseDouble(diverScoreSpplited[j]));
			}

			scoreMap.put(i + "", scoreList);
		}

		for (int i = 0; i < diversNumber; i++) {
			List<Double> scoreList = scoreMap.get(i + "");

			Double maior = 0.0;
			for (Double score : scoreList) {
				if (score > maior)
					maior = score;
			}

			Double menor = maior;
			for (Double score : scoreList) {
				if (score < menor)
					menor = score;
			}

			System.out.println(maior);
			System.out.println(menor);

			Double d = 0.0;
			for (Double score : scoreList) {
				if (score != menor && score != maior)
					d += score;
			}

			DecimalFormat format = new DecimalFormat("##.00");
			format.format(diverDifficulty[i] * d);

			System.out.println(diverName[i] + " " + format.format(diverDifficulty[i] * d));
		}

		scanner.close();
	}
}