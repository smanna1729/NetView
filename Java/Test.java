public class Test {
	public Test ( String str ) {
		System.out.println ( "Testing !! - " + str ) ;
	}
	public static void main (String[] args) {
		Test t = new Test ( args[0] ) ;
	}
}
