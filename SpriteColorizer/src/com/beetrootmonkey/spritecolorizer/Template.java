package com.beetrootmonkey.spritecolorizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.beetrootmonkey.spritecolorizer.Layer;
import com.beetrootmonkey.spritecolorizer.Palette;

public class Template {

	private String name = "";
	
	
	public String getName() {
		return name;
	}

	private List<Layer> layers = new ArrayList<Layer>();

	public void addLayer(File file) {
		Layer layer = Layer.create(file);
		if (layer != null) {
			layers.add(layer);
		}
	}

	public static Template createTemplate(File file) {
		Layer layer = Layer.create(file);
		return layer != null ? new Template(layer) : null;
	}
	
	public BufferedImage color(Palette palette) {
		BufferedImage image = null;
		for (Layer layer : layers.stream().sorted((l1, l2) -> l2.getNumber() - l1.getNumber()).collect(Collectors.toList())) {
			image = image == null ? layer.color(palette) : Utils.joinBufferedImage(image, layer.color(palette));
		}
		return image;
	}

	public Template(Layer layer) {
		if (layer != null) {
			layers.add(layer);
			name = layer.getName();
		}
	}

}
