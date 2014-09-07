package com.ollieread.technomagi.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.entity.player.EntityPlayer;

import org.apache.commons.io.Charsets;

import com.google.common.io.CharStreams;
import com.ollieread.technomagi.common.Reference;

public class VersionChecker implements Runnable
{

    protected EntityPlayer player;

    public VersionChecker(EntityPlayer player)
    {
        this.player = player;
    }

    @Override
    public void run()
    {
        try {
            String versions = CharStreams.toString(new InputStreamReader(new URL("http://technomagi.ollieread.com/versions.txt").openStream(), Charsets.UTF_8));

            if (!versions.equals(Reference.VERSION)) {
                PlayerHelper.addChatMessage(player, "There is a new version of TechnoMagi available. http://s.ollieread.com/XPa8");
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
