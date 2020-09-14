package com.example.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tom = Barbarian("Tom", 31, 1.5,20.0)
        var jerry = Barbarian("Jerry", 30, 1.6,20.0)

        while(tom.health > 0 && jerry.health > 0) {
            val whoAttacked = Random.nextBoolean()
            val isLvlUp = Random.nextInt(0,20)

            if(whoAttacked) {
                tom.attack(jerry)
                Log.i("Game", "\nTom attacked... Tom: HP ${tom.health}, lvl ${tom.level}\n" +
                        "\tJerry: HP ${jerry.health}, lvl ${jerry.level}")
            }
            else {
                jerry.attack(tom)
                Log.i("Game", "\nJerry attacked... Tom: HP ${tom.health}, lvl ${tom.level}\n" +
                        "\tJerry: HP ${jerry.health}, lvl ${jerry.level}")
            }

            if(isLvlUp == 2 || isLvlUp == 5) tom.levelUp()
            else if (isLvlUp == 17 || isLvlUp == 19) jerry.levelUp()
        }

        if(tom.health <= 0) Log.i("Game", "Jerry is winner! Jerry HP: ${jerry.health}")
        else if(jerry.health <= 0) Log.i("Game", "Tom is winner! Tom HP: ${tom.health}")

    }
}