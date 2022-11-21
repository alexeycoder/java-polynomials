import java.util.TreeMap;
import java.util.Random;

// Задана натуральная степень k. Сформировать случайным образом список коэффициентов (значения от 0 до 100)
// многочлена и записать в файл многочлен степени k
// k - максимальная степень многочлена, следующий степень следующего на 1 меньше и так до ноля
// Коэффициенты расставляет random, поэтому при коэффициенте 0 просто пропускаем данную итерацию степени

// Пример:
// k=2 -> 2x² + 4x + 5 = 0 или x² + 5 = 0 или 10x² = 0
// k=5 -> 3x⁵ + 5x⁴ - 6x³ - 3x = 0

public class PolynomialGenerator {
	private static final String MULT_SIGN = "\u22c5";
	private static final Random rnd = new Random();
	private static final Translator translator = Translator.ToSuperscriptedDecimalStr;

	private static int getRandomInt(int min, int max) {
		return rnd.nextInt(max - min + 1) + min;
	}

	private static String get_term_str(int degree, int coefficient, String var_name) {
		if (coefficient == 0)
			return "";
		if (degree == 0)
			return Integer.toString(coefficient);

		String degree_str = degree > 1 ? translator.translate(Integer.toString(degree)) : "";

		if (coefficient == 1)
			return var_name + degree_str;
		else if (coefficient == -1)
			return "-" + var_name + degree_str;

		return String.format("%d%s%s%s", coefficient, MULT_SIGN, var_name, degree_str);
	}

	public static String generate(int degree, int coef_min, int coef_max, String var_name) {
		if (degree == 0) {
			return "0 = 0";
		}

		if (coef_min > coef_max) {
			var tmp = coef_max;
			coef_max = coef_min;
			coef_min = tmp;
		}

		var poly_dict = new TreeMap<Integer, Integer>();
		do {
			for (int i = 0; i <= degree; ++i) {
				int coef = getRandomInt(coef_min, coef_max);
				if (coef != 0) {
					poly_dict.put(i, coef);
				}
			}
		} while (poly_dict.size() <= 1 && poly_dict.containsKey(0));

		int topDeg = poly_dict.lastKey();
		StringBuilder sb = new StringBuilder(get_term_str(topDeg, poly_dict.get(topDeg), var_name));

		for (var deg : poly_dict.subMap(0, true, topDeg, false).descendingKeySet()) {
			int coef = poly_dict.get(deg);
			if (coef > 0) {
				sb.append(" + ");
			} else if (coef < 0) {
				sb.append(" - ");
			} else {
				continue;
			}
			sb.append(get_term_str(deg, Math.abs(coef), var_name));
		}

		sb.append(" = 0");

		// for (var termEntry : poly_dict.descendingMap().subMap(0,
		// topTermEntry.getKey()).entrySet()) {
		// int deg = termEntry.getKey();
		// int coef = termEntry.getValue();
		// if (coef > 0) {
		// sb.append(" + ");
		// } else if (coef < 0) {
		// sb.append(" - ");
		// } else {
		// continue;
		// }
		// sb.append(get_term_str(deg, Math.abs(coef), var_name));
		// }

		return sb.toString();
	}
}