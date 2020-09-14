package com.example.homework

class Barbarian(name: String,
                age: Int,
                coordsX: Double,
                coordsY: Double) :
    Unit(name, age, coordsX, coordsY), Attackable {

    companion object {
        var typeOfUnit = "Barbarian"
        var power = 2.1
    }

    override fun jump() {
        coordsX *= power * .2
        coordsY *= power * .85
    }

    override fun attack(anotherUnit: Unit) {
        anotherUnit.health -= power
    }

}