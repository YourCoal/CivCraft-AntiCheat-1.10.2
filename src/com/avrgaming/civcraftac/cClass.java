package com.avrgaming.civcraftac;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;

// Referenced classes of package com.avrgaming.civcraftac:
//            a, f

public class cClass extends FMLIndexedMessageToMessageCodec
{

    public cClass()
    {
        addDiscriminator(0, com/avrgaming/civcraftac/aClass);
    }

    public void encodeInto(ChannelHandlerContext ctx, fClass packet, ByteBuf data)
        throws Exception
    {
        packet.b(data);
    }

    public void decodeInto(ChannelHandlerContext ctx, ByteBuf data, fClass packet)
    {
        packet.c(data);
    }

    public void decodeInto(ChannelHandlerContext x0, ByteBuf x1, Object x2)
    {
        decodeInto(x0, x1, (fClass)x2);
    }

    public void encodeInto(ChannelHandlerContext x0, Object x1, ByteBuf x2)
        throws Exception
    {
        encodeInto(x0, (fClass)x1, x2);
    }
}
