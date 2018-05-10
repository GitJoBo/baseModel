/*
 * Copyright (C) 2015 Square, Inc.
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
package com.kinbong.model.api;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.kinbong.model.bean.Result;
import com.kinbong.model.util.GsonUtil;
import com.kinbong.model.util.LogUtil;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T > {
  private final Gson gson;
  private Type mType;
//  private final TypeAdapter<T> adapter;

  GsonResponseBodyConverter(Gson gson, Type type) {
    this.gson = gson;
//    this.adapter = adapter;
    mType = type;
  }

  @Override public T convert(ResponseBody value) throws IOException {

      String string = value.string();
      LogUtil.i(string);
      try {
        return  gson.fromJson(string, mType);
      } finally {
        value.close();
      }


  }
}
