package me.xmertsalov;

import java.util.Arrays;
import java.util.List;

public class Main  {
	public static void main(String[] args) {
		List<String> argsList = Arrays.stream(args).toList();
		boolean debugColliders = false;
		boolean showFps = false;

		if(args.length > 0) {
			if(argsList.contains("--debug-colliders") || argsList.contains("-dc")) {
				debugColliders = true;
			}
			else if(argsList.contains("--show-fps") || argsList.contains("-f")) {
				showFps = true;
			}
		}

		new Game(debugColliders,showFps);
	}
}
