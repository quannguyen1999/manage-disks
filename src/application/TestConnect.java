package application;
import java.io.IOException;
import java.text.DecimalFormat;

import application.controller.impl.TitleImpl;
import application.controller.services.TitleService;
//test CRUD dưới console
public class TestConnect {
	public static void main(String[] args) throws IOException {
//		int e = 14562345; 
//		DecimalFormat df = new DecimalFormat("#,###"); 
//		String Str5 = df.format(e); 
//		System.out.println(Str5); 
		
		TitleService titleService = new TitleImpl();
		
		titleService.listAllTitleByCategoryId("C5679")
			.forEach(t->{
				System.out.println(t.getTitleId());
			});
	}
}
