package optimization.hillclimbing;

import optimization.EncodeTest;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.binary.BitMutation;
import unalcol.optimization.method.AdaptOperatorOptimizationFactory;
//import unalcol.optimization.binary.testbed.MaxOnes;
import unalcol.optimization.method.OptimizationFactory;
import unalcol.optimization.integer.MutationIntArray;
import unalcol.optimization.real.BinaryToRealVector;
import unalcol.optimization.real.HyperCube;
import unalcol.optimization.real.mutation.Mutation;
import unalcol.optimization.real.mutation.OneFifthRule;
//import unalcol.optimization.real.testbed.Rastrigin;
import unalcol.search.Search;
import unalcol.search.local.LocalSearch;
import unalcol.search.multilevel.CodeDecodeMap;
import unalcol.search.multilevel.MultiLevelSearch;
import unalcol.search.space.Space;
import unalcol.testbed.optimization.FunctionTestBed;
import unalcol.testbed.optimization.real.basic.BasicFunctionTestBed;
import unalcol.testbed.optimization.real.lsgo.LSGOFunction;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.types.object.Tagged;

public class HillClimbingTest{
	// ******* Binary space problem ******** //
	public static void binary(){
		// Search Space definition
		Space<BitArray> space = EncodeTest.binary_space();
    	
    	// Optimization Function
    	OptimizationFunction<BitArray> function = EncodeTest.binary_f();   	
    	
    	// Variation definition
    	BitMutation variation = EncodeTest.binary_mutation();
        
        // Search method
        int MAXITERS = 150;
        boolean neutral = true; // Accepts movements when having same function value
        boolean adapt_operator = true; //
        LocalSearch<BitArray,Double> search;
        if( adapt_operator ){
        	OneFifthRule adapt = new OneFifthRule(20, 0.9); // One Fifth rule for adapting the mutation parameter
        	AdaptOperatorOptimizationFactory<BitArray,Double> factory = new AdaptOperatorOptimizationFactory<BitArray,Double>();
        	search = factory.hill_climbing( function, variation, adapt, neutral, MAXITERS );
        }else{
        	OptimizationFactory<BitArray> factory = new OptimizationFactory<BitArray>();
        	search = factory.hill_climbing( function, variation, neutral, MAXITERS );
        }

        // Apply the search method
        // Services
        EncodeTest.binary_service(function, search);
        // Apply the search method
        Tagged<BitArray> sol = search.solve(space);        
        System.out.println(sol.getTag(function));		
	}
	
	public static void binary2real(){
		// Search Space definition
		// Search space
		int DIM=10;
    	// Optimization Function
    	FunctionTestBed<double[]> function = new BasicFunctionTestBed(0,DIM);
    	HyperCube space = (HyperCube)function.space();    	
		
        // CodeDecodeMap
        int BITS_PER_DOUBLE = 16; // Number of bits per integer (i.e. per real)
        CodeDecodeMap<BitArray, double[]> map = 
        		new BinaryToRealVector(BITS_PER_DOUBLE, space.min(), space.max());

    	// Variation definition in the binary space
    	BitMutation variation = EncodeTest.binary_mutation();
        
        // Search method in the binary space
        int MAXITERS = 10000;
        boolean neutral = true; // Accepts movements when having same function value
        boolean adapt_operator = true; //
        LocalSearch<BitArray,Double> bin_search;
        if( adapt_operator ){
        	OneFifthRule adapt = new OneFifthRule(20, 0.9); // One Fifth rule for adapting the mutation parameter
        	AdaptOperatorOptimizationFactory<BitArray,Double> factory = new AdaptOperatorOptimizationFactory<BitArray,Double>();
        	bin_search = factory.hill_climbing( null, variation, adapt, neutral, MAXITERS );
        }else{
        	OptimizationFactory<BitArray> factory = new OptimizationFactory<BitArray>();
        	bin_search = factory.hill_climbing( null, variation, neutral, MAXITERS );
        }

        // The multilevel search method (moves in the binary space, but computes fitness in the real space)
        Search<double[], Double> search = new MultiLevelSearch<>(bin_search, map);
        search.setGoal(function);
        
        // Services
        EncodeTest.binary2real_service(function, search);
        // Apply the search method
        Tagged<double[]> sol = search.solve(space);        
        System.out.println(sol.getTag(function));		
	}
	
	public static void queen(){
		// It is the well-known problem of setting n-queens in a chess board without attacking among them
		// Search Space definition
		int DIM = 8; // Board size		
		Space<int[]> space = EncodeTest.queen_space(DIM);
    	// Optimization Function
    	OptimizationFunction<int[]> function = EncodeTest.queen_f();		    	
    	// Variation definition
    	MutationIntArray variation = EncodeTest.queen_variation(DIM);
        
        // Search method
        int MAXITERS = 200;
        boolean neutral = true; // Accepts movements when having same function value
        boolean adapt_operator = true; //
        LocalSearch<int[],Double> search;
        if( adapt_operator ){
        	OneFifthRule adapt = new OneFifthRule(20, 0.9); // One Fifth rule for adapting the mutation parameter
        	AdaptOperatorOptimizationFactory<int[],Double> factory = new AdaptOperatorOptimizationFactory<int[],Double>();
        	search = factory.hill_climbing( function, variation, adapt, neutral, MAXITERS );
        }else{
        	OptimizationFactory<int[]> factory = new OptimizationFactory<int[]>();
        	search = factory.hill_climbing( function, variation, neutral, MAXITERS );
        }

        // Tracking the goal evaluations
        // Apply the search method
        // Services
        EncodeTest.queen_service(function, search);
        // Apply the search method
        Tagged<int[]> sol = search.solve(space);        
        System.out.println(sol.getTag(function));		
	}
    
    public static void main(String[] args){
    	real(); // Uncomment if testing real valued functions
    	//binary(); // Uncomment if testing binary valued functions
    	//binary2real(); // Uncomment if you want to try the multi-level search method
    	//queen(); // Uncomment if testing queens (integer) value functions
    }
}