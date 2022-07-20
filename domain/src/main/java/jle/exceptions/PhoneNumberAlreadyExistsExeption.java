package jle.exceptions;

public class PhoneNumberAlreadyExistsExeption extends RuntimeException{
  public PhoneNumberAlreadyExistsExeption(){
    super("Phone number already exists for this member!");
  }
}
