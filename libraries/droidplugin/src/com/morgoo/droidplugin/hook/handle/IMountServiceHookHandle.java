/*
**        DroidPlugin Project
**
** Copyright(c) 2015 Andy Zhang <zhangyong232@gmail.com>
**
** This file is part of DroidPlugin.
**
** DroidPlugin is free software: you can redistribute it and/or
** modify it under the terms of the GNU Lesser General Public
** License as published by the Free Software Foundation, either
** version 3 of the License, or (at your option) any later version.
**
** DroidPlugin is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
** Lesser General Public License for more details.
**
** You should have received a copy of the GNU Lesser General Public
** License along with DroidPlugin.  If not, see <http://www.gnu.org/licenses/lgpl.txt>
**
**/

package com.morgoo.droidplugin.hook.handle;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;

import com.morgoo.droidplugin.hook.BaseHookHandle;
import com.morgoo.droidplugin.hook.HookedMethodHandler;

import java.lang.reflect.Method;

/**
 * Created by Andy Zhang(zhangyong232@gmail.com) on 2015/3/6.
 */
public class IMountServiceHookHandle extends BaseHookHandle {
    public IMountServiceHookHandle(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        sHookedMethodHandlers.put("mkdirs", new mkdirs(mHostContext));
    }

    private class mkdirs extends HookedMethodHandler {
        public mkdirs(Context context) {
            super(context);
        }

        @Override
        protected boolean beforeInvoke(Object receiver, Method method, Object[] args) throws Throwable {
            if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                final int index = 0;
                if (args != null && args.length > index && args[index] instanceof String) {
                    String callingPkg = (String) args[index];
                    if (!TextUtils.equals(callingPkg, mHostContext.getPackageName())) {
                        args[index] = mHostContext.getPackageName();
                    }
                }

                //FIXME ??????????????????????????????????????????????????????????????????????????????????????????
                final int index1 = 1;
                if (args != null && args.length > index1 && args[index1] instanceof String) {
                    String path = (String) args[index1];
//                    String path1 = new File(Environment.getExternalStorageDirectory(), "Android/data/").getPath();
                    final boolean isAndroiDataHostPath = path.indexOf("Android/data/" + mHostContext.getPackageName()) < 0;
                    if (path != null && isAndroiDataHostPath) {
                        path = path.replaceFirst("Android/data/", "Android/data/" + mHostContext.getPackageName() + "/Plugin/");
                        args[index1] = path;
                    } else {
                        final boolean isAndroiObbHostPath = path.indexOf("Android/obb/" + mHostContext.getPackageName()) < 0;
                        if (path != null && isAndroiObbHostPath) {
                            path = path.replaceFirst("Android/obb/", "Android/obb/" + mHostContext.getPackageName() + "/Plugin/");
                            args[index1] = path;
                        }
                    }
                }
            } else {
                //FIXME ??????????????????????????????????????????????????????????????????????????????????????????
                final int index1 = 0;
                if (args != null && args.length > index1 && args[index1] instanceof String) {
                    String path = (String) args[index1];
//                    String path1 = new File(Environment.getExternalStorageDirectory(), "Android/data/").getPath();
                    final boolean isHostPath = path.indexOf("Android/data/" + mHostContext.getPackageName()) < 0;
                    if (path != null && isHostPath) {
                        path = path.replaceFirst("Android/data/", "Android/data/" + mHostContext.getPackageName() + "/Plugin/");
                        args[index1] = path;
                    } else {
                        final boolean isAndroiObbHostPath = path.indexOf("Android/obb/" + mHostContext.getPackageName()) < 0;
                        if (path != null && isAndroiObbHostPath) {
                            path = path.replaceFirst("Android/obb/", "Android/obb/" + mHostContext.getPackageName() + "/Plugin/");
                            args[index1] = path;
                        }
                    }
                }
            }

            return super.beforeInvoke(receiver, method, args);
        }
    }
}
