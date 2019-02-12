package app;

import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;
import java.util.Random;

public class MusicPlayer {
    private Media myMedia;
    private MediaPlayer myMediaPlayer;
    private String[] MusicList = {"nfl-music.wav", "superbowl-music.wav", "welcome_to_the_jungle.wav"};


    public MusicPlayer(){

    }

    public void play(){
        Random rand = new Random();
        int i = rand.nextInt(3);
        String musicName = MusicList[i];

        String path = null;
        try {
            path = Game.class.getClassLoader().getResource(musicName).toURI().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        myMedia = new Media(path);
        myMediaPlayer = new MediaPlayer(myMedia);
        myMediaPlayer.setAutoPlay(true);


    }

    public void check(){
        if(!this.isPlaying()){
            this.play();
        }
    }

    private boolean isPlaying(){
        if(this.myMediaPlayer!=null){
            return this.myMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
        }else{
            return false;
        }
    }





}
