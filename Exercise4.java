import java.nio.file.*;
import java.io.*;

public class Exercise4 {

	private final String READLINE_PROMPT = "%s > ";
	private final Path path = Paths.get(System.getProperty("user.dir"));
	
	public static void main(String[] args){
		
//		new Exercise4().exercise4Runner();
		new Exercise4().exercise4bRunner();
	}
	
	
	private void exercise4Runner() {
		
		final int EXPECTED_FILES = 2;
		boolean performCopy = false;
		String fileNames = System.console().readLine(READLINE_PROMPT, "Copy");
		String[] files = fileNames.split("\\s+");
		if (files.length != EXPECTED_FILES) {
			System.out.println("Expected " + EXPECTED_FILES + " files, found " +  files.length);
		} else {
			if (!fileExists(files[0])) {
				System.out.println("Cannot file source file: " + files[0]);
			} else {
				if (fileExists(files[1])) {
					// Prompt for action
					String action = System.console().readLine(READLINE_PROMPT, "File: " + files[1] + " exists. Overwrite ? (Y|N)");
					action = action == null ? "N" : action;
					performCopy = action.toUpperCase().startsWith("Y");
				} else {
					performCopy = true;
				}
			}
		}
		if (performCopy) {
			try {
				copyFile(files[0], files[1]);
			} catch (IOException e) {
				System.out.println("Unable to copy"  + files[0] + " to " + files[1]);
				System.out.println(e.getMessage());
			}
			//System.out.println("Copied " + files[0] + " to " + files[1]);
		} else {
			System.out.println("No files copied");
		}
	}

	private void exercise4bRunner() {
		
		final int MIN_EXPECTED_FILES = 2;
		boolean performCopy = false;
		String fileNames = System.console().readLine(READLINE_PROMPT, "Copy");
		String[] files = fileNames.split("\\s+");
		if (files.length < MIN_EXPECTED_FILES) {
			System.out.println("Expected a minimum of " + MIN_EXPECTED_FILES + " files, found " +  files.length);
			return;
		}
		for (String file: files) {
			if (!fileExists(file)) {
				System.out.println("Cannot copy, '" + file + "' does not exist.");
				return;
			}
		}
		// Final file must be a directory
		File destinationDirectory = new File(files[files.length-1]);
		if (!destinationDirectory.isDirectory()) {
			System.out.println("Destination '" + files[files.length-1] + "' must be a directory.");
			return;
		}	
		Path destinationPath = Paths.get(destinationDirectory.getName());
		for (int i = 0; i < files.length-1; i++) {
			Path destinationFilePath = destinationPath.resolve(files[i]);
			try {
				copyFileWithOverrideCheck(files[i], destinationFilePath.toString());
			} catch (IOException e) {
				System.out.println("Unable to copy '"  + files[i] + "' to '" + destinationFilePath + " '");
				System.out.println(e.getMessage());
			}			
		}

	}

	private boolean fileExists(String fileName) {
		
		Path resolvedPath = path.resolve(Paths.get(fileName));
		return resolvedPath == null ? false : resolvedPath.toFile().exists();
	}
	
	private void copyFileWithOverrideCheck(String fromFile, String toFile) throws IOException{
		
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
		Files.copy(Paths.get(fromFile), Paths.get(toFile), StandardCopyOption.REPLACE_EXISTING );
	}
	
	private void copyFile(String fromFile, String toFile) throws IOException{
		
		Files.copy(Paths.get(fromFile), Paths.get(toFile), StandardCopyOption.REPLACE_EXISTING );
	}
	
	private void printFileContents(File file){
		
		System.out.println((file != null ? file.getName() : "No file found") + ":\n");
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(file))) 
		{
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
        } catch (IOException e){
			e.printStackTrace();
		}
    }
}
