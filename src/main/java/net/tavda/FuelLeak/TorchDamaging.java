package net.tavda.FuelLeak;

import static java.lang.Math.*;

public class TorchDamaging {

    private final double[] max_q = {1.4d, 4.2d, 7.0d, 10.5d, 13.9d, 14.8d};

    //Входные данные
    private double d, H;

    private double h, E_f;

    public void setD(double d) {
        this.d = d;
    }

    public void setH(double h) {
        H = h;
    }

    public String Calculate() {

        String result = "";
        //E_f для одного типа горючего
        if (d <= 10) {
            E_f = 220d;
        } else if (d <= 20) {
            E_f = 180d;
        } else if (d <= 30) {
            E_f = 150d;
        } else if (d <= 40) {
            E_f = 130d;
        } else {
            E_f = 180d;
        }

        h = 2d * H / d;

        for (double q : max_q) {
            result += String.format("q=%.1f r=%.1f\n", q, search_r(q));
        }
        return result;
    }

    private double search_r(double q) {
        double r;
        double min = 0, max = 0;
        for (r = 0d; r <= 1000d; r++) {
            //System.out.println(String.format("r=%f => q=%f", r, q(r)));
            if (q(r) < q) {
                max = r;
                break;
            }
            min = r;
        }
        for (r = min; r <= max; r = r + 0.1d) {
            //System.out.println(String.format("r=%f => q=%f", r, q(r)));
            if (q(r) < q) {
                break;
            }
        }
        return r;
    }

    private double q(double r) {
        double tau = exp(-0.0007d * (r - 0.5d * d));
        return E_f * Fq(r) * tau;
    }

    private double Fq(double r) {
        double Fv, Fh;
        Fv = Fv(r);
        Fh = Fh(r);
        return sqrt(pow(Fv, 2d) + pow(Fh, 2d));
    }

    private double Fv(double r) {
        double S_1 = 2d * r / d;
        double A = (pow(h, 2d) + pow(S_1, 2d) + 1d) / (2d * S_1);
        double Fv1 = 1d / S_1 * atan(h / (sqrt(pow(S_1, 2d) - 1d)));
        double Fv2 = atan(sqrt((S_1 - 1d) / (S_1 + 1d)));
        double Fv3 = A / sqrt(pow(A, 2d) - 1d) * atan(sqrt(((A + 1d) * (S_1 - 1d)) / ((A - 1d) * (S_1 + 1d))));
        return 1d / PI * (Fv1 + h / S_1 * (Fv2 - Fv3));
    }

    private double Fh(double r) {
        double S_1 = 2d * r / d;
        double A = (pow(h, 2d) + pow(S_1, 2d) + 1d) / (2d * S_1);
        double B = (1d + pow(S_1, 2d)) / (2d * S_1);
        double Fh1 = (B - 1d / S_1) / sqrt(pow(B, 2d) - 1d) * atan(sqrt(((B + 1d) * (S_1 - 1d)) / ((B - 1d) * (S_1 + 1d))));
        double Fh2 = (A - 1d / S_1) / sqrt(pow(A, 2d) - 1d) * atan(sqrt(((A + 1d) * (S_1 - 1d)) / ((A - 1d) * (S_1 + 1d))));
        return 1d / PI * (Fh1 - Fh2);
    }
}
