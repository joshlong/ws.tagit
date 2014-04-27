// $ANTLR 3.5.2 ws/tagit/impl/Tag.g 2014-04-28 00:18:59

package ws.tagit.impl ;
import java.util.List ;
import java.util.ArrayList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class TagParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ATOMIC_STRING", "HASH_TAG", "QUOTE", 
		"SPACE"
	};
	public static final int EOF=-1;
	public static final int ATOMIC_STRING=4;
	public static final int HASH_TAG=5;
	public static final int QUOTE=6;
	public static final int SPACE=7;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public TagParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public TagParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return TagParser.tokenNames; }
	@Override public String getGrammarFileName() { return "ws/tagit/impl/Tag.g"; }



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



	public static class tags_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "tags"
	// ws/tagit/impl/Tag.g:54:1: tags : (x= HASH_TAG |x= ATOMIC_STRING |x= SPACE )+ ;
	public final TagParser.tags_return tags() throws RecognitionException {
		TagParser.tags_return retval = new TagParser.tags_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token x=null;

		CommonTree x_tree=null;

		try {
			// ws/tagit/impl/Tag.g:54:7: ( (x= HASH_TAG |x= ATOMIC_STRING |x= SPACE )+ )
			// ws/tagit/impl/Tag.g:54:9: (x= HASH_TAG |x= ATOMIC_STRING |x= SPACE )+
			{
			root_0 = (CommonTree)adaptor.nil();


			// ws/tagit/impl/Tag.g:54:9: (x= HASH_TAG |x= ATOMIC_STRING |x= SPACE )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=4;
				switch ( input.LA(1) ) {
				case HASH_TAG:
					{
					alt1=1;
					}
					break;
				case ATOMIC_STRING:
					{
					alt1=2;
					}
					break;
				case SPACE:
					{
					alt1=3;
					}
					break;
				}
				switch (alt1) {
				case 1 :
					// ws/tagit/impl/Tag.g:54:10: x= HASH_TAG
					{
					x=(Token)match(input,HASH_TAG,FOLLOW_HASH_TAG_in_tags172); 
					x_tree = (CommonTree)adaptor.create(x);
					adaptor.addChild(root_0, x_tree);

					 tags.add((x!=null?x.getText():null));  
					}
					break;
				case 2 :
					// ws/tagit/impl/Tag.g:55:5: x= ATOMIC_STRING
					{
					x=(Token)match(input,ATOMIC_STRING,FOLLOW_ATOMIC_STRING_in_tags182); 
					x_tree = (CommonTree)adaptor.create(x);
					adaptor.addChild(root_0, x_tree);

					 tags.add((x!=null?x.getText():null));  
					}
					break;
				case 3 :
					// ws/tagit/impl/Tag.g:55:45: x= SPACE
					{
					x=(Token)match(input,SPACE,FOLLOW_SPACE_in_tags188); 
					x_tree = (CommonTree)adaptor.create(x);
					adaptor.addChild(root_0, x_tree);

					 tags.add((x!=null?x.getText():null));  
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "tags"

	// Delegated rules



	public static final BitSet FOLLOW_HASH_TAG_in_tags172 = new BitSet(new long[]{0x00000000000000B2L});
	public static final BitSet FOLLOW_ATOMIC_STRING_in_tags182 = new BitSet(new long[]{0x00000000000000B2L});
	public static final BitSet FOLLOW_SPACE_in_tags188 = new BitSet(new long[]{0x00000000000000B2L});
}
