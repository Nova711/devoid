package devoid_boosted;

// Java program to play an Audio 
// file using Clip Object 
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import rpg.Out;

public class AudioPlayer {
	
	static File[] music;

	// to store current position
	Long currentFrame;
	Clip clip;

	// current status of clip
	String status;

	int theme;

	AudioInputStream audioInputStream;

	String filePath;
	
	static {
		music = (new File("D:\\Projects\\Java\\src\\rift\\audio")).listFiles();
	}

	// constructor to initialize streams and clip
	public AudioPlayer(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		// create AudioInputStream object
		this.filePath = filePath;
		audioInputStream = AudioSystem.getAudioInputStream(new File(this.filePath).getAbsoluteFile());

		// create clip reference
		clip = AudioSystem.getClip();

		// open audioInputStream to the clip
		clip.open(audioInputStream);

		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public AudioPlayer(int theme) {
		this.theme = theme;
	}

	public static void main(String[] args) {
		try {
			String filePath = "D:\\Projects\\Java\\src\\rift\\audio\\ThoughtBot.wav";
			AudioPlayer audioPlayer = new AudioPlayer(filePath);

			audioPlayer.play();
			while (true) {

			}
		}

		catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();

		}
	}

	// Method to play the audio
	public void play() {
		// start the clip
		clip.start();

		status = "play";
	}

	// Method to pause the audio
	public void pause() {
		if (status.equals("paused")) {
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}

	// Method to resume the audio
	public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (status.equals("play")) {
			System.out.println("Audio is already " + "being played");
			return;
		}
		clip.close();
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}

	// Method to restart the audio
	public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}

	// Method to stop the audio
	public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}

	// Method to jump over a specific part
	public void jump(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (c > 0 && c < clip.getMicrosecondLength()) {
			clip.stop();
			clip.close();
			resetAudioStream();
			currentFrame = c;
			clip.setMicrosecondPosition(c);
			this.play();
		}
	}

	// Method to reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioInputStream = AudioSystem.getAudioInputStream(new File(this.filePath).getAbsoluteFile());
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}
