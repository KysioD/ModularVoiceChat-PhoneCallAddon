package fr.kysio.modularvoicechat.call.common.commands;

import com.google.common.collect.Lists;
import fr.kysio.modularvoicechat.call.common.CallManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@SideOnly(Side.SERVER)
public class ReplyCallCommand implements ICommand {

    @Override
    public String getName() {
        return "replycall";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "replycallcommand.help";
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("rca");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(sender instanceof EntityPlayerMP){
            EntityPlayerMP player = (EntityPlayerMP) sender;
            if(args.length > 0) {
                String targetUsername = args[0];

                if (!targetUsername.isEmpty()) {
                    EntityPlayerMP target = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(targetUsername);
                    if (target != null) {
                        if(!CallManager.callingPlayers.containsKey(target)) {
                            if(!CallManager.callingPlayers.containsKey(player)) {
                                CallManager.startCall(player, target);
                                player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Vous répondez à l'appel de "+targetUsername+" !"));
                                target.sendMessage(new TextComponentString(TextFormatting.GREEN + "" + player.getName()+" a répondu à votre appel !"));
                            }else{
                                player.sendMessage(new TextComponentString(TextFormatting.RED + "Erreur, vous êtes déja en appel !"));
                            }
                        }else{
                            player.sendMessage(new TextComponentString(TextFormatting.RED + "Erreur, votre correspondant est déja en appel !"));
                        }
                    }else{
                        player.sendMessage(new TextComponentString(TextFormatting.RED + "Erreur, veuillez spécifier le pseudo d'un joueur valide."));
                    }
                } else {
                    player.sendMessage(new TextComponentString(TextFormatting.RED + "Erreur, veuillez spécifier le pseudo d'un joueur à qui répondre."));
                }
            }else{
                player.sendMessage(new TextComponentString(TextFormatting.RED + "Erreur, veuillez spécifier le pseudo d'un joueur à qui répondre."));
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

}
