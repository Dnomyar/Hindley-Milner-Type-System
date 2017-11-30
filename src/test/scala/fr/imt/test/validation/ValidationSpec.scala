package fr.imt.test.validation

import fr.imt.inference.ExpressionInferer

import fr.imt.inference.`type`.{ArrowType, IntegerType, TypeVariable}
import fr.imt.parser.ExpressionParser
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}

class ValidationSpec extends WordSpec with Matchers with BeforeAndAfter {

  "The system" should {
    "give the type `a -> a` for the expression `let f = \\f -> f in let g = \\a b -> f in g f f`" in {
      val res =
        new ExpressionParser()
          .parse("let f = \\f -> f in let g = \\a b -> f in app (app g f) f")

      val result = new ExpressionInferer().infer(res.get())

      result.get().isArrow should be (true)

      val arrowType = result.get().asInstanceOf[ArrowType]

      arrowType.left should be (arrowType.right)
      arrowType.left shouldBe a [TypeVariable]
    }

    "throws an UnificationFailureException for the expression `app 6 True`" in {
      val result = new ExpressionInferer().infer(new ExpressionParser().parse("app 6 True").get())
      val exceptionMessage = "UnificationFailureException : Cannot unify type `Int` with type `Bool -> t0`"

      result.getLeft should be (exceptionMessage)
    }

    "throws an UnificationFailureException for the expression `con True == 6`" in {
      val result = new ExpressionInferer().infer(new ExpressionParser().parse("con True == 6").get())
      val exceptionMessage = "UnificationFailureException : Cannot unify type `Int` with type `Bool`"

      result.getLeft should be (exceptionMessage)
    }

    "give the type `a -> a` for the expression `\\f -> (app (\\l -> l) f)`" in {
      val result = new ExpressionInferer().infer(new ExpressionParser().parse("\\f -> (app (\\l -> l) f)").get())
      val arrowType = result.get().asInstanceOf[ArrowType]

      arrowType.left should be (arrowType.right)
    }

    "give the type `a -> a` for the expression `let f = 4 in (\\f -> f)`" in {
      val result = new ExpressionInferer().infer(new ExpressionParser().parse("let f = 4 in (\\f -> f)").get())
      val arrowType = result.get().asInstanceOf[ArrowType]

      arrowType.left should be (arrowType.right)
    }

    "give the type `Int` for the expression `let f = 4 in (app (\\f -> f) f)`" in {
      val result = new ExpressionInferer().infer(new ExpressionParser().parse("let f = 4 in (app (\\f -> f) f)").get())

      result.get shouldBe a [IntegerType]
    }

    "give the type `a -> Int -> Int -> Int` for the expression `\\f -> (\\a b -> op a + b)`" in {
      val result = new ExpressionInferer().infer(new ExpressionParser().parse("\\f -> (\\a b -> op a + b)").get())

      result.get should be (new ArrowType(new TypeVariable("t0"), new ArrowType(new IntegerType, new ArrowType(new IntegerType, new IntegerType))))
    }

    "give the type `Int` for the expression `app (\\n -> let fibo = (\\a b n -> if con n == 2 then b else app (app (app fibo b) (op a + b)) (op n - 1)) in app (app (app fibo 1) 1) n) 12444`" in {
      val result = new ExpressionInferer().infer(new ExpressionParser().parse(
        "app (\\n -> let fibo = (\\a b n -> if con n == 2 then b else app (app (app fibo b) (op a + b)) (op n - 1)) in app (app (app fibo 1) 1) n) 12444"
      ).get())
      val exceptionMessage = "Variable `fibo` not found"

      result.getLeft should be (exceptionMessage)

      // NOTE: for now, you could not infer recursive method ...
    }
  }

}
