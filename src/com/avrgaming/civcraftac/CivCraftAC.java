package com.avrgaming.civcraftac;

import com.google.common.collect.ImmutableMap;
import io.netty.channel.ChannelHandler;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;

// Referenced classes of package com.avrgaming.civcraftac:
//            Log, e, c, g

public class CivCraftAC
{

    public static final String MODID = "CivCraftAC";
    public static final String VERSION = "2.1";
    public static final String NAME = "CivCraft Anti-Cheat";
    public static HashMap<?, ?> c = new HashMap<Object, Object>();
    public static HashMap<String, Long> d = new HashMap<String, Long>();
    public static EnumMap<?, ?> e;

    public CivCraftAC()
    {
    }

    @SuppressWarnings("deprecation")
	public void preInit(FMLPreInitializationEvent event)
    {
        Log.info("PreInit");
        FMLCommonHandler.instance().bus().register(new eClass());
        e = NetworkRegistry.INSTANCE.newChannel("CAC", new ChannelHandler[] {
            new cClass()
        });
    }

    @SuppressWarnings("deprecation")
	public void init(FMLInitializationEvent event)
    {
        Log.info("Init");
        FMLCommonHandler.instance().bus().register(new eClass());
        FMLEventChannel networkEvent = NetworkRegistry.INSTANCE.newEventDrivenChannel("CAC");
        networkEvent.register(new gClass());
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        Log.info("PostInit");
        a();
    }

    public static boolean a()
    {
        boolean result = false;
        try
        {
            Class<?> minecraftClass = Class.forName("net.minecraft.client.main.Main");
            String path = minecraftClass.getProtectionDomain().getCodeSource().getLocation().getPath().split("!")[0];
            path = path.substring(6);
            new File(path);
            CheckedInputStream cis = new CheckedInputStream(new FileInputStream(path), new CRC32());
            byte buf[];
            for(buf = new byte[1024]; cis.read(buf) >= 0;) { }
            long checksum = cis.getChecksum().getValue();
            cis.close();
            d.put("net.minecraft.client.main.Main", Long.valueOf(checksum));
            List<?> mods = Loader.instance().getModList();
            for(Iterator<?> i$ = mods.iterator(); i$.hasNext();)
            {
                ModContainer mod = (ModContainer)i$.next();
                try
                {
                    File file = mod.getSource();
                    for(cis = new CheckedInputStream(new FileInputStream(file.getAbsolutePath()), new CRC32()); cis.read(buf) >= 0;) { }
                    checksum = cis.getChecksum().getValue();
                    d.put(mod.getName(), Long.valueOf(checksum));
                }
                catch(FileNotFoundException localFileNotFoundException1) { }
            }

            try
            {
                if(FMLClientHandler.instance().hasOptifine())
                {
                    Class<?> optifineConfig = Class.forName("Config", false, Loader.instance().getModClassLoader());
                    String optifineVersion = (String)optifineConfig.getField("VERSION").get(null);
                    ImmutableMap<Object, Object> dummyOptifineMeta = ImmutableMap.builder().put("name", "Optifine").put("version", optifineVersion).build();
                    HashMap<String, Object> dummyOptifineMeta2 = new HashMap<String, Object>();
                    Iterator<Object> i$ = dummyOptifineMeta.keySet().iterator();
                    do
                    {
                        if(!i$.hasNext())
                        {
                            break;
                        }
                        Object obj = i$.next();
                        if(obj instanceof String)
                        {
                            dummyOptifineMeta2.put((String)obj, dummyOptifineMeta.get(obj));
                        }
                    } while(true);
                    MetadataCollection.from(FMLClientHandler.instance().getClass().getResourceAsStream("optifinemod.info"), "optifine").getMetadataForId("optifine", dummyOptifineMeta2);
                    path = optifineConfig.getProtectionDomain().getCodeSource().getLocation().getPath().split("!")[0];
                    path = path.substring(6);
                    File file = new File(path);
                    for(cis = new CheckedInputStream(new FileInputStream(file.getAbsolutePath()), new CRC32()); cis.read(buf) >= 0;) { }
                    checksum = cis.getChecksum().getValue();
                    d.put("Optifine", Long.valueOf(checksum));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                d.put("BAD OPTIFINE CHECKSUM", Long.valueOf(0x3b9ac9ffL));
            }
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
