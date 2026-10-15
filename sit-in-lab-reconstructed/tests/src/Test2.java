import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class Test2 {
  private Test2() {}

  static void run() {
    Class<?> entry = TestSupport.load("library.Entry");
    TestSupport.check("Entry class should exist", entry != null);
    if (entry == null) {
      return;
    }
    Class<?> holder = TestSupport.load("library.Entry$Holder");
    TestSupport.check("nested Holder class should exist", holder != null);
    if (holder == null) {
      return;
    }

    TestSupport.check(
        "library.Entry$Holder correctly declares the field entry",
        TestSupport.fieldMatches(holder, "entry", entry, "library.Entry<S>"));
    TestSupport.check(
        "library.Entry$Holder correctly declares its constructor",
        TestSupport.constructorMatches(holder, "library.Entry<S>"));
    TestSupport.check(
        "library.Entry$Holder correctly declares the method getEntry",
        TestSupport.isPublicInstanceMethod(
            holder, "getEntry", entry, "library.Entry<S>"));
    TestSupport.check(
        "library.Entry$Holder correctly declares the method hasSameValue",
        TestSupport.isPublicInstanceMethod(
            holder,
            "hasSameValue",
            boolean.class,
            "boolean",
            "library.Entry<? extends S>"));
    TestSupport.check(
        "library.Entry$Holder correctly declares the method storeIn",
        TestSupport.isPublicInstanceMethod(
            holder, "storeIn", void.class, "void", "java.util.List<? super S>"));
    TestSupport.check(
        "library.Entry$Holder correctly declares the method toString",
        TestSupport.isPublicInstanceMethod(
            holder, "toString", String.class, "java.lang.String"));

    boolean staticNested = Modifier.isPublic(holder.getModifiers())
        && Modifier.isStatic(holder.getModifiers())
        && holder.getTypeParameters().length == 1
        && holder.getTypeParameters()[0].getName().equals("S");
    TestSupport.check(
        "Holder should be a static nested class that can be created without an outer instance",
        staticNested);

    String holderProbe = String.join("\n",
        "import java.util.ArrayList;",
        "import library.Entry;",
        "class HolderVarianceProbe {",
        "  void test() {",
        "    Entry<Number> number = new Entry<>(\"A\", 10);",
        "    Entry.Holder<Number> holder = new Entry.Holder<>(number);",
        "    boolean same = holder.hasSameValue(new Entry<Integer>(\"B\", 10));",
        "    ArrayList<Object> destination = new ArrayList<>();",
        "    holder.storeIn(destination);",
        "    Entry<Number> stored = holder.getEntry();",
        "  }",
        "}");
    TestSupport.check(
        "hasSameValue() should accept Entry<Integer> when holder stores Entry<Number>",
        TestSupport.compiles("HolderVarianceProbe", holderProbe));

    try {
      Object stored = TestSupport.constructEntry(entry, "A", 10);
      Object same = TestSupport.constructEntry(entry, "B", 10);
      Constructor<?> constructor = holder.getConstructor(entry);
      Object instance = constructor.newInstance(stored);
      Method getEntry = holder.getMethod("getEntry");
      Method hasSameValue = holder.getMethod("hasSameValue", entry);
      Method storeIn = holder.getMethod("storeIn", List.class);

      Object returned = getEntry.invoke(instance);
      TestSupport.check(
          "getEntry() should return the exact stored entry returns A: 10",
          returned == stored);
      TestSupport.checkEquals(
          "hasSameValue() should allow subtype values", true, hasSameValue.invoke(instance, same));
      Object different = TestSupport.constructEntry(entry, "C", 20);
      TestSupport.checkEquals(
          "hasSameValue() should reject a different value",
          false,
          hasSameValue.invoke(instance, different));

      List<Object> destination = new ArrayList<>();
      storeIn.invoke(instance, destination);
      TestSupport.checkEquals(
          "storeIn() should append the held value", "[10]", destination.toString());

      List<Object> nonEmptyDestination = new ArrayList<>();
      nonEmptyDestination.add("existing");
      storeIn.invoke(instance, nonEmptyDestination);
      TestSupport.checkEquals(
          "storeIn() should append after existing elements",
          "[existing, 10]",
          nonEmptyDestination.toString());

      TestSupport.checkEquals(
          "toString() should return the stored entry", "A: 10", instance.toString());
    } catch (Exception e) {
      TestSupport.check("Question 2 behavior could be executed", false);
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

