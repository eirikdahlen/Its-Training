package tdt4140.gr1802.app.core;

public class AnalyzeCoachTest {
	
	//init
	Database db = new Database();
	AnalyzeCoach analyzeCoach = new AnalyzeCoach();
	
	public static void main(String[] args) throws Exception {
		AnalyzeCoachTest test = new AnalyzeCoachTest();
		
		
		//__________
		test.testGetTotalTrainingTime();
		//_______-
		

	}
	
	//tests
	
	public void testGetTotalTrainingTime() throws Exception {
		//tests with coach that already exists in database
		
		//creates coach for testing
		Coach coach1 = db.getCoach("petter22");
		
		//prints total time
		int total = analyzeCoach.getTotalTrainingTime(coach1);
		
		System.out.println("Petter22's athletes' total training time:");
		System.out.println(total);
	}
	

}
