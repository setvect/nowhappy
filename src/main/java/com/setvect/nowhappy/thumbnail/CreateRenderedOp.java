package com.setvect.nowhappy.thumbnail;

import com.sun.media.jai.codec.SeekableStream;
import java.awt.image.renderable.ParameterBlock;
import java.io.InputStream;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

public class CreateRenderedOp
{

    public CreateRenderedOp()
    {
    }

    private SeekableStream getSeekableStream(InputStream is, boolean canSeekBackwards)
    {
        SeekableStream s = SeekableStream.wrapInputStream(is, canSeekBackwards);
        return s;
    }

    private ParameterBlock getParameterBlockOfImage(Object stream)
    {
        ParameterBlock pb = new ParameterBlock();
        pb.add(stream);
        return pb;
    }

    private RenderedOp getRenderOpDefault(InputStream is, String opName)
    {
        RenderedOp op = null;
        SeekableStream s = getSeekableStream(is, false);
        ParameterBlock pb = getParameterBlockOfImage(s);
        op = JAI.create(opName, pb);
        return op;
    }

    public static RenderedOp getRenderOpFromParameterBlock(String opName, ParameterBlock pb)
    {
        return JAI.create(opName, pb, null);
    }

    public RenderedOp getRenderOpPNG(InputStream is, String opName)
    {
        RenderedOp op = null;
        ParameterBlock pb = getParameterBlockOfImage(is);
        op = JAI.create(opName, pb);
        return op;
    }
}
