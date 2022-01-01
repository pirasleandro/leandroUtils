package com.github.pirasleandro.containers;

import java.awt.Point;

/**
 * @author Leandro Piras
 * @version 1.1
 * 
 * Class extends java.awt.Point for it to add a third value, the slot. Is used to store coordinates of a TriGrid.
 */
public class TriPoint extends Point {
  /**
   * Enum used to determine the slot of a TriPoint
   */
  public Slot slot;

  /**
   * An emtpy constructor.
   * Values will be set to x = 0, y = 0, Slot = Slot.A by default.
   */
  public TriPoint() {
    this(0, 0, Slot.A);
  }

  /**
   * A constructor.
   * 
   * @param x     x coordinate
   * @param y     y coordinate
   * @param slot  slot of the coordinate
   */
  public TriPoint(int x, int y, Slot slot) {
    this.x = x;
    this.y = y;
    this.slot = slot;
  }

  /**
   * A constructor.
   * Slot set to Slot.A by default.
   * 
   * @param x x coordinate
   * @param y y coordinate
   * 
   * @see com.github.pirasleandro.containers.TriPoint#TriPoint(int, int, Slot)
   */
  public TriPoint(int x, int y) {
    this(x, y, Slot.A);
  }

  /**
   * A constructor.
   * 
   * @param triPoint TriPoint
   * 
   * @see com.github.pirasleandro.containers.TriPoint#TriPoint(int, int, Slot)
   */
  public TriPoint(TriPoint triPoint) {
    this(triPoint.x, triPoint.y, triPoint.slot);
  }

  /**
   * A constructor.
   * 
   * @param point Point
   * @param slot  Slot of the Point
   * 
   * @see com.github.pirasleandro.containers.TriPoint#TriPoint(int, int, Slot)
   */
  public TriPoint(Point point, Slot slot) {
    this(point.x, point.y, slot);
  }

  /**
   * A getter method for the Slot of the TriPoint
   * 
   * @return slot
   */
  public Slot getSlot() {
    return slot;
  }
  
  /**
   * An overridden method to get a new TriPoint with the same values as this.
   * 
   * @see Point#getLocation()
   */
  @Override
  public TriPoint getLocation() {
    return new TriPoint(x, y, slot);
  }

  /**
   * A method used to set all values.
   * 
   * @param x     x coordinate
   * @param y     y coordinate
   * @param slot  slot
   */
  public void setLocation(int x, int y, Slot slot) {
    super.setLocation(x, y);
    this.slot = slot;
  }

  /**
   * A method used to set all values using another TriPoint.
   * 
   * @param triPoint  TriPoint whos values this will be set to
   */
  public void setLocation(TriPoint triPoint) {
    setLocation(triPoint.x, triPoint.y, triPoint.slot);
  }

  /**
   * A method used to move the TriPoint.
   * 
   * @param dx    value the x coordinate will be increased with
   * @param dy    value the y coordinate will be increased with
   * @param slot  value the slot will be set to
   */
  public void translate(int dx, int dy, Slot slot) {
    this.x += dx;
    this.y += dy;
    this.slot = slot;
  }

  /**
   * An overridden method used to compare the TriPoint to the given TriPoint.
   * 
   * @param obj the object to be compared
   * 
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof TriPoint) {
      TriPoint pt = (TriPoint)obj;
      return (x == pt.x) && (y == pt.y) && (slot == pt.slot);
    }
    return super.equals(obj);
  }

  /**
   * A method used to compare the TriPoints values with the given values.
   * 
   * @param x     value the x coordinate will be compared to
   * @param y     value the y corodinate will be compared to
   * @param slot  value the slot will be compared to
   * @return      true if they are equal, false if they are not
   * 
   * @see com.github.pirasleandro.containers.TriPoint#equals(Object)
   * @see Point#equals(Object)
   */
  public boolean equals(int x, int y, Slot slot) {
    return (this.x == x) && (this.y == y) && (this.slot == slot);
  }

  /**
   * An overridden method used to get a String representation of the TriPoint
   * The format will be "[x=<x>,y=<y>,s=<slot>]".
   * 
   * @see Object#toString()
   * @see Point#toString()
   */
  @Override
  public String toString() {
    return getClass().getName() + "[x=" + x + ",y=" + y + ",s=" + slot + "]";
  }
}
