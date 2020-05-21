package io.piotrjastrzebski.gwtfreetypetest;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class GwtFreetypeGame extends ApplicationAdapter {
	ScreenViewport viewport;
	SpriteBatch batch;
	Stage stage;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		VisUI.load();
		viewport = new ScreenViewport();
		batch = new SpriteBatch();
		stage = new Stage(viewport, batch);

		Table table = new Table();
		table.defaults().pad(8);
		table.add(new VisLabel("Generate fonts")).row();
		{
			VisTextButton button = new VisTextButton("Without shadow");
			final VisLabel label = new VisLabel("Dummy text!");
			label.setStyle(new Label.LabelStyle(label.getStyle()));
			label.setVisible(false);
			button.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeEvent event, Actor actor) {
					loadFont(label, 0, 0);
				}
			});
			table.add(button).uniformX().fillX().row();
			table.add(label).row();
		}
		{
			VisTextButton button = new VisTextButton("With shadow");
			final VisLabel label = new VisLabel("Dummy text!");
			label.setVisible(false);
			label.setStyle(new Label.LabelStyle(label.getStyle()));
			button.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeEvent event, Actor actor) {
					loadFont(label, 2, 2);
				}
			});
			table.add(button).uniformX().fillX().row();
			table.add(label).row();
		}
		table.setFillParent(true);
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
	}

	void loadFont (VisLabel label, int shadowX, int shadowY) {
		label.setVisible(false);
		FileHandle ttf = Gdx.files.internal("Roboto-Bold.ttf");
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 20;
		parameter.shadowColor = Color.BLACK;
		parameter.shadowOffsetX = shadowX;
		parameter.shadowOffsetY = shadowY;

		try {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(ttf);
			Label.LabelStyle style = label.getStyle();
			if (style.font.ownsTexture()) {
				style.font.dispose();
				Gdx.app.log("App", "Disposed old font...");
			}
			style.font = generator.generateFont(parameter);
			label.setStyle(style);
			label.setVisible(true);
			generator.dispose();
		} catch (Exception ex) {
			Gdx.app.error("App", "Generation failed for font with shadow " + shadowX + "," + shadowY, ex);
		}
		Gdx.app.log("App", "Font generated with with shadow " + shadowX + "," + shadowY);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.3f, .3f, .3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize (int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void dispose () {
		batch.dispose();
		VisUI.dispose();
	}
}
