package com.mygdx.game;

/**
 * Created by kennethroffo on 9/20/17.
 */

// Steppables will have their step function called every render cycle
public interface Steppable {

    public void step(double delta);
}
