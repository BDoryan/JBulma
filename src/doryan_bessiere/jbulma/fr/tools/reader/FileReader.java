package doryan_bessiere.jbulma.fr.tools.reader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

import doryan_bessiere.jbulma.fr.JBulma;

public class FileReader {

    public String name;
    public InputStream in;

    public FileReader(String name, File file) {
        this.name = name;
        if (file == null || !file.exists()) {
            throw new IllegalStateException("the selected file is not valid");
        }
        try {
            this.in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public FileReader(String name, String url_or_path, boolean isPath) {
        this.name = name;
        if (url_or_path == null) {
            throw new IllegalStateException("the resource selected is not valid");
        }
        if(isPath) {
            this.in = getClass().getResourceAsStream(url_or_path);	
        } else {
            try {
				this.in = new URL(url_or_path).openStream();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    public HashMap<String, String> messages = new HashMap<String, String>();

    public void read(boolean debug) {
        BufferedReader read = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));

        try {
            String line = null;
            while ((line = read.readLine()) != null) {
                if (!line.startsWith("#")) {
                    if (line.contains("=")) {
                        String[] args = line.split("=");

                        if (args.length == 2) {
                            String key = args[0];
                            String value = args[1];

                            messages.put(key, value);

                            if (debug) {
                            	JBulma.LOGGER.debug("[" + name + "] " + key + "=" + value);
                            }
                        } else {
                        	JBulma.LOGGER.error("[" + name + "] " + "args.length != 2 [line=" + line + "]");
                        }
                    }
                }
            }
            read.close();

            JBulma.LOGGER.info("[" + name + "] " + messages.size() + " messages loaded.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key, String... replace) {
        String message = key;
        if (messages.containsKey(key)) {
            message = messages.get(key);

            if (replace != null && replace.length % 2 == 0) {
                int i = 0;
                while (i < replace.length) {
                    message = message.replace(replace[i], replace[i + 1]);
                    i += 2;
                }
            }
        } else {
        	JBulma.LOGGER
                    .error("[" + name + "] " + key + " : The key you entered is not found in the list of messages");
        }
        return message;
    }
}
