package src;
import static src.EnigmaConstants.*;


public class rotor {
	private String permutation;
	private int offset;
	private String[] permutationArray;
	public rotor(String permutation) {
		this.permutation = permutation;
		offset = 0;
		permutationArray = stringToArray(permutation);
	}
	public int getOffset() {
		return offset;
	}
	public String getPermutation() {
		return permutation;
	}
	public String getOutput(String input) {
     	return LETTERS_ARRAY[(LETTERS_STRING.indexOf(permutationArray[(offset + LETTERS_STRING.indexOf(input)) % 26]) - offset + 26) % 26];
    }
	public String getOutputReverse(String input) {
     	return permutationArray[(permutation.indexOf(LETTERS_ARRAY[(offset + permutation.indexOf(input)) % 26]) - offset + 26) % 26];
	}
	public void advance() {
		offset += 1;
		offset = offset % 26;
	}
}