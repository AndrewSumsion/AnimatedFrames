package me.El_Chupe.animatedframes;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class Animations implements Listener {
    private static Map<Player, Map<Canvas, RunningAnimation>> runningAnimations = new HashMap<Player, Map<Canvas, RunningAnimation>>();

    public static BukkitTask registerAnimation(final Animation animation, int period, final Canvas canvas, final Player player) {
        animation.init(canvas, player);
        BukkitTask task = new BukkitRunnable() {

            public void run() {
                animation.cycle(canvas, player);
            }
        }.runTaskTimer(AnimatedFrames.getInstance(), 0, period);
        if(!runningAnimations.containsKey(player)) runningAnimations.put(player, new HashMap<Canvas, RunningAnimation>());
        runningAnimations.get(player).put(canvas, new RunningAnimation(task, animation.getClass()));
        return task;
    }

    public static void stopAnimation(Player player, Canvas canvas) {
        runningAnimations.get(player).get(canvas).stop();
        runningAnimations.get(player).remove(canvas);
    }

    public static Class<? extends Animation> getRunningAnimation(Player player, Canvas canvas) {
        return runningAnimations.get(player).get(canvas).getAnimationClass();
    }

    private static void playerLeave(Player player) {
        if(!runningAnimations.containsKey(player)) return;
        for(RunningAnimation animation : runningAnimations.get(player).values()) {
            animation.stop();
        }
        runningAnimations.remove(player);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        playerLeave(event.getPlayer());
    }

    @EventHandler
    private void onKick(PlayerKickEvent event) {
        playerLeave(event.getPlayer());
    }

    private static class RunningAnimation {
        private BukkitTask task;
        private Class<? extends Animation> animationClass;

        public RunningAnimation(BukkitTask task, Class<? extends Animation> animationClass) {
            this.task = task;
            this.animationClass = animationClass;
        }

        public void stop() {
            task.cancel();
        }

        public Class<? extends Animation> getAnimationClass() {
            return animationClass;
        }
    }
}
