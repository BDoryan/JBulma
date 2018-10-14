package doryan_bessiere.jbulma.fr.jbulma;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import doryan_bessiere.jbulma.fr.JBulma;
import doryan_bessiere.jbulma.fr.tools.Toolkit;
import doryan_bessiere.jbulma.fr.tools.zip.MyZip;
import doryan_bessiere.jwebapi.fr.engine.HTMLPage;
import doryan_bessiere.jwebapi.fr.engine.css.CSS;
import doryan_bessiere.jwebapi.fr.engine.js.JS;
import doryan_bessiere.jwebapi.fr.engine.nodes.children.link.link;
import doryan_bessiere.jwebapi.fr.engine.nodes.children.meta.meta;
import doryan_bessiere.jwebapi.fr.engine.nodes.parent.script.script;

public abstract class BulmaPage extends HTMLPage {

	public File src_directory;
	public File javascript_directory;
	public File css_directory;
	public File imgs_directory;

	public File css_file;
	public File js_file;

	public String jquery_version = "3.3.1";
	
	public BulmaPage(File directory, String name, String title, String encoding, String description, boolean download_bulma) {
		super(directory, name, title, encoding, description, null, null);
		
		if(download_bulma) {
			downloadResources(true);
		}
		
		buildProject();
	}

	public void buildProject() {
		System.out.println("Construction of the important files");
		
		src_directory = new File(this.directory, "src");
		src_directory.mkdir();
		
		css_directory = new File(this.src_directory, "css");
		css_directory.mkdir();
		
		imgs_directory = new File(this.src_directory, "imgs");
		imgs_directory.mkdir();
		
		css_file = new File(this.css_directory, "main.css");
		try {
			css_file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		javascript_directory = new File(this.src_directory, "javascript");
		javascript_directory.mkdir();
		
		js_file = new File(this.javascript_directory, "main.js");
		try {
			js_file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(File files : src_directory.listFiles()) {
			System.out.println("files: "+files.getPath());
		}

		System.out.println("Implemenatation of bulma in the code html");
		this.html.head.addChildren(new meta("viewport", "\"width=device-width, initial-scale=1\""));
		this.html.head.addChildren(new link("src/bulma-"+JBulma.BULMA_VERSION+"/css/bulma.min.css", "stylesheet"));
		this.html.head.addChildren(new script("https://ajax.googleapis.com/ajax/libs/jquery/"+jquery_version+"/jquery.min.js", null, null));

		System.out.println("Implementation of the code css master and js master");
		setMainCSS(new CSS(css_file, "src/css/"+css_file.getName()));
		setMainJS(new JS(this.js_file, "/src/js/"+js_file.getName()));
		
		System.out.println("finally, Construction of the ended project");
	}

	public void downloadResources(boolean bulma_source) {
		if (bulma_source) {
			String file_name = "bulma-"+JBulma.BULMA_VERSION+".zip";
			String url = JBulma.BULMA_RESOURCES+file_name;
			
			System.out.println("Bulma's download is in progress ..");
			File file = Toolkit.downloadFile(url, this.directory);
			if(file == null) {
				System.err.println("bulma download failed");
				return;
			}
			System.out.println("Bulma's download is complete");

			System.out.println("Preparation of the decompression of the file ("+file.getPath()+").");

			try {
				MyZip.unzip(file, new File(file.getParentFile(), "src"));
			} catch (FileNotFoundException e) {
				System.err.println("The file has to unwind do not exist");
				e.printStackTrace();
				return;
			} catch (IOException e) {
				System.err.println("A problem arose during the decompression");
				e.printStackTrace();
				return;
			}
			System.out.println("The decompression of the file is ended");
			
			file.delete();
		}
	}
}
