package doryan_bessiere.jbulma.fr.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import doryan_bessiere.jbulma.fr.JBulma;

public class Toolkit {

	public static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Integer getInteger(String string) {
		if (!isInteger(string))
			return null;
		return Integer.parseInt(string);
	}

	public static boolean isFloat(String string) {
		try {
			Float.parseFloat(string);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static Float getFloat(String string) {
		if (!isFloat(string))
			return null;
		return Float.parseFloat(string);
	}

	public static boolean isDouble(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static Double getDouble(String string) {
		if (!isDouble(string))
			return null;
		return Double.parseDouble(string);
	}

	public static String tab_spacer(int tab_spacer) {
		String spacer = "";
		for (int i = 0; i < tab_spacer; i++) {
			spacer += "	";
		}

		return spacer;
	}

	public static File downloadFile(String url, File directory) {
		try {
			String file_name = url.substring(url.lastIndexOf("/"));
			URL u = new URL(url);

			directory.mkdir();

			File destination = new File(directory, file_name);
			destination.createNewFile();

			URLConnection uc = u.openConnection();

			int taille = uc.getContentLength();

			InputStream brut = uc.getInputStream();
			InputStream entree = new BufferedInputStream(brut);

			byte[] donnees = new byte[taille];

			int octetsLus = 0;

			int deplacement = 0;
			float alreadyRead = 0;

			while (deplacement < taille) {
				octetsLus = entree.read(donnees, deplacement, donnees.length - deplacement);

				alreadyRead = alreadyRead + octetsLus;

				if (octetsLus == -1)
					break;

				deplacement += octetsLus;
			}
			entree.close();

			FileOutputStream fichierSortie = new FileOutputStream(destination);

			fichierSortie.write(donnees);

			fichierSortie.flush();
			fichierSortie.close();
			
			return destination;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getFormattedFileSize(long size) {
		String[] suffixes = new String[] { "byte", "Ko", "Mo", "Go", "To" };

		double tmpSize = size;
		int i = 0;

		while (tmpSize >= 1024) {
			tmpSize /= 1024.0;
			i++;
		}

		// arrondi à 10^-2
		tmpSize *= 100;
		tmpSize = (int) (tmpSize + 0.5);
		tmpSize /= 100;

		return tmpSize + " " + suffixes[i];
	}
}
