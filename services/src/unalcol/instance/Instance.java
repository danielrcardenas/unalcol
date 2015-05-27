package unalcol.instance;

import unalcol.service.Service;

/**
 * <p>Instance generator.</p>
 *
 * <p>Copyright: Copyright (c) 2015</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 * @param <T> Type of objects from which instances will be generated
 */
public abstract class Instance<T> extends Service {
    /**
     * Creates an instance belonging to the class of the given object
     * @param obj Object used for determining the class from which a new instance will be generated
     * @return An instance belonging to the class of the given object if possible, <i>null</i> otherwise
     */
    public abstract T get( T obj );
    
    /**
     * Creates an instance belonging to the class of the given object
     * @param obj Object used for determining the class from which a new instance will be generated
     * @return An instance belonging to the class of the given object if possible, <i>null</i> otherwise
     */
    @SuppressWarnings("unchecked")
	public static Object create( Object obj ){
        Instance<?> service = (Instance<?>)get(Instance.class,obj);
        if( service != null ){
            return ((Instance<Object>)service).get(obj);
        }
        return null;
    }    
}