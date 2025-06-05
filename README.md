# Gravity Runner
![Gravity Runner](/background_readme.png)

Are you ready for a real competition of reaction and speed? In **Gravity Runner**, you don't control your character — you control gravity itself! One tap and your character goes up, another tap and he goes down. Overcome static obstacles and dodge deadly saws that won't give you a second chance.

But that's not all – there are bonuses along the way! Accelerate to get ahead, slow down time for precise manoeuvres. Each race is a new chaotic surprise where only the fastest and most agile survive.

Compete with friends in local multiplayer on the same screen or show everyone who's in the lead in a single race! A trip meter at the end of the game will help you determine who is the true master of gravity.

## Features
- **Gravity-based controls**: Tap to switch gravity and navigate through obstacles.
- **Dynamic gameplay**: Dodge deadly saws and static obstacles.
- **Power-ups**: Speed boosts and time-slowing bonuses for precise manoeuvres.
- **Local multiplayer**: Play with friends in local multiplayer or compete in single-player mode.
- **Trip meter**: Determine the ultimate winner at the end of each race.

## Libraries and Frameworks Used
- **Swing**: For the graphical user interface and rendering.
- **org.apache.logging.log4j**: For logging and debugging.
- **org.junit.jupiter**: For unit testing.

## How to Run the Game

You have two options to run the game:

### 1. Run via Maven (build manually)

1. Make sure you have **Java 8** or later installed.
2. Clone the repository:
   ```bash
   git clone git@github.com:maxim-mertsalov/GravityRunner.git
   cd GravityRunner
    ```

3. Build the project using Maven:
    ```bash
    mvn clean install
   ```

4. Run the game:
    ```bash
    mvn exec:java
   ```

### 2. Download the Release (easier)

1. Go to the [Releases page](https://github.com/maxim-mertsalov/GravityRunner/releases).
2. Download the latest `.jar` file (`GravityRunner-1.3-SNAPSHOT.jar`).
3. Run the game with Java:
   ```bash
   java -jar GravityRunner-1.3-SNAPSHOT.jar
   ```

### Runtime Options

You can run the game with the following options for debugging and additional features:

- `--debug-colliders` or `-dc`: Show all colliders in the game for debugging purposes.
- `--show-fps` or `-f`: Display the FPS (frames per second) counter during gameplay.

Example:
```bash
java -jar GravityRunner-1.3-SNAPSHOT.jar --debug-colliders --show-fps
```
or
```bash
mvn exec:java -Dexec.args="--debug-colliders --show-fps"
```

## Third-party Resources

- **Treasure Hunters Assets** – Free – [Link](https://pixelfrog-assets.itch.io/treasure-hunters)
- **Free Trap Platformer** – Free – [Link](https://bdragon1727.itch.io/free-trap-platformer)
- **Pixel Adventure Character 16x16** – Free – [Link](https://bdragon1727.itch.io/pixel-character-16x16)
- **SFX** – [Pixabay License](https://pixabay.com/service/license-summary)
    - Countdown SFX – [Link](https://pixabay.com/sound-effects/countdown-sound-effect-8-bit-151797)
    - Jump SFX – [Link](https://pixabay.com/sound-effects/pixel-jump-319167)
    - Click SFX – [Link](https://pixabay.com/sound-effects/retro-click-236673)
    - Coin SFX – [Link](https://pixabay.com/sound-effects/retro-coin-2-236685)
- **Music** – [Uppbeat Commercial License](https://uppbeat.io/commercial-licenses)
    - Threshold – Michael Grubb – [Link](https://uppbeat.io/track/michael-grubb/threshold)
    - Pookatori And Friends – Kevin MacLeod – [Link](https://uppbeat.io/track/kevin-macleod/pookatori-and-friends)
    - Cyborg Ninja – Kevin MacLeod – [Link](https://uppbeat.io/track/kevin-macleod/cyborg-ninja)

## Contributing

Feel free to fork the repository and submit pull requests. Contributions are welcome!

## License

This project is licensed under the MIT License.