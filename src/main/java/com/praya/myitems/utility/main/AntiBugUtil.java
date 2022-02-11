// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.utility.main;

import com.praya.agarthalib.utility.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;

public class AntiBugUtil {
    public static void antiBugCustomStats() {
        for (final Location loc : BlockUtil.getDataLoc()) {
            loc.getBlock().setType(Material.AIR);
        }
    }
}
