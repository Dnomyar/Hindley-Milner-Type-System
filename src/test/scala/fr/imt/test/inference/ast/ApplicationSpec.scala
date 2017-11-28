package fr.imt.test.inference.ast

import fr.imt.inference.`type`.{ArrowType, BooleanType, IntegerType, TypeVariable}
import fr.imt.inference.ast.factory.ExpressionFactory.{App, Bool, Int, Lamb, Var}
import fr.imt.inference.{Constraint, ConstraintCollection, Environment, FreshVariable}
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}

class ApplicationSpec extends WordSpec with Matchers with BeforeAndAfter {

  before {
    FreshVariable.reset()
  }

  "An Application expression" should {
    "be equals to another Application with the same parameters" in {
      App(Int(3), Bool(true)) should equal(App(Int(3), Bool(true)))
      App(Lamb(Var("x"), Lamb(Var("b"), Bool(false))), Int(8)) should equal(App(Lamb(Var("x"), Lamb(Var("b"), Bool(false))), Int(8)))
    }

    "not be equals to another Application without the same parameters" in {
      App(Int(6), Bool(true)) should not equal Lamb(Var("x"), Int(4))
      App(Int(3), Bool(true)) should not equal App(Int(2), Bool(false))
    }

    "infer and return a TypeVariable" in {
      val constraintCollection = new ConstraintCollection

      // Unifiyer will failed with this expression
      // App(Int(6), Bool(true)).infer(environment, constraintCollection) shouldBe a [TypeVariable]

      App(Lamb(Var("x"), Int(6)), Bool(true)).infer(new Environment, constraintCollection) shouldBe a[TypeVariable]

      FreshVariable.reset()

      constraintCollection.size() should be > 0

      val actual = constraintCollection.head()

      val expected = new Constraint(new ArrowType(new FreshVariable, new IntegerType), new ArrowType(new BooleanType, new FreshVariable))

      actual should equal(expected)
    }



  }

}