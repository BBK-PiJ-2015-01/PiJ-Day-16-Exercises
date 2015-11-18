import java.nio.file.*;
import java.io.*;
import java.net.*;

public class Exercise9 {

	private final String READLINE_PROMPT = "%s > ";
	private final Path path = Paths.get(System.getProperty("user.dir"));
	
	public static void main(String[] args){
		
		new Exercise9().exercise9Runner();
	}
	
	
	private void exercise9Runner() {
		
		final int EXPECTED_FILES = 2;
		String fileNames = System.console().readLine(READLINE_PROMPT, "Copy");
		String[] files = fileNames.split("\\s+");
		if (files.length != EXPECTED_FILES) {
			System.out.println("Expected " + EXPECTED_FILES + " files, found " +  files.length);
			return;
		}
		if (!fileExists(files[0])) {
			System.out.println("Cannot copy, '" + files[0] + "' does not exist.");
			return;
		}
		try {
			copyBinaryFileWithOverrideCheck(files[0], files[1]);
		} catch (IOException e) {
			System.out.println("Unable to copy " + files[0] + " to " + files[1]);
			System.out.print(e.getMessage());
		}

	}

	private boolean fileExists(String fileName) {
		
		Path resolvedPath = path.resolve(Paths.get(fileName));
		return resolvedPath == null ? false : resolvedPath.toFile().exists();
	}
	
	private void copyBinaryFileWithOverrideCheck(String fromFile, String toFile) throws IOException{
		
		System.out.println("Copy "+ fromFile + " to " + toFile);
		final String NO_COPY = "N";
		File destinationFile = new File(toFile);
		if  (destinationFile.exists()) {
			String action = System.console().readLine(READLINE_PROMPT, "File: " + destinationFile.getName() + " exists. Overwrite ? (Y|N)");
			action = action == null ? NO_COPY : action;
			if (action.toUpperCase().startsWith(NO_COPY)) {
				return;
			}
		}
		copyBinaryFileWithOverride(fromFile, toFile );
	}
	
	private void copyBinaryFileWithOverride(String fromFile, String toFile) throws IOException  {
		
		final int BUFFER_SIZE = 1024;
		final int EOF = -1;
		final int EMPTY_BUFFER = -1;
		byte[] buffer = new byte[BUFFER_SIZE];
		File sourceFile = new File(fromFile);
		URL sourceURL = sourceFile.toURI().toURL();
		
		try (InputStream is = sourceURL.openStream(); OutputStream os = new FileOutputStream(toFile, false)) 
		{
			while(true) {
			
				int result = is.read(buffer);
				if (result == EOF || result == EMPTY_BUFFER) {
					break;
				}
				os.write(buffer, 0, result);
			}
        } catch (IOException e){
			e.printStackTrace();
		}		
	}
	
}
