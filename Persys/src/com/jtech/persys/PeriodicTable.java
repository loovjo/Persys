package com.jtech.persys;

import java.io.File;
import java.util.ArrayList;

public class PeriodicTable {

	public static boolean[][] shape;
	static {
		String[] sshape = new String[0];
		try {
			sshape = FileUtils.readFile(new File("PeriodicTableShape.txt"))
					.split("\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shape = new boolean[sshape[0].length()][];
		for (int x = 0; x < sshape[0].length(); x++) {
			shape[x] = new boolean[sshape.length];
		}
		for (int y = 0; y < sshape.length; y++) {
			for (int x = 0; x < sshape[y].length(); x++) {
				if (sshape[y].charAt(x) != '#')
					shape[x][y] = true;
			}
		}
	}

	public static boolean canAtomBeAt(Vector pos) {
		return !shape[(int) pos.getX()][(int) pos.getY()];

	}

	public static Vector getPosition(Atom a) { // Value is zero-indexed,
												// Hydrogen -> Vector(0,0)
		int n = 0;
		for (int y = 0;; y++) {
			for (int x = 0; x < shape.length; x++) {
				try {
					if (canAtomBeAt(new Vector(x, y)))
						n++;
				} catch (ArrayIndexOutOfBoundsException e) {
					int p = a.protons;
					if (p >= 57 && p <= 71) {
						return new Vector(p - 55, 9);
					}
					if (p >= 89 && p <= 103) {
						return new Vector(p - 87, 10);
					}
					if (p > 71) {
						p -= 14;
					}
					if (a.neutrons > 103)
						p -= 14;
					p -= 19;
					int group = p % shape.length;
					int period = p / shape.length + 3;
					return new Vector(group, period);
				}
				if (n >= a.protons)
					return new Vector(x, y);
			}
		}
	}

	public static Atom getAtomAt(Vector place) {
		return getAtomAt(place, Atoms.atoms);
	}

	public static Atom getAtomAt(Vector place, ArrayList<Atom> search) {
		for (Atom a : search) {
			Vector p = getPosition(a);
			if (p.getX() == (int) place.getX()
					&& p.getY() == (int) place.getY())
				return a;
		}
		return null;
	}
}
