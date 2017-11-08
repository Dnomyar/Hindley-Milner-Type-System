package fr.imt.inference

import fr.imt.inference.AST.factory.ExpressionFactory.{Bool, Int, Lamb, Var}
import fr.imt.inference.`type`.ArrowType
import org.scalatest.{Matchers, WordSpec}

class LambdaSpec extends WordSpec with Matchers {

  "A Lambda expression" should {
    "be equals to another Lambda with the same parameters" in {
      Lamb(Var("x"), Int(3)) should equal(Lamb(Var("x"), Int(3)))
      Lamb(Var("x"), Int(3)) should not equal Lamb(Var("x"), Int(4))
      Lamb(Var("x"), Lamb(Var("b"), Bool(false))) should equal(Lamb(Var("x"), Lamb(Var("b"), Bool(false))))
      Lamb(Var("x"), Lamb(Var("b"), Bool(false))) should not equal Lamb(Var("x"), Bool(true))
    }

    "infer and return an ArrowType" in {
      val environment = new Environment
      val identifier = Var("x")

      Lamb(identifier, Int(3)).infer(environment, new ConstraintCollection) shouldBe a[ArrowType]
      an[NoSuchElementException] should be thrownBy environment.get(identifier)
    }
  }

}