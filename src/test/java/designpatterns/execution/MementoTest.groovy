package designpatterns.execution

import spock.lang.Specification

class MementoTest extends Specification{
    def "Memento Pattern must work as expected"() {
        when:
        def player1 = new Memento.DefaultPlayer()
        List<Memento.Player.SavedScore> savedGames = new ArrayList<>()

        then:
        player1.play()
        savedGames.add(player1.saveScore())
        player1.play()
        savedGames.add(player1.saveScore())
        player1.play()
        player1.restoreScore(savedGames.get(1))
        player1.restoreScore(savedGames.get(0))
    }
}
