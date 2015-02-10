package com.jtech.persys;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;

public class Tester {
	public static Logger log = new Logger("Tester");
	
	public static int sleeping = 0;

	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (sleeping < 10000) {
					sleeping++;
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
				}
				System.exit(0);
			}
		}).start();
		log.log("Testing all Atoms...");
		for (Atom a : Atoms.atoms) {
			log.log(a);
		}
		ArrayList<StringBuilder> sys = new ArrayList<StringBuilder>();
		int mul = 4;
		for (Atom a : Atoms.atoms) {
			Vector pos = PeriodicTable.getPosition(a);
			while (sys.size() <= pos.getY())
				sys.add(new StringBuilder());
			StringBuilder cur = sys.get((int) pos.getY());
			for (int i = 0; i < a.symbol.length(); i++) {
				try {
					cur.setCharAt((int) (pos.getX()*mul + i), a.symbol.charAt(i));
				} catch (StringIndexOutOfBoundsException e) {
					cur.append(' ');
					i--;
				}
			}
		}
		for (StringBuilder sb : sys) {
			System.out.println(sb.toString());
		}

		// log.log(Atoms.getAtom("Silver"));
		while (true) {
			sleeping = 0;
			String atom = "";
			InputStreamReader isr = new InputStreamReader(System.in);
			try {
				while (!(atom += "" + (char) isr.read()).endsWith("\n")) {
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			atom = atom.trim();
			try {
				boolean found = false;
				for (Atom a : Atoms.getAtoms(atom)) {
					found = true;
					log.log(a);
				}
				if (!found)
					log.log(Level.WARNING, "The atom " + atom
							+ " dosent exist!");
			} catch (NullPointerException e) {
			}
		}

	}
}
