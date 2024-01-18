package com.ohmyraid.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class CustomNamingStrategy extends PropertyNamingStrategies.SnakeCaseStrategy {

    @Override
    public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        return convertSnakeCaseToCamelCaseName(defaultName);
    }


    private String convertSnakeCaseToCamelCaseName(String input) {
        StringBuilder stringBuilder = new StringBuilder();

        // We need to know if the previous character was upper case.
        boolean lastCharWasUpper = false;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            // If current character is an upper case letter
            if (Character.isUpperCase(currentChar)) {

                // If it's not the first character AND preceded by a lower case character OR a digit OR another upper case character
                if (i > 0 && ((Character.isLowerCase(input.charAt(i - 1)) || Character.isDigit(input.charAt(i - 1))))) {
                    stringBuilder.append('_');
                }

                // If it's not the first char AND it's preceded by an upper case letter AND the next char (if it exists) is lower case
                if (i > 0 && lastCharWasUpper && (i < input.length() - 1 && Character.isLowerCase(input.charAt(i + 1)))) {
                    stringBuilder.append('_');
                }

                stringBuilder.append(Character.toLowerCase(currentChar));
                lastCharWasUpper = true;
            }
            // If current character is a lower case letter
            else if (Character.isLowerCase(currentChar)) {
                // Append underscore before digit if it's preceeded by a number
                if (i > 0 && Character.isDigit(input.charAt(i - 1))) {
                    stringBuilder.append('_');
                }

                stringBuilder.append(currentChar);
                lastCharWasUpper = false;
            }
            // If current character is a digit
            else if (Character.isDigit(currentChar)) {
                if (i > 0 && (Character.isLetter(input.charAt(i - 1)))) {
                    stringBuilder.append('_');
                }

                stringBuilder.append(currentChar);
                lastCharWasUpper = false;
            }
        }

        return stringBuilder.toString();
    }
}
