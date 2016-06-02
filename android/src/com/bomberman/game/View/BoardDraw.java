package com.bomberman.game.View;

/**
 * Created by Patryk on 15.05.2016.
 */
public class BoardDraw {
//    public static int CAMERA_WIDTH = 80;
//    public static int CAMERA_HEIGHT = 50;
//
//    private Map board;
//    public OrthographicCamera cam;
//    ShapeRenderer renderer = new ShapeRenderer();
//
//    public int width;
//    public int height;
//    public float ppuX;
//    public float ppuY;
//
//    public void setSize (int w, int h) {
//        this.width = w;
//        this.height = h;
//        ppuX = (float)width / CAMERA_WIDTH;
//        ppuY = (float)height / CAMERA_HEIGHT;
//    }
//
//    public void SetCamera(float x, float y){
//        this.cam.position.set(x, y,0);
//        this.cam.update();
//    }
//
//    public BoardDraw(Map board) {
//        this.board = board;
//        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
//        SetCamera(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2);
//    }
//
//    public void render() {
//        drawBricks();
//        drawPlayer();
//
//    }
//    private void drawBricks() {
//        renderer.setPro
// jectionMatrix(cam.combined);
//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        for (Wall wall : board.getWalls()) {
//            Rectangle rect =  wall.getBounds();
//            float x1 =  wall.getPosition().x + rect.x;
//            float y1 =  wall.getPosition().y + rect.y;
//            renderer.setColor(new Color(0, 0, 0, 1));
//            renderer.rect(x1, y1, rect.width, rect.height);
//        }
//        renderer.end();
//    }
//    private void drawPlayer() {
//        renderer.setProjectionMatrix(cam.combined);
//        Player player = board.getPlayer();
//        renderer.begin(ShapeRenderer.ShapeType.Line);
//
//        Rectangle rect = player.getBounds();
//        float x1 = player.getPosition().x + rect.x;
//        float y1 = player.getPosition().y + rect.y;
//        renderer.setColor(new Color(1, 0, 0, 1));
//        renderer.rect(x1, y1, rect.width, rect.height);
//        renderer.end();
//    }
}
