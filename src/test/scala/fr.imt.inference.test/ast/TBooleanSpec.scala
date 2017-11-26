package fr.imt.inference.test.ast

import fr.imt.inference.ast.factory.ExpressionFactory.Bool
import fr.imt.inference.`type`.BooleanType
import fr.imt.inference.{ConstraintCollection, Environment}
import org.scalatest.{Matchers, WordSpec}

class TBooleanSpec extends WordSpec with Matchers {

  "A TBoolean expression" should {
    "be equals to another TBoolean with the same value" in {
      Bool(true) should equal(Bool(true))
      Bool(true) should not equal Bool(false)
    }

    "infer and return a BooleanType" in {
      Bool(true).infer(new Environment, new ConstraintCollection) shouldBe a[BooleanType]
    }
  }

}