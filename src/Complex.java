public class Complex {
    private double re, im;

    public Complex() {
        re = 0;
        im = 0;
    }

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public void add(Complex z) {
        set(add(this, z));
    }

    public void subtract(Complex z) {
        set(subtract(this, z));
    }

    public void multiply(Complex z) {
        set(multiply(this, z));
    }

    public void divide(Complex z) {
        set(divide(this, z));
    }

    public void set(Complex z) {
        this.re = z.re;
        this.im = z.im;
    }

    public static Complex add(Complex z1, Complex z2) {
        return new Complex(z1.re + z2.re, z1.im + z2.im);
    }

    public static Complex subtract(Complex z1, Complex z2) {
        return new Complex(z1.re - z2.re, z1.im - z2.im);
    }

    public static Complex multiply(Complex z1, Complex z2) {
        return new Complex(z1.re * z2.re - z1.im * z2.im, z1.re * z2.im + z1.im * z2.re);
    }

    public static Complex divide(Complex z1, Complex z2) {
        Complex num = multiply(z1, z2.conjugate());
        double div = Math.pow(z2.mod(), 2);
        return new Complex(num.re / div, num.im / div);
    }

    public Complex conjugate() {
        return new Complex(this.re, -this.im);
    }

    public double mod() {
        return Math.sqrt(Math.pow(this.re, 2) + Math.pow(this.im, 2));
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }
}
