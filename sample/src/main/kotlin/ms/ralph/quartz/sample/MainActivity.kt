/*
 * Copyright 2016 Ralph
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("UNUSED_PARAMETER", "unused")

package ms.ralph.quartz.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick1(view: View) {
        val i = Sub1ActivityIntentBuilder.create(this).build()
        startActivity(i)
    }

    fun onClick2(view: View) {
        val i = Sub2ActivityIntentBuilder.create(this, "Hoge").build()
        startActivity(i)
    }

    fun onClick3(view: View) {
        val i = Sub3ActivityIntentBuilder.create(this, "Hoge").build()
        startActivity(i)
    }

    fun onClick4(view: View) {
        val i = Sub4ActivityIntentBuilder.create(this)
                .optional1("Hoge")
                .build()
        startActivity(i)
    }

    fun onClick5(view: View) {
        val i = Sub5ActivityIntentBuilder.create(this)
                .optional1("Hoge")
                .build()
        startActivity(i)
    }

    fun onClick6(view: View) {
        val i = Sub6ActivityIntentBuilder.create(this, "Hoge")
                .optional1("Huga")
                .build()
        startActivity(i)

    }

    fun onClick7(view: View) {
        val i = Sub7ActivityIntentBuilder.create(this, "Hoge")
                .optional1("Huga")
                .build()
        startActivity(i)
    }

    fun onClick8(view: View) {
        val i = Sub8ActivityIntentBuilder.create(this, "Hoge1", "Hoge2")
                .optional1("Huga")
                .build()
        startActivity(i)
    }

    fun onClick9(view: View) {
        val i = Sub9ActivityIntentBuilder.create(this, "Hoge1", "Hoge2")
                .optional1("Huga")
                .build()
        startActivity(i)
    }

    fun onClick10(view: View) {
        val i = Sub10ActivityIntentBuilder.create(this, "Hoge")
                .optional1("Huga")
                .build()
        startActivity(i)
    }

    fun onClick11(view: View) {
        val i = Sub11ActivityIntentBuilder.create(this, "Hoge")
                .optional1("Huga")
                .build()
        startActivity(i)
    }
}
