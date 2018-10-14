package doryan_bessiere.jbulma.fr.tools.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class LoggerFile {

	protected File file;
	protected FileWriter writer;
	
	public LoggerFile(File folder) {
		folder.mkdir();
		this.file = new File(folder, time()+"-0.log");
		int i = 1;
		while(this.file.exists()) {
			this.file = new File(folder, time()+"-"+i+".log");
			i++;
		}
		try {
			this.file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.writer = new FileWriter(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void message(String message) {
		try {
			this.writer.write(message+"\n");
			this.writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String time() {
		return new SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis());
	}
}
