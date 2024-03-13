package net.nothingteam.nsnoxpfarms;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = NsNoXpfarms.MOD_ID)
public class EventHandler {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event){
        if(event.getEntity().getExperienceReward() == 0) return;
        ChunkAccess c = event.getEntity().level().getChunk(event.getEntity().blockPosition());
        if(NsNoXpfarms.chunkKillCounterMapGetter(c) >= 15){ event.getEntity().skipDropExperience();}
        NsNoXpfarms.chunkKillCounterMap.put(c,NsNoXpfarms.chunkKillCounterMapGetter(c)+1);
    }
    @SubscribeEvent
    public static void onLevelTickEvent(TickEvent.LevelTickEvent e){

        NsNoXpfarms.timeCounterToReduceChunkKills++;
        if(NsNoXpfarms.timeCounterToReduceChunkKills >=NsNoXpfarms.timeItTakesToReduceChunkKills){
            for(Map.Entry<ChunkAccess, Integer> entry: NsNoXpfarms.chunkKillCounterMap.entrySet()) {
                if(!(entry.getValue()==0)) {
                    entry.setValue(entry.getValue() - 1);
                }else NsNoXpfarms.chunkKillCounterMap.remove(entry.getKey());
            }
            NsNoXpfarms.timeCounterToReduceChunkKills = 0;
        }
    }
}
