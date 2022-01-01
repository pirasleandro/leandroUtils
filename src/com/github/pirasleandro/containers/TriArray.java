package com.github.pirasleandro.containers;

/**
 * Generic class used to store values in a triangle-based array.
 * @author Leandro Piras
 * @version 1.0
 * @param T datatype of the TriArray
 */
public class TriArray<T> {
  // size of the TriArray
  private final int size;
  // array all the values will be stored in
  private Object[][] array;

  /**
   * An empty constructor to prevent empty TriArrays;
   */
  @SuppressWarnings("unused")
  private TriArray() {
    size = 0;
  }

  /**
   * A consructor.
   * 
   * @param size  size of the TriArray
   * 
   * @throws IllegalArgumentException if the size is smaller than 1
   */
  public TriArray(int size) {
    if (size < 1) {
      throw new IllegalArgumentException("cannot invoke TriArray of size " + size + ". Must be minimum 1.");
    }
    this.size = size;
    array = new Object[(size*2)][size];
  }

  /**
   * A constructor that additionally sets all cells to the given default value.
   * 
   * @param size          size of the TriArray
   * @param defaultValue  value that all cells will be set to
   * 
   * @see com.github.pirasleandro.containers.TriArray#fill(Object)
   */
  public TriArray(int size, T defaultValue) {
    this(size);
    fill(defaultValue);
  }

  /**
   * A method used to set all cells of the TriGrid to the given default value.
   * 
   * @param defaultValue  value that all cells will be set to
   */
  public void fill(T defaultValue) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size-x; y++) {
        set(x, y, Slot.A, defaultValue);
        if (x != size-y) {
          set(x, y, Slot.B, defaultValue);
        }
      }
    }
  }

  public Object get(int x, int y, Slot slot) {
    return array[convertX(x, slot)][y];
  }

  public void set(int x, int y, Slot slot, T value) {
    array[convertX(x, slot)][y] = value;
  }

  public static int convertX(int x, Slot slot) {
    return (slot.isA() ? (x*2) : (x*2)+1);
  }

  /**
   * Returns the TriArray as a regular array.
   * 
   * @return  Object[][] {@link #array}
   */
  public Object[][] toArray() {
    return array;
  }
}
