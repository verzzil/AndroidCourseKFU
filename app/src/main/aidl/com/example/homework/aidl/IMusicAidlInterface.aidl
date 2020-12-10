// IMusicAidlInterface.aidl
package com.example.homework.aidl;


interface IMusicAidlInterface {

    void play();
    void playNewAuthor(int authorId, int musicId);
    void skipToNext();
    void skipToPrev();
    void seekTo(int postition);
    int getDuration();
    int getCurrentPostition();
    void prepareToPlay(int authorId, int musicId);

}
