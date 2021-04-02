package pacman;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Hier kann der Sound der Niederlage oder des Gewinns abgespielt werden.
 * 
 * @author Janne
 *
 */
public class Sound {

	private final static String SOUND_ROOT = "res/sounds/";
	private static boolean notactiv = true;
	
	public static void playLost() {
		if (notactiv) {
			playSound(SOUND_ROOT + "lost.wav");
			notactiv = false;
		}
	}
	
	public static void playWin() {
		if (notactiv) {
			playSound(SOUND_ROOT + "win.wav");
			notactiv = false;
		}
	}
	
	private static void playSound(String soundPath) {
		new Thread(new Runnable() {
		@Override
		public void run() {
			while (true) {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputstream = AudioSystem.getAudioInputStream(new File(soundPath));
					clip.open(inputstream);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					Thread.sleep(clip.getMicrosecondLength() / 1000);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Die Audiodatei wurde nicht gefunden.");
				}
			}
		}
	}).start();
	}
}
