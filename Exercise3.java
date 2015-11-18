import java.nio.file.*;
import java.io.*;

public class Exercise3 {

	private final String READLINE_PROMPT = ">";
	private final Path path = Paths.get(System.getProperty("user.dir"));
	
	public static void main(String[] args){
		
		new Exercise3().exercise3Runner();
	}
	
	
	private void exercise3Runner() {
		
		String fileNames = System.console().readLine(READLINE_PROMPT);
		String[] files = fileNames.split("\\s+");
		for(String file : files) {
			if (fileExists(file)) {
				printFileContents(new File(file));
			} else {
				System.out.println(file + " is NOT a file");
			}
		}

	}
	
	private void exercise3bRunner() {
	}

	private boolean fileExists(String fileName) {
		
		Path resolvedPath = path.resolve(Paths.get(fileName));
		return resolvedPath == null ? false : resolvedPath.toFile().exists();
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
