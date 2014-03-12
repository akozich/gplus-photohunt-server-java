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
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Represents a single vote by a single User on a single Photo.
 *
 * @author vicfryzel @google.com (Vic Fryzel)
 */
@Entity
@Cache
public class Vote extends Jsonifiable {

    /**
     * The constant kind.
     */
    @Expose
    public static String kind = "photohunt#vote";

    /**
     * Key key.
     *
     * @param id ID of Vote for which to get a Key.
     * @return Key representation of given Vote's ID.
     */
    public static Key<Vote> key(long id) {
        return Key.create(Vote.class, id);
    }

    /**
     * Primary identifier of this Vote.
     */
    @Id
    @Expose
    private long id;

    /**
     * ID of User who owns this Vote.
     */
    @Index
    @Expose
    private long ownerUserId;

    /**
     * ID of the Photo to which this Vote was made.
     */
    @Index
    @Expose
    private long photoId;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets owner user id.
     *
     * @return the owner user id
     */
    public long getOwnerUserId() {
        return ownerUserId;
    }

    /**
     * Sets owner user id.
     *
     * @param ownerUserId the owner user id
     */
    public void setOwnerUserId(long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * Gets photo id.
     *
     * @return the photo id
     */
    public long getPhotoId() {
        return photoId;
    }

    /**
     * Sets photo id.
     *
     * @param photoId the photo id
     */
    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vote vote = (Vote) o;

        return id == vote.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
