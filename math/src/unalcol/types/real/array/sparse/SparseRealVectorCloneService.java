/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.types.real.array.sparse;

import unalcol.clone.Clone;
import unalcol.services.MicroService;
import unalcol.services.Service;
import unalcol.types.collection.sparse.vector.SparseVector;

/**
 *
 * @author jgomez
 */
public class SparseRealVectorCloneService extends MicroService<SparseRealVector> implements Clone<SparseRealVector>{
    public SparseRealVectorCloneService() {}

    /**
     * Clones a Java Vector
     * @param obj The Java Vector to be cloned
     * @return A clone of the Java Vector
     */
    @SuppressWarnings("unchecked")
	@Override
    public SparseRealVector clone(){
    	SparseRealVector obj = caller();
        SparseRealVector x = new SparseRealVector( obj.dim() );
        try{
        	x.values = (SparseVector<Double>)Service.run(Clone.name, obj.values);
        }catch(Exception e){ x.values = obj.values;  }	
        return x;
    }    
}