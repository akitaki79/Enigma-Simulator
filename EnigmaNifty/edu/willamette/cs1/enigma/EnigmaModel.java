/*
 * File: EnigmaModel.java
 * ----------------------
 * This class defines the starter version of the EnigmaModel class,
 * which doesn't implement any of the methods.
 */

package edu.willamette.cs1.enigma;

import static edu.willamette.cs1.enigma.EnigmaConstants.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
public class EnigmaModel {
	
    public EnigmaModel() {
        views = new ArrayList<EnigmaView>();
    	for (String letter : LETTERS_ARRAY){
    		keyStorage.put(letter, false);
    		lampStorage.put(letter, false);
    	}
    }

/**
 * Adds a view to this model.
 *
 * @param view The view being added
 */

    public void addView(EnigmaView view) {
        views.add(view);
    }

/**
 * Sends an update request to all the views.
 */

    public void update() {
        for (EnigmaView view : views) {
            view.update();
        }
    }

/**
 * Returns true if the specified letter key is pressed.
 *
 * @param letter The letter key being tested as a one-character string.
 */

    public boolean isKeyDown(String letter) {
    	return(keyStorage.get(letter));
    }

/**
 * Returns true if the specified lamp is lit.
 *
 * @param letter The lamp being tested as a one-character string.
 */

    public boolean isLampOn(String letter) {
    	return(lampStorage.get(letter));
    }

/**
 * Returns the letter visible through the rotor at the specified inded.
 *
 * @param index The index of the rotor (0-2)
 * @return The letter visible in the indicated rotor
 */

    public String getRotorLetter(int index) {
        return(LETTERS_ARRAY[rotors[index].getOffset()]);
    }

/**
 * Called automatically by the view when the specified key is pressed.
 *
 * @param key The key the user pressed as a one-character string
 */

    public void keyPressed(String key) {
        keyStorage.put(key, true);
        String output = key;
        output = rotors[2].getOutput(output);
        output = rotors[1].getOutput(output);
        output = rotors[0].getOutput(output);
        output = reflector.getOutput(output);
        output = rotors[0].getOutputReverse(output);
        output = rotors[1].getOutputReverse(output);
        output = rotors[2].getOutputReverse(output);
        lampStorage.put(output, true);
        update();
    }
    
    

/**
 * Called automatically by the view when the specified key is released.
 *
 * @param key The key the user released as a one-character string
 */

    public void keyReleased(String key) {
        keyStorage.put(key, false);
        String output = key;
        output = rotors[2].getOutput(output);
        output = rotors[1].getOutput(output);
        output = rotors[0].getOutput(output);
        output = reflector.getOutput(output);
        output = rotors[0].getOutputReverse(output);
        output = rotors[1].getOutputReverse(output);
        output = rotors[2].getOutputReverse(output);
        lampStorage.put(output, false);
        
        rotors[2].advance();
        
        if(rotors[2].getOffset() == 0) {
        	rotors[1].advance();
        	if(rotors[1].getOffset() == 0) {
        		rotors[0].advance();   
        	}
        }
        
        update();
    }

/**
 * Called automatically by the view when the rotor at the specified
 * index (0-2) is clicked.
 *
 * @param index The index of the rotor that was clicked
 */

    public void rotorClicked(int index) {
        rotors[index].advance();
        update();
    }

    
/* Main program */

    public static void main(String[] args) {
        EnigmaModel model = new EnigmaModel();
        EnigmaView view = new EnigmaView(model);
        model.addView(view);        
    }

/* Private instance variables */
    private Dictionary<String, Boolean> keyStorage = new Hashtable<>(); 
    private Dictionary<String, Boolean> lampStorage = new Hashtable<>(); 
    private ArrayList<EnigmaView> views;
    private rotor[] rotors = {new rotor(ROTOR_PERMUTATIONS[0]), new rotor(ROTOR_PERMUTATIONS[1]), new rotor(ROTOR_PERMUTATIONS[2])};
    private rotor reflector = new rotor(REFLECTOR_PERMUTATION);
}