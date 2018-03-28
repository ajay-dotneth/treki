/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2016. 曾繁添 <zftlive@163.com>
 *     Github：https://github.com/zengfantian || http://git.oschina.net/zftlive
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.zftlive.android.library.tools.reflect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * APP崩溃监控异常处理类
 *
 * @author 曾繁添
 * @version 1.0
 */
public class ToolClass {

  static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

  static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];

  static boolean isSameLength(final Object[] array1, final Object[] array2) {
    if ((array1 == null && array2 != null && array2.length > 0) ||
            (array2 == null && array1 != null && array1.length > 0) ||
            (array1 != null && array2 != null && array1.length != array2.length)) {
      return false;
    }
    return true;
  }

  static Class<?>[] toClass(final Object... array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_CLASS_ARRAY;
    }
    final Class<?>[] classes = new Class[array.length];
    for (int i = 0; i < array.length; i++) {
      classes[i] = array[i] == null ? null : array[i].getClass();
    }
    return classes;
  }

  static Class<?>[] nullToEmpty(final Class<?>[] array) {
    if (array == null || array.length == 0) {
      return EMPTY_CLASS_ARRAY;
    }
    return array;
  }

  static Object[] nullToEmpty(final Object[] array) {
    if (array == null || array.length == 0) {
      return EMPTY_OBJECT_ARRAY;
    }
    return array;
  }

  public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
    if (cls == null) {
      return null;
    }
    final LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<Class<?>>();
    getAllInterfaces(cls, interfacesFound);
    return new ArrayList<Class<?>>(interfacesFound);
  }

  private static void getAllInterfaces(Class<?> cls, final HashSet<Class<?>> interfacesFound) {
    while (cls != null) {
      final Class<?>[] interfaces = cls.getInterfaces();

      for (final Class<?> i : interfaces) {
        if (interfacesFound.add(i)) {
          getAllInterfaces(i, interfacesFound);
        }
      }

      cls = cls.getSuperclass();
    }
  }
}
