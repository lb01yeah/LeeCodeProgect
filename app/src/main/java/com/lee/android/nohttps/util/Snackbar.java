/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lee.android.nohttps.util;

import android.app.Activity;
import android.support.annotation.StringRes;

/**
 * Created in Jan 31, 2016 4:15:36 PM.
 */
public class Snackbar {

    public static void show(Activity context, CharSequence msg) {
        android.support.design.widget.Snackbar.make(context.getWindow().getDecorView(), msg, android.support.design.widget.Snackbar.LENGTH_LONG).show();
    }

    public static void show(Activity context, @StringRes int stringId) {
        android.support.design.widget.Snackbar.make(context.getWindow().getDecorView(), stringId, android.support.design.widget.Snackbar.LENGTH_LONG).show();
    }

}
