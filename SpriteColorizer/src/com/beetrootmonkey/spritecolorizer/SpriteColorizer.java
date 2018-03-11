package com.beetrootmonkey.spritecolorizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class SpriteColorizer {

	private String path;
	private final String ROOT_FOLDER = "/spritecolorizer";
	private final String TEMPLATES_FOLDER = ROOT_FOLDER + "/templates";
	private final String PALETTES_FOLDER = ROOT_FOLDER + "/palettes";
	private final String OUTPUT_FOLDER = ROOT_FOLDER + "/output";
	public static String NUMBER_SEPARATOR = "-";
	public static String NAME_PLACEHOLDER = "#";

	private Map<String, Template> templates = new HashMap<String, Template>();

	// Load all templates
	public void loadTemplates() {
		System.out.println("Loading templates...");

		File src = new File(path + TEMPLATES_FOLDER);
		for (File file : src.listFiles()) {
			loadLayer(file);
		}

		for (Map.Entry<String, Template> entry : templates.entrySet()) {
			// System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

	// Load a single layer
	private void loadLayer(File file) {

		// Load template = all registered layers from map
		Template t = templates.get(Utils.extractName(file.getName()));

		if (t == null) {
			// If there is no template yet, it means we have to create a new one and
			// register the first layer
			Template template = Template.createTemplate(file);
			if (template != null) {
				templates.put(Utils.extractName(template.getName()), template);
				System.out.println(" - Created new template - " + template.getName());
			} else {
				System.out.println(" - Couldn't create new template!");
			}

		} else {
			// If we found a template, we can just add a new layer to it
			t.addLayer(file);
			System.out.println(" - Added new layer to existing template - " + t.getName());
		}
	}

	// Load all palettes
	public void loadPalettes() {
		System.out.println("Loading palettes...");

		File src = new File(path + PALETTES_FOLDER);
		for (File file : src.listFiles()) {
			loadPalette(file);
		}
	}

	private void loadPalette(File file) {
		// Load a single palette
		Palette palette = Palette.create(file);
		if (palette == null) {
			return;
		}

		System.out.println(" - Loading palette " + palette.getName());

		// For all templates
		for (Map.Entry<String, Template> entry : templates.entrySet()) {
			BufferedImage image = entry.getValue().color(palette);
			if (image != null) {
				String dirPath = OUTPUT_FOLDER + "/" + palette.getName();
				File dir = new File(path + dirPath);
				if (!dir.exists()) {
					dir.mkdir();
				}
				String savePath = path + dirPath + "/" + Utils.applyPalette(entry.getValue().getName(), palette)
						+ ".png";
				
				try {

					ImageIO.write(image, "png", new File(savePath));
					System.out.println("Saved image - " + path + "!");
				} catch (IOException e) {
					System.out.println("Error: Couldn't save image " + savePath + "!");
				}
			}

		}

		// For all sorted layers
		// Color layers
		// Stitch layers together
		// Save template
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
