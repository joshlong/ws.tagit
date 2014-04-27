grammar Tag;

options
{
 language=Java;
 output=AST;
 ASTLabelType=CommonTree;
}

@lexer::members {
}

@parser::members {

    private List <String> tags = new ArrayList<String>() ;

    private Object as=null;

    public List <String> getParsedTags()     {
    try {
        this.tags();
        List <String> nStrings = new ArrayList<String>() ;
        for(String t :  tags ){
            String nt = t == null? "":t.trim();
            boolean isSep = nt.equals("")|| nt.equals(",");
            if(!isSep ){
                nStrings.add( nt.trim() ) ;
            }
        }
        return nStrings;
        } catch ( java.lang.Exception e) {
         throw new RuntimeException(e);
        }
    }

}

@lexer::header {
package ws.tagit.impl;
import java.util.List ;
import java.util.ArrayList;
}

@parser::header {
package ws.tagit.impl ;
import java.util.List ;
import java.util.ArrayList;
}

ATOMIC_STRING :  ('a'..'z'|'A'..'Z'|'0'..'9')+ |   '"' (~'"')* '"'   |   '\'' (~'\'')* '\'';
QUOTE 	: ('\''|'"');
SPACE 	: ' '| ' '*','' '*;
HASH_TAG:	 '#' ('a'..'z'|'A'..'Z')+   ;
tags	 : (x=HASH_TAG { tags.add($x.text);  }
		| x=ATOMIC_STRING { tags.add($x.text);  }|x=SPACE { tags.add($x.text);  })+  ;