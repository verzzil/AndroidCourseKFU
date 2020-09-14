package com.example.homework

import kotlin.random.Random

abstract class Unit(
    var name: String,
    var age: Int,
    var coordsX: Double,
    var coordsY: Double
    ): Jumpable {

    var health = 100.0
    var level = 1


    fun levelUp() {
        level++
        Barbarian.power *= Random.nextDouble(1.1,2.0)
    }

}