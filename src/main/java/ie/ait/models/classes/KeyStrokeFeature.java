package ie.ait.models.classes;

import com.opencsv.bean.CsvBindByName;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */

public class KeyStrokeFeature{
    @CsvBindByName
    private double A;
    @CsvBindByName
    private double B;
    @CsvBindByName
    private double C;
    @CsvBindByName
    private double D;
    @CsvBindByName
    private double E;
    @CsvBindByName
    private double F;
    @CsvBindByName
    private double G;
    @CsvBindByName
    private double H;
    @CsvBindByName
    private double I;
    @CsvBindByName
    private double J;
    @CsvBindByName
    private double K;
    @CsvBindByName
    private double L;
    @CsvBindByName
    private double M;
    @CsvBindByName
    private double N;
    @CsvBindByName
    private double O;
    @CsvBindByName
    private double P;
    @CsvBindByName
    private double Q;
    @CsvBindByName
    private double R;
    @CsvBindByName
    private double S;
    @CsvBindByName
    private double T;
    @CsvBindByName
    private double U;
    @CsvBindByName
    private double V;
    @CsvBindByName
    private double W;
    @CsvBindByName
    private double X;
    @CsvBindByName
    private double Y;
    @CsvBindByName
    private double Z;

    @CsvBindByName
    private double TH;
    @CsvBindByName
    private double HE;
    @CsvBindByName
    private double IN;
    @CsvBindByName
    private double ER;
    @CsvBindByName
    private double AN;
    @CsvBindByName
    private double RE;
    @CsvBindByName
    private double ND;
    @CsvBindByName
    private double AT;
    @CsvBindByName
    private double ON;
    @CsvBindByName
    private double NT;

    @CsvBindByName
    private double average1;
    @CsvBindByName
    private double average2;

    @CsvBindByName(column = "left_keys_sum")
    private double leftKeysSum;
    @CsvBindByName(column = "right_keys_sum")
    private double rightKeysSum;

    @CsvBindByName(column = "class")
    private String featureClass;

    public KeyStrokeFeature(){}


    public String getFeatureClass() {
        return featureClass;
    }

    public void setFeatureClass(String featureClass) {
        this.featureClass = featureClass;
    }

    public double getAverage1() {
        return average1;
    }

    public void setAverage1(double average1) {
        this.average1 = average1;
    }

    public double getAverage2() {
        return average2;
    }

    public void setAverage2(double average2) {
        this.average2 = average2;
    }

    public double getTH() {
        return TH;
    }

    public void setTH(double TH) {
        this.TH = TH;
    }

    public double getHE() {
        return HE;
    }

    public void setHE(double HE) {
        this.HE = HE;
    }

    public double getIN() {
        return IN;
    }

    public void setIN(double IN) {
        this.IN = IN;
    }

    public double getER() {
        return ER;
    }

    public void setER(double ER) {
        this.ER = ER;
    }

    public double getAN() {
        return AN;
    }

    public void setAN(double AN) {
        this.AN = AN;
    }

    public double getRE() {
        return RE;
    }

    public void setRE(double RE) {
        this.RE = RE;
    }

    public double getND() {
        return ND;
    }

    public void setND(double ND) {
        this.ND = ND;
    }

    public double getAT() {
        return AT;
    }

    public void setAT(double AT) {
        this.AT = AT;
    }

    public double getON() {
        return ON;
    }

    public void setON(double ON) {
        this.ON = ON;
    }

    public double getNT() {
        return NT;
    }

    public void setNT(double NT) {
        this.NT = NT;
    }

    public double getA() {
        return A;
    }

    public void setA(double a) {
        A = a;
    }

    public double getB() {
        return B;
    }

    public void setB(double b) {
        B = b;
    }

    public double getC() {
        return C;
    }

    public void setC(double c) {
        C = c;
    }

    public double getD() {
        return D;
    }

    public void setD(double d) {
        D = d;
    }

    public double getE() {
        return E;
    }

    public void setE(double e) {
        E = e;
    }

    public double getF() {
        return F;
    }

    public void setF(double f) {
        F = f;
    }

    public double getG() {
        return G;
    }

    public void setG(double g) {
        G = g;
    }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public double getI() {
        return I;
    }

    public void setI(double i) {
        I = i;
    }

    public double getJ() {
        return J;
    }

    public void setJ(double j) {
        J = j;
    }

    public double getK() {
        return K;
    }

    public void setK(double k) {
        K = k;
    }

    public double getL() {
        return L;
    }

    public void setL(double l) {
        L = l;
    }

    public double getM() {
        return M;
    }

    public void setM(double m) {
        M = m;
    }

    public double getN() {
        return N;
    }

    public void setN(double n) {
        N = n;
    }

    public double getO() {
        return O;
    }

    public void setO(double o) {
        O = o;
    }

    public double getP() {
        return P;
    }

    public void setP(double p) {
        P = p;
    }

    public double getQ() {
        return Q;
    }

    public void setQ(double q) {
        Q = q;
    }

    public double getR() {
        return R;
    }

    public void setR(double r) {
        R = r;
    }

    public double getS() {
        return S;
    }

    public void setS(double s) {
        S = s;
    }

    public double getT() {
        return T;
    }

    public void setT(double t) {
        T = t;
    }

    public double getU() {
        return U;
    }

    public void setU(double u) {
        U = u;
    }

    public double getV() {
        return V;
    }

    public void setV(double v) {
        V = v;
    }

    public double getW() {
        return W;
    }

    public void setW(double w) {
        W = w;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public double getZ() {
        return Z;
    }

    public void setZ(double z) {
        Z = z;
    }

    public double getLeftKeysSum() {
        return leftKeysSum;
    }

    public void setLeftKeysSum(double leftKeysSum) {
        this.leftKeysSum = leftKeysSum;
    }

    public double getRightKeysSum() {
        return rightKeysSum;
    }

    public void setRightKeysSum(double rightKeysSum) {
        this.rightKeysSum = rightKeysSum;
    }

    public void setLeftAndRightKeysSum(){
        double leftSum = this.getA()+this.getQ()+this.getZ()+this.getW()+this.getS()+this.getX()+this.getE()+this.getD()+
                this.getC()+this.getR()+this.getF()+this.getV()+this.getG()+this.getT();
        double rightSum = this.getY()+this.getH()+this.getB()+this.getU()+this.getJ()+this.getN()+this.getI()+this.getK()+
                this.getM()+this.getO()+this.getL()+this.getP();
        this.setLeftKeysSum(leftSum);
        this.setRightKeysSum(rightSum);
    }

    public static String getKeyStrokeFeatureHeader(){
        String headerString = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,TH,HE,IN,ER,AN,RE,ND,AT,ON,NT," +
                "AVERAGE1,AVERAGE2,LEFT_KEYS_SUM,RIGHT_KEYS_SUM,CLASS";
        return headerString;
    }

    public void mockInitialization(){
        this.A = 0.1;this.B = 0.1;this.C = 0.1;this.D = 0.1;this.E = 0.1;this.F = 0.1;this.G = 0.1;this.H = 0.1;this.I = 0.1;
        this.J = 0.1;this.K = 0.1;this.L = 0.1;this.M = 0.1;this.N = 0.1;this.O = 0.1;this.P = 0.1;this.Q = 0.1;this.R = 0.1;
        this.S = 0.1;this.T = 0.1;this.U = 0.1;this.V = 0.1;this.W = 0.1;this.X = 0.1;this.Y = 0.1;this.Z = 0.1;
        this.TH = 0.23;this.HE = 0.23;this.IN = 0.23;this.ER = 0.23;this.AN = 0.23;this.RE = 0.23;this.ND = 0.23;
        this.AT = 0.23;this.ON = 0.23;this.NT = 0.23;
        this.average1 = 5; this.average2 = 6; this.leftKeysSum = 0.3; this.rightKeysSum = 0.3;
        this.featureClass = "Class";
    }

    public void randomInitialization(){
        this.A = Double.parseDouble(String.format("%.2f",Math.random()));this.B = Double.parseDouble(String.format("%.2f",Math.random()));this.C = Double.parseDouble(String.format("%.2f",Math.random()));this.D = Double.parseDouble(String.format("%.2f",Math.random()));this.E = Double.parseDouble(String.format("%.2f",Math.random()));this.F = Double.parseDouble(String.format("%.2f",Math.random()));this.G = Double.parseDouble(String.format("%.2f",Math.random()));this.H = Double.parseDouble(String.format("%.2f",Math.random()));this.I = Double.parseDouble(String.format("%.2f",Math.random()));
        this.J = Double.parseDouble(String.format("%.2f",Math.random()));this.K = Double.parseDouble(String.format("%.2f",Math.random()));this.L = Double.parseDouble(String.format("%.2f",Math.random()));this.M = Double.parseDouble(String.format("%.2f",Math.random()));this.N = Double.parseDouble(String.format("%.2f",Math.random()));this.O = Double.parseDouble(String.format("%.2f",Math.random()));this.P = Double.parseDouble(String.format("%.2f",Math.random()));this.Q = Double.parseDouble(String.format("%.2f",Math.random()));this.R = Double.parseDouble(String.format("%.2f",Math.random()));
        this.S = Double.parseDouble(String.format("%.2f",Math.random()));this.T = Double.parseDouble(String.format("%.2f",Math.random()));this.U = Double.parseDouble(String.format("%.2f",Math.random()));this.V = Double.parseDouble(String.format("%.2f",Math.random()));this.W = Double.parseDouble(String.format("%.2f",Math.random()));this.X = Double.parseDouble(String.format("%.2f",Math.random()));this.Y = Double.parseDouble(String.format("%.2f",Math.random()));this.Z = Double.parseDouble(String.format("%.2f",Math.random()));
        this.TH = Double.parseDouble(String.format("%.2f",Math.random()));this.HE = Double.parseDouble(String.format("%.2f",Math.random()));this.IN = Double.parseDouble(String.format("%.2f",Math.random()));this.ER = Double.parseDouble(String.format("%.2f",Math.random()));this.AN = Double.parseDouble(String.format("%.2f",Math.random()));this.RE = Double.parseDouble(String.format("%.2f",Math.random()));this.ND = Double.parseDouble(String.format("%.2f",Math.random()));
        this.AT = Double.parseDouble(String.format("%.2f",Math.random()));this.ON = Double.parseDouble(String.format("%.2f",Math.random()));this.NT = Double.parseDouble(String.format("%.2f",Math.random()));
        //this.modal1 = (int)(Double.parseDouble(String.format("%.3f",Math.random()))*100);
        this.average1 = Double.parseDouble(String.format("%.2f",Math.random()));
        this.average2 = Double.parseDouble(String.format("%.2f",Math.random()));
        this.leftKeysSum = Double.parseDouble(String.format("%.2f",Math.random()));
        this.rightKeysSum = Double.parseDouble(String.format("%.2f",Math.random()));
        this.featureClass = "Class "+String.valueOf((int)(Double.parseDouble(String.format("%.3f",Math.random()))*100));
    }

    public String geKeyStrokeFeatureAsTestString(){
        String stringValue = String.format("%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f," +
                        "%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f," +
                        "%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f",
                A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,
                TH,HE,IN,ER,AN,RE,ND,AT,ON,NT,
                average1, average2, leftKeysSum, rightKeysSum);
        return stringValue;
    }
    
    @Override
    public String toString(){
        String stringValue = String.format("%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f," +
                "%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f," +
                        "%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%s",
                A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,
                TH,HE,IN,ER,AN,RE,ND,AT,ON,NT,
                average1, average2,leftKeysSum, rightKeysSum,
                featureClass);
        return stringValue;
    }
    
    public boolean isValid(){
        boolean isValid = true;
        if(!(A > 0 && B > 0 && C > 0 && D > 0 && E > 0 && F > 0 && G > 0 && H > 0 && I > 0 && J > 0 && K > 0 && L > 0 &&
                M > 0 && N > 0 && O > 0 && P > 0 && Q > 0 && R > 0 && S > 0 && T > 0 && U > 0 && V > 0 && W > 0 && X > 0 &&
                Y > 0 && Z > 0)){
            isValid = false;
        }
        return isValid;
    }
}
