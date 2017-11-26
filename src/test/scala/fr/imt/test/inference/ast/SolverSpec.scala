package fr.imt.test.inference.ast

import fr.imt.inference.ast.factory.ExpressionFactory.{App, Bool, Int, Lamb, Let, Var}
import fr.imt.inference.`type`.{BooleanType, IntegerType, Type, TypeVariable}
import fr.imt.inference.errors.UnificationFailureException
import fr.imt.inference.{ConstraintCollection, Environment, Unifiyer}
import fr.imt.inference.ast.{Expression}
import org.scalatest.{Matchers, WordSpec}

class SolverSpec extends WordSpec with Matchers {

  "(\\F -> (\\a b -> b) (F True) (F 1)) (\\x -> x)" should {
    "throw an UnificationFailureException" in {
      val F = Var("F")
      val varA = Var("a")
      val varB = Var("b")
      val varX = Var("x")
      // (\a b -> b)
      val snd = Lamb(varA, varB, varB)
      //(F True)
      val FTrue = App(F, Bool(true))
      // (F 1)
      val F1 = App(F, Int(1))
      // (\a b -> b) (F True) (F 1)
      val application = App(snd, FTrue, F1)
      // (\x -> x)
      val identityX = Lamb(varX, varX)
      // (\F -> (\a b -> b) (F True) (F 1))
      val lambda = Lamb(F, application)
      // (\F -> (\a b -> b) (F True) (F 1)) (\x -> x)
      val expression = App(lambda, identityX)

      val exceptionMessage = "UnificationFailureException : Cannot unify type `Bool` with type `Int`"
      the[UnificationFailureException] thrownBy infer(expression) should have message exceptionMessage
    }
  }

  "let F = (\\x -> x) in (\\a b -> b) (F True) (F 1)" should {
    "be an IntegerType" in {
      val F = Var("F")
      val varA = Var("a")
      val varB = Var("b")
      val varX = Var("x")
      // (\a b -> b)
      val snd = Lamb(varA, varB, varB)
      //(F True)
      val FTrue = App(F, Bool(true))
      // (F 1)
      val F1 = App(F, Int(1))
      // (\a b -> b) (F True) (F 1)
      val application = App(snd, FTrue, F1)
      // (\x -> x)
      val identityX = Lamb(varX, varX)
      // let F = (\x -> x) in (\a b -> b) (F True) (F 1)
      val expression = Let(F, identityX, application)

      val expressionType = infer(expression)

      expressionType shouldBe an[IntegerType]
      expressionType should not be a[BooleanType]
    }
  }

  private def infer(expression: Expression): Type = {
    val constraintCollection = new ConstraintCollection

    val rawReturnType = expression.infer(new Environment, constraintCollection)

    new Unifiyer()
      .runSolve(constraintCollection)
      .getOrElse(rawReturnType.asInstanceOf[TypeVariable], rawReturnType.asInstanceOf[TypeVariable])
  }


}