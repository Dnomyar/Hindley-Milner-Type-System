package fr.imt.test.inference.ast.binaryexpression

import fr.imt.inference.`type`.IntegerType
import fr.imt.inference.ast.binaryexpression.operators.ArithmeticOperator
import fr.imt.inference.ast.factory.ExpressionFactory.{Bool, Int, Ope, Var}
import fr.imt.inference.{ConstraintCollection, Environment, Unifiyer}
import org.scalatest.{Matchers, WordSpec}

class ArithmeticOperationSpec extends WordSpec with Matchers {

  "An ArithmeticOperation expression" should {
    "be equals to another ArithmeticOperation with the same parameters" in {
      Ope(Var("a"), Bool(true), ArithmeticOperator.PLUS) should equal(Ope(Var("a"), Bool(true), ArithmeticOperator.PLUS))
    }

    "not be equals to a different lambda" in {
      Ope(Var("a"), Bool(true), ArithmeticOperator.PLUS) should not equal Ope(Var("a"), Var("b"), ArithmeticOperator.TIME)
      Ope(Var("a"), Bool(true), ArithmeticOperator.PLUS) should not equal Ope(Bool(false), Bool(true), ArithmeticOperator.PLUS)
    }

    "infer and return an IntegerType" in {
      val environment = new Environment
      val constraintCollection = new ConstraintCollection

      val rawReturnType = Ope(Int(2), Int(3), ArithmeticOperator.TIME).infer(environment, constraintCollection)
      val result = new Unifiyer().runSolve(constraintCollection)

      rawReturnType.applySubstitution(result) shouldBe an[IntegerType]
    }
  }

}