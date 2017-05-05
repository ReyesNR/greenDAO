package de.greenrobot.dao;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by reyes.navarro on 25/04/2017.
 */

public abstract class DAOTrigger<T> {

    //FIXME Add deferrable constraints
    //FIXME Add update on column support
    //FIXME Add for statement support

    protected abstract boolean when(T entityOld,T entityNew);

    protected abstract boolean run(T entityOld, T entityNew);

    protected abstract DaoException raise();

    public final void executeTrigger(T entityOld, T entityNew,SQLiteDatabase db) throws DaoException{
        boolean triggerOK = true;
        if ( when(entityOld,entityNew)){
            triggerOK = run(entityOld,entityNew);
        }
        if ( !triggerOK){
            db.endTransaction();
            raise();
        }

    }
}
