package designpatterns.execution

import spock.lang.Specification

class CommandTest extends Specification {
    def "Command Pattern works as expected"() {
        when:
        final Player player = new DefaultPlayer()

        final Command moveForward = new MoveForward()
        final Command moveBackward = new MoveBackward()
        final Command jump = new Jump()
        final Command crouch = new Crouch()

        final InputHandler handler = new InputHandler(
            moveForward, // maps to A
            moveBackward, // maps to B
            jump, // maps to X
            crouch // maps to Y.
        )

        then:
        handler.handle(Button.B, player)
        handler.handle(Button.A, player)
        handler.handle(Button.B, player)
        handler.handle(Button.Y, player)
        handler.handle(Button.X, player)
        handler.handle(Button.Y, player)
    }
}
