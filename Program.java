public class Program {
	public static void main(String[] args) {
		for (int i = 0; i < 10; ++i) {
			String poly_str = PolynomialGenerator.generate(i, -100, 100, "x");
			System.out.println(poly_str);
		}
	}
}
