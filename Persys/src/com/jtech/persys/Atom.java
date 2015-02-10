package com.jtech.persys;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Atom {

	public static int maxElectronsInShell(int n) { // 1 is first shell, 2 is
													// second etc.
		return 2 * (int) Math.pow(n + 1, 2);
	}

	public short[] shells = new short[0]; // 0 for start, addElectrons will fix
											// it
	public int protons;
	public int neutrons;
	public String name;
	public String symbol;
	public String info = "";
	public BufferedImage image = new BufferedImage(1, 1,
			BufferedImage.TYPE_INT_RGB);

	public HashMap<String, String> mInfo = new HashMap<String, String>();

	public int amountOfElectrons() {
		int sum = 0;
		for (int i = 0; i < shells.length; i++)
			sum += shells[i];
		return sum;
	}

	public Atom(String config) {
		String[] lines = config.split("\n");
		int number = 0;
		int indx;
		for (indx = 0; lines[0].charAt(indx) != ' '; indx++) {
			number += number * 10 + (int) (lines[0].charAt(indx) - (int) '0');
		}
		indx++;
		symbol = "";
		name = "";
		for (; lines[0].charAt(indx) != ' '; indx++) {
			symbol += lines[0].charAt(indx);
		}
		indx++;
		boolean first = true;
		for (; indx < lines[0].length() && lines[0].charAt(indx) != ' '; indx++) {
			if (first)
				name += ("" + lines[0].charAt(indx)).toUpperCase();
			else {
				name += lines[0].charAt(indx);
			}
			first = false;
		}
		int l = 0;
		int sum = 0;
		lines[1] = lines[1] + " ";
		for (indx = 0; indx < lines[1].length(); indx++) {
			if (lines[1].charAt(indx) == ' ') {
				for (int i = 0; i < sum; i++)
					addElectron(l);
				l++;
				sum = 0;
			} else {
				sum = (sum * 10) + (int) lines[1].charAt(indx) - ((int) '0');
			}
		}
		number = 0;
		for (int i = 0; i < shells.length; i++)
			number += shells[i];
		protons = number;
		neutrons = number;
		setupStuff();
	}

	public Atom(String name, int aNumber, int symbolChars) {
		this.name = name;
		for (int i = 0; i < aNumber; i++) {
			addElectron(0);
		}
		protons = aNumber;
		symbol = "";
		for (int i = 0; i < symbolChars; i++)
			symbol += name.charAt(i);
		setupStuff();
	}

	public Atom(String name, int aNumber, String symbol) {
		this.name = name;
		for (int i = 0; i < aNumber; i++) {
			addElectron(0);
		}
		protons = aNumber;
		this.symbol = symbol;
		setupStuff();
	}

	private void setupStuff() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					"Atoms/" + symbol + ".txt")));
			String line = br.readLine();
			String[] info = line.split("\t");
			mInfo.put("mass", info[1] + "u");
			try {
				mInfo.put("mp", "" + (Utils.cToKelvin(Integer.parseInt(info[4]))));
			} catch (NumberFormatException e) {
			}
			try {
				mInfo.put("bp", "" + (Utils.cToKelvin(Integer.parseInt(info[5]))));
			} catch (NumberFormatException e) {
			}
			mInfo.put("density", info[6] + "g/cm³");
			mInfo.put("discYear", info[7]);
			br.close();
			this.info = Utils.generateInfoText(this);
		} catch (Exception e) {
			System.out.println(symbol);
		}

	}

	public void generateImage() {
		try {
			File imgFile = new File("Images/" + symbol + ".png");
			if (imgFile.exists()) {
				image = ImageIO.read(imgFile);
				// Resize it if it is too big
				final int maxSize = 1000;
				if (image.getWidth() > maxSize) {
					Image img = image.getScaledInstance(maxSize,
							(int) (image.getHeight() * ((float) maxSize / image
									.getWidth())), 0);
					image = new BufferedImage(img.getWidth(null),
							img.getHeight(null), image.getType());
					image.getGraphics().drawImage(img, 0, 0, image.getWidth(),
							image.getHeight(), null);
					ImageIO.write(image, "png", imgFile);
					System.out.println("Image " + imgFile
							+ " has been resized.");
				}
				if (image.getHeight() > maxSize) {
					Image img = image.getScaledInstance(
							(int) (image.getWidth() * ((float) maxSize / image
									.getHeight())), maxSize, 0);
					image = new BufferedImage(img.getWidth(null),
							img.getHeight(null), image.getType());
					image.getGraphics().drawImage(img, 0, 0, image.getWidth(),
							image.getHeight(), null);
					ImageIO.write(image, "png", imgFile);
					System.out.println("Image " + imgFile
							+ " has been resized.");
				}
			} else {
				image = null;
				//System.out.println("lalalalalal");
			}
		} catch (Exception e) {
			image = null;
		}
	}

	private void addElectron(int shell) {
		ArrayList<Short> lShells = new ArrayList<Short>();
		for (int i = 0; i < shells.length; i++) {
			lShells.add(shells[i]);
		}
		int j;
		for (j = shell; j < lShells.size(); j++) {
			int limit = maxElectronsInShell(j);
			if (lShells.get(j) < limit) {
				break;
			} else if (j == shells.length) {
				j++;
			}
		}
		if (j >= lShells.size()) {
			lShells.add((short) 1);
		} else {
			lShells.set(j, (short) (lShells.get(j) + 1));
		}
		shells = toPrimitives((Short[]) lShells.toArray(new Short[lShells
				.size()]));
	}

	public static short[] toPrimitives(Short[] oBytes) {

		short[] bytes = new short[oBytes.length];
		for (int i = 0; i < oBytes.length; i++) {
			bytes[i] = oBytes[i];
		}
		return bytes;
	}

	private Vector ptPos() {
		return new Vector(0, 0);
	}

	public boolean isTransmetal() {
		try {
			return shells[shells.length - 2] > 8;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public String toString() {
		Vector p = PeriodicTable.getPosition(this);
		return name + "(Symbol: " + symbol + ", Atom number: " + protons
				+ ", Electron shells (in-out): " + Arrays.toString(shells)
				+ ", Transmetal: " + isTransmetal() + ", Group: "
				+ (int) (p.getX() + 1) + ", Period: " + (int) (p.getY() + 1)
				+ ")";
	}

}
