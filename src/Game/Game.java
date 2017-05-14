package Game;

import java.io.FileInputStream;
import java.io.InputStream;

import Controller.Controller;
import Model.Model;
import View.View;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Game 
{
	AdvancedPlayer ap;
	private void myPlay()
	{
		try
		{
			InputStream is=new FileInputStream("D:\\Documents\\Downloads\\NWA - Fuck The Police.mp3");
			AudioDevice device=new JavaSoundAudioDevice();
			ap=new AdvancedPlayer(is,device);
			ap.play();
			ap.stop();
			ap.close();
		}
		catch(Exception e){}
 
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				Game game = new Game();
				game.starting();
            }
		});
	}
		
		public void starting()
		{
			Model model = new Model();
			Controller controller = new Controller(model);			
			View view = new View(model, controller);			
			model.addObserver(view);
		}
}