package com.avrgaming.civcraftac;

import io.netty.buffer.ByteBuf;
import io.netty.util.Attribute;
import java.io.PrintStream;
import java.security.InvalidKeyException;
import java.util.*;
import javax.crypto.spec.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;

// Referenced classes of package com.avrgaming.civcraftac:
//            CivCraftAC, a

public class gClass
{

    protected static final char a[] = "0123456789ABCDEF".toCharArray();

    public gClass()
    {
    }

    public void a(net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent event)
    {
        System.out.println("Got server packet");
    }

    public static String a(byte bytes[])
    {
        char hexChars[] = new char[bytes.length * 2];
        for(int j = 0; j < bytes.length; j++)
        {
            int v = bytes[j] & 0xff;
            hexChars[j * 2] = a[v >>> 4];
            hexChars[j * 2 + 1] = a[v & 0xf];
        }

        return new String(hexChars);
    }

    public void a(net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent event)
    {
        DESKeySpec dkey;
        try
        {
            byte k[] = new byte[8];
            int i = 0;
            int j = 0;
            byte arr$[] = event.getPacket().payload().array();
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                byte b = arr$[i$];
                if(j > 8)
                {
                    if(b != 0)
                    {
                        k[i] = b;
                        i++;
                    }
                } else
                {
                    j++;
                }
            }

            dkey = new DESKeySpec(k, 0);
        }
        catch(InvalidKeyException e)
        {
            e.printStackTrace();
            return;
        }
        SecretKeySpec key = new SecretKeySpec(dkey.getKey(), "DES");
        IvParameterSpec ivSpec = new IvParameterSpec(event.getPacket().payload().array(), 0, 8);
        long value = 0L;
        int a = 0;
        for(int i = 7; i >= 0; i--)
        {
            value += (ivSpec.getIV()[i] & 0xff) << 8 * a;
            a++;
        }

        boolean result = CivCraftAC.a();
        String out = (new StringBuilder()).append("v:1:").append(result).append(",").toString();
        for(Iterator<String> i$ = CivCraftAC.d.keySet().iterator(); i$.hasNext();)
        {
            String className = (String)i$.next();
            Long checksum = (Long)CivCraftAC.d.get(className);
            out = (new StringBuilder()).append(out).append(className).append(":").append(checksum).append(",").toString();
        }

        ((FMLEmbeddedChannel)CivCraftAC.e.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(net.minecraftforge.fml.common.network.FMLOutboundHandler.OutboundTarget.TOSERVER);
        ((FMLEmbeddedChannel)CivCraftAC.e.get(Side.CLIENT)).writeOutbound(new Object[] {
            new aClass(out, key, ivSpec)
        });
    }

}
