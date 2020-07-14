package physics;

import objects.particles.Particle;
import processing.core.PVector;

public class Collision {

    private Particle p1, p2;

    private float c;
    private boolean overlap;

    private PVector contactNormal;

    public Collision(Particle p1, Particle p2, boolean overlap) {
        this.p1 = p1;
        this.p2 = p2;
        this.c = 0.9f;
        this.overlap = overlap;

        this.contactNormal = PVector.sub(p2.position, p1.position).normalize();
    }

    /**
     * Resolve this collision between two particles.
     * Code mostly follows example collision code given in lectures.
     */
    public void resolveCollision() {

        /* Fix for overlapping by moving p1's position */
        if (overlap) p1.position.add(contactNormal.copy().mult(-1));

        float closingVel = calculateClosingVelocity();

        float deltaVel = (-closingVel * c) - closingVel;

        float impulse = deltaVel / ((1f / p1.mass) + (1f / p2.mass));

        PVector impulsePerInverseMass = contactNormal.copy().mult(impulse);

        PVector p1Impulse = impulsePerInverseMass.copy().mult((1f / p1.mass));
        PVector p2Impulse = impulsePerInverseMass.copy().mult(-(1f / p2.mass));

        p1.velocity.add(p1Impulse);
        p2.velocity.add(p2Impulse);

    }

    public float calculateClosingVelocity() {
        float cv1 = p1.velocity.dot(PVector.sub(p2.position, p1.position).normalize());
        float cv2 = p2.velocity.dot(PVector.sub(p1.position, p2.position).normalize());

        float closingVel = cv1 + cv2;

        return closingVel;
    }


}