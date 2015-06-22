package com.setvect.nowhappy.thumbnail;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

public class WritingImage {

	public WritingImage() {
	}

	public static void writeImageToFile(RenderedOp srcOp, String outFilename,
			String format) {
		File file = new File(outFilename);
		try {
			ImageIO.write(srcOp, format, file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void writeImageToFileFromGIF(RenderedOp srcOp,
			String outFilename, String format) {
		File file = new File(outFilename);
		try {
			ImageIO.write(srcOp, format, file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void writeImageToStream(String opName, RenderedOp srcOp,
			OutputStream stream, String format) {
		JAI.create(opName, srcOp, stream, format, null);
		if (stream != null) {
			try {
				stream.flush();
				stream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
