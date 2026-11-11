package e5;

public class EnemyExample {
    public static void main(String[] args) {
        // CREATORS
        EnemyCreator[] creators = {
            new SoldierCreator(),
            new DroneCreator()
        };

        // TEST (With for each, to test inheritance)
        for (EnemyCreator creator : creators) {
            creator.createEnemy().showStats();
        }
    }
}


// Product

abstract class Enemy {
    protected String name;
    protected int health;
    protected int damage;
    protected int speed;
    protected String tactic;

    public void showStats() {
        System.out.println(name + " | HP: " + health + " | DMG: " + damage
            + " | SPD: " + speed + " | Tactic: " + tactic);
    }
}

class Soldier extends Enemy {
    Soldier() {
        name = "Soldier";
        health = 100;
        damage = 10;
        speed = 5;
        tactic = "Take cover and shoot";
    }
}

class Drone extends Enemy {
    Drone() {
        name = "Combat Drone";
        health = 60;
        damage = 15;
        speed = 12;
        tactic = "Fly around the player";
    }
}

// Creators

abstract class EnemyCreator {
    public abstract Enemy createEnemy();
}

class SoldierCreator extends EnemyCreator {
    @Override
    public Enemy createEnemy() {
        return new Soldier();
    }
}

class DroneCreator extends EnemyCreator {
    @Override
    public Enemy createEnemy() {
        return new Drone();
    }
}
