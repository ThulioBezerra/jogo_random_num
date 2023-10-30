import java.util.Scanner;

public class Catch {
    public static Scanner leitor;

    public Catch() {
        leitor = new Scanner(System.in);
    }

    public int catch_int() {
        String digitado = "";
        digitado = leitor.nextLine();
        return Integer.parseInt(digitado);
    }

    public float catch_float() {
        String digitado = "";
        digitado = leitor.nextLine();
        return Float.parseFloat(digitado);
    }

    public double catch_double() {
        String digitado = "";
        digitado = leitor.nextLine();
        return Double.parseDouble(digitado);
    }

    public String catch_String() {
        String digitado = "";
        digitado = leitor.nextLine();
        return digitado;
    }

    public char catch_char() {
        String digitado = "";
        digitado = leitor.nextLine();
        return digitado.charAt(0);
    }
    public void close(){
        leitor.close();
    }

}
