# Luhn numbers

The Luhn algorithm is usually used for the initial verification of bank card numbers. Other areas of its
application are also known. This module provides functions that check numbers using the Luhn algorithm,
and methods that create valid numbers.

## Example of usage

```java
LuhnNumbers.isNumberValid(""); //false
LuhnNumbers.isNumberValid("ab2345cd", 2, 5); // true

Random r = new Random(0);
LuhnNumbers.createNumber(8, r);
// 36628063

LuhnNumbers.createNumberWithPrefix("1234", 8, r);
// 123417557832

LuhnNumbers.createNumberByTemplate("4231xxxx8675xxxx", r);
// 4231771486751885
```

## API

- `package luhnNumbers`
    - `public class LuhnNumbers`
        * `public static boolean isNumberValid(CharSequence number)`
        * `public static boolean isNumberValid(CharSequence charSequence, int startIndex, int lastIndex)`
        * `public static char[] createNumber(int length, Random generator)`
        * `public static char[] createNumberWithPrefix(CharSequence prefix, int lengthOfTail, Random generator)`
        * `public static char[] createNumberByTemplate(CharSequence template, Random generator)`

### public static boolean isNumberValid(CharSequence number)

* `number` - number to check
* returns whether number has passed check

Checks a number using the Luhn algorithm. If `number` is less than 1 in length or contains characters other than
digits, the method throws `IllegalArgumentException`.

### public static boolean isNumberValid(CharSequence charSequence, int startIndex, int lastIndex)

* `charSequence` - a sequence of characters containing a number to check
* `startIndex` - index of the first character of the number in the sequence
* `lastIndex` - index of the last character of the number in the sequence
* returns whether number has passed check

Check a number using the Luhn algorithm in char sequence from start index to last inclusive. In cases of invalid
indexes, the method throws `IndexOutOfBoundsException`. If number is less than 1 in length or contains characters
other than digits, then the method throws `IllegalArgumentException`.

### public static char[] createNumber(int length, Random generator)

* `length` - length of number to create
* `generator` - numbers generator
* returns number in a сharacter representation

Creates a number, that is valid by the Luhn algorithm, using the given numbers generator. If the length is less than 1,
then the method throws `IllegalArgumentException`.

### public static char[] createNumberWithPrefix(CharSequence prefix, int lengthOfTail, Random generator)

* `prefix` - prefix for number to create
* `lengthOfTail` - length of the part added after the prefix
* `generator` - numbers generator
* returns number in a сharacter representation

Creates a number, that is valid by the Luhn algorithm, starting with the specified prefix. If the prefix contains
characters other than digits, or the length of generated part - `lengthOfTail` is less than 1, then the method thows
`IllegalArgumentException`.

### public static char[] createNumberByTemplate(CharSequence template, Random generator)

* `template` - template for creating a number. Contains characters of digits and placeholder for numbers to generate
symbol `'x'` in lowercase. Example: `"9685xxxx14xx25xx"`.
* `generator` - numbers generator
* returns number in a сharacter representation

Create a number, that is valid by the Luhn algorithm, by the specified template. If the template is less than 1 in
length or contains characters other than digits, then the method throws `IllegalArgumentException`.

## Build and install

The build requires Maven. Download the source code and execute the command: `mvn package` in the root folder of
the project. After that, an artifact luhn-numbers-0.1.0.jar will appear in the target/ folder.

Installation of the module to the local Maven repository is achieved with the following command executed from
the project folder: `mvn install`. Then add entry about dependency to pom:

```xml
<dependencies>
  <!-- ... -->
  <dependency>
    <groupId>luhn-numbers</groupId>
    <artifactId>luhn-numbers</artifactId>
    <version>0.1.0</version>
  </dependency>

</dependencies>
```

The `LuhnNumbers` module is a jpms module, so to use it in your own modules, you will need to add the following
entry to the file module-info.java of your module:

```java
module my.module {
  requires luhnNumbers;
}
```
