package fr.imt.inference

import fr.imt.inference.AST.factory.ExpressionFactory.{App, Bool, Int, Lamb, Var}
import fr.imt.inference.`type`.{ArrowType, BooleanType, IntegerType, TypeVariable}
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}

class ApplicationSpec extends WordSpec with Matchers with BeforeAndAfter {

  before {
    FreshVariable.reset()
  }

  "An Application expression" should {
    "be equals to another Application with the same parameters" in {
      App(Int(3), Bool(true)) should equal(App(Int(3), Bool(true)))
      App(Int(6), Bool(true)) should not equal Lamb(Var("x"), Int(4))
      App(Lamb(Var("x"), Lamb(Var("b"), Bool(false))), Int(8)) should equal(App(Lamb(Var("x"), Lamb(Var("b"), Bool(false))), Int(8)))
    }

    "infer and return a TypeVariable" in {
      val constraintCollection = new ConstraintCollection

      // Unifiyer will failed with this expression
      // App(Int(6), Bool(true)).infer(environment, constraintCollection) shouldBe a [TypeVariable]

      App(Lamb(Var("x"), Int(6)), Bool(true)).infer(new Environment, constraintCollection) shouldBe a[TypeVariable]

      FreshVariable.reset()

      val actual = constraintCollection.head()
      val expected = new Constraint(new ArrowType(new FreshVariable, new IntegerType), new ArrowType(new BooleanType, new FreshVariable))

      actual should equal(expected)
    }
  }

}