package sattoglpk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class CnfConvertor {

	public static ArrayList<Predicate3sat> predicates = new ArrayList<>();

	public static void main(String[] args) {
		File datafile = promptfile();

		try {
			BufferedReader b = new BufferedReader(new FileReader(datafile));
			String readLine = "";
			System.out.println("Reading file using Buffered Reader");
			int i = 0;
			while ((readLine = b.readLine()) != null) {
				System.out.println("Reading line " + i + " : " + readLine);
				readLine(readLine, i);
				++i;
			}
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Asks the user to input a file and returns it. Stops the asking thread
	 * till the user selects a file.
	 */
	public static File promptfile() {
		JFileChooser filechooser = new JFileChooser();
		filechooser.setDialogTitle("Select your .cnf file");
		if (filechooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			System.out.println("You only had one job...");
			System.exit(0);
		}
		return filechooser.getSelectedFile();
	}

	public static void readLine(String line, int number) {
		if (line.startsWith("c") || line.startsWith("p") || line.startsWith("%") || line.startsWith("0"))
			return;
		else if (line.startsWith(" "))
			line = line.substring(1);
		String[] values = line.split(" ");
		predicates.add(new Predicate3sat(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
				Integer.parseInt(values[2])));
	}

}
