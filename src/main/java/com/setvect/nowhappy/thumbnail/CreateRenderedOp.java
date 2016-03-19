package com.setvect.nowhappy.thumbnail;

import java.awt.image.renderable.ParameterBlock;
import java.io.InputStream;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

public class CreateRenderedOp {

	public CreateRenderedOp() {
	}

	private ParameterBlock getParameterBlockOfImage(Object stream) {
		ParameterBlock pb = new ParameterBlock();
		pb.add(stream);
		return pb;
	}

	public static RenderedOp getRenderOpFromParameterBlock(String opName, ParameterBlock pb) {
		return JAI.create(opName, pb, null);
	}

	public RenderedOp getRenderOpPNG(InputStream is, String opName) {
		RenderedOp op = null;
		ParameterBlock pb = getParameterBlockOfImage(is);
		op = JAI.create(opName, pb);
		return op;
	}
}
