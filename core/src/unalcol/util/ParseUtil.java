package unalcol.util;

public class ParseUtil {
	protected int pos;
	protected int length;
	protected String code;
	
	protected char c(){ return code.charAt(pos); }
	protected boolean end(){ return pos==code.length(); }
	
	public void remove_space(){	while( !end() && (c()==' ' || c()=='\n' || c()=='\t' || c()=='\r') ){ pos++; }	}
	
	public void remove_space_check_end() throws Exception{
		remove_space();
		if(end()) throw new Exception("End of object reached" );
	}
		
	protected Object number() throws Exception{ 
		int start = pos;
		if( c()=='-' ) pos++;
		if( end() ) throw new NumberFormatException(code.substring(start,pos));
		if( c()=='0' ){ pos++; }
		else{
			if( !Character.isDigit(c()) ) throw new NumberFormatException(code.substring(start,pos));
			pos++;
			while( !end() && Character.isDigit(c()) ) pos++;
		}
		this.length = pos-start;
		if( end() ) return Integer.parseInt(code.substring(start, pos));
			
		if( c()=='.' ){
			pos++;
			int counter=0;
			while( !end() && Character.isDigit(c()) ){
				counter++;
				pos++;
			}
			if( counter==0 ) throw new NumberFormatException(code.substring(start,pos));
		}
		if( !end() && (c()=='e' || c()=='E') ){
			pos++;
			if( end() ) throw new NumberFormatException(code.substring(start,pos));
			if(c()=='+' || c()=='-' ) pos++;
			if( end() ) throw new NumberFormatException(code.substring(start,pos));
			int counter=0;
			while( !end() && Character.isDigit(c()) ){
				counter++;
				pos++;
			}
			if( counter==0 ) throw new NumberFormatException(code.substring(start,pos));
		}
		this.length = pos-start;
		String s = code.substring(start, pos);
		try{ return Integer.parseInt(s); }catch(NumberFormatException e){}
		return Double.parseDouble(s);		
	}
	
	protected char character( boolean inside_string ) throws Exception{
		int start = pos;
		char c = c();
		if(c=='\\'){
			pos++;
			if(end()) throw new Exception("Invalid character "+ code.substring(start)+". End of input reached" );
			c = c();
			switch( c ){
				case '\'':
					if( inside_string ) throw new Exception("Invalid escape character \\' inside string." );
					pos++;
				break;
				case '\\':
					c = '\\';
					pos++;
				break;
				case '/':
					pos++;
				break;
				case 'b':
					c = '\b';
					pos++;
				break;
				case 'f':
					c = '\f';
					pos++;
				break;
				case 'n':
					c = '\n';
					pos++;
				break;
				case 'r':
					c = '\r';
					pos++;
				break;
				case 't':
					c = '\t';
					pos++;
				break;
				case 'u':
					pos++;
					if(pos+4>=code.length()) throw new Exception("Invalid unicode " + code.substring(pos));
					String uc = code.substring(pos,pos+4);
					try{
						int k=Integer.parseInt(uc,16);
						c = (char)k;
					}catch(NumberFormatException e){ throw new Exception("Invalid unicode " + uc); }
					pos += 4;
					
				break;
				default: throw new Exception("Invalid escape character \\' inside string."); 
			}
		}else{
			pos++;
		}
		this.length = pos-start;
		return c;
	}
	
	protected char character() throws Exception{
		int start = pos;
		if( c()!='\'' ) throw new Exception("Invalid character "+code.substring(pos,pos+1));
		pos++;
		char c = character(false);
		if(end()) throw new Exception("Invalid characer "+ code.substring(start)+". End of input reached" );
		if(c!='\'') throw new Exception("Invalid characer "+ code.substring(start,pos));
		pos++;
		this.length = pos-start;
		return c;
	}
	
	protected String string() throws Exception{
		int start = pos;
		if( c()!='"' ) throw new Exception("Invalid string "+code.substring(pos,pos+1));
		pos++;
		StringBuilder sb = new StringBuilder();
		while( c()!='"' ){
			char c = character(true);
			sb.append(c);
		}
		pos++;
		this.length = pos-start;
		return sb.toString();
	}
	
	public String string( String source, int beginIndex ) throws Exception{
		this.code = source;
		this.pos = beginIndex;
		return string();
	}
	
	public Object number( String source, int beginIndex ) throws Exception{
		this.code = source;
		this.pos = beginIndex;
		return number();
	}	
	
	public int consumed(){ return length; }
	
	public static String encode( String str ){
		StringBuilder sb = new StringBuilder();
		sb.append('"');
		for( int i=0; i<str.length(); i++ ){
			char c = str.charAt(i);
			switch( c ){
				case '/': sb.append("\\/"); break;	
				case '\\': sb.append("\\\\"); break;
				case '\b': sb.append("\\b"); break;
				case '\f': sb.append("\\f"); break;
				case '\n': sb.append("\\n"); break;
				case '\r': sb.append("\\r"); break;
				case '\t': sb.append("\\t"); break;
				case '\'': sb.append("\'"); break;
				case '"': sb.append("\\\""); break;
				default:
					if( c < 32 || c > 255 ){
						sb.append("\\u");
						sb.append(Integer.toHexString((int)c));
					}else sb.append(c);
				break;
			}
		}
		sb.append('"');
		return sb.toString();	
	} 	
}
