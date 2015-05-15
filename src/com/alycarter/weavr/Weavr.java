package com.alycarter.weavr;

public class Weavr {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Loom loom = new Loom(2);
		Window window = new Window("weavr");
		window.initialize(loom);
		window.setVisible(true);
	}

}
