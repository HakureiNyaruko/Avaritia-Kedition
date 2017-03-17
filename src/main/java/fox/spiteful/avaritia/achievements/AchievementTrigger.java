package fox.spiteful.avaritia.achievements;


import fox.spiteful.avaritia.blocks.LudicrousBlocks;
import fox.spiteful.avaritia.compat.Compat;
import fox.spiteful.avaritia.items.LudicrousItems;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class AchievementTrigger {

    /*@SubscribeEvent
    public void onItemPickedUp(PlayerEvent.ItemPickupEvent event) {
        ItemStack stack = event.pickedUp.getEntityItem();

    }*/

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if(event.crafting != null){

            if(event.crafting.getItem() == LudicrousItems.resource){
                switch(event.crafting.getItemDamage()){
                    case 1:
                        event.player.addStat(Achievements.crystal_matrix, 1);
                        return;
                    case 4:
                        event.player.addStat(Achievements.neutronium, 1);
                        return;
                    case 5:
                        event.player.addStat(Achievements.catalyst, 1);
                        return;
                    case 6:
                        event.player.addStat(Achievements.infinity, 1);
                        return;
                }
            }
            else if(event.crafting.getItem() == Item.getItemFromBlock(LudicrousBlocks.DIRE_CRAFTING)){
                event.player.addStat(Achievements.dire_crafting, 1);
            }
            else if(event.crafting.getItem() == Item.getItemFromBlock(LudicrousBlocks.NEUTRON_COLLECTOR)){
                event.player.addStat(Achievements.collector, 1);
            }
            else if(event.crafting.getItem() == LudicrousItems.singularity){
                event.player.addStat(Achievements.singularity, 1);
            }
            else if(Compat.blood && event.crafting.getItem() == LudicrousItems.armok_orb){
                event.player.addStat(Achievements.armok, 1);
            }
        }
    }

    @SubscribeEvent
    public void onItemSmelted(PlayerEvent.ItemSmeltedEvent event) {
        if(event.smelting != null){

            if(event.smelting.getItem() == LudicrousItems.singularity){
                event.player.addStat(Achievements.singularity, 1);
            }
        }
    }

    @SubscribeEvent
    public void onBorken(BreakEvent event){
        if(event.getState().getBlock() == LudicrousBlocks.DIRE_CRAFTING && event.getPlayer() != null)
            event.getPlayer().addStat(Achievements.dire_uncrafting, 1);
    }
}