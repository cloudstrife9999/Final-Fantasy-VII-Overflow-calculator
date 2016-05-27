package com.ff7damage;

import com.ff7damage.view.View;

public class Main {

	public static void main(String[] args) {
		Model calculator = new Model();
		
		Controller controller = new Controller();
		View view = new View(controller);
		
		calculator.addObserver(controller);
		controller.addObserver(calculator);
		controller.addObserver(view);
		view.addObserver(controller);
		
		view.createAndShowInitialView();
	}
}