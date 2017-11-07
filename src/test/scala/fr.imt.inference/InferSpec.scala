package fr.imt.inference

import fr.imt.inference.AST.factory.ExpressionFactory.{App, Bool, Int, Lamb, Let, Var}
import org.scalatest.{Matchers, WordSpec}

class InferSpec extends WordSpec with Matchers {

  "The Lambda's infer method" should {
    "returns an ArrowType" in {
      // (\a -> a)
      val test = Var("a")
      val lambda = Lamb(Var("a"), Var("a"))

      val constraintCollection = new ConstraintCollection
      val environment = new Environment

      val rawReturnType = lambda.infer(environment, constraintCollection)

      // TODO

      print(environment)
      print(constraintCollection)

      true == true
    }
  }


}