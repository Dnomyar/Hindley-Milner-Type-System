package fr.imt.inference.testtest.ast

import fr.imt.inference.`type`.{IntegerType, TypeVariable}
import fr.imt.inference.ast.factory.ExpressionFactory.{App, Bool, Int, Lamb, Let, Var}
import fr.imt.inference.{ConstraintCollection, Environment}
import org.scalatest.{Matchers, WordSpec}

class LetSpec extends WordSpec with Matchers {

  "A Let expression" should {
    "be equals to another Application with the same parameters" in {
      Let(Var("f"), Bool(true), App(Var("f"), Int(2))) should equal(Let(Var("f"), Bool(true), App(Var("f"), Int(2))))
      Let(Var("f"), Bool(true), App(Var("f"), Int(2))) should not equal Let(Var("a"), Bool(true), Var("f"))
    }

    "infer and return a TypeVariable" in {
      val environment = new Environment
      val identifier = Var("f")
      val expression = Let(identifier, Lamb(Var("x"), Var("x")), App(Var("f"), Int(2)))

      expression.infer(environment, new ConstraintCollection) shouldBe a[TypeVariable]
      an[NoSuchElementException] should be thrownBy environment.get(identifier)
    }

    "infer and return a IntegerType" in {
      val environment = new Environment
      val identifier = Var("f")
      val expression = Let(identifier, Lamb(Var("x"), Var("x")), Int(2))

      expression.infer(environment, new ConstraintCollection) shouldBe a[IntegerType]
      an[NoSuchElementException] should be thrownBy environment.get(identifier)
    }
  }

}