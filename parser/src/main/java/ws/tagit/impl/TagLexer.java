// $ANTLR 3.5.2 ws/tagit/impl/Tag.g 2014-04-28 00:18:59

package ws.tagit.impl;
import java.util.List ;
import java.util.ArrayList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class TagLexer extends Lexer {
	public static final int EOF=-1;
	public static final int ATOMIC_STRING=4;
	public static final int HASH_TAG=5;
	public static final int QUOTE=6;
	public static final int SPACE=7;



	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public TagLexer() {} 
	public TagLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public TagLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "ws/tagit/impl/Tag.g"; }

	// $ANTLR start "ATOMIC_STRING"
	public final void mATOMIC_STRING() throws RecognitionException {
		try {
			int _type = ATOMIC_STRING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ws/tagit/impl/Tag.g:50:15: ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+ | '\"' (~ '\"' )* '\"' | '\\'' (~ '\\'' )* '\\'' )
			int alt4=3;
			switch ( input.LA(1) ) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				{
				alt4=1;
				}
				break;
			case '\"':
				{
				alt4=2;
				}
				break;
			case '\'':
				{
				alt4=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}
			switch (alt4) {
				case 1 :
					// ws/tagit/impl/Tag.g:50:18: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+
					{
					// ws/tagit/impl/Tag.g:50:18: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )+
					int cnt1=0;
					loop1:
					while (true) {
						int alt1=2;
						int LA1_0 = input.LA(1);
						if ( ((LA1_0 >= '0' && LA1_0 <= '9')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
							alt1=1;
						}

						switch (alt1) {
						case 1 :
							// ws/tagit/impl/Tag.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
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
					break;
				case 2 :
					// ws/tagit/impl/Tag.g:50:52: '\"' (~ '\"' )* '\"'
					{
					match('\"'); 
					// ws/tagit/impl/Tag.g:50:56: (~ '\"' )*
					loop2:
					while (true) {
						int alt2=2;
						int LA2_0 = input.LA(1);
						if ( ((LA2_0 >= '\u0000' && LA2_0 <= '!')||(LA2_0 >= '#' && LA2_0 <= '\uFFFF')) ) {
							alt2=1;
						}

						switch (alt2) {
						case 1 :
							// ws/tagit/impl/Tag.g:
							{
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop2;
						}
					}

					match('\"'); 
					}
					break;
				case 3 :
					// ws/tagit/impl/Tag.g:50:74: '\\'' (~ '\\'' )* '\\''
					{
					match('\''); 
					// ws/tagit/impl/Tag.g:50:79: (~ '\\'' )*
					loop3:
					while (true) {
						int alt3=2;
						int LA3_0 = input.LA(1);
						if ( ((LA3_0 >= '\u0000' && LA3_0 <= '&')||(LA3_0 >= '(' && LA3_0 <= '\uFFFF')) ) {
							alt3=1;
						}

						switch (alt3) {
						case 1 :
							// ws/tagit/impl/Tag.g:
							{
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop3;
						}
					}

					match('\''); 
					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ATOMIC_STRING"

	// $ANTLR start "QUOTE"
	public final void mQUOTE() throws RecognitionException {
		try {
			int _type = QUOTE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ws/tagit/impl/Tag.g:51:8: ( ( '\\'' | '\"' ) )
			// ws/tagit/impl/Tag.g:
			{
			if ( input.LA(1)=='\"'||input.LA(1)=='\'' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "QUOTE"

	// $ANTLR start "SPACE"
	public final void mSPACE() throws RecognitionException {
		try {
			int _type = SPACE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ws/tagit/impl/Tag.g:52:8: ( ' ' | ( ' ' )* ',' ( ' ' )* )
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==' ') ) {
				int LA7_1 = input.LA(2);
				if ( (LA7_1==' '||LA7_1==',') ) {
					alt7=2;
				}

				else {
					alt7=1;
				}

			}
			else if ( (LA7_0==',') ) {
				alt7=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}

			switch (alt7) {
				case 1 :
					// ws/tagit/impl/Tag.g:52:10: ' '
					{
					match(' '); 
					}
					break;
				case 2 :
					// ws/tagit/impl/Tag.g:52:15: ( ' ' )* ',' ( ' ' )*
					{
					// ws/tagit/impl/Tag.g:52:15: ( ' ' )*
					loop5:
					while (true) {
						int alt5=2;
						int LA5_0 = input.LA(1);
						if ( (LA5_0==' ') ) {
							alt5=1;
						}

						switch (alt5) {
						case 1 :
							// ws/tagit/impl/Tag.g:52:15: ' '
							{
							match(' '); 
							}
							break;

						default :
							break loop5;
						}
					}

					match(','); 
					// ws/tagit/impl/Tag.g:52:22: ( ' ' )*
					loop6:
					while (true) {
						int alt6=2;
						int LA6_0 = input.LA(1);
						if ( (LA6_0==' ') ) {
							alt6=1;
						}

						switch (alt6) {
						case 1 :
							// ws/tagit/impl/Tag.g:52:22: ' '
							{
							match(' '); 
							}
							break;

						default :
							break loop6;
						}
					}

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SPACE"

	// $ANTLR start "HASH_TAG"
	public final void mHASH_TAG() throws RecognitionException {
		try {
			int _type = HASH_TAG;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ws/tagit/impl/Tag.g:53:9: ( '#' ( 'a' .. 'z' | 'A' .. 'Z' )+ )
			// ws/tagit/impl/Tag.g:53:12: '#' ( 'a' .. 'z' | 'A' .. 'Z' )+
			{
			match('#'); 
			// ws/tagit/impl/Tag.g:53:16: ( 'a' .. 'z' | 'A' .. 'Z' )+
			int cnt8=0;
			loop8:
			while (true) {
				int alt8=2;
				int LA8_0 = input.LA(1);
				if ( ((LA8_0 >= 'A' && LA8_0 <= 'Z')||(LA8_0 >= 'a' && LA8_0 <= 'z')) ) {
					alt8=1;
				}

				switch (alt8) {
				case 1 :
					// ws/tagit/impl/Tag.g:
					{
					if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt8 >= 1 ) break loop8;
					EarlyExitException eee = new EarlyExitException(8, input);
					throw eee;
				}
				cnt8++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "HASH_TAG"

	@Override
	public void mTokens() throws RecognitionException {
		// ws/tagit/impl/Tag.g:1:8: ( ATOMIC_STRING | QUOTE | SPACE | HASH_TAG )
		int alt9=4;
		switch ( input.LA(1) ) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
		case 'H':
		case 'I':
		case 'J':
		case 'K':
		case 'L':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'S':
		case 'T':
		case 'U':
		case 'V':
		case 'W':
		case 'X':
		case 'Y':
		case 'Z':
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt9=1;
			}
			break;
		case '\"':
			{
			int LA9_2 = input.LA(2);
			if ( ((LA9_2 >= '\u0000' && LA9_2 <= '\uFFFF')) ) {
				alt9=1;
			}

			else {
				alt9=2;
			}

			}
			break;
		case '\'':
			{
			int LA9_3 = input.LA(2);
			if ( ((LA9_3 >= '\u0000' && LA9_3 <= '\uFFFF')) ) {
				alt9=1;
			}

			else {
				alt9=2;
			}

			}
			break;
		case ' ':
		case ',':
			{
			alt9=3;
			}
			break;
		case '#':
			{
			alt9=4;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 9, 0, input);
			throw nvae;
		}
		switch (alt9) {
			case 1 :
				// ws/tagit/impl/Tag.g:1:10: ATOMIC_STRING
				{
				mATOMIC_STRING(); 

				}
				break;
			case 2 :
				// ws/tagit/impl/Tag.g:1:24: QUOTE
				{
				mQUOTE(); 

				}
				break;
			case 3 :
				// ws/tagit/impl/Tag.g:1:30: SPACE
				{
				mSPACE(); 

				}
				break;
			case 4 :
				// ws/tagit/impl/Tag.g:1:36: HASH_TAG
				{
				mHASH_TAG(); 

				}
				break;

		}
	}



}
