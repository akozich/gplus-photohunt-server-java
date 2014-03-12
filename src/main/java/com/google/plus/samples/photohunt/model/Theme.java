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

import java.util.Calendar;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import static com.google.plus.samples.photohunt.model.OfyService.ofy;

/**
 * Represents a Theme for Photos.
 *
 * @author vicfryzel @google.com (Vic Fryzel)
 */
@Entity
@Cache
public class Theme extends Jsonifiable {

    /**
     * The constant kind.
     */
    @Expose
    public static String kind = "photohunt#theme";

    /**
     * Key key.
     *
     * @param id ID of Theme for which to get a Key.
     * @return Key representation of given Theme's ID.
     */
    public static Key<Theme> key(long id) {
        return Key.create(Theme.class, id);
    }

    /**
     * Primary identifier of this Theme.
     */
    @Id
    @Expose
    private long id;

    /**
     * Display name of this Theme.
     */
    @Expose
    private String displayName;

    /**
     * Date that this Theme was created.
     */
    @Index
    @Expose
    private Date created;

    /**
     * Date that this Theme should start.
     */
    @Index
    @Expose
    private Date start;

    /**
     * ID of Photo to display as a preview of this Theme.
     */
    @Expose
    private long previewPhotoId;

    /**
     * Gets current theme.
     *
     * @return Current Theme for PhotoHunt today.  A current theme is the theme
     * for the day.  There cannot be two themes on the same day.
     */
    public static Theme getCurrentTheme() {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        return ofy().load().type(Theme.class)
                .filter("start >", start.getTime())
                .filter("start <", end.getTime())
                .order("-start").first().get();
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
     * Gets display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets display name.
     *
     * @param displayName the display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
     * Gets start.
     *
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Gets preview photo id.
     *
     * @return the preview photo id
     */
    public long getPreviewPhotoId() {
        return previewPhotoId;
    }

    /**
     * Sets preview photo id.
     *
     * @param previewPhotoId the preview photo id
     */
    public void setPreviewPhotoId(long previewPhotoId) {
        this.previewPhotoId = previewPhotoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theme theme = (Theme) o;

        return id == theme.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
