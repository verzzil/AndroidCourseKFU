// IMusicAidlInterface.aidl
package com.example.homework.aidl;


interface IMusicAidlInterface {

    void play();
    void playNewAuthor(int authorId, int musicId);
    void pause();
    void skipToNext();
    void skipToPrev();
    void seekTo(int postition);
    boolean isPlaying();
    int getDuration();
    int getCurrentPostition();
    void prepareToPlay(int authorId, int musicId);

}
