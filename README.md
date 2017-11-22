# Sensibilisation à la recherche

Groupe: Anaël CHARDAN / Clément GARBAY / Damien RAYMOND

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
    <Expression> <Expression>
    
<Lambda> ::= 
    '(\' (<Variable>)+ '->' <Expression> ')'
    
<Let> ::= 
    'let' <Variable> '=' <Expression> 'in' <Expression>
    
<Literal> ::= 
    <BoolLiteral> | <IntLiteral> 
    
<BoolLiteral> ::= 
    'True' | 'False'

<IntLiteral> ::= 
    ('1'..'9')('0'..'9')+
    
<Variable> ::= 
    ('a'..'z' | 'A'..'Z' | '_')('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*
``` 
