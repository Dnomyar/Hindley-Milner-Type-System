# Sensibilisation à la recherche

Ce projet est une implémentation de l'algorithme de Hindley Milner en Java.
Ce travail se base sur une analyse du chapitre 7 "Hindley-Milner Inference" de [Write you a haskell](http://dev.stephendiehl.com/fun/006_hindley_milner.html).

## Auteurs

- Anaël CHARDAN
- Clément GARBAY
- Damien RAYMOND

## Logs

L'ensemble des logs des différentes sessions et réunions est disponible [ici](https://github.com/anaelChardan/IMT-Recherche/blob/master/LOGS.md).

## REPL

### Commandes

- Pour quitter `:q`
- Pour afficher l'aide `:h`
- Pour activer/désactiver les informations des étapes d'inférence et d'unification `:log`

## Grammaire
Dans l'objectif de réaliser un parseur pour un REPL, nous avons créé un grammaire BNF qui représente le langage pour lequel nous validons le type.

```
<BasicExpression> ::=
    <Identifier> | <Literal> | <Application> | <Lambda> | <Let> | <BinaryExpression> | <If>

<Expression> ::=
    <BasicExpression> | '(' <BasicExpression> ')'

<ArithmeticOperator> ::=
    '+' | '-' | '*' | '/'

<ArithmeticOperation> ::=
    'op' <Expression> <ArithmeticOperator> <Expression>

<EqualityOperator> ::=
    '=='

<Condition> ::=
    'con' <Expression> <EqualityOperator> <Expression>

<BinaryExpression> ::=
    <ArithmeticOperation> | <Condition>

<If> ::=
    'if' <Condition> 'then' <Expression> 'else' <Expression>

<Application> ::=
    'app' <Expression> <Expression>
    
<Lambda> ::= 
    '\' (<Identifier>)+ '->' <Expression>
    
<Let> ::= 
    'let' <Identifier> '=' <Expression> 'in' <Expression>
    
<Literal> ::= 
    <BoolLiteral> | <IntLiteral> 
    
<BoolLiteral> ::= 
    'True' | 'False'

<IntLiteral> ::= 
    ('1'..'9')('0'..'9')+
    
<Identifier> ::= 
    ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')*
``` 

Voici un exemple d'expression qui peut être analysée :
```
let f = (\x -> x) in (app (app (\a b -> b) (app f True)) (app f 1))

    f => Identifier
    
    (\x -> x) => Expression
        \x -> x => Lambda
            x => Identifier
            x => Identifier
            
    app (app (\a b -> b) (app f True)) (app f 1) => Application
        app (\a b -> b) (app f True) => Application
            (\a b -> b) => Expression
                \a b -> b => Lambda
                    a => Identifier
                    b => Identifier
                    b => Identifier
            (app f True) => Expression
                f True => Application
                    f => Identifier
                    True => BoolLiteral
               
        (app f 1) => Expression
            app f 1 => Application
                f => Identifier
                1 => IntLiteral
        
    
```

## Organisation

Le projet essaie de respecter au maximum le programme original écrit en Haskell.

Nous avons donc dans l'organisation:

Un package `inference` qui contient toute la logique de l'inférence de type.

* Dans celui-ci, on peut retrouver un package `ast` qui lui va contenir tout les éléments qui composent une expression à savoir

![inference](./IMAGES/inference.png)
    - Application / Lambda / Let / Literal / Variable / **ArithmeticOperation / If**
    - Ces éléments sont ceux qui sont inférables (retourne un type) et qui sont représentés [ici](https://github.com/sdiehl/write-you-a-haskell/blob/master/chapter7/poly_constraints/src/Infer.hs#L163) en Haskell
* On retrouve également un package `type` qui contient tous les types qui vont pouvoir être inférés
    - ArrowType / BooleanType / Integer / Boolean / Literal / Variable
    - Ces types sont généralizable c'est à dire, Ils peuvent être convertie en un autre type en fermant toutes les variables libres dans un schéma de type.
    - Dans ce package on retrouve aussi le Schema qui contient un type et les variables de type à partir de ses propriétés, il est capable d'instantier des nouvelles variables de types fraiche.
* En dehors de ce package, on retrouve la résolution des contraintes afin de rendre le type trouvé.
    - Le type est trouvé grâce au processus d'unification, c'est à dire résoudre les contraintes en application les subsitutions nécessaires.
* De plus, nous avons intégré un parser en utilisant la bibliothèque [parsecj](https://github.com/jon-hanson/parsecj) et en définissant nos propres règles définies dans la grammaire ci-dessus.
* Ce parser est utilisé dans un REPL afin de pouvoir tester des expressions très rapidement.
* Nous avons également utilisé [Vavr](http://www.vavr.io/) qui permet d'avoir une écriture plus *fonctionnelle* sur les collections que ce qui est proposé de base par les streams de Java.

Pour finir voici le flow d'exécution de notre algorithme. 

![sequence](./IMAGES/sequence.jpg)