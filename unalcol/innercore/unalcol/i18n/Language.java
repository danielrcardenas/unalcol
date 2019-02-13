package unalcol.i18n;

import unalcol.collection.KeyMap;
import unalcol.collection.keymap.HashMap;

public class Language {
	public static final String DEFAULT = "default";
	
	protected String name;
	protected HashMap<String, String> codes = new HashMap<String,String>();
	
	public Language(){ this(DEFAULT); }

	public Language( String name ){ this.name = name; }
	
	public Language( String name, KeyMap<String, String> codes ){
		this.name = name;
		add(codes);
	}
	
	public String name(){ return name; }
	
	public String get( String code ){ try{ return codes.get(code); }catch(Exception e){ return null; } }
	
	public void set( String code, String msg ){ codes.set(code, msg); }
	
	public void add( KeyMap<String,String> codes ){ this.codes.merge(codes); }
}