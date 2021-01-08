import processing.core.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Visualization extends PApplet {
    float time = 0;
    static ArrayList<Float[]> result = new ArrayList<>();


    private static ArrayList<Float[]> dft(ArrayList<Float[]> coordinates) {
        int N = coordinates.size();
        ArrayList<Float[]> res = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Complex num = new Complex();
            for (int j = 0; j < N; j++) {
                float angle = (TWO_PI * i * j) / N;
                Complex c = new Complex(cos(angle), sin(angle));
                num.add(Complex.multiply(new Complex(coordinates.get(j)[0], coordinates.get(j)[1]), c));
            }
            num.divide(new Complex(N, 0));
            float amp = (float) num.mod();
            float phase = atan2((float) num.getIm(), (float) num.getRe());
            res.add(new Float[]{(float) num.getRe(), (float) num.getIm(), (float) i, amp, phase});
        }

        return res;

    }

    @Override
    public void settings() {
        size(1200, 800);
        frameRate = 1;

    }


    ArrayList<Float[]> footprint = new ArrayList<>();

    public Float[] epicycle(float x, float y, float rotation, ArrayList<Float[]> path) {
        for (Float[] floats : path) {
            float prevx = x;
            float prevy = y;
            float freq = floats[2];
            float radius = (float) (0.7 * floats[3]);
            float phase = floats[4];
            x += radius * cos(freq * time + phase + rotation);
            y += radius * sin(freq * time + phase + rotation);

            stroke(255, 100);
            noFill();
            circle(prevx, prevy, 2 * radius);

            fill(255);
            stroke(255);
            line(prevx, prevy, x, y);
        }

        return new Float[]{x, y};
    }

    boolean finished = false;

    @Override
    public void draw() {
        background(0);
        Float[] next;
        if (!finished) {
            next = epicycle(300, 50, 0, result);
            footprint.add(0, new Float[]{next[0], next[1]});
        } else
            noLoop();
        noFill();
        beginShape();
        for (Float[] floats : footprint) {
            vertex(floats[0], floats[1]);
        }
        endShape();

        if (footprint.size() >= 100) {

            finished = true;
            footprint.remove(footprint.size() - 1);
        }
//        saveFrame("animation/p_####.png");
        float time_inc = TWO_PI / result.size();
        time += time_inc;
    }

    public static void main(String[] args) {

        ArrayList<Float[]> coordinates = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File("src/input_path.txt"));
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                Float[] num = {Float.parseFloat(line[0]), Float.parseFloat(line[1])};
                coordinates.add(num);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        result = dft(coordinates);

        result.sort((o1, o2) -> o2[3].compareTo(o1[3]));

        PApplet.main("Visualization");
    }
}
