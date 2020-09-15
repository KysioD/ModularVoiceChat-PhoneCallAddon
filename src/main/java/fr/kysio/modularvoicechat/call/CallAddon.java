package fr.kysio.modularvoicechat.call;

import fr.kysio.modularvoicechat.call.common.CallManager;
import fr.kysio.modularvoicechat.call.common.CommonProxy;
import fr.kysio.modularvoicechat.call.common.commands.CallCommand;
import fr.kysio.modularvoicechat.call.common.commands.ReplyCallCommand;
import fr.kysio.modularvoicechat.call.common.commands.StopCallCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = CallAddon.modid, name = CallAddon.name)
public class CallAddon {

    public static final String modid = "modularvoicechatcalladdon";
    public static final String name = "ModularVoiceChatCallAddon";

    @SidedProxy(serverSide = "fr.kysio.modularvoicechat.call.common.CommonProxy", clientSide = "fr.kysio.modularvoicechat.call.client.ClientProxy")
    private static CommonProxy proxy;

    @Mod.EventHandler
    public void preInitialization(FMLPreInitializationEvent event){
        if(event.getSide().isServer()){
            MinecraftForge.EVENT_BUS.register(new CallManager());
        }
    }

    @Mod.EventHandler
    @SideOnly(Side.SERVER)
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CallCommand());
        event.registerServerCommand(new StopCallCommand());
        event.registerServerCommand(new ReplyCallCommand());
    }

}
