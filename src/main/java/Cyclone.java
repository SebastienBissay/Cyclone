import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class Cyclone extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Cyclone.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
        noiseSeed(floor(random(MAX_INT)));
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        stroke(STROKE_COLOR.red(), STROKE_COLOR.green(), STROKE_COLOR.blue(), STROKE_COLOR.alpha());
        blendMode(BLENDMODE);
        noFill();
        noLoop();
    }

    @Override
    public void draw() {
        PVector[] points = new PVector[NUMBER_OF_PARTICLES];
        for (int i = 0; i < NUMBER_OF_PARTICLES; i++) {
            points[i] = PVector.fromAngle(TWO_PI * i / NUMBER_OF_PARTICLES)
                    .mult(INITIAL_RADIUS)
                    .add(width / 2f, height / 2f);
        }

        for (int l = 0; l < NUMBER_OF_ITERATIONS; l++) {
            beginShape();
            for (PVector point : points) {
                vertex(point.x, point.y);
                point.add(PVector.fromAngle(map(dist(point.x, point.y, width / 2f, height / 2f),
                                0, min(width, height),
                                0, ANGLE_FACTOR * TWO_PI)
                                + TWO_PI * floor(ANGLE_FACTOR
                                * noise(point.x * NOISE_SCALE, point.y * NOISE_SCALE)) / ANGLE_FACTOR)
                        .mult(VELOCITY));
            }
            endShape(CLOSE);
        }
        saveSketch(this);
    }
}
