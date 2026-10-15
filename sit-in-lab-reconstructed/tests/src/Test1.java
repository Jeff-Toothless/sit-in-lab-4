import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class Test1 {
  private Test1() {}

  static void run() {
    Class<?> entry = TestSupport.load("library.Entry");
    TestSupport.check("Entry class should exist", entry != null);
    if (entry == null) {
      return;
    }

    TestSupport.check(
        "library.Entry is a generic class",
        entry.getTypeParameters().length == 1
            && entry.getTypeParameters()[0].getName().equals("T"));
    TestSupport.check(
        "library.Entry is correctly declared",
        Modifier.isPublic(entry.getModifiers())
            && !Modifier.isAbstract(entry.getModifiers())
            && !Modifier.isFinal(entry.getModifiers())
            && !entry.isInterface());
    TestSupport.check(
        "library.Entry correctly declares the field isbnNumber",
        TestSupport.fieldMatches(entry, "isbnNumber", String.class, "java.lang.String"));
    TestSupport.check(
        "library.Entry correctly declares the field value",
        TestSupport.fieldMatches(entry, "value", Object.class, "T"));
    TestSupport.check(
        "library.Entry correctly declares its constructor",
        TestSupport.constructorMatches(entry, "java.lang.String", "T"));
    TestSupport.check(
        "library.Entry correctly declares the method get",
        TestSupport.isPublicInstanceMethod(entry, "get", Object.class, "T"));
    TestSupport.check(
        "library.Entry correctly declares the method sameValueAs",
        TestSupport.isPublicInstanceMethod(
            entry,
            "sameValueAs",
            boolean.class,
            "boolean",
            "library.Entry<? extends T>"));
    TestSupport.check(
        "library.Entry correctly declares the method storeIn",
        TestSupport.isPublicInstanceMethod(
            entry, "storeIn", void.class, "void", "java.util.List<? super T>"));
    TestSupport.check(
        "library.Entry correctly declares the method pairWith",
        TestSupport.isPublicInstanceMethod(
            entry,
            "pairWith",
            entry,
            "library.Entry<java.util.List<T>>",
            "T"));
    TestSupport.check(
        "library.Entry correctly declares the method toString",
        TestSupport.isPublicInstanceMethod(
            entry, "toString", String.class, "java.lang.String"));
    TestSupport.check(
        "library.Entry correctly declares the method equals",
        TestSupport.isPublicInstanceMethod(
            entry, "equals", boolean.class, "boolean", "java.lang.Object"));

    String varianceProbe = String.join("\n",
        "import java.util.ArrayList;",
        "import java.util.List;",
        "import library.Entry;",
        "class EntryVarianceProbe {",
        "  void test() {",
        "    Entry<Number> number = new Entry<>(\"A\", 120);",
        "    Entry<Integer> integer = new Entry<>(\"B\", 120);",
        "    boolean same = number.sameValueAs(integer);",
        "    ArrayList<Object> destination = new ArrayList<>();",
        "    number.storeIn(destination);",
        "    Entry<List<Number>> pair = number.pairWith(200);",
        "  }",
        "}");
    boolean varianceCompiles = TestSupport.compiles("EntryVarianceProbe", varianceProbe);
    TestSupport.check(
        "sameValueAs() should accept Entry<Integer> when receiver is Entry<Number>",
        varianceCompiles);
    TestSupport.check(
        "storeIn() should accept ArrayList<Object> when receiver is Entry<Number>",
        varianceCompiles);
    TestSupport.check(
        "pairWith() should return nested generic Entry<List<T>>",
        varianceCompiles);

    try {
      Object first = TestSupport.constructEntry(entry, "A", 120);
      Object sameValue = TestSupport.constructEntry(entry, "B", 120);
      Object differentValue = TestSupport.constructEntry(entry, "C", 121);
      Object sameId = TestSupport.constructEntry(entry, "A", "different value");
      Object differentId = TestSupport.constructEntry(entry, "D", 120);

      Method get = entry.getMethod("get");
      Method toString = entry.getMethod("toString");
      Method sameValueAs = entry.getMethod("sameValueAs", entry);
      Method storeIn = entry.getMethod("storeIn", List.class);
      Method pairWith = entry.getMethod("pairWith", Object.class);
      Method equals = entry.getMethod("equals", Object.class);

      TestSupport.checkEquals("get() on first Entry", 120, get.invoke(first));
      TestSupport.checkEquals("toString() on first Entry", "A: 120", toString.invoke(first));
      TestSupport.checkEquals(
          "sameValueAs() should allow subtype value",
          true,
          sameValueAs.invoke(first, sameValue));
      TestSupport.checkEquals(
          "sameValueAs() should reject different value",
          false,
          sameValueAs.invoke(first, differentValue));

      List<Object> destination = new ArrayList<>();
      storeIn.invoke(first, destination);
      TestSupport.checkEquals(
          "storeIn() should append to destination list", "[120]", destination.toString());

      List<Object> nonEmptyDestination = new ArrayList<>();
      nonEmptyDestination.add("existing");
      storeIn.invoke(first, nonEmptyDestination);
      TestSupport.checkEquals(
          "storeIn() should append after existing elements",
          "[existing, 120]",
          nonEmptyDestination.toString());

      Object pair = pairWith.invoke(first, 200);
      TestSupport.checkEquals(
          "pairWith() should produce a nested generic value", "A: [120, 200]", pair.toString());
      TestSupport.check(
          "pairWith() should return a new Entry",
          pair != first && entry.isInstance(pair));
      TestSupport.checkEquals(
          "pairWith() should preserve value order",
          TestSupport.list(120, 200),
          get.invoke(pair));

      TestSupport.checkEquals(
          "equals() should compare only the id field", true, equals.invoke(first, sameId));
      TestSupport.checkEquals(
          "equals() should reject a different id", false, equals.invoke(first, differentId));
      TestSupport.checkEquals(
          "equals() should accept the same object", true, equals.invoke(first, first));
      TestSupport.checkEquals(
          "equals() should reject a non-Entry object", false, equals.invoke(first, "A"));
      TestSupport.checkEquals(
          "equals() should reject null", false, equals.invoke(first, new Object[] {null}));
    } catch (Exception e) {
      TestSupport.check("Question 1 behavior could be executed", false);
      System.out.println("  with exception: " + e);
    }
  }

  public static void main(String[] args) {
    run();
    if (TestSupport.failures() > 0) {
      System.exit(1);
    }
  }
}

