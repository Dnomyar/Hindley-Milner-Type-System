# Sensibilisation à la recherche

Ce projet est une implémentation de l'algorithme de Hindley Milner en Java.
Ce travail se base sur une analyse du chapitre 7 "Hindley-Milner Inference" de [Write you a haskell](http://dev.stephendiehl.com/fun/006_hindley_milner.html).

## Auteurs

- Anaël CHARDAN
- Clément GARBAY
- Damien RAYMOND

## Logs

L'ensemble des logs des différentes sessions et réunions est disponible [ici](https://github.com/anaelChardan/IMT-Recherche/blob/master/LOGS.md).


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

## REPL

### Commandes

- Pour quitter `:q`
- Pour afficher l'aide `:h`
- Pour activer/désactiver les informations des étapes d'inférence et d'unification `:log`