package fr.imt.inference

import fr.imt.inference.AST.factory.ExpressionFactory.{Lamb, Var}
import org.scalatest.{Matchers, WordSpec}

class VariableSpec extends WordSpec with Matchers {

  "The Variable's equal method" should {
    "returns true with another Variable with the same name" in {
      val var1 = Var("a").hashCode()
      val var2 = Var("a").hashCode()
      print(var1, var2) // (2065857933,1914301543)
      Var("a") == Var("a")
    }
  }


}