package com.beetrootmonkey.spritecolorizer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.FileUtils;

public class SpriteColorizer {
	
	private String path;
	private final String ROOT_FOLDER = "/spritecolorizer";
	private final String TEMPLATES_FOLDER = ROOT_FOLDER + "/templates";
	private final String PALETTES_FOLDER = ROOT_FOLDER + "/palettes";
	private final String OUTPUT_FOLDER = ROOT_FOLDER + "/output";
	
	public void loadTemplates() {
		// Load all templates
		File src = new File(path + TEMPLATES_FOLDER);
		for (File file : src.listFiles()) {
			System.out.println(file.getAbsolutePath());
		}
	}
	
	private void loadTemplate() {
		// Load all layers
	}
	
	private void loadLayer() {
		// Load a single layer
	}
	
	public void loadPalettes() {
		// Load all palettes
	}
	
	private void loadPalette() {
		// Load a single palette
		// For all templates
		//   For all sorted layers
		//     Color layers
		//   Stitch layers together
		//   Save template
	}
	
	public void createExampleDirectory() {
		// Create sample structure
		URL res = this.getClass().getResource("/sample");
		File srcDir = null;
		try {
			srcDir = new File(res.toURI());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}

		String destination = path;
		File destDir = new File(destination);

		try {
		    FileUtils.copyDirectory(srcDir, destDir);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public SpriteColorizer(String path) {
		this.path = path;
	}
}
