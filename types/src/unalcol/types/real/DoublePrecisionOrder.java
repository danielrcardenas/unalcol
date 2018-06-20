package unalcol.types.real;


/**
 * <p>Doubles considering the double precision defined in DoubleUtil class</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 * 
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */

public class DoublePrecisionOrder extends DoubleOrder{
    /**
     * Determines if the first Double is less than (in some order) the second Double (one<two) considering the precision
     * defined in DoubleUtil class
     * @param one First Double
     * @param two Second Double
     * @return (one<two)
     */
    public int compare(Double one, Double two){ return ( DoubleUtil.equal(one, two) )?0:one.compareTo(two); }    

	@Override
	public String toString(){ return "DoublePrecisonOrder"; }
}