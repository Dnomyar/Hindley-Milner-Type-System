package fr.imt.test.inference.ast

import fr.imt.inference.ast.factory.ExpressionFactory.Var
import fr.imt.inference.errors.NonexistentVariableException
import fr.imt.inference.{ConstraintCollection, Environment}
import org.scalatest.{Matchers, WordSpec}

class VariableSpec extends WordSpec with Matchers {

  "A Variable expression" should {
    "be equals to another Variable with the same name" in {
      Var("a") should equal(Var("a"))
      Var("a") should not equal Var("b")
    }

    "raise an exception when infer with an empty Environment" in {
      an[NonexistentVariableException] should be thrownBy Var("a").infer(new Environment, new ConstraintCollection)
    }
  }

}