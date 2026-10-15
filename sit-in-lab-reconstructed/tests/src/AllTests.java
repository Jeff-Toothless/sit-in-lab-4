public final class AllTests {
  private AllTests() {}

  public static void main(String[] args) {
    System.out.println("== Test1 ==");
    Test1.run();
    System.out.println("== Test2 ==");
    Test2.run();
    if (TestSupport.failures() == 0) {
      System.out.println("All reconstructed tests passed.");
    } else {
      System.out.println(TestSupport.failures() + " reconstructed test(s) failed.");
      System.exit(1);
    }
  }
}

