package fr.imt.test.parser

import fr.imt.inference.ast.factory.ExpressionFactory.{App, Bool, Int, Lamb, Var, Let}
import fr.imt.parser.ExpressionParser
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}

class ExpressionParserSpec extends WordSpec with Matchers with BeforeAndAfter {

  "The ExpressionParser" should {
    "parse a boolean literal" in {
      val res = new ExpressionParser().parse("True")
      val expected = Bool(true)

      res.get() should equal (expected)
    }

    "parse an integer literal" in {
      val res = new ExpressionParser().parse("25")
      val expected = Int(25)

      res.get() should equal (expected)
    }

    "parse a lambda expression with one identifier" in {
      val res = new ExpressionParser().parse("\\ f -> f")
      val expected = Lamb(Var("f"), Var("f"))

      res.get() should equal (expected)
    }

    "parse a lambda expression with two identifiers" in {
      val res = new ExpressionParser().parse("\\ a b -> a")
      val expected = Lamb(Var("a"), Lamb(Var("b"), Var("a")))

      res.get() should equal (expected)
    }

    "parse a complex lambda expression with two identifiers" in {
      val res = new ExpressionParser().parse("\\ b a -> (app b True)")
      val expected = Lamb(Var("b"), Lamb(Var("a"), App(Var("b"), Bool(true))))

      res.get() should equal (expected)
    }

    "parse a complex expression (with let and app)" in {
      val res = new ExpressionParser().parse("let f = (\\x -> x) in app (app (\\a b -> b) (app f True)) (app f 1)")
      val expected = Let(Var("f"), Lamb(Var("x"), Var("x")), App(App(Lamb(Var("a"), Lamb(Var("b"), Var("b"))), App(Var("f"), Bool(true))), App(Var("f"), Int(1))))

      res.get() should equal (expected)
    }
  }

}
