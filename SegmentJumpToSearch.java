import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.HashMap; // import the HashMap class

public class q2 {

    HashMap<Int, Float> Optimal = new HashMap<Int, Float>();
    public static void segmented_least_square(float[] yp, int penalty){
        int n = yp.length;

//****************************** setting X Y coordinated *****************
        Arrays.sort(yp); // sort y
        float[][] points= new float [n][2];
        for (int i=0;i<n;i++)
        {   points [i][0]=i;
            points [i][1]=yp[i];
        }
//********************************* To print coordinates ******************
// 		for (int i=0;i<n;i++)
//        {   System.out.print(points[i][0] +"  ");
//            System.out.print(points [i][1]);
//            System.out.println();
//        }       


        float[] ysum = new float[n+1]; // sum of y coordinates sotred in matrix
        float[] sumsqy = new float[n]; // sum of squares of y coordinates sotred in matrix

        ysum[0] = points[0][1];
        float k;
        double max, temp;
//to calculate runtime
        long sTime = System.nanoTime();

//sum of y coordinates calculated
        for(int i=1; i<n; i++)
            ysum[i] = ysum[i-1]+points[i-1][1];

//sum of squares calculated
        sumsqy[0]=points[0][1]*points[0][1];
        for(int i=1; i<n; i++)
            sumsqy[i] = sumsqy[i-1]+(points[i][1]*points[i][1]);

//******************** Creating Dynamic error square matrix ***************
        float[][] err = new float[n+1][n+1];

        for(int j=0; j<n; j++){
            for(int i=0; i<j; i++){
                float sy = ysum[j]-ysum[i];
                float sqsy = sumsqy[j]-sumsqy[i];
                int size = j-i+1;
                err [i][j]= Math.abs(sqsy - (float)((sy*sy)/size)); // formula summation(y_i^2) - summation (y_i)^2 * 1/n
            }
        }
//*****************************TO print the error Matrix*********************
/*        for(int j=1; j<n; j++) {
            for (int i = 1; i <= j; i++) {
                System.out.print(err[i][j] + "  ");
            }
            System.out.println("");
        }
*/
        double[] optimal = new double[n+1];
        double[] optimal_segment = new double[n+1];

        for(int j=0; j<n; j++){
            max=Float.MAX_VALUE;
            k=0;
            optimal[0]=0;
            for(int i=1; i<=j; i++){
                temp = err[i-1][j] + optimal[i-1] + penalty;
                if(temp<max){
                    max = temp;
                    k = i;
                }
            }
            optimal[j] = max;
            Optimal.put(j, max);
            optimal_segment[j+1] = k;
        }

        System.out.println("Run Time in NanoSec: "+(System.nanoTime() - sTime));


//*********************** Storing the start and end segment parts ***********************
        ArrayDeque<Integer> segments = new ArrayDeque<>();
        int i,j;
        for (i = n, j = (int)optimal_segment[n]; i > 0; i = j-1, j = (int)optimal_segment[i])	{
            segments.push(i);
            segments.push(j);
        }

        int count=1;

//************************* To Print the Solution **********************
        System.out.println("Solution :");
        while (!segments.isEmpty())	{
            i = segments.pop();
            j = segments.pop();
            System.out.println("Segment "+count+": From " +i+ " to "+j+"    error square: "+err[i-1][j-1]);
            count++;
        }
        System.out.println("Total cost: "+optimal[n-1]);

    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of points: ");
        int n = sc.nextInt();
        int penalty = 100;	// Segment Penalty
        System.out.println("Set Penalty: "+ penalty );

        float [] ypoints = new float[n];
        Random rd = new Random(); // creating Random object

        for(int i=1; i<n; i++){
            ypoints[i] = ypoints[i-1] + rd.nextFloat();
        }

        segmented_least_square(ypoints, penalty);
    }
}
