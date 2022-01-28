package designpatterns.execution;

/**
 * The command pattern is used to reify function calls.
 *
 * <p>
 * The goal is to decouple the command execution logic from the parameter instances the command is acting upon.
 * In this way, we can execute commands on different object without worrying about the objects themselves. </p>
 *
 * <p>
 * E.g., we are developing a game for a console, and buttons A, B, X, and Y map to different commands a player can
 * execute, such as move forwards or backwards, jump or crouch. </p>
 *
 * <ul>
 * <li>Command execution must be decoupled from the player (target): it should work for different players, or even
 * different objects, like cars and trucks, as applicable. </li>
 * <li>Command execution must be decoupled from the button (invoker): we should be able to remap buttons so that
 * any button can map to any command. </li>
 * </ul>
 */

// The command could be even more general: e.g., execute(MovableObject object).
interface Command {
    void execute(Player player);
}

// Concrete command implementation: makes a given player move forward. The details of execution are left to the
// target.
class MoveForward implements Command {
    @Override
    public void execute(final Player player) {
        player.moveForwards();
    }
}

class MoveBackward implements Command {
    @Override
    public void execute(final Player player) {
        player.moveBackwards();
    }
}

class Jump implements Command {
    @Override
    public void execute(final Player player) {
        player.jump();
    }
}

class Crouch implements Command {
    @Override
    public void execute(final Player player) {
        player.crouch();
    }
}

// A target object, such as player, truck, car, etc. which is acted upon by the commands.
interface Player {
    void moveForwards();

    void moveBackwards();

    void jump();

    void crouch();
}

// One implementation of a target. We could implements Player with different notions of what it means to execute
// the command. E.g., a BackwardsOnlyPlayer might throw an Exception if asked to moveForwards().
class DefaultPlayer implements Player {
    private static final int HORIZONTAL_MIN = -10;
    private static final int HORIZONTAL_MAX = 10;
    private static final int VERTICAL_MIN = -10;
    private static final int VERTICAL_MAX = 10;

    private int[] location = {HORIZONTAL_MIN, VERTICAL_MIN};

    @Override
    public void moveForwards() {
        int currentX = location[0];

        if (currentX >= HORIZONTAL_MAX) {
            System.out.print("Cannot move forward!\t");
            printLocation();
        } else {
            System.out.print("Moved forward!\t");
            location[0] = currentX + 1;
            printLocation();
        }
    }

    @Override
    public void moveBackwards() {
        int currentX = location[0];

        if (currentX <= HORIZONTAL_MIN) {
            System.out.print("Cannot move backward!\t");
            printLocation();
        } else {
            System.out.print("Moved backward!\t");
            location[0] = currentX - 1;
            printLocation();
        }
    }

    @Override
    public void jump() {
        int currentY = location[1];

        if (currentY >= VERTICAL_MAX) {
            System.out.print("Cannot jump!\t");
            printLocation();
        } else {
            System.out.print("Jumped!\t");
            location[1] = currentY + 1;
            printLocation();
        }
    }

    @Override
    public void crouch() {
        int currentY = location[1];

        if (currentY <= VERTICAL_MIN) {
            System.out.print("Cannot crouch!\t");
            printLocation();
        } else {
            System.out.print("Crouched!\t");
            location[1] = currentY - 1;
            printLocation();
        }
    }

    private void printLocation() {
        System.out.println("CurrentLocation is : [ " + location[0] + "," + location[1] + " ].");
    }
}

enum Button {
    A, B, X, Y
}


// Where the invoker is mapped to the command. Depending the commands that are mapped to buttonACommand etc.,
// the player can move forwards, backwards, etc. when that button is pressed.
class InputHandler {
    private final Command buttonACommand;
    private final Command buttonBCommand;
    private final Command buttonXCommand;
    private final Command buttonYCommand;

    public InputHandler(
        final Command buttonACommand,
        final Command buttonBCommand,
        final Command buttonXCommand,
        final Command buttonYCommand) {
        this.buttonACommand = buttonACommand;
        this.buttonBCommand = buttonBCommand;
        this.buttonXCommand = buttonXCommand;
        this.buttonYCommand = buttonYCommand;
    }

    public void handle(Button button, Player player) {
        switch (button) {
            case A:
                buttonACommand.execute(player);
                break;
            case B:
                buttonBCommand.execute(player);
                break;
            case X:
                buttonXCommand.execute(player);
                break;
            case Y:
                buttonYCommand.execute(player);
                break;
            default:
                throw new IllegalArgumentException("Unknown button pressed");

        }
    }
}

