import java.util.HashMap;

class q1 {

    static double SubstringFinder(char String1[], char String2[], int m, int n)
    {	double value = 0;
        for (double penalty=0.07; penalty<12.02; penalty=penalty+ 1.5)
        {   System.out.println("\n\n");
            double DMatrix[][] = new double[m + 1][n + 1];
            int SubStringLen[][] = new int[m + 1][n + 1];

            // ******************* length of the substring ********************
            value=0;
            int len = 0;
            int row=0;
            int column=0;

            //*********************Hashmap to store the values of alaphabets ***************
            HashMap<Character, Double > occurance = new HashMap<Character, Double>();
            occurance.put('E', 12.02);
            occurance.put('T', 9.1);
            occurance.put('A', 8.12);
            occurance.put('O', 7.68);
            occurance.put('I', 7.31);
            occurance.put('N', 6.95);
            occurance.put('S', 6.28);
            occurance.put('R', 6.02);
            occurance.put('H', 5.92);
            occurance.put('D', 4.32);
            occurance.put('L', 3.98);
            occurance.put('U', 2.88);
            occurance.put('C', 2.71);
            occurance.put('M', 2.61);
            occurance.put('F', 2.3);
            occurance.put('Y', 2.11);
            occurance.put('W', 2.09);
            occurance.put('G', 2.03);
            occurance.put('P', 1.82);
            occurance.put('B', 1.49);
            occurance.put('V', 1.11);
            occurance.put('K', 0.69);
            occurance.put('X', 0.17);
            occurance.put('Q', 0.11);
            occurance.put('J', 0.1);
            occurance.put('Z', 0.07);

            // ********************* bottom up building *****************
		long sTime = System.nanoTime();
          
		  for (int i = 0; i <= m; i++)
            {
                for (int j = 0; j <= n; j++)
                {
                    if (i == 0 || j == 0)
                        DMatrix[i][j] = 0;
                    else if (String1[i - 1] == String2[j - 1])
                    {
                        if(DMatrix[i-1][j-1]>0)
                        {   DMatrix[i][j]
                                = DMatrix[i - 1][j - 1] + occurance.get(String1[i-1]);
                            SubStringLen [i][j]=SubStringLen [i-1][j-1]+1;

                        }
                        else
                        {   DMatrix[i][j]
                                = 0 + occurance.get(String1[i-1]);

                            SubStringLen [i][j]=1;
                        }
                        double t=value;
                        value = Double.max (value, DMatrix[i][j]);

                        if (value > t)
                        {   len=SubStringLen[i][j];
                            row=i;
                            column=j;
                        }
                    }
                    else
                    {
                        DMatrix[i][j]= DMatrix[i - 1][j - 1] - penalty;
                        if (DMatrix[i][j] > 0.0)
                        {    SubStringLen [i][j]=SubStringLen [i-1][j-1]+1;
                        }
                        else
                        {    SubStringLen[i][j]=0;
                        }
                        value = Double.max (value, DMatrix[i][j]);
                    }
                }
            }
            System.out.println("Length of longest substring with penalties is: " + len);

            if (len == 0) {
                System.out.println("No Common Substring");
                return 0;
            }

            for (int i=row-len+1, j=column-len+1; i<=row && j<=column; i++,j++)
            {
                System.out.print((float)(Math.round(DMatrix[i][j]*100))/100+" ");
            }
            System.out.println();
            //********************* longest substring with penalties *************
            String valueStr1 = "";
            String valueStr2 = "";

            while (DMatrix[row][column] > 0) {
                valueStr1 = String1[row - 1] + valueStr1; // or String2[column-1]
                valueStr2 = String2[column - 1] + valueStr2; // or String2[column-1]
                --len;
                // moving up diagonally
                row--;
                column--;
            }

        System.out.println("Run Time in NanoSec: "+(System.nanoTime() - sTime));
            System.out.println(valueStr1);
            System.out.println(valueStr2);

            System.out.println("The highest occurance of common substing with penalty of "+penalty+" per mismatch is: "+(float)(Math.round (value*100)/100));
        }
        return Math.round (value*100)/100;
    }

    public static void main(String[] args)
    {

        String String1 = "ABCAABCAABBBDBCDHODSBBDB";
        String String2 = "ABBCAACCBBBBDBCBBBBBBBB";

        int m = String1.length();
        int n = String2.length();

        SubstringFinder(String1.toCharArray(), String2.toCharArray(), m, n);
    }
}