package com.jtech.persys;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Atoms {

	public static ArrayList<Atom> atoms = new ArrayList<Atom>();

	static {
		// Read in all atoms
		String[] atomsFile = new String[0];
		try {
			atomsFile = FileUtils.readFile(new File("Atom configuration.txt"))
					.split("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < atomsFile.length; i += 2) {
			atoms.add(new Atom(atomsFile[i] + "\n" + atomsFile[i + 1]));
		}
	}

	public static Atom getAtom(String nameOrSymbol) {
		for (Atom a : atoms)
			if (a.name.equals(nameOrSymbol) || a.symbol.equals(nameOrSymbol))
				return a;
		return null;
	}

	public static ArrayList<Atom> getAtoms(String regex) {
		ArrayList<Atom> m = new ArrayList<Atom>();
		for (Atom a : atoms)
			if (a.name.matches(regex) || a.symbol.matches(regex)
					|| ("" + a.neutrons).matches(regex))
				m.add(a);
		return m;
	}

	public static Atom getAtom(int number) {
		for (Atom a : atoms)
			if (a.protons == number)
				return a;
		return null;
	}
}
