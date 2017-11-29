package fr.imt.test.inference.ast

import fr.imt.inference.`type`.IntegerType
import fr.imt.inference.ast.Operator
import fr.imt.inference.ast.factory.ExpressionFactory.{Bool, Int, Ope, Var}
import fr.imt.inference.errors.UnificationFailureException
import fr.imt.inference.{ConstraintCollection, Environment, Unifiyer}
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
      val constraintCollection = new ConstraintCollection

      val rawReturnType = Ope(Int(2), Int(3), Operator.TIME).infer(environment, constraintCollection)
      val result = new Unifiyer().runSolve(constraintCollection)

      rawReturnType.applySubstitution(result) shouldBe an[IntegerType]
    }

    "infer but failed on the constraint substitution step" in {
      val environment = new Environment
      val constraintCollection = new ConstraintCollection

      Ope(Bool(true), Int(3), Operator.MINUS).infer(environment, constraintCollection)
      val exceptionMessage = "UnificationFailureException : Cannot unify type `Bool` with type `Int`"

      the[UnificationFailureException] thrownBy new Unifiyer().runSolve(constraintCollection) should have message exceptionMessage
    }
  }

}