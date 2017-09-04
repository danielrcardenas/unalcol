package unalcol.instance;

import java.lang.reflect.Constructor;

import unalcol.services.TaggedMicroService;
import unalcol.types.tag.Tags;

public class DefaultInstance<T> extends Tags implements TaggedMicroService, Instance<T>{
	/**
	 * Generates an instance belonging to the class <i>type</i> according to the parameters (it does not support VarArgs constructors).
	 * @param type Class of instances that will be generated.
	 * @param args Arguments for creating an instance.
	 * @return An instance belonging to the class <i>type</i> using the parameters.
	 */
	@SuppressWarnings("unchecked")
	public T create( Class<T> type, Object... args ){
		Class<?>[] argsTypes = new Class<?>[args.length];
		for( int i=0; i<argsTypes.length; i++) argsTypes[i] = args[i].getClass();
		Constructor<?>[] c = type.getConstructors();
		Constructor<?> cc = null;
		// @TODO: Check var ags constructors.. 
		for( int i=0; i<c.length; i++ ){
			Class<?>[] parms = c[i].getParameterTypes();
			if( parms.length==args.length ){
				boolean assignable = true;
				boolean exact = true;
				for( int k=0; k<args.length && assignable; k++ ){
					assignable = parms[k].isAssignableFrom(argsTypes[k]);
					exact = parms[k] == argsTypes[k];
				}
				if( exact ) try{ return (T)c[i].newInstance(args); }catch(Exception e){}
				if( assignable ) cc = c[i];
			}
		}
		if( cc != null ) try{ return (T)cc.newInstance(args); }catch(Exception e){} 
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T create(Object... args) {
		Object obj = caller();
		if( obj instanceof Class<?> ) return create((Class<T>)obj, args);
		return create((Class<T>)obj.getClass(), args);
	}
}