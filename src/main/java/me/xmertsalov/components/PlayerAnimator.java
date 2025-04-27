package me.xmertsalov.components;

import me.xmertsalov.Game;
import me.xmertsalov.utils.BundleLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerAnimator {

    private HashMap<String, Animator> animators; // for many skins


    private HashMap<String, String> skinsURL;
    private HashMap<String, List<Integer>> animationStates; // first - animation state, second - number of frames in animation


    public PlayerAnimator() {
        animators = new HashMap<>();
        loadAnimationStates();
        loadAnimationURLs();
        loadAnimators();
    }

    private PlayerAnimator(HashMap<String, Animator> animators){
        loadAnimationStates();
        loadAnimationURLs();
        this.animators = animators;
    }


    public PlayerAnimator clone(){
        HashMap<String, Animator> new_animators = new HashMap<>();
        for (String skinName : animators.keySet()) {
            new_animators.put(skinName, new Animator.Builder().clone(animators.get(skinName)));
        }
        return new PlayerAnimator(new_animators);
    }

    public Animator getAnimator(String key) {
        if (animators.containsKey(key)) {
            return animators.get(key);
        } else {
            Game.logger.error("Animator for {} not found", key);
            return animators.get("Adventure Boy A");
        }
    }

    private void loadAnimationURLs(){
        skinsURL = new HashMap<>();

        skinsURL.put("Adventure Boy A", BundleLoader.PLAYER_BOY_ADVENTURE_A_ATLAS);
        skinsURL.put("Adventure Boy B", BundleLoader.PLAYER_BOY_ADVENTURE_B_ATLAS);
        skinsURL.put("Adventure Boy C", BundleLoader.PLAYER_BOY_ADVENTURE_C_ATLAS);
        skinsURL.put("Adventure Boy D", BundleLoader.PLAYER_BOY_ADVENTURE_D_ATLAS);

        skinsURL.put("Knight A", BundleLoader.PLAYER_KNIGHT_A_ATLAS);
        skinsURL.put("Knight B", BundleLoader.PLAYER_KNIGHT_B_ATLAS);
        skinsURL.put("Knight C", BundleLoader.PLAYER_KNIGHT_C_ATLAS);
        skinsURL.put("Knight D", BundleLoader.PLAYER_KNIGHT_D_ATLAS);
        skinsURL.put("Knight E", BundleLoader.PLAYER_KNIGHT_E_ATLAS);
        skinsURL.put("Knight F", BundleLoader.PLAYER_KNIGHT_F_ATLAS);
        skinsURL.put("Knight G", BundleLoader.PLAYER_KNIGHT_G_ATLAS);
        skinsURL.put("Knight H", BundleLoader.PLAYER_KNIGHT_H_ATLAS);

        skinsURL.put("Special Knight 1", BundleLoader.PLAYER_SPECIAL_KNIGHT_1_ATLAS);
        skinsURL.put("Special Knight 2", BundleLoader.PLAYER_SPECIAL_KNIGHT_2_ATLAS);
        skinsURL.put("Special Knight 3", BundleLoader.PLAYER_SPECIAL_KNIGHT_3_ATLAS);
        skinsURL.put("Special Knight 4", BundleLoader.PLAYER_SPECIAL_KNIGHT_4_ATLAS);

        skinsURL.put("Worker A", BundleLoader.PLAYER_WORKER_A_ATLAS);
        skinsURL.put("Worker B", BundleLoader.PLAYER_WORKER_B_ATLAS);
        skinsURL.put("Worker C", BundleLoader.PLAYER_WORKER_C_ATLAS);
        skinsURL.put("Worker D", BundleLoader.PLAYER_WORKER_D_ATLAS);
    }

    private void loadAnimationStates(){
        animationStates = new HashMap<>();

        animationStates.put("IDLE", new ArrayList<>(List.of(0, 6)) );
        animationStates.put("WALKING", new ArrayList<>(List.of(1, 6)) );
        animationStates.put("RUNNING", new ArrayList<>(List.of(2, 6)) );
        animationStates.put("JUMPING", new ArrayList<>(List.of(3, 4)) );
        animationStates.put("ATTACK1", new ArrayList<>(List.of(5, 8)) );
        animationStates.put("ATTACK2", new ArrayList<>(List.of(6, 8)) );
        animationStates.put("HIT", new ArrayList<>(List.of(10, 4)) );
        animationStates.put("DEATH", new ArrayList<>(List.of(11, 8)) );

    }

    private void loadAnimators(){
        for (String skinName : skinsURL.keySet()) {
            Animator animator = new Animator.Builder()
                    .setSpriteWidth(48)
                    .setSpriteHeight(48)
                    .setRows(12)
                    .setColumns(8)
                    .setImageURL(skinsURL.get(skinName))
                    .setAnimationStates(animationStates)
                    .setCurrentState("IDLE")
                    .setAnimationSpeed(25)
                    .build_and_load();

            animators.put(skinName, animator);
        }
        Game.logger.info("Loaded {} characters", animators.size());
    }

    public HashMap<String, String> getSkinsURL() {
        return skinsURL;
    }

    public HashMap<String, Animator> getAnimationStates() {
        return animators;
    }



}
