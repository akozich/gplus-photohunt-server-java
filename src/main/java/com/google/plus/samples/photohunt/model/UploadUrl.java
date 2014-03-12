/*
 * Copyright 2013 Google Inc. All Rights Reserved.
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

package com.google.plus.samples.photohunt.model;

import com.google.gson.annotations.Expose;

/**
 * Represents image upload URL response.
 */
public class UploadUrl extends Jsonifiable {

    /**
     * The constant kind.
     */
    @Expose
    public static String kind = "photohunt#uploadurl";

    /**
     * Instantiates a new Upload url.
     *
     * @param urlString the url string
     */
    public UploadUrl(String urlString) {
        this.url = urlString;
    }

    /**
     * URL ready to received an uploaded image.
     */
    @Expose
    private String url;

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}