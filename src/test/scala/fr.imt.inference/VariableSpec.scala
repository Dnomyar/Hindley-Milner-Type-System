package fr.imt.inference

import fr.imt.inference.AST.factory.ExpressionFactory.Var
import org.scalatest.{Matchers, WordSpec}

class VariableSpec extends WordSpec with Matchers {

  "A Variable expression" should {
    "be equals to another Variable with the same name" in {
      Var("a") should equal(Var("a"))
      Var("a") should not equal Var("b")
    }

    "raise an exception when infer with an empty Environment" in {
      an[NoSuchElementException] should be thrownBy Var("a").infer(new Environment, new ConstraintCollection)
    }
  }

}