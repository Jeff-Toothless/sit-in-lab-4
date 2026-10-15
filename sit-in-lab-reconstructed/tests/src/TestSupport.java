import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

final class TestSupport {
  private static int failures = 0;

  private TestSupport() {}

  static void check(String description, boolean passed) {
    System.out.printf("%s.. %s%n", description, passed ? "ok" : "failed");
    if (!passed) {
      failures += 1;
    }
  }

  static void checkEquals(String description, Object expected, Object actual) {
    boolean passed = expected == null ? actual == null : expected.equals(actual);
    check(description + " returns " + expected, passed);
    if (!passed) {
      System.out.println("  expected: " + expected);
      System.out.println("  got this: " + actual);
    }
  }

  static int failures() {
    return failures;
  }

  static Class<?> load(String name) {
    try {
      return Class.forName(name);
    } catch (ReflectiveOperationException e) {
      return null;
    }
  }

  static boolean isPublicInstanceMethod(
      Class<?> owner,
      String name,
      Class<?> returnType,
      String genericReturnType,
      String... genericParameterTypes) {
    for (Method method : owner.getDeclaredMethods()) {
      if (!method.getName().equals(name)
          || !Modifier.isPublic(method.getModifiers())
          || Modifier.isStatic(method.getModifiers())
          || !method.getReturnType().equals(returnType)
          || !method.getGenericReturnType().getTypeName().equals(genericReturnType)) {
        continue;
      }
      Type[] actual = method.getGenericParameterTypes();
      if (actual.length != genericParameterTypes.length) {
        continue;
      }
      boolean matches = true;
      for (int i = 0; i < actual.length; i += 1) {
        matches &= actual[i].getTypeName().equals(genericParameterTypes[i]);
      }
      if (matches) {
        return true;
      }
    }
    return false;
  }

  static boolean fieldMatches(
      Class<?> owner, String name, Class<?> erasedType, String genericType) {
    try {
      Field field = owner.getDeclaredField(name);
      int modifiers = field.getModifiers();
      return Modifier.isPrivate(modifiers)
          && Modifier.isFinal(modifiers)
          && !Modifier.isStatic(modifiers)
          && field.getType().equals(erasedType)
          && field.getGenericType().getTypeName().equals(genericType);
    } catch (ReflectiveOperationException e) {
      return false;
    }
  }

  static boolean constructorMatches(
      Class<?> owner, String... genericParameterTypes) {
    for (Constructor<?> constructor : owner.getDeclaredConstructors()) {
      if (!Modifier.isPublic(constructor.getModifiers())) {
        continue;
      }
      Type[] actual = constructor.getGenericParameterTypes();
      if (actual.length != genericParameterTypes.length) {
        continue;
      }
      boolean matches = true;
      for (int i = 0; i < actual.length; i += 1) {
        matches &= actual[i].getTypeName().equals(genericParameterTypes[i]);
      }
      if (matches) {
        return true;
      }
    }
    return false;
  }

  static boolean compiles(String className, String source) {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    if (compiler == null) {
      return false;
    }
    try {
      Path directory = Files.createTempDirectory("entry-compile-test-");
      Path sourceFile = directory.resolve(className + ".java");
      Files.writeString(sourceFile, source, StandardCharsets.UTF_8);
      ByteArrayOutputStream diagnostics = new ByteArrayOutputStream();
      int result = compiler.run(
          null,
          null,
          diagnostics,
          "-classpath",
          System.getProperty("java.class.path"),
          "-d",
          directory.toString(),
          sourceFile.toString());
      if (result != 0) {
        System.out.println("  compiler diagnostics:");
        System.out.print(diagnostics.toString(StandardCharsets.UTF_8));
      }
      return result == 0;
    } catch (Exception e) {
      return false;
    }
  }

  static Object constructEntry(Class<?> entryClass, String id, Object value) throws Exception {
    Constructor<?> constructor = entryClass.getConstructor(String.class, Object.class);
    return constructor.newInstance(id, value);
  }

  @SuppressWarnings("unchecked")
  static List<Object> list(Object... values) {
    return new java.util.ArrayList<>(java.util.Arrays.asList(values));
  }
}

