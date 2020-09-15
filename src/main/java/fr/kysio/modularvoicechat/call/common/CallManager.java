package fr.kysio.modularvoicechat.call.common;

import fr.nathanael2611.modularvoicechat.api.VoiceDispatchEvent;
import fr.nathanael2611.modularvoicechat.api.VoiceProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

@SideOnly(Side.SERVER)
public class CallManager {

    /**
     * Hashmap speaker, target
     */
    public static HashMap<EntityPlayerMP, EntityPlayerMP> callingPlayers = new HashMap<>();
    public static HashMap<EntityPlayerMP, EntityPlayerMP> pendingCalls = new HashMap<>();

    public static void addPendingCall(EntityPlayerMP player1, EntityPlayerMP player2){
        pendingCalls.put(player1, player2);
        player2.sendMessage(new TextComponentString(TextFormatting.GREEN+"Vous recevez un appel de "+player1.getName()+" effectuez /replycall "+player1.getName()+" pour décrocher !"));
    }

    public static void startCall(EntityPlayerMP player1, EntityPlayerMP player2){
        callingPlayers.put(player1, player2);
        callingPlayers.put(player2, player1);
    }

    public static void stopCall(EntityPlayerMP player){
        if(callingPlayers.containsKey(player)){
            callingPlayers.get(player).sendMessage(new TextComponentString(TextFormatting.DARK_RED+"Votre correspondant à mit fin à l'appel !"));
            player.sendMessage(new TextComponentString(TextFormatting.DARK_RED+"Vous avez mit fin à l'appel !"));
            callingPlayers.remove(callingPlayers.get(player));
            callingPlayers.remove(player);
        }

        if(pendingCalls.containsKey(player)){
            pendingCalls.get(player).sendMessage(new TextComponentString(TextFormatting.DARK_RED+"Votre correspondant à mit fin à l'appel !"));
            player.sendMessage(new TextComponentString(TextFormatting.DARK_RED+"Vous avez mit fin à l'appel !"));
            pendingCalls.remove(pendingCalls.get(player));
            pendingCalls.remove(player);
        }
    }

    @SubscribeEvent
    public void onVoiceDispatch(VoiceDispatchEvent event)
    {
        EntityPlayerMP speaker = event.getSpeaker();
        System.out.println("Dispatch : "+callingPlayers);
        if(callingPlayers.containsKey(speaker)){
            System.out.println("dispatching");
            event.dispatchTo(callingPlayers.get(speaker), 80, VoiceProperties.builder().with("IsRadio", true).build());
        }
    }
}
