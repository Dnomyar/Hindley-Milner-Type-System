package fr.imt.test.validation

import fr.imt.inference.ExpressionInferer
import fr.imt.inference.`type`.ArrowType
import fr.imt.inference.ast.{Expression, Operator}
import fr.imt.inference.ast.factory.ExpressionFactory.{App, Bool, Int, Lamb, Let, Ope, Var}
import fr.imt.parser.{ExpressionParser, InferenceProcessor, ReplProcessable}
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
    }
  }

}
