package com.gregspitz.cbbwhoslaughing.game.domain.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Immutable model class for a Laugher, which is just a person who has laughed
 */
@Entity
public final class Laugher {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entryid")
    private final String mId;

    @NonNull
    @ColumnInfo(name = "name")
    private final String mName;

    /**
     * Use this constructor to create a new Laugher
     *
     * @param name the name of the laugher
     */
    @Ignore
    public Laugher(@NonNull String name) {
        this(UUID.randomUUID().toString(), name);
    }

    /**
     * Use this constructor to specify a Laugher if it already has an id (copy of a Laugher)
     *
     * @param id the id of the Laugher
     * @param name the name of the laugher
     */
    public Laugher(@NonNull String id, @NonNull String name) {
        mId = id;
        mName = name;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }
}
