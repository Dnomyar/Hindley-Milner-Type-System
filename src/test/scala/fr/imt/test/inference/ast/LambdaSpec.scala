package fr.imt.test.inference.ast

import fr.imt.inference.ast.factory.ExpressionFactory.{Bool, Int, Lamb, Var}
import fr.imt.inference.`type`.ArrowType
import fr.imt.inference.{ConstraintCollection, Environment}
import org.scalatest.{Matchers, WordSpec}

class LambdaSpec extends WordSpec with Matchers {

  "A Lambda expression" should {
    "be equals to another Lambda with the same parameters" in {
      Lamb(Var("x"), Int(3)) should equal(Lamb(Var("x"), Int(3)))
      Lamb(Var("x"), Lamb(Var("b"), Bool(false))) should equal(Lamb(Var("x"), Lamb(Var("b"), Bool(false))))
    }

    "not be equals to a different lambda" in {
      Lamb(Var("x"), Lamb(Var("b"), Bool(false))) should not equal Lamb(Var("x"), Bool(true))
      Lamb(Var("x"), Int(3)) should not equal Lamb(Var("x"), Int(4))
    }

    "infer and return an ArrowType" in {
      val environment = new Environment
      val identifier = Var("x")

      Lamb(identifier, Int(3)).infer(environment, new ConstraintCollection) shouldBe a[ArrowType]
      an[NoSuchElementException] should be thrownBy environment.get(identifier)
    }
  }

}