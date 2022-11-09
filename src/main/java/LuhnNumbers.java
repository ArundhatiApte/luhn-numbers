package luhnNumbers;

import java.util.Random;

import static luhnNumbers.ConversionOfDigit.extractDigitAsIntFromChar;
import static luhnNumbers.ConversionOfDigit.extractDigitAsCharFromInt;


public class LuhnNumbers {
  public static boolean isNumberValid(CharSequence number) {
    int length = number.length();
    if (length < 1) {
      throw new IllegalArgumentException("Length of number < 1");
    }
    return _isNumberValidUnsafe(number, 0, length - 1);
  }

  public static boolean isNumberValid(CharSequence charSequence, int startIndex, int lastIndex) {
    if (startIndex < 0) {
      throw new IndexOutOfBoundsException("Start index < 0");
    }
    int safeLastIndex = charSequence.length() - 1;
    if (lastIndex > safeLastIndex) {
      throw new IndexOutOfBoundsException("Last index out of bound");
    }
    if (startIndex > lastIndex) {
      throw new IndexOutOfBoundsException("Start index > last index");
    }
    return _isNumberValidUnsafe(charSequence, startIndex, lastIndex);
  }

  private static boolean _isNumberValidUnsafe(CharSequence charSequence, int startIndex, int lastIndex) {
    int totalSum = 0;
    int number = 0;
    boolean isSecond = false;
    int lastIndexPlus1 = lastIndex + 1;

    for (int i = lastIndex; i >= startIndex; i -= 1) {
      number = extractDigitAsIntFromChar(charSequence.charAt(i));
      if (isSecond) {
        number = _multipDigitX2AndGetSumOfDigits(number);
      }
      totalSum += number;
      isSecond  = !isSecond;
    }
    return (totalSum % 10) == 0;
  }

  private static int _multipDigitX2AndGetSumOfDigits(int digit) {
    digit *= 2;
    return digit > 9 ? (digit - 9) : digit;
  }


  public static char[] createNumber(int length, Random generator) {
    if (length < 1) {
      throw new IllegalArgumentException("Length of number < 1");
    }
    char[] buffer = new char[length];
    _fillFullCharBufferWithGeneratedNumber(buffer, generator);
    return buffer;
  }

  private static void _fillFullCharBufferWithGeneratedNumber(char[] buffer, Random generator) {
    int sumWithoutLastDigit = 0;
    int number = 0;
    int lastIndex = buffer.length - 1;
    boolean isSecond = true;

    for (int i = lastIndex - 1; i >= 0; i -= 1) {
      number = _getRandomDigitAsInt(generator);
      buffer[i] = ConversionOfDigit.extractDigitAsCharFromInt(number);

      if (isSecond) {
        number = _multipDigitX2AndGetSumOfDigits(number);
      }
      sumWithoutLastDigit += number;
      isSecond = !isSecond;
    }
    int lastNumber = _calcSummandToNext10(sumWithoutLastDigit);
    buffer[lastIndex] = extractDigitAsCharFromInt(lastNumber);
  }

  private static int _getRandomDigitAsInt(Random generator) {
    int number = generator.nextInt() % 10;
    return number < 0 ? -number : number;
  }

  private static int _calcSummandToNext10(int number) {
    return (number * 9) % 10;
  }


  public static char[] createNumberWithPrefix(CharSequence prefix, int lengthOfTail, Random generator) {
    if (lengthOfTail < 1) {
      throw new IllegalArgumentException("Length of tail < 1");
    }
    char[] buffer = new char[prefix.length() + lengthOfTail];
    _fillFullCharBufferWithPrefixWithGeneratedNumberUnsafe(prefix, buffer, generator);
    return buffer;
  }

  private static void _fillFullCharBufferWithPrefixWithGeneratedNumberUnsafe(
    CharSequence prefix,
    char[] buffer,
    Random generator
  ) {
    int totalLengthOfNumber = buffer.length;
    int endIndexOfPrefix = prefix.length() - 1;
    int sumWithoutLastDigit = 0;
    int number = 0;
    boolean isSecond = true;

    for (int i = totalLengthOfNumber - 2; i > endIndexOfPrefix; i -= 1) {
      number = _getRandomDigitAsInt(generator);
      buffer[i] = extractDigitAsCharFromInt(number);

      if (isSecond) {
        number = _multipDigitX2AndGetSumOfDigits(number);
      }
      sumWithoutLastDigit += number;
      isSecond = !isSecond;
    }
    for (int i = endIndexOfPrefix; i >= 0; i -= 1) {
      char charFromPrefix = prefix.charAt(i);
      buffer[i] = charFromPrefix;
      number = extractDigitAsIntFromChar(charFromPrefix);

      if (isSecond) {
        number = _multipDigitX2AndGetSumOfDigits(number);
      }
      sumWithoutLastDigit += number;
      isSecond = !isSecond;
    }
    int lastNumber = _calcSummandToNext10(sumWithoutLastDigit);
    int lastIndex = totalLengthOfNumber - 1;
    buffer[lastIndex] = extractDigitAsCharFromInt(lastNumber);
  }

  public static char[] createNumberByTemplate(CharSequence template, Random generator) {
    int length = template.length();
    if (length < 1) {
      throw new IllegalArgumentException("Length of template < 1");
    }
    char[] buffer = new char[length];
    _fillFullCharBufferByTemplate(template, buffer, generator);
    return buffer;
  }

  public static void _fillFullCharBufferByTemplate(CharSequence template, char[] buffer, Random generator) {
    int currentSum = 0;
    boolean isSecond = false;
    int indexOfLastGeneratedNumber = -1;
    int lastGeneratedNumber = 0;
    int lastAddedNumberFromGenerated = 0;
    boolean wasLastGeneratedNumberSecond = false;

    for (int i = buffer.length - 1; i >= 0; i -= 1) {
      char charFromPattern = template.charAt(i);
      int addedToSum = 0;

      if (charFromPattern == _placeholderForNumber) {
        indexOfLastGeneratedNumber = i;
        lastGeneratedNumber = _getRandomDigitAsInt(generator);
        buffer[i] = extractDigitAsCharFromInt(lastGeneratedNumber);
        addedToSum = isSecond ? _multipDigitX2AndGetSumOfDigits(lastGeneratedNumber) : lastGeneratedNumber;

        lastAddedNumberFromGenerated = addedToSum;
        wasLastGeneratedNumberSecond = isSecond;
      }
      else {
        int numberFromPattern = extractDigitAsIntFromChar(charFromPattern);
        buffer[i] = charFromPattern;
        addedToSum = numberFromPattern;
        if (isSecond) {
          addedToSum = _multipDigitX2AndGetSumOfDigits(addedToSum);
        }
      }
      currentSum += addedToSum;
      isSecond = !isSecond;
    }
    if (indexOfLastGeneratedNumber == -1) {
      throw new IllegalArgumentException("Pattern does not contain numbers for generation: " + template);
    }
    currentSum -= lastAddedNumberFromGenerated;
    int numberForValidCheckSum = _calcNumberForValidCheckSum(currentSum, wasLastGeneratedNumberSecond);
    buffer[indexOfLastGeneratedNumber] = extractDigitAsCharFromInt(numberForValidCheckSum);
  }

  private static final char _placeholderForNumber = 'x';

  private static int _calcNumberForValidCheckSum(
    int sumByLuhnWithoutTargetNumber,
    boolean isTargetNumberSecond
  ) {
    if (isTargetNumberSecond) {
      return _getNumberForValidCheckSumAfterX2AndSumOfDigits(sumByLuhnWithoutTargetNumber);
    }
    else {
      return _calcSummandToNext10(sumByLuhnWithoutTargetNumber);
    }
  }

  private static int _getNumberForValidCheckSumAfterX2AndSumOfDigits(int sumByLuhnWithoutTargetNumber) {
    int numberToNext10 = _calcSummandToNext10(sumByLuhnWithoutTargetNumber);
    switch (numberToNext10) {
      case 0: return 0;
      case 1: return 5;
      case 2: return 1;
      case 3: return 6;
      case 4: return 2;
      case 5: return 7;
      case 6: return 3;
      case 7: return 8;
      case 8: return 4;
      case 9: return 9;
      default: throw new RuntimeException("");
    }
  }
}
