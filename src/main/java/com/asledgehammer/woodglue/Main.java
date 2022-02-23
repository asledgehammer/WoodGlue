package com.asledgehammer.woodglue;

import java.lang.reflect.InvocationTargetException;

public class Main {
  public static void main(String[] args) {
    try {
      Class.forName("zombie.network.GameServer")
          .getDeclaredMethod("main", String[].class)
          .invoke(null, new Object[] {args});
    } catch (IllegalAccessException
        | InvocationTargetException
        | NoSuchMethodException
        | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
