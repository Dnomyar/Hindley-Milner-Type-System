package fr.imt.test.inference.ast.binaryexpression

import fr.imt.inference.{Constraint, ConstraintCollection, Environment, FreshVariable}
import fr.imt.inference.`type`.{BooleanType, IntegerType}
import fr.imt.inference.ast.binaryexpression.operators.{ArithmeticOperator, EqualityOperator}
import fr.imt.inference.ast.factory.ExpressionFactory._
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}

class ConditionSpec extends WordSpec with Matchers with BeforeAndAfter {

  before {
    FreshVariable.reset()
  }

  "A Condition expression" should {
    "be equals to another ArithmeticOperation with the same parameters" in {
      Con(Var("a"), Int(1), EqualityOperator.EQUALS) should equal(Con(Var("a"), Int(1), EqualityOperator.EQUALS))
    }

    "not be equals to a different lambda" in {
      Con(Var("a"), Var("b"), EqualityOperator.EQUALS) should not equal Con(Var("a"), Int(1), EqualityOperator.EQUALS)
      Con(Bool(true), Var("b"), EqualityOperator.EQUALS) should not equal Con(Var("a"), Int(1), EqualityOperator.EQUALS)
    }

    "infer and return an IntegerType" in {
      val environment = new Environment

      Con(Int(2), Int(3), EqualityOperator.EQUALS).infer(environment, new ConstraintCollection) shouldBe an[BooleanType]

      Con(Int(2), Var("a"), EqualityOperator.EQUALS).infer(environment, new ConstraintCollection) shouldBe an[BooleanType]

      Con(Bool(false), Var("a"), EqualityOperator.EQUALS).infer(environment, new ConstraintCollection) shouldBe an[BooleanType]
    }


    "infer and generate right constraints" in {
      val environment = new Environment

      val collection = new ConstraintCollection

      Con(Int(2), Int(3), EqualityOperator.EQUALS).infer(environment, collection)

      //collection.all() should contain new Constraint()

    }
  }

}