package io.github.lrzeszotarski.landminesapp

import spock.lang.Specification

class LandMinesImplTest extends Specification {

    def testedInstance = new LandMinesImpl(new LandCreatorImpl())

    def "NumClear should return 1"() {
        expect:
        testedInstance.numClear(["-M-", "---", "M--"] as String[]) == 1
    }

    def "NumClear should return 3"() {
        expect:
        testedInstance.numClear(["-M-", "-M-", "--M"] as String[]) == 3
    }

    def "NumClear should return 12"() {
        expect:
        testedInstance.numClear(["--M-", "-MM-", "----", "----"] as String[]) == 12
    }

    def "NumClear should return 21"() {
        expect:
        testedInstance.numClear(["-----" , "--M-M", "-----", "-M---", "---M-"] as String[]) == 21
    }
}
