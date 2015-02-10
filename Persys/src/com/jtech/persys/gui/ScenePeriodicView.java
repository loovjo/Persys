package com.jtech.persys.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.jtech.persys.Atom;
import com.jtech.persys.Atoms;
import com.jtech.persys.PeriodicTable;
import com.jtech.persys.Vector;
import com.jtech.persys.scene.EventListeners;
import com.jtech.persys.scene.Scene;

public class ScenePeriodicView extends Scene {
	public static float size = 50;
	public static BufferedImage background;
	public ArrayList<String> hasImage = new ArrayList<String>();
	static {
		try {
			background = ImageIO.read(new File("SpaceBig.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void switchedTo() {
		for (File f : new File("Images/").listFiles())
			hasImage.add(f.getName());
	}

	public void draw(Graphics g) {
		size = getWidth() / 18;
		if (getHeight() * 18 < getWidth() * 11)
			size = getHeight() / 11;
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		int strokeSize = 4;
		drawBackground(g, getWidth(), getHeight());
		for (Atom a : Atoms.atoms) {
			Vector pos = PeriodicTable.getPosition(a);
			Color col = Color.cyan;
			if (a.isTransmetal())
				col = Color.pink;
			if (a.info.isEmpty()) {
				//col = Color.BLUE;
			}
			if (!hasImage.contains(a.symbol + ".png")) {
				//col = col.darker();
			}
			g.setColor(col);
			g.fillRect((int) (pos.getX() * size), (int) (pos.getY() * size),
					(int) size, (int) size);
			g.setColor(Color.black);
			g.setFont(new Font("Helvetica", Font.PLAIN, 12));
			g.setFont(g.getFont().deriveFont(
					g.getFont().getStyle(),
					BasicGui.getGoodSize(
							g.getFontMetrics(),
							a.symbol
									+ new String(new char[Math.max(
											2 - a.symbol.length(), 0)])
											.replace('\0', ' '),
							(int) (size / 1.5), size)));
			g.drawString(a.symbol, (int) (pos.getX() * size),
					(int) ((pos.getY() + 1) * size - (size - g.getFontMetrics()
							.getHeight())));

			g.setFont(g.getFont().deriveFont(
					g.getFont().getStyle(),
					BasicGui.getGoodSize(g.getFontMetrics(), "" + a.neutrons,
							size - (int) (size / 1.5), size)));
			g.drawString("" + a.protons, (int) ((pos.getX() + 1) * size)
					- g.getFontMetrics().stringWidth("" + a.neutrons),
					(int) ((pos.getY() + 1) * size));
			g.drawLine((int) (pos.getX() * size), (int) (pos.getY() * size),
					(int) (pos.getX() * size), (int) ((pos.getY() + 1) * size));
			g.drawLine((int) (pos.getX() * size), (int) (pos.getY() * size),
					(int) ((pos.getX() + 1) * size), (int) (pos.getY() * size));
		}
	}

	public static void drawBackground(Graphics g, int width, int height) {
		float scale = width / (float) ScenePeriodicView.background.getWidth();
		for (int i = 0; i < height; i += ScenePeriodicView.background
				.getHeight() * scale)
			g.drawImage(ScenePeriodicView.background, 0, i,
					(int) (ScenePeriodicView.background.getWidth() * scale),
					(int) (ScenePeriodicView.background.getHeight() * scale),
					null);
	}

	@Override
	public void update() {
		if (EventListeners.jmbp.contains(MouseEvent.BUTTON1)) {
			Vector pos = EventListeners.mousePos.div(size);
			Atom a = PeriodicTable.getAtomAt(pos);
			if (a != null)
				switchToScene(new SceneAtomView(a));
		}
	}
}
