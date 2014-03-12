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

import java.util.Date;
import java.util.List;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.gson.annotations.Expose;
import com.google.plus.samples.photohunt.PhotosServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnLoad;

import static com.google.plus.samples.photohunt.model.OfyService.ofy;

/**
 * Represents a User's Photo in PhotoHunt.  Contains all of the properties that
 * allow the Photo to be rendered and managed.
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 */
@Entity
@Cache
public class Photo extends Jsonifiable {
    @Expose
    public static String kind = "photohunt#photo";

    /**
     * ImagesService to use for image operation execution.
     */
    protected static ImagesService images =
            ImagesServiceFactory.getImagesService();

    /**
     * @param id ID of Photo for which to get a Key.
     * @return Key representation of given Photo's ID.
     */
    public static Key<Photo> key(long id) {
        return Key.create(Photo.class, id);
    }

    /**
     * Primary identifier of this Photo.
     */
    @Id
    @Expose
    private long id;

    /**
     * ID of the User who owns this Photo.
     */
    @Index
    @Expose
    private long ownerUserId;

    /**
     * Display name of the User who owns this Photo.
     */
    @Expose
    private String ownerDisplayName;

    /**
     * Profile URL of the User who owns this Photo.
     */
    @Expose
    private String ownerProfileUrl;

    /**
     * Profile photo of the User who owns this Photo.
     */
    @Expose
    private String ownerProfilePhoto;

    /**
     * ID of the Theme to which this Photo belongs.
     */
    @Index
    @Expose
    private long themeId;

    /**
     * Display name of the Theme to which this Photo belongs.
     */
    @Index
    @Expose
    private String themeDisplayName;

    /**
     * Number of votes this Photo has received.
     */
    @Index
    @Expose
    @Ignore
    private int numVotes;

    /**
     * True if the current user has already voted this Photo.
     */
    @Expose
    private boolean voted;

    /**
     * Image blob key for this Photo.
     */
    private String imageBlobKey;

    /**
     * Date this Photo was uploaded to PhotoHunt.
     */
    @Expose
    private Date created;

    /**
     * URL for full-size image of this Photo.
     */
    @Expose
    @Ignore
    private String fullsizeUrl;

    /**
     * URL for thumbnail image of this Photo.
     */
    @Expose
    @Ignore
    private String thumbnailUrl;

    /**
     * URL for vote call to action on this photo.
     */
    @Expose
    @Ignore
    private String voteCtaUrl;

    /**
     * URL for interactive posts and deep linking to this photo.
     */
    @Expose
    @Ignore
    private String photoContentUrl;

    /**
     * Default size of thumbnails.
     */
    @Expose
    @Ignore
    public static final int DEFAULT_THUMBNAIL_SIZE = 400;

    /**
     * Setup image URLs (fullsizeUrl and thumbnailUrl) after this Photo has been
     * loaded.
     */
    @OnLoad
    protected void setupImageUrls() {
        fullsizeUrl = getImageUrl();
        thumbnailUrl = getImageUrl(DEFAULT_THUMBNAIL_SIZE);
    }

    /**
     * @return URL for full-size image of this photo.
     */
    public String getImageUrl() {
        return getImageUrl(-1);
    }

    /**
     * @param size Size of image for URL to return.
     * @return URL for images for this Photo of given size.
     */
    public String getImageUrl(int size) {
        ServingUrlOptions options = ServingUrlOptions.Builder
                .withBlobKey(new BlobKey(imageBlobKey))
                .secureUrl(true);
        if (size > -1) {
            options.imageSize(size);
        }
        return images.getServingUrl(options);
    }

    /**
     * Setup voteNum after this Photo has been loaded.
     */
    @OnLoad
    protected void setupVoteNum() {
        List<Vote> votes = ofy().load().type(Vote.class)
                .filter("photoId", id)
                .list();
        numVotes = votes.size();
    }

    /**
     * Setup voteCtaUrl after this Photo has been loaded.
     */
    @OnLoad
    protected void setupVoteCtaUrl() {
        voteCtaUrl = PhotosServlet.BASE_URL + "/index.html?photoId=" + id +
                "&action=VOTE";
    }

    /**
     * Setup photoContentUrl after this Photo has been loaded.
     */
    @OnLoad
    protected void photoDeepLinkUrl() {
        photoContentUrl = PhotosServlet.BASE_URL + "/photo.html?photoId=" + id;
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
     * Gets owner display name.
     *
     * @return the owner display name
     */
    public String getOwnerDisplayName() {
        return ownerDisplayName;
    }

    /**
     * Sets owner display name.
     *
     * @param ownerDisplayName the owner display name
     */
    public void setOwnerDisplayName(String ownerDisplayName) {
        this.ownerDisplayName = ownerDisplayName;
    }

    /**
     * Gets owner profile url.
     *
     * @return the owner profile url
     */
    public String getOwnerProfileUrl() {
        return ownerProfileUrl;
    }

    /**
     * Sets owner profile url.
     *
     * @param ownerProfileUrl the owner profile url
     */
    public void setOwnerProfileUrl(String ownerProfileUrl) {
        this.ownerProfileUrl = ownerProfileUrl;
    }

    /**
     * Gets owner profile photo.
     *
     * @return the owner profile photo
     */
    public String getOwnerProfilePhoto() {
        return ownerProfilePhoto;
    }

    /**
     * Sets owner profile photo.
     *
     * @param ownerProfilePhoto the owner profile photo
     */
    public void setOwnerProfilePhoto(String ownerProfilePhoto) {
        this.ownerProfilePhoto = ownerProfilePhoto;
    }

    /**
     * Gets theme id.
     *
     * @return the theme id
     */
    public long getThemeId() {
        return themeId;
    }

    /**
     * Sets theme id.
     *
     * @param themeId the theme id
     */
    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    /**
     * Gets theme display name.
     *
     * @return the theme display name
     */
    public String getThemeDisplayName() {
        return themeDisplayName;
    }

    /**
     * Sets theme display name.
     *
     * @param themeDisplayName the theme display name
     */
    public void setThemeDisplayName(String themeDisplayName) {
        this.themeDisplayName = themeDisplayName;
    }

    /**
     * Gets num votes.
     *
     * @return the num votes
     */
    public int getNumVotes() {
        return numVotes;
    }

    /**
     * Sets num votes.
     *
     * @param numVotes the num votes
     */
    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }

    /**
     * Is voted.
     *
     * @return the boolean
     */
    public boolean isVoted() {
        return voted;
    }

    /**
     * Sets voted.
     *
     * @param voted the voted
     */
    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    /**
     * Gets image blob key.
     *
     * @return the image blob key
     */
    public String getImageBlobKey() {
        return imageBlobKey;
    }

    /**
     * Sets image blob key.
     *
     * @param imageBlobKey the image blob key
     */
    public void setImageBlobKey(String imageBlobKey) {
        this.imageBlobKey = imageBlobKey;
    }

    /**
     * Gets created.
     *
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * Gets thumbnail url.
     *
     * @return the thumbnail url
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * Gets vote cta url.
     *
     * @return the vote cta url
     */
    public String getVoteCtaUrl() {
        return voteCtaUrl;
    }

    /**
     * Gets photo content url.
     *
     * @return the photo content url
     */
    public String getPhotoContentUrl() {
        return photoContentUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        return id == photo.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
