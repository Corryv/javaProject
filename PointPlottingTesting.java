public class PointPlottingTesting {
    public static void main(String[] args) {

        //-------------------------------------------------------------------------------------------------------
        //instantiate variables
        //linear
        double M = 1.0;
        double B = 1.0;

        //quadratic
        double a = 1.0;
        double b = 4.0;
        double c = 3.0;


        //-------------------------------------------------------------------------------------------------------
        // calculate all points
        // X can be made into smaller bit to increment on a more precise scale
        for (double x = -50; x<50 ; x++){
            //instantiate y
            double y = 1;

            //solve quadratic for y
            y = (a*x*x) + (b*x) + c;

            System.out.printf("X=%.2f    Y=%.2f\n", x, y);
            }



        //-------------------------------------------------------------------------------------------------------
        //special values instantiated
        double Xintercept1= 1.0;
        double Xintercept2= 1.0;
        double Yintercept1= c ;
        double VertexX = 1.0;
        double VertexY = 1.0;

        System.out.println("\n\n___________ special figures ____________");
        System.out.printf("Y intercept = %f\n",Yintercept1);


        //-------------------------------------------------------------------------------------------------------
        //to find x intercepts or lack thereof

        double discriminant = (b * b) - (4 * a * c);

        if (discriminant >= 0) {
            Xintercept1 = ((-b) + Math.sqrt(discriminant)) / (2 * a);
            Xintercept2 = ((-b) - Math.sqrt(discriminant)) / (2 * a);

            System.out.printf("Xintercept1 = %.3f\n", Xintercept1);
            System.out.printf("Xintercept2 = %.3f\n", Xintercept2);
        }
        else {
            System.out.println("No real x-intercepts (discriminant < 0)\n");
        }


        //-------------------------------------------------------------------------------------------------------
        //to find axis of symetry-  the min or max of a square function
        // x = -b / (2a)

        VertexX = -b / (2* a);
        VertexY = (a* VertexX * VertexX) + (b* VertexX) + c;
        System.out.printf("vertex is at %.3f,%.3f\n", VertexX, VertexY);

    }
}