# Sensibilisation à la recherche

Groupe: Anaël CHARDAN / Clément GARBAY / Damien RAYMOND

L'article de recherche concerné est [Write you a haskell](http://dev.stephendiehl.com/fun/006_hindley_milner.html)

## Logs

L'ensemble des logs des différentes sessions et réunions est disponible [ici](https://github.com/anaelChardan/IMT-Recherche/blob/master/LOGS.md).


## Grammaire
Dans l'objectif de réaliser un parseur pour un REPL, nous avons créé un grammaire BNF qui représente le langage pour lequel nous validons le type.

```
<BasicExpression> ::=
    <Identifier> | <Literal> | <Application> | <Lambda> | <Let> | <ArithmeticOperation>

<Expression> ::=
    <BasicExpression> | '(' <BasicExpression> ')'

<Operator> ::=
    '+' | '-' | '*' | '/'

<ArithmeticOperation> ::=
    'op' <Expression> <Operator> <Expression>

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
    ('a'..'z' | 'A'..'Z' | '_')('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*
``` 

Here is an example of an expression that can be parsed :
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