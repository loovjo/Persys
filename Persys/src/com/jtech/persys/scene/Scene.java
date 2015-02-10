package com.jtech.persys.scene;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public abstract class Scene {

	public Scene sscene = null; // Scene to switch to
	public boolean goBack;
	private int width, height;
	public boolean backOnEsq = true;

	public void draw(Graphics g) {
	}

	public void update() {// Will be called 60 times per sec
	}

	public final void sUpdate() {
		if (backOnEsq
				&& EventListeners.justKeyPressed.contains(KeyEvent.VK_ESCAPE)) {
			goBack = true;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void resize(Dimension d) {
		width = (int) d.getWidth();
		height = (int) d.getHeight();
	}

	public void switchToScene(Scene s) {
		sscene = s;
	}
	
	public void onSceneSwitch() {
		sscene = null;
	}

	public void goBack() {
		goBack = true;
	}
	public void switchedTo() {
		
	}
}
