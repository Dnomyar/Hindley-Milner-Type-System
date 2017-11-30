package fr.imt.test.inference.ast.binaryexpression

import fr.imt.inference._
import fr.imt.inference.`type`._
import fr.imt.inference.ast.Expression
import fr.imt.inference.ast.binaryexpression.operators.{ArithmeticOperator, EqualityOperator}
import fr.imt.inference.ast.factory.ExpressionFactory._
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}

class ConditionSpec extends WordSpec with Matchers with BeforeAndAfter {

  before {
    FreshVariable.reset()
  }

  "A Condition expression" should {
    "be equals to another Condition with the same parameters" in {
      Con(Var("a"), Int(1), EqualityOperator.EQUALS) should equal(Con(Var("a"), Int(1), EqualityOperator.EQUALS))
    }

    "not be equals to a different Condition" in {
      Con(Var("a"), Var("b"), EqualityOperator.EQUALS) should not equal Con(Var("a"), Int(1), EqualityOperator.EQUALS)
      Con(Bool(true), Var("b"), EqualityOperator.EQUALS) should not equal Con(Var("a"), Int(1), EqualityOperator.EQUALS)
    }

    "infer and return a BooleanType" in {
      infer(Con(Int(2), Int(3), EqualityOperator.EQUALS)) shouldBe an[BooleanType]
      infer(Con(Int(2), Int(2), EqualityOperator.EQUALS)) shouldBe an[BooleanType]
      infer(Con(Bool(false), Bool(true), EqualityOperator.EQUALS)) shouldBe an[BooleanType]
    }
  }

  private def infer(exp : Expression): Type = {
    val environment = new Environment
    val constraintCollection = new ConstraintCollection

    val rawReturnType = exp.infer(environment, constraintCollection)
    val result = new Unifiyer().runSolve(constraintCollection)

    rawReturnType.applySubstitution(result)
  }

}