package application;
import java.io.IOException;
import java.text.DecimalFormat;
//test CRUD dưới console
public class TestConnect {
	public static void main(String[] args) throws IOException {
		int e = 14562345; 
		DecimalFormat df = new DecimalFormat("#,###"); 
		String Str5 = df.format(e); 
		System.out.println(Str5); 
	}
}
