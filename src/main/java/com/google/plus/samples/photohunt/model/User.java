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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import static com.google.plus.samples.photohunt.model.OfyService.ofy;

/**
 * User of the PhotoHunt application.  This instance maintains all information
 * needed to interact with and manage a single user.  That includes things like
 * their tokens for social services or third-party APIs, their role within
 * PhotoHunt, their profile information, etc.
 * <p/>
 * Data members of this class are intentionally public in order to allow Gson
 * to function effectively when generating JSON representations of the class.
 *
 * @author vicfryzel @google.com (Vic Fryzel)
 */
@Entity
@Cache
public class User extends Jsonifiable {

    /**
     * The constant kind.
     */
    @Expose
    public static String kind = "photohunt#user";

    /**
     * Key key.
     *
     * @param id ID of User for which to get a Key.
     * @return Key representation of given User's ID.
     */
    public static Key<User> key(long id) {
        return Key.create(User.class, id);
    }

    /**
     * Primary identifier of this User.  Specific to PhotoHunt.
     */
    @Id
    @Expose
    private long id;

    /**
     * Primary email address of this User.
     */
    @Index
    @Expose
    private String email;

    /**
     * UUID identifier of this User within Google products.
     */
    @Index
    @Expose
    private String googleUserId;

    /**
     * Display name that this User has chosen for Google products.
     */
    @Index
    @Expose
    private String googleDisplayName;

    /**
     * Public Google+ profile URL for this User.
     */
    @Expose
    private String googlePublicProfileUrl;

    /**
     * Public Google+ profile image for this User.
     */
    @Expose
    private String googlePublicProfilePhotoUrl;

    /**
     * Access token used to access Google APIs on this User's behalf.
     */
    @Index
    private String googleAccessToken;

    /**
     * Refresh token used to refresh this User's googleAccessToken.
     */
    private String googleRefreshToken;

    /**
     * Validity of this User's googleAccessToken in seconds.
     */
    private long googleExpiresIn;

    /**
     * Expiration time in milliseconds since Epoch for this User's
     * googleAccessToken.
     * Exposed for mobile clients, to help determine if they should request a new
     * token.
     */
    @Expose
    private long googleExpiresAt;

    /**
     * Gets friend keys.
     *
     * @return List of Key<User> representing keys of friends.
     */
    public List<Key<User>> getFriendKeys() {
        List<DirectedUserToUserEdge> edges = ofy().load()
                .type(DirectedUserToUserEdge.class)
                .filter("ownerUserId", getId())
                .list();
        List<Key<User>> friendKeys = new ArrayList<Key<User>>();
        for (DirectedUserToUserEdge edge : edges) {
            friendKeys.add(key(edge.getFriendUserId()));
        }
        return friendKeys;
    }

    /**
     * Gets friend ids.
     *
     * @return List of longs representing IDs of friends.
     */
    public List<Long> getFriendIds() {
        List<DirectedUserToUserEdge> edges = ofy().load()
                .type(DirectedUserToUserEdge.class)
                .filter("ownerUserId", getId())
                .list();
        List<Long> friendIds = new ArrayList<Long>();
        for (DirectedUserToUserEdge edge : edges) {
            friendIds.add(edge.getFriendUserId());
        }
        return friendIds;
    }

    /**
     * Gets friends.
     *
     * @return Collection of Users that this User has listed as their friend(s).
     */
    public Collection<User> getFriends() {
        return ofy().load().keys(getFriendKeys()).values();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets google user id.
     *
     * @return the google user id
     */
    public String getGoogleUserId() {
        return googleUserId;
    }

    /**
     * Sets google user id.
     *
     * @param googleUserId the google user id
     */
    public void setGoogleUserId(String googleUserId) {
        this.googleUserId = googleUserId;
    }

    /**
     * Gets google display name.
     *
     * @return the google display name
     */
    public String getGoogleDisplayName() {
        return googleDisplayName;
    }

    /**
     * Sets google display name.
     *
     * @param googleDisplayName the google display name
     */
    public void setGoogleDisplayName(String googleDisplayName) {
        this.googleDisplayName = googleDisplayName;
    }

    /**
     * Gets google public profile url.
     *
     * @return the google public profile url
     */
    public String getGooglePublicProfileUrl() {
        return googlePublicProfileUrl;
    }

    /**
     * Sets google public profile url.
     *
     * @param googlePublicProfileUrl the google public profile url
     */
    public void setGooglePublicProfileUrl(String googlePublicProfileUrl) {
        this.googlePublicProfileUrl = googlePublicProfileUrl;
    }

    /**
     * Gets google public profile photo url.
     *
     * @return the google public profile photo url
     */
    public String getGooglePublicProfilePhotoUrl() {
        return googlePublicProfilePhotoUrl;
    }

    /**
     * Sets google public profile photo url.
     *
     * @param googlePublicProfilePhotoUrl the google public profile photo url
     */
    public void setGooglePublicProfilePhotoUrl(String googlePublicProfilePhotoUrl) {
        this.googlePublicProfilePhotoUrl = googlePublicProfilePhotoUrl;
    }

    /**
     * Gets google access token.
     *
     * @return the google access token
     */
    public String getGoogleAccessToken() {
        return googleAccessToken;
    }

    /**
     * Sets google access token.
     *
     * @param googleAccessToken the google access token
     */
    public void setGoogleAccessToken(String googleAccessToken) {
        this.googleAccessToken = googleAccessToken;
    }

    /**
     * Gets google refresh token.
     *
     * @return the google refresh token
     */
    public String getGoogleRefreshToken() {
        return googleRefreshToken;
    }

    /**
     * Sets google refresh token.
     *
     * @param googleRefreshToken the google refresh token
     */
    public void setGoogleRefreshToken(String googleRefreshToken) {
        this.googleRefreshToken = googleRefreshToken;
    }

    /**
     * Gets google expires in.
     *
     * @return the google expires in
     */
    public long getGoogleExpiresIn() {
        return googleExpiresIn;
    }

    /**
     * Sets google expires in.
     *
     * @param googleExpiresIn the google expires in
     */
    public void setGoogleExpiresIn(long googleExpiresIn) {
        this.googleExpiresIn = googleExpiresIn;
    }

    /**
     * Gets google expires at.
     *
     * @return the google expires at
     */
    public long getGoogleExpiresAt() {
        return googleExpiresAt;
    }

    /**
     * Sets google expires at.
     *
     * @param googleExpiresAt the google expires at
     */
    public void setGoogleExpiresAt(long googleExpiresAt) {
        this.googleExpiresAt = googleExpiresAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
