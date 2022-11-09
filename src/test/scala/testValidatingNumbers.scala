package luhnNumbers

import org.scalatest.Assertions.assert
import org.scalatest.Assertions.assertThrows
import org.scalatest.funspec.AnyFunSpec

import luhnNumbers.LuhnNumbers.isNumberValid


final class TestValidatingNumbersByLuhn extends AnyFunSpec {
  describe("проверка целого номера") {
    it("верные номерa") {
      checkValidatingNumbersByLuhn(
        Array(
          "0",
          "18",
          "117",
          "1214",
          "79927398713",
          "378282246310005",
          "4111111111111111",
          "5105105105105100",
          "6011111111111117"
        ),
        true,
        isNumberValid
      )
    }

    it("ошибочные номера") {
      checkValidatingNumbersByLuhn(
        Array(
          "1",
          "19",
          "127",
          "1215",
          "378282246310004",
          "411111111111111"
        ),
        false,
        isNumberValid
      )
    }

    object checkValidatingNumbersByLuhn {
      def apply(
        numbers: Array[CharSequence],
        isValid: Boolean,
        validatingFn: (CharSequence) => Boolean
      ): Unit = {
        for (number <- numbers) {
          _checkValidatingNumberByLuhn(number, isValid, validatingFn)
        }
      }

      private def _checkValidatingNumberByLuhn(
        number: CharSequence,
        isValid: Boolean,
        validatingFn: (CharSequence) => Boolean
      ): Unit = {
        assert(isValid == validatingFn(number))
      }
    }
  }

  describe("проверка номера внутри последовательности символов") {
    it("проверка индекса") {
      def checkDetectingIndexOutOfBounds(number: CharSequence, startIndex: Int, lastIndex: Int): Unit = {
        assertThrows[IndexOutOfBoundsException] {
          isNumberValid(number, startIndex, lastIndex)
        }
      }

      checkDetectingIndexOutOfBounds("0", -1, 0)
      checkDetectingIndexOutOfBounds("0123", 1, 4)
      checkDetectingIndexOutOfBounds("0123", 2, 1)
    }

    it("верный номер") {
      assert(isNumberValid("ab2345cd", 2, 5))
    }
  }
}
