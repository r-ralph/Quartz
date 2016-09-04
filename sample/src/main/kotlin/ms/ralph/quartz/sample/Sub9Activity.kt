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

package ms.ralph.quartz.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

import ms.ralph.quartz.Optional
import ms.ralph.quartz.Quartz
import ms.ralph.quartz.Required

/**
 * Sample activity written with Kotlin
 * 2 Required and 2 Optional (Use only one)
 */
@Quartz
class Sub9Activity : AppCompatActivity() {

    @Required
    var required1: String? = null

    @Required
    var required2: String? = null

    @Optional
    var optional1: String? = null

    @Optional
    var optional2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        Sub9ActivityIntentBuilder.restore(this)

        setText()
    }

    @SuppressLint("SetTextI18n")
    private fun setText() {
        val textView = findViewById(R.id.textView) as TextView
        textView.text = "required1: $required1\n" +
                "required2: $required2\n" +
                "optional1: $optional1\n" +
                "optional2: $optional2"
    }
}
