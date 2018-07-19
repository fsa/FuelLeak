package net.tavda.FuelLeak;

import static java.lang.Math.*;

public class AirFuelBlast {

    private final double c0 = 340;
    private final double P0 = 101325;

    // Исходные данные
    private double R;
    private double M_g;
    private String substance, st_zagr;

    public void setR(double r) {
        R = r;
    }

    public void setM_g(double m_g) {
        M_g = m_g;
    }

    public void setSubstance(String substance) {
        this.substance = substance;
    }

    public void setSt_zagr(String st_zagr) {
        this.st_zagr = st_zagr;
    }

    public String Calculate() {
        String result = "";

        // Постоянные величины
        double sigma = 7; // Для газовых смесей
        double q_g;
        //double c_g;
        //double c_st;
        double vr;

        if (substance.equals("Бензин")) {
            //c_g = 0.07329d;
            //c_st = c_g;
            q_g = 44e6d;
            if (st_zagr.equals("Слабая")) {
                vr = 142.056d;
            } else { // Средняя
                vr = 200d;
            }
        } else { // СУГ
            //c_g = 0.074958d;
            //c_st = c_g;
            q_g = 46.4e6d;
            if (st_zagr.equals("Слабая")) {
                vr = 200d;
            } else { // Средняя
                vr = 300d;
            }
        }
        double E = 2d * M_g * q_g;

        double Rx = R / pow(E / P0, 1d / 3d);
        double lambda = 100d * R / pow(E, 1d / 3d);

        double Px_det = exp(-1.124d - 1.66d * log(Rx) + 0.26d * pow(log(Rx), 2d));
        double Ix_det = exp(-3.4217d - 0.898d * log(Rx) - 0.0096d * pow(log(Rx), 2d));

        double Px_def = pow(vr / c0, 2d) * ((sigma - 1d) / sigma) * (0.83d / Rx - 0.14d / pow(Rx, 2d));
        double Ix_def = (vr / c0) * ((sigma - 1d) / sigma) * (1d - 0.4d * (sigma - 1d) * vr / (sigma * c0)) * (0.06d / Rx + 0.01d / pow(Rx, 2d) - 0.0025d / pow(Rx, 3d));

        double Px = min(Px_det, Px_def);
        if (Px < 0.2d || Px > 6.5d) {
            result += "Px выходит за границы 0,2...6,5!!!\n\n";
        }
        double Ix = min(Ix_det, Ix_def);
        double delta_P = Px * P0;
        double I = Ix * pow(P0, 2d / 3d) * pow(E, 1d / 3d) / c0;

        double v1 = pow(17500d / delta_P, 8.4d) + pow(290 / I, 9.3d);
        double v2 = pow(40000d / delta_P, 7.4d) + pow(460 / I, 11.3d);
        double v5 = 7.38e3d / delta_P + 1.3e9d / (delta_P * I);
        double Pr1 = 5d - 0.26d * log(v1);
        double Pr2 = 5d - 0.22d * log(v2);
        double Pr4 = -12.6d + 1.52d * log(delta_P);
        double Pr5 = 5d - 2.44d * log(v5);

        result += "Исходные данные:\n" +
                String.format("Расстояние до взрыва R=%.3f км.\n", R) +
                String.format("Масса горючего в облаке, Mг=%.3f кг.\n", M_g) +
                String.format("Вид горючего: %s\n", substance) +
                String.format("Степень загроможденности: %s\n", st_zagr) +
                "\nРезультат вычислений:\n" +
                String.format("E=%.3f\n", E) +
                String.format("Rx=%.3f\n", Rx) +
                String.format("lambda=%.3f\n", lambda) +
                String.format("Детонация Px=%f\n", Px_det) +
                String.format("Детонация Ix=%f\n", Ix_det) +
                String.format("Дефлаграция Px=%f\n", Px_def) +
                String.format("Дефлаграция Ix=%f\n", Ix_def) +
                String.format("Px=%f\n", Px) +
                String.format("Ix=%f\n", Ix) +
                String.format("delta P=%.3f\n", delta_P) +
                String.format("I=%f\n", I) +
                String.format("Вероятность повреждения стен промышленных зданий Pr1=%.3f. %s\n", Pr1, probability(Pr1)) +
                String.format("Вероятность разрушений промышленных зданий Pr2=%.3f. %s\n", Pr2, probability(Pr2)) +
                String.format("Вероятность разрыва барабанных перепонок Pr4=%.3f. %s\n", Pr4, probability(Pr4)) +
                String.format("Вероятность отброса людей ударной волной Pr5=%.3f. %s\n", Pr5, probability(Pr5));
        return result;
    }

    private String probability(double fx) {
        int probability;
        final double[] link = {
                0.00d, 2.67d, 2.95d, 3.12d, 3.25d, 3.38d, 3.45d, 3.52d, 3.59d, 3.66d,
                3.72d, 3.77d, 3.82d, 3.86d, 3.92d, 3.96d, 4.01d, 4.05d, 4.08d, 4.12d,
                4.16d, 4.19d, 4.23d, 4.26d, 4.29d, 4.33d, 4.36d, 4.39d, 4.42d, 4.45d,
                4.48d, 4.50d, 4.53d, 4.56d, 4.59d, 4.61d, 4.64d, 4.67d, 4.69d, 4.72d,
                4.75d, 4.77d, 4.80d, 4.82d, 4.85d, 4.87d, 4.90d, 4.92d, 4.95d, 4.97d,
                5.00d, 5.03d, 5.05d, 5.08d, 5.10d, 5.13d, 5.15d, 5.18d, 5.20d, 5.23d,
                5.25d, 5.28d, 5.31d, 5.33d, 5.36d, 5.39d, 5.41d, 5.44d, 5.47d, 5.50d,
                5.52d, 5.55d, 5.58d, 5.61d, 5.64d, 5.67d, 5.71d, 5.74d, 5.77d, 5.81d,
                5.84d, 5.88d, 5.92d, 5.95d, 5.99d, 6.04d, 6.08d, 6.13d, 6.18d, 6.23d,
                6.28d, 6.34d, 6.41d, 6.48d, 6.55d, 6.64d, 6.75d, 6.88d, 7.05d, 7.33d,
                7.33d, 7.37d, 7.41d, 7.46d, 7.51d, 7.58d, 7.65d, 7.75d, 7.88d, 8.09d
        };
        if (fx < 0) {
            return "Вероятность близка к нулю.";
        }
        for (probability = 0; probability < 100; probability++) {
            if (fx <= link[probability]) {
                break;
            }
        }
        return String.format("Вероятность %s%%.", probability);
    }
}
