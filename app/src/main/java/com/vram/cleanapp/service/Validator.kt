package com.vram.cleanapp.service

import android.util.Patterns
import java.util.regex.Pattern

/**
 * Created by Kalpesh on 23/01/18.
 *
 * This class is used to validate data like email, password policy, etc.
 *
 * https://gist.github.com/KalpeshTalkar/e6a50efd08a3d5d142e47da559936bb7
 */

/**
 * Checks if the email is valid.
 */
fun isValidEmail(str: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(str).matches()
}

/**
 * Checks if the password is valid as per the following password policy.
 * Password should be minimum minimum 8 characters long.
 * Password should contain at least one number.
 * Password should contain at least one capital letter.
 * Password should contain at least one small letter.
 * Password should contain at least one special character.
 * Allowed special characters: "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
 */
fun isValidPassword(str: String): Boolean {
    var valid = true

    // Password policy check
    // Password should be minimum minimum 8 characters long
    if (str.length < 8) {
        valid = false
    }
    // Password should contain at least one number
    var exp = ".*[0-9].*"
    var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
    var matcher = pattern.matcher(str)
    if (!matcher.matches()) {
        valid = false
    }

    // Password should contain at least one capital letter
    exp = ".*[A-Z].*"
    pattern = Pattern.compile(exp)
    matcher = pattern.matcher(str)
    if (!matcher.matches()) {
        valid = false
    }

    // Password should contain at least one small letter
    exp = ".*[a-z].*"
    pattern = Pattern.compile(exp)
    matcher = pattern.matcher(str)
    if (!matcher.matches()) {
        valid = false
    }
    // Password should contain at least one special character
    // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
    exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
    pattern = Pattern.compile(exp)
    matcher = pattern.matcher(str)
    if (!matcher.matches()) {
        valid = false
    }
    return valid
}

