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

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Directed edge between two Users, used to construct a directed social graph.
 * Data to build these edges can be pulled from various social sources, like
 * the Google+ People feed, or your existing data set.
 *
 * @author vicfryzel @google.com (Vic Fryzel)
 */
@Entity
@Cache
public class DirectedUserToUserEdge {
    /**
     * Key key.
     *
     * @param id ID of a DirectedUserToUserEdge for which to create Key.
     * @return Key based on given ID.
     */
    public static Key<DirectedUserToUserEdge> key(long id) {
        return Key.create(DirectedUserToUserEdge.class, id);
    }

    /**
     * Primary identifier of this edge.
     */
    @Id
    private long id;

    /**
     * User who owns this relationship.  In Google+, this would be the user who
     * has added targetUserId to their circles.
     */
    @Index
    private long ownerUserId;

    /**
     * Friend of the relationship owner.  This person does not necessarily have
     * the owner in their own social graph.
     */
    @Index
    private long friendUserId;


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
     * Gets friend user id.
     *
     * @return the friend user id
     */
    public long getFriendUserId() {
        return friendUserId;
    }

    /**
     * Sets friend user id.
     *
     * @param friendUserId the friend user id
     */
    public void setFriendUserId(long friendUserId) {
        this.friendUserId = friendUserId;
    }
}
