package com.jtech.persys;

import java.util.HashMap;
import java.util.Map.Entry;

public class Utils {

	public static float zoom = 1.1f;

	public static String generateInfoText(Atom atom) {
		String text = "The atom $ATOM is the $PR atom in the perodic table and has the symbol $SYMB. It has a melting point at $MP °K and a boiling point at $BP °K. ";
		text += "$ATOMs has $SL electron shells, $SHLordered from in to out. $ATOM was discovered $DISC and has a density of $DENS.";
		// Start by adding the simplest stuff to the replace map
		HashMap<String, String> replace = new HashMap<String, String>();
		replace.put("atom", atom.name);
		replace.put("symb", atom.symbol);
		replace.put("pr", EnglishNumberToWords.ordinal(atom.protons));
		float mp = Float.parseFloat(atom.mInfo.get("mp"));
		replace.put("mp", mp + " °K (" + kelvinToC(mp) + " °C, " + kelvinToFarh(mp) + " °F)");
		float bp = Float.parseFloat(atom.mInfo.get("bp"));
		replace.put("bp", bp + " °K (" + kelvinToC(bp) + " °C, " + kelvinToFarh(bp) + " °F)");
		replace.put("sl", "" + atom.shells.length);
		replace.put("dens", atom.mInfo.get("density"));

		String s = "";
		for (int shell : atom.shells) {
			s += shell + ", ";
		}
		replace.put("shl", s);

		String year = atom.mInfo.get("discYear");
		if (year.matches("\\d+"))
			replace.put("disc", "year " + year);
		else {
			replace.put("disc", "in the ancient");
		}

		for (Entry<String, String> e : replace.entrySet()) {
			text = text.replace("$" + e.getKey().toUpperCase(), e.getValue());
		}

		return text;
	}

	public static float kelvinToC(float kelvin) {
		return kelvin - 273;
	}

	public static float cToKelvin(float c) {
		return c + 273;
	}

	public static float cToFahr(float c) {
		return c * 9.0f / 5 + 32;
	}

	public static float fahrToC(float fahr) {
		return (fahr - 32) * 5f / 9;
	}

	public static float kelvinToFarh(float kelvin) {
		return cToFahr(kelvinToC(kelvin));
	}

	public static float fahrToKelvin(float fahr) {
		return cToKelvin(fahrToC(fahr));
	}
}
