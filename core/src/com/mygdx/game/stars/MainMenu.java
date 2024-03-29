package com.mygdx.game.stars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.Serializable;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MainMenu implements Screen, Serializable {

    private final tankstars game;
    private final Texture backgroundImage,logo;
    private final TextureRegion backgroundTexture;
    private final TextureRegion settings;
    private TextureRegion play1,exit,resume;
    private OrthographicCamera camera;
    private ImageButton button3,buttonPlay,buttonExit,buttonResume;
    private Stage stage;
    private  FitViewport viewp;
    private Sound sound;
    private Music music;
    private window window1;
    public MainMenu(final tankstars game) {
        camera=new OrthographicCamera();
        camera.setToOrtho(false, 800, 580);
        this.game = game;
        this.stage = new Stage(new FillViewport(800,580,camera));
        Gdx.input.setInputProcessor(stage);
        //stage.clear();
        backgroundImage = new Texture(Gdx.files.internal("background5.jpg"));
        backgroundTexture = new TextureRegion(backgroundImage, 0, 0, 1024, 500);
        settings=new TextureRegion(new Texture(Gdx.files.internal("settings.png")),80,80);
        play1=new TextureRegion(new Texture(Gdx.files.internal("play1_up.png")));
        logo= new Texture(Gdx.files.internal("logo.png"));
        music=Gdx.audio.newMusic(Gdx.files.internal("mainmenu.mp3"));
        music.setLooping(true);
        music.play();
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Drawable drawable2 = new TextureRegionDrawable(play1);
        buttonPlay = new ImageButton(drawable2);
        buttonPlay.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("play1_down.png"))));
        buttonPlay.setScale(0.6f);
        buttonPlay.setPosition(270,250);
        buttonPlay.setTransform(true);
        buttonPlay.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new gamemenu(game));
            }
        });
        exit=new TextureRegion(new Texture(Gdx.files.internal("exit_up.png")));
        Drawable drawable3 = new TextureRegionDrawable(exit);
        buttonExit = new ImageButton(drawable3);
        buttonExit.setScale(0.6f);
        buttonExit.setPosition(270,110);
        buttonExit.setTransform(true);
        buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dispose();
                System.exit(0);

            }
        });
        resume=new TextureRegion(new Texture(Gdx.files.internal("resume_up.png")));
        Drawable drawable4 = new TextureRegionDrawable(resume);
        buttonResume = new ImageButton(drawable4);
        buttonResume.setScale(0.6f);
        buttonResume.setPosition(270,180);
        buttonResume.setTransform(true);
        buttonResume.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resume_down.png"))));
        buttonResume.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new loadgame(game));
//                game.setScreen(new GameScreen(game));

            }
        });
        window1 = new window(game);
        window1.setPosition(140,120);

        Drawable drawable = new TextureRegionDrawable(settings);
        button3 = new ImageButton(drawable);
        button3.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("settings.png")),75,75));
        button3.setSize(40, 40);
        button3.setTransform(true);
        button3.setPosition(20,550);
        button3.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        button3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window1.setVisible(true);
            }
        });

        stage.addActor(buttonPlay);
        stage.addActor(buttonExit);
        stage.addActor(button3);
        stage.addActor(buttonResume);
        stage.addActor(window1);
        stage.setDebugAll(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, 800, 580);
        game.batch.draw(logo, 200, 300, 360, 360);
        game.batch.end();
        stage.act();
        stage.draw();
        }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().setScreenSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    @Override
    public void hide() {

    }
    @Override
    public void dispose() {
        music.stop();
        stage.dispose();
    }
}
