
import java.util.Arrays;

class EqSolver
{
    double[] solve(double[][] matrix)   // 'matrix' must have dimensions N x (N+1)
    {
        double roots[] = new double[matrix.length], toSub;
        int i, j;
        
        echelonify(matrix);
        for(i = matrix.length-1; i >= 0; --i)
        {
            if(matrix[i][i] == 0)
                continue;
            toSub = 0;
            for(j = 0; j < matrix[i].length-1; ++j)
                if(j != i)
                    toSub += roots[j] * matrix[i][j];
            roots[i] = (matrix[i][matrix[i].length-1] - toSub) / matrix[i][i];
        }
        return roots;
    }
    
    void echelonify(double[][] matrix)
    {
        int i, j, k;    double factor;
        for(i = 0; i < matrix.length; ++i)
            for(j = 0; j <= i; ++j)
            {
                if((factor = matrix[i][j]) == 0)
                    continue;
                for(k = 0; k < matrix[i].length; ++k)
                    matrix[i][k] /= factor;
                if(i > j)
                    for(k = 0; k < matrix[i].length; ++k)
                        matrix[i][k] -= matrix[j][k];
            }
    }
    
    void printMatrix(double[][] ar)
    {
        for(int i = 0; i < ar.length; ++i)
            System.out.println(Arrays.toString(ar[i]));
        System.out.println();
    }
}
