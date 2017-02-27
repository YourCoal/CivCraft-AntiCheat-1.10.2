package com.avrgaming.civcraftac;

import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

// Referenced classes of package com.avrgaming.civcraftac:
//            f

public class aClass
    implements fClass
{

    String a;
    SecretKeySpec b;
    IvParameterSpec c;
    int d;

    public aClass(String message, SecretKeySpec key2, IvParameterSpec ivSpec)
    {
        a = message;
        b = key2;
        c = ivSpec;
    }

    public void c(ByteBuf bytes)
    {
        a = "";
    }

    public void b(ByteBuf bytes)
    {
        ByteBuffer bb = Charset.forName("UTF-8").encode(a);
        try
        {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(1, this.b, c);
            byte encypted[] = new byte[cipher.getOutputSize(bb.array().length)];
            int enc_len = cipher.update(bb.array(), 0, bb.array().length, encypted, 0);
            enc_len += cipher.doFinal(encypted, enc_len);
            byte arr$[] = encypted;
            int len$ = arr$.length;
            for(int i$ = 0; i$ < len$; i$++)
            {
                byte b = arr$[i$];
                bytes.writeByte(b);
            }

        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        catch(ShortBufferException e)
        {
            e.printStackTrace();
        }
        catch(IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }
        catch(BadPaddingException e)
        {
            e.printStackTrace();
        }
        catch(InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch(InvalidAlgorithmParameterException e)
        {
            e.printStackTrace();
        }
    }
}
