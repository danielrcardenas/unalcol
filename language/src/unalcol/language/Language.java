package unalcol.language;

import unalcol.io.reader.CharReader;
import unalcol.language.generalized.GeneralizedLanguage;

public interface Language<T> extends GeneralizedLanguage<T,Integer>{
	public default T process( String reader ) throws LanguageException{ return process(reader,0); }
	public default T process( String reader, int src ) throws LanguageException{ return process(new CharReader(reader, src)); }
}