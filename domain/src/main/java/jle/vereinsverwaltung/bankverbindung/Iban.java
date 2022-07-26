package jle.vereinsverwaltung.bankverbindung;

import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

import jle.exceptions.InvalidIbanException;

public class Iban {
    private final String ibanString;

    public Iban(String ibanString) {
        this.ibanString = validateIban(ibanString);
    }

    private String validateIban(String iban) {
        if (isValid(iban)) {
            return iban;
        } else {
            throw new InvalidIbanException();
        }
    }


    private boolean isValid(String iban) {
        return IBANCheckDigit.IBAN_CHECK_DIGIT.isValid(iban.trim());
    }

    /*
    public boolean isValid(String iban) {
        String checkDigit = iban.substring(4, 22) + ((int) iban.charAt(0) - 55) + ((int) iban.charAt(1) - 55) + "00";
        BigDecimal checkDigitNumber = new BigDecimal(checkDigit);
        checkDigitNumber = new BigDecimal("98").subtract(checkDigitNumber.remainder(new BigDecimal("97")));
        return checkDigitNumber.compareTo(new BigDecimal(iban.substring(2, 4))) == 0;
    }
    */

    @Override
    public int hashCode() {
        return ibanString.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean gleich = false;
        if (obj instanceof Iban) {
            Iban iban = (Iban)obj;
            if (this.getIbanString().trim().equalsIgnoreCase(iban.getIbanString().trim())) {
                gleich = true;
            }
        }
        return gleich;
    }

    @Override
    public String toString() {
        return ibanString;
    }

    public String getIbanString() {
        return ibanString;
    }
}
