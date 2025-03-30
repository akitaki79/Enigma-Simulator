
package src;

import static src.EnigmaConstants.*;

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
 * @param view 
 */

    public void addView(EnigmaView view) {
        views.add(view);
    }

    public void update() {
        for (EnigmaView view : views) {
            view.update();
        }
    }

/**
 * Returns true if the specified letter key is pressed.
 *
 * @param letter 
 */

    public boolean isKeyDown(String letter) {
    	return(keyStorage.get(letter));
    }

/**
 * Returns true if the specified lamp is lit.
 *
 * @param letter
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
 * Called by the view when the specified key is pressed.
 *
 * @param key 
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
 * Called by the view when the specified key is released.
 *
 * @param key 
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
 * @param index 
 */

    public void rotorClicked(int index) {
        rotors[index].advance();
        update();
    }

    
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