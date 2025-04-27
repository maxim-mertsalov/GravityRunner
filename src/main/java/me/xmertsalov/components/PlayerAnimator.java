package me.xmertsalov.components;

import me.xmertsalov.Game;
import me.xmertsalov.components.Animator.Animator;
import me.xmertsalov.components.Animator.LoopingAnimationStrategy;
import me.xmertsalov.utils.BundleLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The PlayerAnimator class is responsible for managing animations for different player skins.
 * It provides functionality to load animation states, URLs, and create Animator objects for each skin.
 * This class supports cloning and provides access to specific Animator instances based on skin names.
 *
 * <p>
 * The PlayerAnimator class uses a combination of animation states and sprite sheets to define
 * animations for various player skins. It relies on the Animator class to handle the actual
 * animation logic and rendering.
 * </p>
 */
public class PlayerAnimator {

    private HashMap<String, Animator> animators; // Map of skin names to Animator objects
    private HashMap<String, String> skinsURL; // Map of skin names to sprite sheet URLs
    private HashMap<String, List<Integer>> animationStates; // Map of animation states to frame data

    /**
     * Default constructor for PlayerAnimator.
     * Initializes the animators map and loads animation states, URLs, and animators.
     *
     * <p>
     * This constructor sets up the PlayerAnimator by loading predefined animation states,
     * sprite sheet URLs, and creating Animator objects for each skin. The animators map
     * is populated with Animator instances that are configured based on the loaded data.
     * </p>
     */
    public PlayerAnimator() {
        animators = new HashMap<>();
        loadAnimationStates();
        loadAnimationURLs();
        loadAnimators();
    }

    /**
     * Private constructor used for cloning.
     *
     * @param animators A map of skin names to Animator objects.
     * <p>
     * This constructor is used internally to create a deep copy of the PlayerAnimator instance.
     * It reuses the provided animators map and reloads the animation states and URLs.
     * </p>
     */
    private PlayerAnimator(HashMap<String, Animator> animators) {
        loadAnimationStates();
        loadAnimationURLs();
        this.animators = animators;
    }

    /**
     * Creates a deep copy of the current PlayerAnimator instance.
     *
     * @return A new PlayerAnimator instance with cloned Animator objects.
     * <p>
     * This method creates a new PlayerAnimator instance by cloning all Animator objects
     * in the current instance. The cloning is performed using the Animator.Builder's clone method.
     * </p>
     */
    public PlayerAnimator clone() {
        HashMap<String, Animator> new_animators = new HashMap<>();
        for (String skinName : animators.keySet()) {
            new_animators.put(skinName, new Animator.Builder().clone(animators.get(skinName)));
        }
        return new PlayerAnimator(new_animators);
    }

    /**
     * Retrieves the Animator object for a given skin name.
     *
     * @param key The name of the skin.
     * @return The Animator object associated with the given skin name.
     */
    public Animator getAnimator(String key) {
        if (animators.containsKey(key)) {
            return animators.get(key);
        } else {
            Game.logger.error("Animator for {} not found", key);
            return animators.get("Adventure Boy A");
        }
    }

    /**
     * Loads the URLs for different player skins into the skinsURL map.
     * The URLs are used to locate the sprite sheets for each skin.
     */
    private void loadAnimationURLs() {
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

    /**
     * Loads the animation states into the animationStates map.
     * <p>
     * Each animation state is associated with a list of integers that define
     * the row index and the number of frames in the sprite sheet for that state.
     * </p>
     */
    private void loadAnimationStates() {
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

    /**
     * Loads Animator objects for each skin and populates the animators map.
     * <p>
     * This method iterates through the skinsURL map, creates an Animator object
     * for each skin using the Animator.Builder, and configures it with the
     * appropriate sprite sheet, animation states, and other properties.
     * </p>
     */
    private void loadAnimators() {
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
                    .setAnimationStrategy(new LoopingAnimationStrategy())
                    .build_and_load();

            animators.put(skinName, animator);
        }
        Game.logger.info("Loaded {} characters", animators.size());
    }

    /**
     * Retrieves the map of skin names to sprite sheet URLs.
     *
     * @return A HashMap where the keys are skin names and the values are the
     *         corresponding sprite sheet URLs.
     */
    public HashMap<String, String> getSkinsURL() {
        return skinsURL;
    }

    /**
     * Retrieves the map of skin names to Animator objects.
     *
     * @return A HashMap where the keys are skin names and the values are the
     *         corresponding Animator objects.
     */
    public HashMap<String, Animator> getAnimationStates() {
        return animators;
    }
}
