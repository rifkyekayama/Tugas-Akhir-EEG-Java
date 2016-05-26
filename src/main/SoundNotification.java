package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundNotification {
	
	public static synchronized void playNotif() {
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							Main.class.getResource("/resource/notif.wav"));
					clip.open(inputStream);
					clip.start(); 
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
			}
		}).start();
	}
	
	public static synchronized void playError() {
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							Main.class.getResource("/resource/error.wav"));
					clip.open(inputStream);
					clip.start(); 
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
			}
		}).start();
	}
	
	public static synchronized void playClick() {
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							Main.class.getResource("/resource/click.wav"));
					clip.open(inputStream);
					clip.start(); 
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
			}
		}).start();
	}
}
