package fr.imt.test.inference

import fr.imt.inference.SubstitutionCollection
import fr.imt.inference.`type`.{BooleanType, TypeVariable}
import org.scalatest.{Matchers, WordSpec}
import io.vavr.collection.HashMap

class SubstitutionCollectionSpec extends WordSpec with Matchers {

  "SubstitutionCollection" should {
    "be the same as another which is equals" in {
      val substitutionCollection1 =
        new SubstitutionCollection(HashMap.of(new TypeVariable("b"), new BooleanType))

      val substitutionCollection2 =
        new SubstitutionCollection(HashMap.of(new TypeVariable("b"), new BooleanType))

      substitutionCollection1 should be (substitutionCollection2)
    }

    "concat and apply substitution" in {
      val substitutionCollection1 =
        new SubstitutionCollection(HashMap.of(new TypeVariable("b"), new BooleanType))

      val substitutionCollection2 =
        new SubstitutionCollection(HashMap.of(new TypeVariable("a"), new TypeVariable("b")))

      val substitutionCollectionExpected =
        new SubstitutionCollection(
          HashMap.of(
            new TypeVariable("a"), new BooleanType,
            new TypeVariable("b"), new BooleanType))


      substitutionCollection1.concat(substitutionCollection2) should be (substitutionCollectionExpected)
    }
  }

}
