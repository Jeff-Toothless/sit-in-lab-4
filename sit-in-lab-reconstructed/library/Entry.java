package library;

import java.util.ArrayList;
import java.util.List;

/**
 * Reconstructed starter code.
 *
 * Complete the method bodies without changing the required declarations.
 */
public class Entry<T> {
  private final String isbnNumber;
  private final T value;

  public Entry(String isbnNumber, T value) {
    this.isbnNumber = isbnNumber;
    this.value = value;
  }

  public T get() {
    return null;
  }

  public boolean sameValueAs(Entry<? extends T> other) {
    return false;
  }

  public void storeIn(List<? super T> list) {
    // TODO
  }

  public Entry<List<T>> pairWith(T other) {
    return null;
  }

  @Override
  public String toString() {
    return "";
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  public static class Holder<S> {
    private final Entry<S> entry;

    public Holder(Entry<S> entry) {
      this.entry = entry;
    }

    public Entry<S> getEntry() {
      return null;
    }

    public boolean hasSameValue(Entry<? extends S> other) {
      return false;
    }

    public void storeIn(List<? super S> list) {
      // TODO
    }

    @Override
    public String toString() {
      return "";
    }
  }
}

