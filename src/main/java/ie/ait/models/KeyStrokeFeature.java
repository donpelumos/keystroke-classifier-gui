package ie.ait.models;

/**
 * Created by Pelumi.Oyefeso on 02-Mar-2020
 */
public class KeyStrokeFeature {
    private double A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z;
    private double TH,HE,IN,ER,AN,RE,ND,AT,ON,NT;
    private int modal1, modal2;
    private String featureClass;

    public String getFeatureClass() {
        return featureClass;
    }

    public void setFeatureClass(String featureClass) {
        this.featureClass = featureClass;
    }

    public int getModal1() {
        return modal1;
    }

    public void setModal1(int modal1) {
        this.modal1 = modal1;
    }

    public int getModal2() {
        return modal2;
    }

    public void setModal2(int modal2) {
        this.modal2 = modal2;
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

    public static String getKeyStrokeFeatureHeader(){
        String headerString = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,TH,HE,IN,ER,AN,RE,ND,AT,ON,NT," +
                "MODAL1,MODAL2,CLASS";
        return headerString;
    }

    public void mockInitialization(){
        this.A = 0.1;this.B = 0.1;this.C = 0.1;this.D = 0.1;this.E = 0.1;this.F = 0.1;this.G = 0.1;this.H = 0.1;this.I = 0.1;
        this.J = 0.1;this.K = 0.1;this.L = 0.1;this.M = 0.1;this.N = 0.1;this.O = 0.1;this.P = 0.1;this.Q = 0.1;this.R = 0.1;
        this.S = 0.1;this.T = 0.1;this.U = 0.1;this.V = 0.1;this.W = 0.1;this.X = 0.1;this.Y = 0.1;this.Z = 0.1;
        this.TH = 0.23;this.HE = 0.23;this.IN = 0.23;this.ER = 0.23;this.AN = 0.23;this.RE = 0.23;this.ND = 0.23;
        this.AT = 0.23;this.ON = 0.23;this.NT = 0.23;
        this.modal1 = 5; this.modal2 = 6;
        this.featureClass = "Class";
    }
    
    @Override
    public String toString(){
        String stringValue = String.format("%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f," +
                "%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f, " +
                        "%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f, %d,%d, %s",
                A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,
                TH,HE,IN,ER,AN,RE,ND,AT,ON,NT,
                modal1,modal2,
                featureClass);
        return stringValue;
    }
}
