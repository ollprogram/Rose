package Jtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import fr.olleroy.rose.commands.CommandBase;

class Jtests {

	@Test
	void test() {
		String[] wool = {"green_wool","red_planks", "green_planks", "blue_wool"};
		String[] exp = {"blue_wool", "green_wool", "red_planks", "green_planks"};
		trieSelec(wool);
		assertArrayEquals(exp, wool);
		
	}
	public static void trieSelec(String[] s) {
		for(int i = 0; i<s.length-1; i++) {
			int min = i;
			for(int j = i+1; j<s.length; j++) {
				if(CommandBase.compareInv(s[min], s[j]) > 0) {
					min = j;
				}
			}
			String tmp = s[i];
			s[i] = s[min];
			s[min] = tmp;
		}
	}
}
