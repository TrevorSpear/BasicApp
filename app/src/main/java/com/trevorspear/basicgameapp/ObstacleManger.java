package com.trevorspear.basicgameapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.ArrayList;

/**
 * Created by TrevorSpear on 4/3/17.
 */

public class ObstacleManger {
    //Higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;      //Gap between the 2 lines
    private int obstacleGap;    //Gap between the start of the next obstacle
    private int obstacleHeight; //Width of obstacle
    private int color;          //color of the obstacle

    private long startTime;
    private long initTime;

    private int score = 0;

    //Calls this upon startup
    public ObstacleManger(int playerGap, int obstacleGap, int obstacleHeight, int color){
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    //If the player collides with the line then it returns true
    public boolean playerCollide(RectPlayer player){
        for(Obstacle ob : obstacles){
            if(ob.playerCollide(player)){
                return true;
            }
        }

        return false;
    }

    //Makes obstacles
    private void populateObstacles(){
        int currY = -5*Constants.SCREEN_Height/4;

        while(currY < 0){
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1 + (startTime - initTime)/(3000.0))*Constants.SCREEN_Height/(10000.0f));


        for(Obstacle ob : obstacles){
          ob.incrementY(speed * elapsedTime);
        }

        if(obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_Height){
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
            score++;
        }
    }

    //Draws the obstacles then the score
    public void draw(Canvas canvas){

        //Draws obstacle here
        for(Obstacle ob : obstacles){
            ob.draw(canvas);
        }

        //Sets up the score text
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.GREEN);

        //draws the score to the screen
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }
}
