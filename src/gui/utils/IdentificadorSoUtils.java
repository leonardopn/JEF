package gui.utils;

public class IdentificadorSoUtils {
	
	private static String SO = System.getProperty("os.name").toLowerCase();
	
	public static String sistema() {
		if (isWindows()) {
            return "win";
        }
        if(isUnix()){
            return "linux";
        }
		return SO;
	}
	
    //Fun��o que verifica se � windows
    public static boolean isWindows() {
        return (SO.indexOf("win") >= 0);
    }
  
    //Fun��o que verifica se � Unix-Like
    public static boolean isUnix() {
        return (SO.indexOf("nix") >= 0 || SO.indexOf("nux") >= 0 || SO.indexOf("aix") > 0 );
    }
 }
