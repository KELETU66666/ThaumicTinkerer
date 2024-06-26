package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.utils.ItemNBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.common.lib.CommandThaumcraft;

import javax.annotation.Nullable;
import java.util.*;

public class ItemShareBook extends TTItem {
    private static final String TAG_PLAYER = "player";
    private static final String NON_ASIGNED = "[none]";

    public ItemShareBook() {
        super(LibItemNames.SHARE_BOOK);

        setMaxStackSize(1);
    }

    private static String getPlayerName(ItemStack stack) {
        return ItemNBTHelper.getString(stack, TAG_PLAYER, NON_ASIGNED);
    }

    private static void setPlayerName(ItemStack stack, String name) {
        ItemNBTHelper.setString(stack, TAG_PLAYER, name);
    }

    private static void clearPlayerResearch(ItemStack stack) {
        ItemNBTHelper.getItemTag(stack).removeTag("research");
    }

    protected static void setPlayerObservations(ItemStack stack, EntityPlayer player) {
        NBTTagCompound cmp = ItemNBTHelper.getItemTag(stack);
        NBTTagList list = new NBTTagList();
        for (ResearchCategory category: ResearchCategories.researchCategories.values()){
            int obs=ThaumcraftCapabilities.getKnowledge(player).getKnowledgeRaw(IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, category);
            NBTTagCompound obsCmp=new NBTTagCompound();
            obsCmp.setString("category",category.key);
            obsCmp.setInteger("amount",obs);
            list.appendTag(obsCmp);
        }
        cmp.setTag("observations",list);
    }

    private static void setPlayerResearch(ItemStack stack, EntityPlayer playername) {
        Set<String> researchesDone = ThaumcraftCapabilities.getKnowledge(playername).getResearchList();
        NBTTagCompound cmp = ItemNBTHelper.getItemTag(stack);
        NBTTagList list = new NBTTagList();
        for (String tag : researchesDone) {
            if (ThaumcraftCapabilities.getKnowledge(playername).isResearchComplete(tag))
                list.appendTag(new NBTTagString(tag));
        }
        cmp.setTag("research", list);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack item = playerIn.getHeldItem(handIn);
        if (!playerIn.isSneaking()) {
            if (item.getItem() instanceof ItemShareBook) {
                String name = getPlayerName(item);
                if (name.endsWith(NON_ASIGNED)) {
                    setPlayerName(item, playerIn.getGameProfile().getName());
                    setPlayerResearch(item, playerIn);
                    setPlayerObservations(item,playerIn);
                    if (!worldIn.isRemote)
                        playerIn.sendStatusMessage(new TextComponentTranslation("ttmisc.shareTome.write"), true);
                } else {
                    Set<String> researchesDone;
                    if (!worldIn.isRemote) {
                        researchesDone = getPlayerResearch(item);
                        for (String researchkey : researchesDone) {
                            CommandThaumcraft.giveRecursiveResearch(playerIn, researchkey);
                            ThaumcraftCapabilities.getKnowledge(playerIn).sync((EntityPlayerMP) playerIn);
                        }
                        Map<String,Integer> observations = getPlayerObservations(item);
                        for(String category:observations.keySet()) {
                            int curAmmount=ThaumcraftCapabilities.getKnowledge(playerIn).getKnowledgeRaw(IPlayerKnowledge.EnumKnowledgeType.OBSERVATION,ResearchCategories.getResearchCategory(category));
                            int amtToAdd=Math.abs(observations.get(category) - curAmmount);
                            ThaumcraftCapabilities.getKnowledge(playerIn).addKnowledge(IPlayerKnowledge.EnumKnowledgeType.OBSERVATION,ResearchCategories.getResearchCategory(category),amtToAdd);
                        }
                    } else {
                        playerIn.sendStatusMessage(new TextComponentTranslation(("ttmisc.shareTome.sync")), true);
                    }

                }
            }
        } else {
            clearPlayerResearch(item);
            setPlayerName(item,NON_ASIGNED);
            playerIn.sendStatusMessage(new TextComponentTranslation("ttmisc.shareTome.clear"), true);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, item);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        String name = getPlayerName(stack);
        tooltip.add(name.equals(NON_ASIGNED) ? ThaumicTinkerer.proxy.localize("ttmisc.shareTome.noAssign") : ThaumicTinkerer.proxy.localize("ttmisc.shareTome.playerName", name));
    }

    private Set<String> getPlayerResearch(ItemStack stack) {
        Set<String> retVals = new HashSet<>();
        NBTTagCompound cmp = ItemNBTHelper.getItemTag(stack);
        if (!cmp.hasKey("research"))
            return retVals;
        NBTTagList list = cmp.getTagList("research", Constants.NBT.TAG_STRING);
        for (int i = 0; i < list.tagCount(); i++) {

            retVals.add(list.getStringTagAt(i));
        }
        return retVals;
    }

    private Map<String,Integer> getPlayerObservations(ItemStack stack) {
        Map<String,Integer> retVals = new HashMap<>();
        NBTTagCompound cmp = ItemNBTHelper.getItemTag(stack);
        if (!cmp.hasKey("observations"))
            return retVals;
        NBTTagList list = cmp.getTagList("observations", Constants.NBT.TAG_COMPOUND);
        for(int i=0;i<list.tagCount();i++) {
            NBTTagCompound listCmp=list.getCompoundTagAt(i);
            retVals.put(listCmp.getString("category"),listCmp.getInteger("amount"));
        }
        return retVals;

    }
}
