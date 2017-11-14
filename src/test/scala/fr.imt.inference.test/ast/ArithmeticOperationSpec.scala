package fr.imt.inference.test.ast

import fr.imt.inference.`type`.IntegerType
import fr.imt.inference.ast.Operator
import fr.imt.inference.ast.factory.ExpressionFactory.{Bool, Int, Ope, Var}
import fr.imt.inference.{ConstraintCollection, Environment}
import org.scalatest.{Matchers, WordSpec}

class ArithmeticOperationSpec extends WordSpec with Matchers {

  "An ArithmeticOperation expression" should {
    "be equals to another ArithmeticOperation with the same parameters" in {
      Ope(Var("a"), Bool(true), Operator.PLUS) should equal(Ope(Var("a"), Bool(true), Operator.PLUS))
    }

    "not be equals to a different lambda" in {
      Ope(Var("a"), Bool(true), Operator.PLUS) should not equal Ope(Var("a"), Var("b"), Operator.TIME)
      Ope(Var("a"), Bool(true), Operator.PLUS) should not equal Ope(Bool(false), Bool(true), Operator.PLUS)
    }

    "infer and return an IntegerType" in {
      val environment = new Environment

      Ope(Int(2), Int(3), Operator.TIME).infer(environment, new ConstraintCollection) shouldBe an[IntegerType]
      // Note: Infer but will failed on the constraint substitution step
      Ope(Bool(true), Bool(false), Operator.MINUS).infer(environment, new ConstraintCollection) shouldBe an[IntegerType]
    }
  }

}