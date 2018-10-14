package doryan_bessiere.jbulma.fr.tools.logger;

import java.io.PrintStream;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

public class Logger {

	protected String prefix;
	protected LoggerFile loggerFile;

	public boolean debug = false;

	public Logger(String prefix, LoggerFile loggerFile) {
		this.prefix = prefix;
		this.loggerFile = loggerFile;
		init();
	}

	public void init() {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				throwable("exception in thread '" + t.getName() + "' " + e.getClass().getName(), e);
			}
		});

		PrintStream newOut = new PrintStream(System.out) {
			@Override
			public void println(String msg) {
				String message = null;
				if (msg.startsWith("i:")) {
					message = "[INFO] " + msg.substring(2);
				} else if (msg.startsWith("w:")) {
					message = "[WARN] " + msg.substring(2);
				} else if (msg.startsWith("d:")) {
					message = "[DEBUG] " + msg.substring(2);
				} else {
					message = "[INFO] " + msg;
				}

				message = "[" + prefix + "] [" + time() + "] " + message;

				loggerFile.message(message);
				super.println("_:"+message);
			}
			
			@Override
			public void print(String msg) {
				if(!msg.startsWith("_:")) {
					String message = null;
					if (msg.startsWith("i:")) {
						message = "[INFO] " + msg.substring(2);
					} else if (msg.startsWith("w:")) {
						message = "[WARN] " + msg.substring(2);
					} else if (msg.startsWith("d:")) {
						message = "[DEBUG] " + msg.substring(2);
					} else {
						message = "[INFO] " + msg;
					}

					message = "[" + prefix + "] [" + time() + "] " + message;	
					loggerFile.message(message);
					super.print(message);		
				}
				
				loggerFile.message(msg.substring(2));
				super.print(msg.substring(2));
			}
		};
		System.setOut(newOut);

		PrintStream newErr = new PrintStream(System.err) {
			@Override
			public void println(String msg) {
				String message = "[ERROR] " + msg;

				if (msg.startsWith("e:")) {
					message = "[ERROR] " + msg.substring(2);
				}

				message = "[" + prefix + "] [" + time() + "] " + message;

				loggerFile.message(message);
				super.println("_:"+message);
			}
			
			@Override
			public void print(String msg) {
				if(!msg.startsWith("_:")) {
					String message = "[ERROR] " + msg;

					if (msg.startsWith("e:")) {
						message = "[ERROR] " + msg.substring(2);
					}

					message = "[" + prefix + "] [" + time() + "] " + message;

					loggerFile.message(message);
					super.print(message);		
				} else {
					loggerFile.message(msg.substring(2));
					super.print(msg.substring(2));	
				}
			}
		};
		System.setErr(newErr);
	}

	public void exception(Exception exception) {
		for (Throwable throwable : exception.getSuppressed()) {
			throwable(exception.getLocalizedMessage(), throwable);
		}
	}
	
	public void throwable(String localizedMessage, Throwable throwable) {
		error(localizedMessage);
		for (StackTraceElement element : throwable.getStackTrace()) {
			error("    at " + element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName() + ":"
					+ element.getLineNumber() + ")");
		}

		Throwable ourCause = throwable.getCause();
		if (ourCause != null)
			throwable("Caused by: " + ourCause.getClass().getName(), ourCause);
	}

	public String time() {
		return new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
	}

	public void info(String message) {
		System.out.println("i:" + message);
	}
	
	public void info_(String message) {
		System.out.print("i:"+message);
	}

	public void error(String message) {
		System.err.println("e:" + message);
	}

	public void warn(String message) {
		System.out.println("w:" + message);
	}

	public void debug(String message) {
		if (!debug)
			return;
		System.out.println("d:" + message);
	}

	public void prefix(String prefix) {
		this.prefix = prefix;
	}

	public void close() {
		this.loggerFile.close();
	}
}
