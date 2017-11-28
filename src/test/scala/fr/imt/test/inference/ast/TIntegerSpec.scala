package fr.imt.test.inference.ast

import fr.imt.inference.`type`.IntegerType
import fr.imt.inference.ast.factory.ExpressionFactory.Int
import fr.imt.inference.{ConstraintCollection, Environment}
import org.scalatest.{Matchers, WordSpec}

class TIntegerSpec extends WordSpec with Matchers {

  "A TInteger expression" should {
    "be equals to another TInteger with the same value" in {
      Int(3) should equal(Int(3))
      Int(4) should not equal Int(79)
    }

    "infer and return a IntegerType" in {
      Int(10).infer(new Environment, new ConstraintCollection) shouldBe a[IntegerType]
    }
  }

}