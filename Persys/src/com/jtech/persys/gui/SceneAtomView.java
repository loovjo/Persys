package com.jtech.persys.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jtech.persys.Atom;
import com.jtech.persys.Utils;
import com.jtech.persys.Vector;
import com.jtech.persys.scene.Scene;

public class SceneAtomView extends Scene {

	public Atom atom;
	public ArrayList<Vector> electrons = new ArrayList<Vector>();

	public static float protonSize = 0.1f, electronSize = 1f, electronSpace = 0.2f;
	public static float electronSpeed = 0.5f;

	public SceneAtomView(Atom atom) {
		this.atom = atom;
		electronSize /= atom.shells.length;
		setupViewElectrons();
		atom.generateImage();
	}

	public void setupViewElectrons() {
		for (int i = 0; i < atom.shells.length; i++) {
			int initRot = (int) (Math.random() * 360);
			for (float j = 0; j < 360; j += 360.0 / atom.shells[i]) {
				electrons.add(new Vector(0, i+1).rotate(j
						+ initRot));
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		ScenePeriodicView.drawBackground(g, getWidth(), getHeight());
		g.setColor(new Color(1f, 1f, 1f, 0.8f));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		String astr = atom.name;
		int m = 5;
		g.setFont(new Font("Helvetica", Font.PLAIN, 12));
		g.setFont(BasicGui.getGoodFont(g.getFontMetrics(), astr,
				getWidth() / m, getHeight()));
		g.setFont(g.getFont().deriveFont(g.getFont().getSize() * Utils.zoom));
		g.drawString(astr, getWidth() / 2
				- g.getFontMetrics().stringWidth(astr) / 2, g.getFont()
				.getSize());
		int l = (int) (atom.shells.length * electronSpace + electronSize);
		Vector center = new Vector(l, l);
		BufferedImage img = new BufferedImage(500, 500,
				BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().setColor(Color.red);
		int size = (int) (img.getWidth() * protonSize);
		img.getGraphics().fillOval((img.getWidth() - size) / 2,
				(img.getHeight() - size) / 2, size, size);
		img.getGraphics().setColor(Color.blue);
		size = (int) (img.getWidth() * electronSize);
		for (Vector el : electrons) {
			Vector p = el.mul(getWidth() * electronSpace).add(new Vector(img.getWidth(), img.getHeight()).div(2));
			img.getGraphics().fillOval((int) (p.getX() - size / 2),
					(int) (p.getY() - size / 2), size,
					size);
		}
		/*g.drawImage(img, 0, 0, (int) (getWidth() / 5f),
				(int) ((getWidth() / 5f) * ((float) img.getWidth() / img
						.getHeight())), null);*/
		if (atom.image != null) {
			if (Math.min(atom.image.getWidth(), atom.image.getHeight()) <= 1) {
				atom.generateImage();
				return;
			}
			int imgSize = (int) (getWidth() / 5f * 2);
			g.drawImage(atom.image, (int)(getWidth() / 5f * 3), g.getFontMetrics().getHeight(),
					imgSize, (int) (atom.image.getHeight()
							* (imgSize) / (float) atom.image.getWidth()), null);
		}
		Vector start = new Vector(g.getFontMetrics().getHeight(), g.getFontMetrics().getHeight());
		g.setFont(new Font("Times new roman", Font.PLAIN, (int)(20 * Utils.zoom)));
		ArrayList<String> lines = new ArrayList<String>();
		int line = 0;
		String f = atom.info;
		if (f.isEmpty())
			f = "The atom " + atom.name + " is the " + atom.protons + "th atom in the periodic table and has the symbol " + atom.symbol + ".";
		for (int i = 0; i < f.split(" ").length; i++) {
			String word = f.split(" ")[i];
			if (lines.size() <= line)
				lines.add("");
			String q = lines.get(line) + " " + word;
			if (g.getFontMetrics().stringWidth(q) + start.getX() < getWidth() / 5f * 3) {
				lines.set(line, q);
			} else {
				lines.add(word);
				line++;
			}
		}

		for (int l2 = 0; l2 < lines.size(); l2++)
			g.drawString(lines.get(l2), (int) start.getX(), (int) start.getY()
					+ (g.getFontMetrics().getHeight() * (l2 + 1)));
	}

	@Override
	public void update() {
		for (int i = 0; i < electrons.size(); i++) {
			electrons.set(
					i,
					electrons.get(i).rotate(
							electronSpeed / electrons.size() + electronSpeed));
		}
	}
}
