package optimization;
import unalcol.descriptors.WriteDescriptors;
import unalcol.optimization.OptimizationFunction;
import unalcol.integer.Array;
import unalcol.integer.HyperCube;
import unalcol.integer.Mutation;
import unalcol.testbed.optimization.integer.QueenFitness;
import unalcol.search.Search;
import unalcol.search.solution.SolutionDescriptors;
import unalcol.search.space.Space;
import unalcol.services.Service;
import unalcol.integer.array.PlainWrite;
import unalcol.object.Tagged;

public class IntTest {
	
	// Queen problem
	public static Space<int[]> queen_space( int DIM ){
		// It is the well-known problem of setting n-queens in a chess board without attacking among them
		// Search Space definition
		int[] min = Array.create(DIM, 0); // First possible row index
		int[] max = Array.create(DIM, DIM-1); // Last possible row index
    	return new HyperCube( min, max );
	}
	
	public static OptimizationFunction<int[]> queen_f(){
    	// Optimization Function
    	OptimizationFunction<int[]> function = new QueenFitness();
    	function.minimize(true);
    	return function;
	}
	
	public static Mutation queen_variation(int DIM){
    	// Variation definition
    	return new Mutation(DIM);
	}
	
	public static void queen_service(OptimizationFunction<int[]> function, Search<int[], Double> search){
		// Tracking the goal evaluations
        Service.register(new SolutionDescriptors<int[]>(function), Tagged.class);
        Service.register(new PlainWrite(',',false), int[].class);
        Service.register(new WriteDescriptors(), Tagged.class);
	}
}