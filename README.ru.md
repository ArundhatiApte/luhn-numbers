# Номера Луна

Алгоритм Луна обычно используется для первичной проверки номеров банковских карт. Также известны другие области его
применения. Данный модуль предоставляет функцию, проверяющую номер по алгоритму Луна, и методы, создающие валидные
последовательности цифр.

## Пример использования

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

* `number` - номер для проверки
* возвращает, прошел ли номер проверку

Проверяет номер по алгоритму Луна. Если `number` имеет длину менее 1 или содержит отличные от цифр символы, то метод
выбрасывает исключение `IllegalArgumentException`.

### public static boolean isNumberValid(CharSequence charSequence, int startIndex, int lastIndex)

* `charSequence` - последовательность символов, содержащая номер для проверки
* `startIndex` - индекс первого символа номера в последовательности
* `lastIndex` - индекс последнего символа номера в последовательности
* возвращает, прошел ли номер проверку

Проверяет номер по алгоритму Луна в последовательности `charSequence` с начального индекса по конечный включительно.
В случаях ошибочных индексов метод выбрасывает исключение `IndexOutOfBoundsException`. Если номер имеет длину менее
1 или содержит отличные от цифр символы, то функция возбуждает `IllegalArgumentException`.

### public static char[] createNumber(int length, Random generator)

* `length` - длина создаваемого номера
* `generator` - генератор чисел
* возвращает номер в символьном представлении

Создаёт номер, проходящий проверку алгоритмом Луна, используя переданный генератор чисел. Если длина `length` меньше
1, то метод выбрасывает исключение `IllegalArgumentException`.

### public static char[] createNumberWithPrefix(CharSequence prefix, int lengthOfTail, Random generator)

* `prefix` - префикс для создаваемого номера
* `lengthOfTail` - длина добавленной после префикса части
* `generator` - генератор чисел
* возвращает номер в символьном представлении

Создаёт номер, проходящий проверку алгоритмом Луна, начинающийся с указанного префикса. Если префикс содержит
отличные от цифр символы, или длина генерированной части - `lengthOfTail` меньше 1, то метод выбрасывает исключение
`IllegalArgumentException`.

### public static char[] createNumberByTemplate(CharSequence template, Random generator)

* `template` - шаблон для создания номера. Содержит символы цифр и знак для сгенерированных чисел - `'x'` в нижнем
регистре. Пример: `"9685xxxx14xx25xx"`.
* `generator` - генератор чисел
* возвращает номер в символьном представлении

Создаёт номер, проходящий проверку алгоритмом Луна, по указанному шаблону. Если шаблон имеет длину менее 1 или
содержит отличные от цифр символы, то метод выбрасывает исключение `IllegalArgumentException`.

## Сборка и установка

Для сборки требуется Maven. Скачать исходный код и в корневой папке проекта выполнить команду: `mvn package`. После
в папке target/ появится артефакт luhn-numbers-0.1.0.jar.

Установки модуля в локальный репозиторий Maven осуществляется с помощью следующей команды, выполненной из
папки проекта: `mvn install`. После чего добавить запись зависимость в pom:

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

Модуль `LuhnNumbers` является jpms модулем, поэтому для использования в собственных модулях потребуется добавить
следующую запись в файл module-info.java модулей пользователей:

```java
module aa.module {
  requires luhnNumbers;
}
```
