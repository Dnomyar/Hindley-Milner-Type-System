package fr.imt.test.inference.ast

import fr.imt.inference.FreshVariable
import fr.imt.inference.ast.binaryexpression.operators.{ArithmeticOperator, EqualityOperator}
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import fr.imt.inference.ast.factory.ExpressionFactory.{If, _}


class IfSpec extends WordSpec with Matchers with BeforeAndAfter {

  before {
    FreshVariable.reset()
  }

  "If" should {
    "be equal to the same If" in {
      val if1 = If(Con(Var("a"), Bool(false), EqualityOperator.EQUALS), Ope(Var("b"), Int(5), ArithmeticOperator.PLUS), Ope(Var("b"), Int(5), ArithmeticOperator.MINUS))

      val if2 = If(Con(Var("a"), Bool(false), EqualityOperator.EQUALS), Ope(Var("b"), Int(5), ArithmeticOperator.PLUS), Ope(Var("b"), Int(5), ArithmeticOperator.MINUS))

      if1 should be (if2)
    }
  }

}
