# Sensibilisation à la recherche

Groupe: Anaël CHARDAN / Clément GARBAY / Damien RAYMOND

L'article de recherche concerné est [Write you a haskell](http://dev.stephendiehl.com/fun/006_hindley_milner.html)

## Logs

L'ensemble des logs des différentes sessions et réunions est disponible [ici](https://github.com/anaelChardan/IMT-Recherche/blob/master/LOGS.md).


## Grammaire
Dans l'objectif de réaliser un parseur pour un REPL, nous avons créé un grammaire BNF qui représente le langage pour lequel nous validons le type.

```
<Program> ::= 
    <Expression> | '(' <Expression> ')'
    
<Expression> ::= 
    <Application> | <Lambda> | <Let> | <Literal>
    
<Application> ::= 
    (<Program> | <Identifier>) <Program>
    
<Lambda> ::= 
    '\' (<Identifier>)+ '->' <Program>
    
<Let> ::= 
    'let' <Identifier> '=' <Program> 'in' <Program>
    
<Literal> ::= 
    <BoolLiteral> | <IntLiteral> 
    
<BoolLiteral> ::= 
    'True' | 'False'

<IntLiteral> ::= 
    ('1'..'9')('0'..'9')+
    
<Identifier> ::= 
    ('a'..'z' | 'A'..'Z' | '_')('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*
``` 

Here are some example of expression that can be parsed :
```
let f = (\x -> x) in (\a b -> b) (f True) (f 1)

    f => Identifier
    
    (\x -> x) => Program
        \x -> x => Lambda
            x => Identifier
            x => Identifier
            
    (\a b -> b) (f True) (f 1) => Application
        (\a b -> b) (f True) => Application
            (\a b -> b) => Program
                \a b -> b => Lambda
                    a => Identifier
                    b => Identifier
                    b => Identifier
            (f True) => Program
                f True => Application
                    f => Identifier
                    True => BoolLiteral
                
        (f 1) => Program
            f 1 => Application
                f => Identifier
                1 => IntLiteral
        
    
```