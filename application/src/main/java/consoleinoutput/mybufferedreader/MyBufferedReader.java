/*
 *
 * consoleinoutput.mybufferedreader.MyBufferedReader
 *
 *
 * This document contains trade secret data which is the property of
 * OpenKnowledge GmbH. Information contained herein may not be used,
 * copied or disclosed in whole or part except as permitted by written
 * agreement from open knowledge GmbH.
 *
 * Copyright (C) {YEAR} open knowledge GmbH / Oldenburg / Germany
 *
 */

package consoleinoutput.mybufferedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import consoleinoutput.exceptions.NotaNumberException;
import consoleinoutput.exceptions.NumberOutOfRangeException;
import consoleinoutput.inputoutput.ConsoleColors;

public class MyBufferedReader {

  private static final int ASCIIZERO = 47;

  private static final int ASCIININE = 58;

  private static final String FAULTYENTRY = "Fehlerhafte Eingabe!";

  private static final Logger LOGGER = Logger.getLogger(MyBufferedReader.class.getName());

  private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

  static {
    try {
      Handler handler = new FileHandler("Vereinsverwaltung.txt");
      handler.setLevel(Level.ALL);
      handler.setFormatter(new MyFormatter());
      LOGGER.addHandler(handler);
    } catch (SecurityException e) {
      LOGGER.log(Level.WARNING, "Security Exception");
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "IOException");
    }
  }

  public static void print(String output) {
    System.out.print(output);
    //LOGGER.config(output);
  }

  public static void println(String output) {
    System.out.println(output);
    //LOGGER.info(output + NEWLINE);
  }

  public static void print(String output, String color) {
    System.out.println(color + output + ConsoleColors.RESET);
    //LOGGER.info(color + output + RESET);
  }

  public static void println(String output, String color) {
    System.out.println(color + output + ConsoleColors.RESET);
    //LOGGER.info(color + output + RESET + NEWLINE);
  }

  public static void printError(String output) {
    System.out.println(ConsoleColors.RED + output + ConsoleColors.RESET + "\n");
    //LOGGER.info(output + NEWLINE);
  }

  public static int readInInt(int maximum, String inputRules) throws NumberOutOfRangeException, NotaNumberException {
    String userInput = checkUserInput(inputRules);
    boolean richtigeEingabe = false;
    while (!richtigeEingabe) {
      for (int i = 0; i < userInput.length(); i++) {
        int asciiValue = userInput.charAt(i);
        if (asciiValue > ASCIIZERO && asciiValue < ASCIININE) {
          richtigeEingabe = true;
        } else {
          throw new NotaNumberException();
        }
      }
      if (richtigeEingabe) {
        int number = Integer.parseInt(userInput);
        if (number > maximum || number < 1) {
          throw new NumberOutOfRangeException();
        }
      }
    }
    return Integer.parseInt(userInput);
  }

  public static int readInInt(String inputRules) throws NotaNumberException {
    String userInput = checkUserInput(inputRules);
    if (isNumber(userInput)) {
      return Integer.parseInt(userInput);
    }
    return -1;
  }

  public static int readInInt() throws NotaNumberException {
    String userInput = checkUserInput("");
    if (isNumber(userInput)) {
      return Integer.parseInt(userInput);
    }
    return -1;
  }

  public static int readInInt(int maximum) throws NotaNumberException, NumberOutOfRangeException {
    String userInput = checkUserInput("");
    boolean richtigeEingabe = isNumber(userInput);
    if (richtigeEingabe) {
      int number = Integer.parseInt(userInput);
      if (number > maximum || number < 1) {
        throw new NumberOutOfRangeException();
      }
    }
    return Integer.parseInt(userInput);
  }

  public static int forceReadInInt() {
    int eingabe = 0;
    boolean ok = false;
    while (!ok) {
      try {
        eingabe = MyBufferedReader.readInInt();
        ok = true;
      } catch (NotaNumberException e) {
        MyBufferedReader.printError("Die eingabe war keine Zahl!");
      }
    }
    return eingabe;
  }

  public static String readInString(String inputRules) {
    return checkUserInput(inputRules);
  }

  public static String readInString() {
    return checkUserInput("");
  }

  public static char readInChar(String inputRules) {
    String userInput = checkUserInput(inputRules);
    while (userInput.length() != 1) {
      printError("Bitte gib genau ein Zeichen ein!");
    }
    return userInput.charAt(0);
  }

  private static boolean isNumber(String userInput) throws NotaNumberException {
    boolean richtigeEingabe = false;
    while (!richtigeEingabe) {
      for (int i = 0; i < userInput.length(); i++) {
        int asciiValue = userInput.charAt(i);
        if (asciiValue > ASCIIZERO && asciiValue < ASCIININE) {
          richtigeEingabe = true;
        } else {
          throw new NotaNumberException();
        }
      }
    }
    return richtigeEingabe;
  }

  private static String checkUserInput(String inputRules) {
    if (!inputRules.equals("")) {
      println(inputRules);
    }
    String userInput = "";
    boolean ok = false;
    while (!ok) {
      try {
        userInput = BUFFERED_READER.readLine();
        ok = true;
      } catch (IOException e) {
        printError(FAULTYENTRY);
      }
    }
    return userInput;
  }

  public static void printInvalidInput() {
    printError(FAULTYENTRY);
  }

}
