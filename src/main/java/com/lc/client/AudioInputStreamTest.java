package com.lc.client;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @author ifly_lc
 */
public class AudioInputStreamTest {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        AePlayWave aePlayWave=new AePlayWave("E:\\source.wav");
        Thread t=new Thread(aePlayWave);
        t.start();
    }
}
class AePlayWave extends Thread
{
    String filename;
    AudioInputStream audioInputStream=null;
    File SoundFile;
    SourceDataLine auline=null;
    public AePlayWave(String filename)
    {
        this.filename=filename;
    }
    public void run()
    {
        SoundFile=new File(filename);
        try {
            audioInputStream=AudioSystem.getAudioInputStream(SoundFile);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        AudioFormat format= audioInputStream.getFormat();
        DataLine.Info info=new DataLine.Info(SourceDataLine.class,format);

        try {
            auline=(SourceDataLine)AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        auline.start();
        int nByteRead=0;
        byte[] abData=new byte[512];
        try {
            while(nByteRead!=-1)
            {
                nByteRead=audioInputStream.read(abData,0,abData.length);
                if(nByteRead>=0)
                {
                    auline.write(abData, 0, nByteRead);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            auline.drain();
            auline.close();
        }

    }
}