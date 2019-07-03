package model.services;

public class IdentificadorSO {
	
	public static String sistema() {
		if (isWindows()) {
            return "win";
        }
        else {
            return "linux";
        }
	}
	private static String SO = System.getProperty("os.name").toLowerCase();
  
    //Função que verifica se é windows
    public static boolean isWindows() {
        return (SO.indexOf("win") >= 0);
    }
  
    //Função que verifica se é Unix-Like
    public static boolean isUnix() {
        return (SO.indexOf("nix") >= 0 || SO.indexOf("nux") >= 0 || SO.indexOf("aix") > 0 );
    }
 }
