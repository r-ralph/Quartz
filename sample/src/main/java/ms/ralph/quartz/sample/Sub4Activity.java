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

package ms.ralph.quartz.sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ms.ralph.quartz.Optional;
import ms.ralph.quartz.Quartz;

/**
 * Sample activity written with Java
 * 0 Required and 1 Optional
 */
@Quartz
public class Sub4Activity extends AppCompatActivity {

    @Optional
    protected String optional1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Sub4ActivityIntentBuilder.restore(this);

        setText();
    }

    @SuppressLint("SetTextI18n")
    private void setText() {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("optional1: " + optional1);
    }
}
