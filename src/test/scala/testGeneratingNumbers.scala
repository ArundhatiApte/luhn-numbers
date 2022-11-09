package luhnNumbers

import java.util.Random
import javax.swing.text.Segment

import org.scalatest.Assertions.assert
import org.scalatest.Assertions.assertThrows
import org.scalatest.funspec.AnyFunSpec

import luhnNumbers.LuhnNumbers.createNumber
import luhnNumbers.LuhnNumbers.isNumberValid
import luhnNumbers.LuhnNumbers.createNumberWithPrefix
import luhnNumbers.LuhnNumbers.createNumberByTemplate


final class TestGeneratingNumbers extends AnyFunSpec {
  private val _random = new Random(4)

  describe("создание номера") {
    it("проверкa размера") {
      _expectThrowsIllegalArgumentException(() => createNumber(0, _random))
    }

    it("генерация верных номеров") {
      def checkGeneratingValidNumber(length: Int): Unit = {
        val number = createNumber(length, _random)
        _expectValidNumberByLuhn(number)
      }
      for (length <- 1 to 100) {
        checkGeneratingValidNumber(length)
      }
    }
  }

  private def _expectThrowsIllegalArgumentException(fn: () => Any): Unit = {
    assertThrows[IllegalArgumentException] { fn() }
  }

  private def _expectValidNumberByLuhn(number: Array[Char]): Unit = {
    assert(isNumberValid(new Segment(number, 0, number.length)))
  }

  describe("создание номера с префиксом") {
    it("проверка размера") {
      _expectThrowsIllegalArgumentException(() => createNumberWithPrefix("1234", 0, _random))
    }

    it("генерация верных номеров") {
      def checkGeneratingValidNumber(prefix: CharSequence, lengthOfTail: Int): Unit = {
        val number = createNumberWithPrefix(prefix, lengthOfTail, _random)
        _expectValidNumberByLuhn(number)
      }
      val cases = Array(
        ("", 1),
        ("", 2),
        ("", 22),
        ("1", 1),
        ("1", 2),
        ("1", 44),
        ("98", 1),
        ("98", 2),
        ("98", 22),
        ("9789123", 182),
        ("", 182)
      )
      for (caseForTest <- cases) {
        checkGeneratingValidNumber(caseForTest._1, caseForTest._2)
      }
    }
  }

  describe("создание номера по шаблону") {
    it("проверка размера") {
      _expectThrowsIllegalArgumentException(() => createNumberByTemplate("",  _random))
    }

    it("исключение при ошибочном формате") {
      _expectThrowsIllegalArgumentException(() => createNumberByTemplate("123456xxnumber", _random))
      _expectThrowsIllegalArgumentException(() => createNumberByTemplate("123456", _random))
    }

    it("генерация верных номеров") {
      def checkGeneratingValidNumber(template: CharSequence): Unit = {
        val number = createNumberByTemplate(template, _random)
        _expectValidNumberByLuhn(number)
      }
      val paterns = Array(
        "x",
        "x1",
        "2x",
        "xx3",
        "3xx",
        "12xx3",
        "xx23xx45",
        "123456789x",
        "x123456789",
        "xxxxxxxxxxxxxx",
        "837843267192312371290xxxxxxxxxxxxxxxxxxxxxxxxxxxxx98980",
      )
      for (template <- paterns) {
        checkGeneratingValidNumber(template)
      }
    }
  }
}
