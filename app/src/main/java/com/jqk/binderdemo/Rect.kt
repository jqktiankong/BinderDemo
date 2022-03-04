package com.jqk.binderdemo

import android.os.Parcelable
import android.os.Parcel

/**
 * Created by Administrator on 2018/5/28 0028.
 */
class Rect : Parcelable {
    var left = 0
    var top = 0
    var right = 0
    var bottom = 0
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(left)
        parcel.writeInt(top)
        parcel.writeInt(right)
        parcel.writeInt(bottom)
    }

    constructor(left: Int, top: Int, right: Int, bottom: Int) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    private constructor(`in`: Parcel) {
        readFromParcel(`in`)
    }

    fun readFromParcel(`in`: Parcel) {
        left = `in`.readInt()
        top = `in`.readInt()
        right = `in`.readInt()
        bottom = `in`.readInt()
    }

    override fun toString(): String {
        return "Rect{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                '}'
    }

    companion object CREATOR : Parcelable.Creator<Rect> {
        override fun createFromParcel(parcel: Parcel): Rect {
            return Rect(parcel)
        }

        override fun newArray(size: Int): Array<Rect?> {
            return arrayOfNulls(size)
        }
    }
}