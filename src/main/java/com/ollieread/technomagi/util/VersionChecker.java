package com.ollieread.technomagi.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.entity.player.EntityPlayer;

import org.apache.commons.io.Charsets;

import com.google.common.io.CharStreams;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.VersionParser;

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
            ArtifactVersion recommended = VersionParser.parseVersionReference(versions);
            ArtifactVersion current = VersionParser.parseVersionReference(Reference.VERSION);

            if (!VersionParser.satisfies(current, recommended)) {
                PlayerHelper.addLinkedChatMessage(player, "You are not using a recommended version. ", "http://s.ollieread.com/XT5E");
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
