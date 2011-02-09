package ch.zhaw.ias.dito.io;

import java.util.HashMap;

public class CommandlineParser {

	public static HashMap<String, String> parse(String... args) {
		HashMap<String, String> params = new HashMap<String, String>();
		for (int i = 0; i < args.length; i++) {
			String key = args[i];
			if (key.startsWith("-") && args.length > (i+1)) {
				String next = args[i+1];
				if (next.startsWith("-")) {
					params.put(key, null);
				} else {
					params.put(key, next);
					i++;
				}
			} else if (key.startsWith("-")) {
				params.put(key, null);
			} else {
				//ignore
			}
		}
		return params;
	}
	
	private CommandlineParser() {
		
	}
}
