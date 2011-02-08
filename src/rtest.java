import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

class TextConsole implements RMainLoopCallbacks {
	public void rWriteConsole(Rengine re, String text, int oType) {
		System.out.print(text);
	}

	public void rBusy(Rengine re, int which) {
		System.out.println("rBusy(" + which + ")");
	}

	public String rReadConsole(Rengine re, String prompt, int addToHistory) {
		System.out.print(prompt);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String s = br.readLine();
			return (s == null || s.length() == 0) ? s : s + "\n";
		} catch (Exception e) {
			System.out.println("jriReadConsole exception: " + e.getMessage());
		}
		return null;
	}

	public void rShowMessage(Rengine re, String message) {
		System.out.println("rShowMessage \"" + message + "\"");
	}

	public String rChooseFile(Rengine re, int newFile) {
		FileDialog fd = new FileDialog(new Frame(),
				(newFile == 0) ? "Select a file" : "Select a new file",
				(newFile == 0) ? FileDialog.LOAD : FileDialog.SAVE);
		fd.show();
		String res = null;
		if (fd.getDirectory() != null)
			res = fd.getDirectory();
		if (fd.getFile() != null)
			res = (res == null) ? fd.getFile() : (res + fd.getFile());
		return res;
	}

	public void rFlushConsole(Rengine re) {
	}

	public void rLoadHistory(Rengine re, String filename) {
	}

	public void rSaveHistory(Rengine re, String filename) {
	}
}

public class rtest {
	public static void main(String[] args) {
		// just making sure we have the right version of everything
		if (!Rengine.versionCheck()) {
			System.err
					.println("** Version mismatch - Java files don't match library version.");
			System.exit(1);
		}
		System.out.println("Creating Rengine (with arguments)");
		// 1) we pass the arguments from the command line
		// 2) we won't use the main loop at first, we'll start it later
		// (that's the "false" as second argument)
		// 3) the callbacks are implemented by the TextConsole class above
		Rengine re = new Rengine(args, false, new TextConsole());
		System.out.println("Rengine created, waiting for R");
		// the engine creates R is a new thread, so we should wait until it's
		// ready
		if (!re.waitForR()) {
			System.out.println("Cannot load R");
			return;
		}

		/*
		 * High-level API - do not use RNI methods unless there is no other way
		 * to accomplish what you want
		 */
		try {
			REXP x;
			x = re.eval("dataset1 <- read.csv(file = \"C:/java-workspace/DistanceTool/Daten/Smartvote/rohdaten2003-numeric.csv\", sep=\";\")");
			RVector r = x.asVector();
			for (int i = 0; i < r.size(); i++) {
				REXP row = (REXP) r.get(i);
				double[] values = row.asDoubleArray();
				for (int j = 0; j < values.length; j++) {
					System.out.print(values[j] + "\t");
				}
				System.out.println();
			}

		} catch (Exception e) {
			System.out.println("EX:" + e);
			e.printStackTrace();
		}
		re.end();
	}
}
