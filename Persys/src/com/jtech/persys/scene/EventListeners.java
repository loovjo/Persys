package com.jtech.persys.scene;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import com.jtech.persys.Utils;
import com.jtech.persys.Vector;

public class EventListeners implements MouseListener, MouseMotionListener,
		MouseWheelListener, KeyListener {

	public static ArrayList<Integer> keyPressed = new ArrayList<Integer>();
	public static ArrayList<Integer> mbp = new ArrayList<Integer>();
	public static ArrayList<Integer> justKeyPressed = new ArrayList<Integer>();
	public static ArrayList<Integer> jmbp = new ArrayList<Integer>();
	public static Vector mousePos = new Vector(0, 0);

	public EventListeners() {
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Utils.zoom = (float) Math.min(Math.max(0.4f, Utils.zoom * (e.getPreciseWheelRotation() / 10f + 1)), 2);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePos = new Vector(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos = new Vector(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePos = new Vector(e.getX(), e.getY());
		mbp.add(e.getButton());
		jmbp.add(e.getButton());

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePos = new Vector(e.getX(), e.getY());
		mbp.remove(mbp.indexOf(e.getButton()));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mousePos = new Vector(e.getX(), e.getY());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mousePos = new Vector(e.getX(), e.getY());
	}


	@Override
	public void keyTyped(KeyEvent e) {	
	}


	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed.add(e.getKeyCode());
		justKeyPressed.add(e.getKeyCode());
	}


	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed.remove(keyPressed.indexOf(e.getKeyCode()));
	}

}
