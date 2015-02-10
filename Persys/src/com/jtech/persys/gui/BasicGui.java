package com.jtech.persys.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jtech.persys.scene.EventListeners;
import com.jtech.persys.scene.Scene;

public class BasicGui extends JPanel implements Runnable {

	public static JFrame frame;
	public static String title = "Persys";
	public static ArrayList<Scene> scenes = new ArrayList<Scene>();

	@Override
	public void run() {
		while (true) {
			if (scenes.size() == 0) {
				setup(new ScenePeriodicView());
			}
			if (scenes.get(scenes.size() - 1).goBack) {
				if (scenes.size() == 1)
					System.exit(0);
				scenes.remove(scenes.get(scenes.size() - 1));
				setup(scenes.get(scenes.size() - 1));
			}
			if (scenes.get(scenes.size() - 1).sscene != null) {
				setup(scenes.get(scenes.size() - 1).sscene);
				scenes.get(scenes.size() - 2).sscene = null;
			}
			scenes.get(scenes.size() - 1).resize(getSize());
			repaint();
			scenes.get(scenes.size() - 1).update();
			scenes.get(scenes.size() - 1).sUpdate();

			EventListeners.jmbp.clear();
			EventListeners.justKeyPressed.clear();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void setup(Scene s) {
		if (!scenes.contains(s))
			scenes.add(s);
		else {
			scenes.add(scenes.remove(scenes.indexOf(s)));
		}
		s.switchedTo();
	}

	public void paint(Graphics g) {
		if (scenes.size() > 0)
			scenes.get(scenes.size() - 1).draw(g);
	}

	public static float getGoodSize(FontMetrics fm, String str, float width,
			float height) {
		str = str.substring(0);
		float sWidth = fm.stringWidth(str);
		float fWidth = width / sWidth;
		float sHeight = fm.getHeight();
		float fHeight = height / sHeight;

		return fm.getFont().getSize() * Math.min(fWidth, fHeight);
	}

	public static Font getGoodFont(FontMetrics fm, String str, float width,
			float height) {
		return fm.getFont().deriveFont(getGoodSize(fm, str, width, height));
	}

	private void start() {
		new Thread(this).start();
		setFocusable(true);
		requestFocus();
		EventListeners el = new EventListeners();
		addKeyListener(el);
		addMouseListener(el);
		addMouseMotionListener(el);
		addMouseWheelListener(el);

	}

	public static void main(String[] args) {
		BasicGui gui = new BasicGui();

		frame = new JFrame(title);
		frame.pack();
		frame.setFocusable(true);
		frame.requestFocus();
		frame.setSize(new Dimension(640, 400));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(gui);

		gui.start();

		if (false) // Just for moving purposes
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(
						"AtomInfos/Info.txt")));
				String line;
				while ((line = br.readLine()) != null) {
					String name = line.split("\t")[3];
					File file = new File("Atoms/" + name + ".txt");
					if (file.exists())
						continue;
					file.getParentFile().mkdirs();
					file.createNewFile();
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					bw.append(line);
					bw.close();
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
