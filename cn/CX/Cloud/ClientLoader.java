/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
 */
package cn.CX.Cloud;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class ClientLoader
implements IFMLLoadingPlugin {
    public static boolean runtimeDeobfuscationEnabled = false;

    public String getAccessTransformerClass() {
        return null;
    }

    public String getModContainerClass() {
        return null;
    }

    public String[] getASMTransformerClass() {
        return new String[]{"cn.snowflake.rose.asm.ClassTransformer"};
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {
        runtimeDeobfuscationEnabled = (Boolean)data.get("runtimeDeobfuscationEnabled");
    }
}

