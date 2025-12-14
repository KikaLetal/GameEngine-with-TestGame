package purpurafingunt;

import Engine.Components.BoxCollider;
import Engine.Components.RigidBody;
import Engine.Components.Sprite;
import Engine.Components.Transform;
import Engine.Core.Game;
import Engine.Math.Vector2f;
import Engine.esc.Entity;
import Engine.esc.EntityManager;
import Engine.esc.Scene;
import Engine.esc.builders.SceneBuilder;

public class Main {
    public static void main(String[] args){
        Game game = new Game("Purpure plague", true);
        Scene FirstScene = SceneBuilder.createScene();
        EntityManager FirstSceneEntityManager = FirstScene.getEntityManager();

        Entity player = FirstScene.addEntity("player");
        FirstSceneEntityManager.addComponent(player.getId(), new Sprite("player.png"));
        FirstSceneEntityManager.addComponent(player.getId(), new Transform(new Vector2f(30, 30), player.getComponent(Sprite.class).getSize()));
        FirstSceneEntityManager.addComponent(player.getId(), new RigidBody(1, 3));
        FirstSceneEntityManager.addComponent(player.getId(), new BoxCollider(player.getComponent(Transform.class).getPosition(), player.getComponent(Transform.class).getSize()));
        FirstSceneEntityManager.addComponent(player.getId(), new PlayerControler());

        Entity enemy = FirstScene.addEntity("bridge");
        FirstSceneEntityManager.addComponent(enemy.getId(), new Transform(new Vector2f(20, 170), new Vector2f(70, 80)));
        FirstSceneEntityManager.addComponent(enemy.getId(), new Sprite((int) enemy.getComponent(Transform.class).getSize().x, (int) enemy.getComponent(Transform.class).getSize().y));
        FirstSceneEntityManager.addComponent(enemy.getId(), new RigidBody(true));
        FirstSceneEntityManager.addComponent(enemy.getId(), new BoxCollider(enemy.getComponent(Transform.class).getPosition(), enemy.getComponent(Transform.class).getSize()));


        game.AddScene(FirstScene);
        game.Start();
    }
}
