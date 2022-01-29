package designpatterns.execution

import spock.lang.Specification

class VisitorTest extends Specification {
    def "Visitor Pattern works as expected"() {
        when:
        final Gargoyle gargoyle = new Gargoyle()
        final Basilisk basilisk = new Basilisk()
        final Visitor recoveryVisitor = new MonsterRecoveryVisitor()
        final Visitor screamVisitor = new MonsterScreamVisitor()

        then:
        float gargoyleAttack1 = gargoyle.attack()
        screamVisitor.visit(basilisk)
        basilisk.defend(gargoyleAttack1)
        recoveryVisitor.visit(basilisk)
        float basiliskAttack1 = basilisk.attack()
        screamVisitor.visit(gargoyle)
        gargoyle.defend(basiliskAttack1)
        recoveryVisitor.visit(gargoyle)
    }
}
