/*
 *
 * consoleinoutput.exceptions.NumberOutOfRangeException
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

package consoleinoutput.exceptions;

public class NumberOutOfRangeException extends Exception {
    public NumberOutOfRangeException() {
        super("This Number is too big!");
    }
}
